package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.general.HashOperation;
import com.badboyz.general.ReturnObject;
import com.badboyz.repository.UserRepo;
import com.badboyz.service.EmailService;
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

import java.util.Calendar;

/**
 * Created by yusuf on 7/19/2017.
 */

@RestController
public class ForgotPassword {

    private Logger LOGGER = LogManager.getLogger(ForgotPassword.class);
    private ReturnObject ro = new ReturnObject();
    private static final int EMAIL_INTERVAL_MINUTE = 5;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity register(@RequestBody String email) {

        if (!EmailValidator.getInstance().isValid(email)) {
            ro.setCode(Utility.INVALID_EMAIL_CODE);
            ro.setMessage(Utility.INVALID_EMAIL_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.getByEmailAndActiveIsTrue(email);
        if (user == null) {
            ro.setCode(Utility.NO_ACTIVE_USER_CODE);
            ro.setMessage(Utility.NO_ACTIVE_USER_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, EMAIL_INTERVAL_MINUTE);
        if (user.getUserDetail() != null &&
                (user.getUserDetail().getHashCodeExpiration() == null || user.getUserDetail().getHashCodeExpiration().after(cal.getTime()))) {

            HashOperation op = new HashOperation();
            user.setHashCode(op);
            emailService.sendEmail(user.getEmail(), Utility.EMAIL_SENDER, Utility.FORGOT_PASSWORD_EMAIL_SUBJECT,
                    Utility.FORGOT_PASSWORD_EMAIL_BODY + user.getUserDetail().getHashCode() + "/");

            ro.setCode(Utility.SUCCESS_CODE);
            ro.setMessage(Utility.SUCCESS_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.OK);

        } else {
            ro.setCode(Utility.FORGOT_PASSWORD_WAIT_CODE);
            ro.setMessage(Utility.FORGOT_PASSWORD_WAIT_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }
    }
}
