package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.general.ReturnObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yusuf on 4/26/2016.
 */

@RestController
public class Welcome {

    private Logger LOGGER = LogManager.getLogger(Welcome.class);
    private ReturnObject ro = new ReturnObject();

    @RequestMapping("/")
    public ResponseEntity welcome() {

        ro.setCode(Utility.SUCCESS_CODE);
        ro.setMessage(Utility.SUCCESS_MSG);
        LOGGER.debug(ro);
        return ro.returnJson(HttpStatus.OK);
    }
}
