package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Dept implements Serializable {
    @Id
    private Integer did;

    private String dname;

    private static final long serialVersionUID = 1L;

    /**
     * @return did
     */
    public Integer getDid() {
        return did;
    }

    /**
     * @param did
     */
    public void setDid(Integer did) {
        this.did = did;
    }

    /**
     * @return dname
     */
    public String getDname() {
        return dname;
    }

    /**
     * @param dname
     */
    public void setDname(String dname) {
        this.dname = dname == null ? null : dname.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", did=").append(did);
        sb.append(", dname=").append(dname);
        sb.append("]");
        return sb.toString();
    }
}