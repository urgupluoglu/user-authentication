package com.badboyz.general;

import com.badboyz.Utility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.Serializable;

/**
 * Created by yusuf on 4/26/2016.
 */
@Controller
public class ReturnObject implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final long serialVersionUID = -8682352246321449123L;

    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseEntity returnJson(HttpStatus status) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return ResponseEntity.status(status).body(objectMapper.writeValueAsString(this));
        } catch (Exception e) {
            LOGGER.catching(e);

            ReturnObject ro = new ReturnObject();
            ro.setCode(Utility.SERIALIZATION_PROBLEM_CODE);
            ro.setMessage(Utility.SERIALIZATION_PROBLEM_MSG);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }
    }
}

