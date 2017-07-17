package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.general.ReturnObject;
import com.badboyz.general.Tokenizer;
import com.badboyz.repository.UserRepo;
import com.badboyz.service.EmailService;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * Created by yusuf on 5/9/2016.
 */

@RestController
public class Activate {

    private Logger LOGGER = LogManager.getLogger(Activate.class);
    private ReturnObject ro = new ReturnObject();
    private static final int hashLength = 64;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    Tokenizer tokenizer;

    // This service is for administrators only!!
    @RequestMapping(value="/activate", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity activate(@RequestHeader String token, @RequestHeader String hash) {

        User admin = tokenizer.getUserByToken(token);
        if(admin == null || !admin.isAdmin()) {
            ro.setCode(Utility.INVALID_CREDENTIALS_CODE);
            ro.setMessage(Utility.INVALID_CREDENTIALS_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        if(GenericValidator.isBlankOrNull(hash) || hash.length() != hashLength) {
            ro.setCode(Utility.LINK_EXPIRED_CODE);
            ro.setMessage(Utility.LINK_EXPIRED_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.getByUserDetailHashCode(hash);
        if(user.getUserDetail().getHashCodeExpiration().before(new Date())) {
            ro.setCode(Utility.LINK_EXPIRED_CODE);
            ro.setMessage(Utility.LINK_EXPIRED_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        user.setActive(true);
        user.getUserDetail().setHashCodeExpiration(null);
        user.getUserDetail().setHashCode(null);
        userRepo.save(user);

        // sending email..
        emailService.sendEmail(user.getEmail(), Utility.EMAIL_SENDER, Utility.ACTIVATE_EMAIL_SUBJECT,
                Utility.ACTIVATE_EMAIL_BODY);

        ro.setCode(Utility.SUCCESS_CODE);
        ro.setMessage(Utility.SUCCESS_MSG);

        LOGGER.debug(ro);
        return ro.returnJson(HttpStatus.OK);
    }
}
