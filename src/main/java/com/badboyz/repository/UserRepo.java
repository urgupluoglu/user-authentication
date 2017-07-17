package com.badboyz.repository;

import com.badboyz.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yusuf on 7/16/2017.
 */
public interface UserRepo extends CrudRepository<User, Long> {

    User getByEmailAndActiveIsTrue(String email);

    User getByUserDetailHashCode(String hash);
}
