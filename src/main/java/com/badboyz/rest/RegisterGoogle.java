package com.badboyz.rest;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.entity.request.Token;
import com.badboyz.enumeration.RegistrationType;
import com.badboyz.general.ReturnObject;
import com.badboyz.repository.UserRepo;
import com.badboyz.service.EmailService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


/**
 * Created by yusuf on 5/9/2016.
 */

@RestController
public class RegisterGoogle {
    // TODO: update db for OAuth registrations !!
    private Logger LOGGER = LogManager.getLogger(Register.class);
    private ReturnObject ro = new ReturnObject();
    private static final String CLIENT_ID = "1063990469955-5gjpvd8vr8855cee98b2kcdjcq11n36m.apps.googleusercontent.com";

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/registerGoogle", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity register(@RequestHeader Token token) {

        HttpTransport transport = new ApacheHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(token.getToken());
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            // String name = (String) payload.get("name");
            // String pictureUrl = (String) payload.get("picture");
            // String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            User user = new User();
            user.setEmail(email);
            user.setLocked(emailVerified);
            user.setFirstName(givenName);
            user.setLastName(familyName);
            user.setPassword(userId);
            user.setRegistrationType(RegistrationType.GOOGLE);

            userRepo.save(user);
            LOGGER.debug(user);

            // TODO: send customized email for google registrations
            emailService.sendEmail(user.getEmail(), Utility.EMAIL_SENDER, Utility.SIGN_UP_EMAIL_SUBJECT,
                    Utility.SIGN_UP_EMAIL_BODY + user.getUserDetail().getHashCode() + "/");

            ro.setCode(Utility.SUCCESS_CODE);
            ro.setMessage(Utility.SUCCESS_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.OK);

        } else {
            ro.setCode(Utility.REGISTRATION_FAILED_CODE);
            ro.setMessage(Utility.REGISTRATION_FAILED_MSG);
            LOGGER.debug(ro);
            return ro.returnJson(HttpStatus.BAD_REQUEST);
        }


    }
}
