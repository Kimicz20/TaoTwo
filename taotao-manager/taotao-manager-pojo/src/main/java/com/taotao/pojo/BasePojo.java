package com.taotao.pojo;

import java.util.Date;

/**
 * Created by geek on 2017/11/23.
 */
public class BasePojo {
    private Date created;
    private Date updated;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
