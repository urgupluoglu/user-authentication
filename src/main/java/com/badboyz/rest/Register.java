package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.general.HashOperation;
import com.badboyz.general.ReturnObject;
import com.badboyz.repository.UserRepo;
import com.badboyz.service.EmailService;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by yusuf on 5/9/2016.
 */

@RestController
public class Register {

    private Logger LOGGER = LogManager.getLogger(Register.class);
    private ReturnObject ro = new ReturnObject();

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

    @RequestMapping(value="/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity register(@RequestBody User user) {

        if(!EmailValidator.getInstance().isValid(user.getEmail())) {
            ro.setCode(Utility.INVALID_EMAIL_CODE);
            ro.setMessage(Utility.INVALID_EMAIL_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        if(GenericValidator.isBlankOrNull(user.getFirstName())
              /*|| GenericValidator.isBlankOrNull(user.getMiddleName())*/
                || GenericValidator.isBlankOrNull(user.getLastName())) {

            ro.setCode(Utility.INVALID_NAME_CODE);
            ro.setMessage(Utility.INVALID_NAME_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        if(GenericValidator.isBlankOrNull(user.getPassword())
                || user.getPassword().length() < Utility.MIN_PASSWORD_LENGTH) {

            ro.setCode(Utility.INSUFFICIENT_PASSWORD_CODE);
            ro.setMessage(Utility.INSUFFICIENT_PASSWORD_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        } else {
            HashOperation op = new HashOperation();
            user.setPassword(op.passwordHashing(user.getEmail(), user.getPassword()));

            // Default: all users start locked
            user.setLocked(true);
            // Set hash code and hash expire date
            user.setHashCode(op);
        }

        userRepo.save(user);
        LOGGER.debug(user);

        // sending email..
        emailService.sendEmail(user.getEmail(), Utility.EMAIL_SENDER, Utility.SIGN_UP_EMAIL_SUBJECT,
                Utility.SIGN_UP_EMAIL_BODY + user.getUserDetail().getHashCode() + "/");

        ro.setCode(Utility.SUCCESS_CODE);
        ro.setMessage(Utility.SUCCESS_MSG);
        LOGGER.debug(ro);
        return ro.returnJson(HttpStatus.OK);
    }
}
