package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.general.ReturnObject;
import com.badboyz.repository.UserRepo;
import com.badboyz.service.EmailService;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.EmailValidator;
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
public class Signup {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ReturnObject ro;

    @Autowired
    EmailService es;

    @RequestMapping(value="/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity signUp(@RequestBody User user) {

        if(!EmailValidator.getInstance().isValid(user.getEmail())) {
            ro.setCode(Utility.INVALID_EMAIL_CODE);
            ro.setMessage(Utility.INVALID_EMAIL_MSG);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        if(GenericValidator.isBlankOrNull(user.getFirstName())
              /*|| GenericValidator.isBlankOrNull(user.getMiddleName())*/
                || GenericValidator.isBlankOrNull(user.getLastName())) {

            ro.setCode(Utility.INVALID_NAME_CODE);
            ro.setMessage(Utility.INVALID_NAME_MSG);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        if(GenericValidator.isBlankOrNull(user.getPassword())
                || user.getPassword().length() < Utility.MIN_PASSWORD_LENGTH) {

            ro.setCode(Utility.INSUFFICIENT_PASSWORD_CODE);
            ro.setMessage(Utility.INSUFFICIENT_PASSWORD_MSG);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        // Default: all users start locked
        user.setLocked(true);


        userRepo.save(user);
        // send email here..
        es.sendEmail(user.getEmail(), Utility.SIGN_UP_EMAIL_SENDER, Utility.SIGN_UP_EMAIL_SUBJECT, Utility.SIGN_UP_EMAIL_BODY);

        ro.setCode(Utility.SUCCESS_CODE);
        ro.setMessage(Utility.SUCCESS_MSG);
        return ro.returnJson(HttpStatus.OK);
    }
}
