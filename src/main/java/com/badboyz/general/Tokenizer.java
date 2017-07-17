package com.badboyz.general;

import com.badboyz.Utility;
import com.badboyz.entity.User;
import com.badboyz.repository.UserRepo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yusuf on 5/15/16.
 */

@Service
public class Tokenizer {

    private Logger LOGGER = LogManager.getLogger(Tokenizer.class);
    private static ConcurrentHashMap<String, JWT> bucket = new ConcurrentHashMap<>();

    @Autowired
    UserRepo userRepo;

    public synchronized User getUserByToken(String token) {

        if(token == null)
            return null;

        JWT jwt = bucket.get(token);
        if(jwt == null)
            return null;

        return userRepo.findOne(jwt.getUserId());
    }

    public synchronized String generateToken(User user) {
        SecureRandom random = new SecureRandom();
        String token = (new BigInteger(130, random).toString(32) + new BigInteger(130, random).toString(32)).toUpperCase();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, Utility.TOKEN_EXPIRATION_IN_MINUTES); // adds minutes
        JWT jwt = new JWT();
        jwt.setCreate(new Date());
        jwt.setExpire(cal.getTime());
        jwt.setUserId(user.getId());
        bucket.keySet()
                .stream()
                .filter(item -> bucket.get(item).getUserId() == user.getId())
                .forEach(bucket::remove);
        bucket.put(token, jwt);

        return token;
    }

    public synchronized boolean removeUserByToken(String token) {
        if (bucket.containsKey(token)) {
            bucket.remove(token);
            return true;
        }
        return false;
    }
}
