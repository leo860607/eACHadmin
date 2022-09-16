package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

<<<<<<< HEAD
@Entity (name = "EACHUSERLOG")
@Table(name = "MASTER_EACH_USERLOG")
@Getter
@Setter
public class EachUserLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3291108105128791501L;
	private EachUserLogPk id;
	private String USERIP;
	private String TXTIME;
	private String UP_FUNC_ID;
	private String FUNC_ID;
	private String FUNC_TYPE;
	private String OPITEM;
	private String BFCHCON;
	private String AFCHCON;
	private String ADEXCODE;
	
	@EmbeddedId
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EachUserLog other = (EachUserLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
=======
@Getter
@Setter
@Entity(name = "tw.org.twntch.po.EACH_USERLOG")
@Table(name = "EACH_USERLOG")
public class EachUserLog implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3291108105128791501L;
    private EACH_USERLOG_PK id;
    private String USERIP;
    private String TXTIME;
    private String UP_FUNC_ID;
    private String FUNC_ID;
    private String FUNC_TYPE;
    private String OPITEM;
    private String BFCHCON;
    private String AFCHCON;
    private String ADEXCODE;

    @EmbeddedId
    public EACH_USERLOG_PK getId() {
        return id;
    }
//
//    public void setId(EACH_USERLOG_PK id) {
//        this.id = id;
//    }
//
//    public String getUSERIP() {
//        return USERIP;
//    }
//
//    public void setUSERIP(String uSERIP) {
//        USERIP = uSERIP;
//    }
//
//    public String getTXTIME() {
//        return TXTIME;
//    }
//
//    public void setTXTIME(String tXTIME) {
//        TXTIME = tXTIME;
//    }
//
//    public String getUP_FUNC_ID() {
//        return UP_FUNC_ID;
//    }
//
//    public void setUP_FUNC_ID(String uP_FUNC_ID) {
//        UP_FUNC_ID = uP_FUNC_ID;
//    }
//
//    public String getFUNC_ID() {
//        return FUNC_ID;
//    }
//
//    public void setFUNC_ID(String fUNC_ID) {
//        FUNC_ID = fUNC_ID;
//    }
//
//
//    public String getFUNC_TYPE() {
//        return FUNC_TYPE;
//    }
//
//    public void setFUNC_TYPE(String fUNC_TYPE) {
//        FUNC_TYPE = fUNC_TYPE;
//    }
//
//    public String getOPITEM() {
//        return OPITEM;
//    }
//
//    public void setOPITEM(String oPITEM) {
//        OPITEM = oPITEM;
//    }
//
//    public String getBFCHCON() {
//        return BFCHCON;
//    }
//
//    public void setBFCHCON(String bFCHCON) {
//        BFCHCON = bFCHCON;
//    }
//
//    public String getAFCHCON() {
//        return AFCHCON;
//    }
//
//    public void setAFCHCON(String aFCHCON) {
//        AFCHCON = aFCHCON;
//    }
//
//    public String getADEXCODE() {
//        return ADEXCODE;
//    }
//
//    public void setADEXCODE(String aDEXCODE) {
//        ADEXCODE = aDEXCODE;
//    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        com.fstop.infra.entity.EachUserLog other = (com.fstop.infra.entity.EachUserLog) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


>>>>>>> 59e6833b044651a789aec58f358bd1a87f454232
}
