package com.badboyz.entity;

import com.badboyz.general.ResponseObjects;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yusuf on 4/26/2016.
 */
@Entity
@Table(name = "`user`")
public class User implements Serializable {

    @JsonView(ResponseObjects.User.class)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private long id;

    @JsonView(ResponseObjects.User.class)
    @Column
    private String firstName;

    @JsonView(ResponseObjects.User.class)
    @Column
    private String middleName;

    @JsonView(ResponseObjects.User.class)
    @Column
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private boolean active;

    @Column
    private boolean locked;

    @Column
    private boolean isAdmin;

    @OneToOne
    @JoinColumn(name = "SUPERUSERID", referencedColumnName = "ID")
    private User superUser;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDetail userDetail = new UserDetail();

    public long getId() {
        return id;
    }

    public void setId(long userId) {
        this.id = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public User getSuperUser() {
        return superUser;
    }

    public void setSuperUser(User superUser) {
        this.superUser = superUser;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
