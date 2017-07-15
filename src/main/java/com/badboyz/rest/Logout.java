package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.general.ReturnObject;
import com.badboyz.general.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yusuf on 5/9/2016.
 */

@RestController
public class Logout {

    @Autowired
    ReturnObject ro;

    @Autowired
    Tokenizer tokenizer;

    @RequestMapping(value="/logout", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity logout(String token) {

        User user = tokenizer.getUserByToken(token);
        // TODO: check if the user is not null
        tokenizer.removeUserByToken(token);

        ro.setCode(Utility.SUCCESS_CODE);
        ro.setMessage(Utility.SUCCESS_MSG);
        return ro.returnJson(HttpStatus.OK);
    }
}
