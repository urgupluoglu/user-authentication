package com.badboyz.general;

import java.util.Date;

/**
 * Created by yusuf on 5/14/2016.
 */
public class JWT {

    private long userId;
    private Date create;
    private Date expire;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}
