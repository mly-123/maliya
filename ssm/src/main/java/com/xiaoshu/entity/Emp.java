package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

public class Emp implements Serializable {
    @Id
    private Integer eid;

    private String ename;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date ebirth;

    private Integer did;
    
    @Transient
    private Dept dept;

    public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	private static final long serialVersionUID = 1L;

    /**
     * @return eid
     */
    public Integer getEid() {
        return eid;
    }

    /**
     * @param eid
     */
    public void setEid(Integer eid) {
        this.eid = eid;
    }

    /**
     * @return ename
     */
    public String getEname() {
        return ename;
    }

    /**
     * @param ename
     */
    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    /**
     * @return ebirth
     */
    public Date getEbirth() {
        return ebirth;
    }

    /**
     * @param ebirth
     */
    public void setEbirth(Date ebirth) {
        this.ebirth = ebirth;
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", eid=").append(eid);
        sb.append(", ename=").append(ename);
        sb.append(", ebirth=").append(ebirth);
        sb.append(", did=").append(did);
        sb.append("]");
        return sb.toString();
    }
}