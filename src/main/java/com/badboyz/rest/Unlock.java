package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.general.ReturnObject;
import com.badboyz.repository.UserRepo;
import com.badboyz.service.EmailService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * Created by yusuf on 5/9/2016.
 */

@RestController
public class Unlock {

    private Logger LOGGER = LogManager.getLogger(Unlock.class);
    private ReturnObject ro = new ReturnObject();
    private static final int hashLength = 64;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

    @RequestMapping(value="/unlock/{hash}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity unlock(@PathVariable("hash") String hash) {

        if(hash == null || hash.length() != hashLength) {

            ro.setCode(Utility.LINK_EXPIRED_CODE);
            ro.setMessage(Utility.LINK_EXPIRED_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.getByUserDetailHashCode(hash);
        Date now = new Date();
        if(now.after(user.getUserDetail().getHashCodeExpiration())) {
            ro.setCode(Utility.LINK_EXPIRED_CODE);
            ro.setMessage(Utility.LINK_EXPIRED_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        user.setLocked(false);
        user.getUserDetail().setHashCodeExpiration(null);
        user.getUserDetail().setHashCode(null);
        userRepo.save(user);

        // sending email..
        emailService.sendEmail(user.getEmail(), Utility.EMAIL_SENDER, Utility.UNLOCK_EMAIL_SUBJECT,
                Utility.UNLOCK_EMAIL_BODY);

        ro.setCode(Utility.SUCCESS_CODE);
        ro.setMessage(Utility.SUCCESS_MSG);

        LOGGER.debug(ro);
        return ro.returnJson(HttpStatus.OK);
    }
}
