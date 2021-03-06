package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.entity.request.Token;
import com.badboyz.general.ReturnObject;
import com.badboyz.general.Tokenizer;
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
public class Logout {

    private Logger LOGGER = LogManager.getLogger(Logout.class);
    private ReturnObject ro = new ReturnObject();

    @Autowired
    Tokenizer tokenizer;

    @RequestMapping(value="/logout", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity logout(@RequestBody Token token) {

        User user = tokenizer.getUserByToken(token.getToken());
        if(user != null && tokenizer.removeUserByToken(token.getToken())) {
            ro.setCode(Utility.SUCCESS_CODE);
            ro.setMessage(Utility.SUCCESS_MSG);
        } else {
            ro.setCode(Utility.USER_NOT_EXIST_CODE);
            ro.setMessage(Utility.USER_NOT_EXIST_MSG);
        }
        LOGGER.debug(ro);
        return ro.returnJson(HttpStatus.OK);
    }
}
