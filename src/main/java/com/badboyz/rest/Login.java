package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.general.HashOperation;
import com.badboyz.general.ReturnObject;
import com.badboyz.general.Tokenizer;
import com.badboyz.repository.UserRepo;
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
public class Login {

    private Logger LOGGER = LogManager.getLogger(Login.class);
    private ReturnObject ro = new ReturnObject();

    @Autowired
    UserRepo userRepo;

    @Autowired
    Tokenizer tokenizer;

    @RequestMapping(value="/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity login(@RequestBody User aUser) {

        if(!EmailValidator.getInstance().isValid(aUser.getEmail())) {
            ro.setCode(Utility.INVALID_EMAIL_CODE);
            ro.setMessage(Utility.INVALID_EMAIL_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        if(GenericValidator.isBlankOrNull(aUser.getPassword())
                || aUser.getPassword().length() < Utility.MIN_PASSWORD_LENGTH) {

            ro.setCode(Utility.INSUFFICIENT_PASSWORD_CODE);
            ro.setMessage(Utility.INSUFFICIENT_PASSWORD_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.getByEmailAndActiveIsTrue(aUser.getEmail());
        HashOperation op = new HashOperation();
        if(user != null &&
                user.getPassword().equals(op.passwordHashing(aUser.getEmail(), aUser.getPassword()))) {

            ro.setData(tokenizer.generateToken(user));
            ro.setCode(Utility.SUCCESS_CODE);
            ro.setMessage(Utility.SUCCESS_MSG);

        } else {
            ro.setCode(Utility.LOGIN_UNSUCCESSFUL_CODE);
            ro.setMessage(Utility.LOGIN_UNSUCCESSFUL_MSG);
        }
        LOGGER.debug(ro);
        return ro.returnJson(HttpStatus.OK);
    }
}
