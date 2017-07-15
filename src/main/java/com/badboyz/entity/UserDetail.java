package com.badboyz.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Created by yusuf on 4/26/2016.
 */
@Entity
@Table
public class UserDetail {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String firstName;

    @Column
    private String middleName;

    @Column
    private String lastName;

    @Column
    private String address1;

    @Column
    private String address2;

    @Column
    private String town;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String country;

    @Column
    private String zipCode;

    @Column
    private Date dateCreated;

    @Column
    private Date dateActivated;

    @Column
    private Date dateDeactivated;

    @Column
    private Date dateLocked;

    @Column
    private Date dateUnlocked;

    @Column
    private String hashCode;

    @Column
    private Date hashCodeExpiration;

    public long getId() {
        return id;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipcode) {
        this.zipCode = zipcode;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(Date dateActivated) {
        this.dateActivated = dateActivated;
    }

    public Date getDateDeactivated() {
        return dateDeactivated;
    }

    public void setDateDeactivated(Date dateDeactivated) {
        this.dateDeactivated = dateDeactivated;
    }

    public Date getDateLocked() {
        return dateLocked;
    }

    public void setDateLocked(Date dateLocked) {
        this.dateLocked = dateLocked;
    }

    public Date getDateUnlocked() {
        return dateUnlocked;
    }

    public void setDateUnlocked(Date dateUnlocked) {
        this.dateUnlocked = dateUnlocked;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String generateHashCode() {
        return new BigInteger(130, new SecureRandom()).toString(40);
    }

    public Date getHashCodeExpiration() {
        return hashCodeExpiration;
    }

    public void setHashCodeExpiration(Date hashCodeExpiration) {
        this.hashCodeExpiration = hashCodeExpiration;
    }

}
