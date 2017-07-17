package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.general.ReturnObject;
import com.badboyz.general.Tokenizer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yusuf on 7/17/2017.
 */

@RestController
public class Homepage {

    private Logger LOGGER = LogManager.getLogger(Homepage.class);
    private ReturnObject ro = new ReturnObject();

    @Autowired
    Tokenizer tokenizer;

    @RequestMapping("/homepage")
    public ResponseEntity homepage(@RequestHeader String token) {

        if(tokenizer.getUserByToken(token) != null) {
            ro.setCode(Utility.SUCCESS_CODE);
            ro.setMessage(Utility.SUCCESS_MSG);
        } else {
            ro.setCode(Utility.INVALID_CREDENTIALS_CODE);
            ro.setMessage(Utility.INVALID_CREDENTIALS_MSG);
        }
        LOGGER.debug(ro);
        return ro.returnJson(HttpStatus.OK);
    }
}
