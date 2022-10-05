package com.fstop.infra.entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity(name= "MASTER_EACH_FUNC_LIST")
@Table(name="Master_EachFuncList")
@Getter
@Setter
public class EACH_FUNC_LIST implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8809598173033682601L;
    @Transient
    private String htmlId ;
    @Transient
    private String UP_FUNC_NAME ;
    @Transient
    private String AUTH_TYPE ;

    @Id
    @OrderBy("FUNC_ID ASC")
    private String FUNC_ID;
    private String FUNC_NAME;
    private String FUNC_TYPE;
    private String FUNC_SEQ;
    private String UP_FUNC_ID;
    private String FUNC_DESC;
    private String FUNC_URL;
    private String USE_IKEY;
    private String TCH_FUNC;
    private String BANK_FUNC;
    private String COMPANY_FUNC;
    private String IS_USED = "N";
    private String START_DATE;
    private String CDATE;
    private String UDATE;
    private String PROXY_FUNC;
    private String IS_RECORD;
    private String FUNC_NAME_BK;

    //這邊不太確定怎麼改 20220916


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((AUTH_TYPE == null) ? 0 : AUTH_TYPE.hashCode());
        result = prime * result
                + ((BANK_FUNC == null) ? 0 : BANK_FUNC.hashCode());
        result = prime * result + ((CDATE == null) ? 0 : CDATE.hashCode());
        result = prime * result
                + ((COMPANY_FUNC == null) ? 0 : COMPANY_FUNC.hashCode());
        result = prime * result
                + ((FUNC_DESC == null) ? 0 : FUNC_DESC.hashCode());
        result = prime * result + ((FUNC_ID == null) ? 0 : FUNC_ID.hashCode());
        result = prime * result
                + ((FUNC_NAME == null) ? 0 : FUNC_NAME.hashCode());
        result = prime * result
                + ((FUNC_SEQ == null) ? 0 : FUNC_SEQ.hashCode());
        result = prime * result
                + ((FUNC_TYPE == null) ? 0 : FUNC_TYPE.hashCode());
        result = prime * result
                + ((FUNC_URL == null) ? 0 : FUNC_URL.hashCode());
        result = prime * result + ((IS_USED == null) ? 0 : IS_USED.hashCode());
        result = prime * result
                + ((PROXY_FUNC == null) ? 0 : PROXY_FUNC.hashCode());
        result = prime * result
                + ((START_DATE == null) ? 0 : START_DATE.hashCode());
        result = prime * result
                + ((TCH_FUNC == null) ? 0 : TCH_FUNC.hashCode());
        result = prime * result + ((UDATE == null) ? 0 : UDATE.hashCode());
        result = prime * result
                + ((UP_FUNC_ID == null) ? 0 : UP_FUNC_ID.hashCode());
        result = prime * result
                + ((UP_FUNC_NAME == null) ? 0 : UP_FUNC_NAME.hashCode());
        result = prime * result
                + ((USE_IKEY == null) ? 0 : USE_IKEY.hashCode());
        result = prime * result + ((htmlId == null) ? 0 : htmlId.hashCode());
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
        EACH_FUNC_LIST other = (EACH_FUNC_LIST) obj;
        if (AUTH_TYPE == null) {
            if (other.AUTH_TYPE != null)
                return false;
        } else if (!AUTH_TYPE.equals(other.AUTH_TYPE))
            return false;
        if (BANK_FUNC == null) {
            if (other.BANK_FUNC != null)
                return false;
        } else if (!BANK_FUNC.equals(other.BANK_FUNC))
            return false;
        if (CDATE == null) {
            if (other.CDATE != null)
                return false;
        } else if (!CDATE.equals(other.CDATE))
            return false;
        if (COMPANY_FUNC == null) {
            if (other.COMPANY_FUNC != null)
                return false;
        } else if (!COMPANY_FUNC.equals(other.COMPANY_FUNC))
            return false;
        if (FUNC_DESC == null) {
            if (other.FUNC_DESC != null)
                return false;
        } else if (!FUNC_DESC.equals(other.FUNC_DESC))
            return false;
        if (FUNC_ID == null) {
            if (other.FUNC_ID != null)
                return false;
        } else if (!FUNC_ID.equals(other.FUNC_ID))
            return false;
        if (FUNC_NAME == null) {
            if (other.FUNC_NAME != null)
                return false;
        } else if (!FUNC_NAME.equals(other.FUNC_NAME))
            return false;
        if (FUNC_SEQ == null) {
            if (other.FUNC_SEQ != null)
                return false;
        } else if (!FUNC_SEQ.equals(other.FUNC_SEQ))
            return false;
        if (FUNC_TYPE == null) {
            if (other.FUNC_TYPE != null)
                return false;
        } else if (!FUNC_TYPE.equals(other.FUNC_TYPE))
            return false;
        if (FUNC_URL == null) {
            if (other.FUNC_URL != null)
                return false;
        } else if (!FUNC_URL.equals(other.FUNC_URL))
            return false;
        if (IS_USED == null) {
            if (other.IS_USED != null)
                return false;
        } else if (!IS_USED.equals(other.IS_USED))
            return false;
        if (PROXY_FUNC == null) {
            if (other.PROXY_FUNC != null)
                return false;
        } else if (!PROXY_FUNC.equals(other.PROXY_FUNC))
            return false;
        if (START_DATE == null) {
            if (other.START_DATE != null)
                return false;
        } else if (!START_DATE.equals(other.START_DATE))
            return false;
        if (TCH_FUNC == null) {
            if (other.TCH_FUNC != null)
                return false;
        } else if (!TCH_FUNC.equals(other.TCH_FUNC))
            return false;
        if (UDATE == null) {
            if (other.UDATE != null)
                return false;
        } else if (!UDATE.equals(other.UDATE))
            return false;
        if (UP_FUNC_ID == null) {
            if (other.UP_FUNC_ID != null)
                return false;
        } else if (!UP_FUNC_ID.equals(other.UP_FUNC_ID))
            return false;
        if (UP_FUNC_NAME == null) {
            if (other.UP_FUNC_NAME != null)
                return false;
        } else if (!UP_FUNC_NAME.equals(other.UP_FUNC_NAME))
            return false;
        if (USE_IKEY == null) {
            if (other.USE_IKEY != null)
                return false;
        } else if (!USE_IKEY.equals(other.USE_IKEY))
            return false;
        if (htmlId == null) {
            if (other.htmlId != null)
                return false;
        } else if (!htmlId.equals(other.htmlId))
            return false;
        return true;
    }

}

