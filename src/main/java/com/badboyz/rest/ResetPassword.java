package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.entity.request.PasswordSetter;
import com.badboyz.general.HashOperation;
import com.badboyz.general.ReturnObject;
import com.badboyz.repository.UserRepo;
import com.badboyz.service.EmailService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * Created by yusuf on 5/9/2016.
 */

@RestController
public class ResetPassword {

    private Logger LOGGER = LogManager.getLogger(ResetPassword.class);
    private ReturnObject ro = new ReturnObject();
    private static final int hashLength = 64;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

    @RequestMapping(value="/resetPassword", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity unlock(@RequestBody PasswordSetter passwordSetter) {

        if(passwordSetter.getHash() == null || passwordSetter.getHash().length() != hashLength) {

            ro.setCode(Utility.LINK_EXPIRED_CODE);
            ro.setMessage(Utility.LINK_EXPIRED_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.getByUserDetailHashCode(passwordSetter.getHash());
        Date now = new Date();
        if(now.after(user.getUserDetail().getHashCodeExpiration())) {
            ro.setCode(Utility.LINK_EXPIRED_CODE);
            ro.setMessage(Utility.LINK_EXPIRED_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        HashOperation op = new HashOperation();
        user.setPassword(op.passwordHashing(user.getEmail(), user.getPassword()));
        user.setLocked(false);
        user.getUserDetail().setHashCodeExpiration(null);
        user.getUserDetail().setHashCode(null);
        userRepo.save(user);

        // sending email..
        emailService.sendEmail(user.getEmail(), Utility.EMAIL_SENDER, Utility.PASSWORD_RESET_EMAIL_SUBJECT,
                Utility.PASSWORD_RESET_EMAIL_BODY + Utility.maskPassword(passwordSetter.getPassword()));

        ro.setCode(Utility.SUCCESS_CODE);
        ro.setMessage(Utility.SUCCESS_MSG);

        LOGGER.debug(ro);
        return ro.returnJson(HttpStatus.OK);
    }
}
