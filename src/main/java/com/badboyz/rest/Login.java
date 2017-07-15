package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.general.ReturnObject;
import com.badboyz.general.Tokenizer;
import com.badboyz.repository.UserRepo;
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
public class Login {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ReturnObject ro;

    @Autowired
    Tokenizer tokenizer;

    @RequestMapping(value="/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity login(@RequestBody User aUser) {

        if(!EmailValidator.getInstance().isValid(aUser.getEmail())) {
            ro.setCode(Utility.INVALID_EMAIL_CODE);
            ro.setMessage(Utility.INVALID_EMAIL_MSG);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        if(GenericValidator.isBlankOrNull(aUser.getPassword())
                || aUser.getPassword().length() < Utility.MIN_PASSWORD_LENGTH) {

            ro.setCode(Utility.INSUFFICIENT_PASSWORD_CODE);
            ro.setMessage(Utility.INSUFFICIENT_PASSWORD_MSG);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.getByEmailAndActiveIsTrue(aUser.getEmail());
        // TODO: user not null check
        ro.setData(tokenizer.generateToken(user));

        ro.setCode(Utility.SUCCESS_CODE);
        ro.setMessage(Utility.SUCCESS_MSG);
        return ro.returnJson(HttpStatus.OK);
    }
}
