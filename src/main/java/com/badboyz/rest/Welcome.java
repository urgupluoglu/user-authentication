package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.general.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yusuf on 4/26/2016.
 */

@RestController
public class Welcome {

    @Autowired
    ReturnObject ro;

    @RequestMapping("/")
    public ResponseEntity welcome() {

        ro.setCode(Utility.SUCCESS_CODE);
        ro.setMessage(Utility.SUCCESS_MSG);
        return ro.returnJson(HttpStatus.OK);
    }
}
