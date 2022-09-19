package com.fstop.infra.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@Entity(name = "EACH_USER")
@Table(name = "MASTER_EACH_USER")
public class EACH_USER implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -1529163750111026803L;
    //	Transient
    @Transient
    private String COM_NAME;
    //	Transient end
    @Id
    private String USER_ID;
    private String USER_COMPANY;
    private String USER_TYPE;
    private String USER_STATUS;
    private String USER_DESC;
    private String USE_IKEY;
    private String ROLE_ID;
    private Integer NOLOGIN_EXPIRE_DAY;
    private String IP;
    private Integer IDLE_TIMEOUT;
    private String LAST_LOGIN_DATE;
    private String LAST_LOGIN_IP;
    private String CDATE;
    private String UDATE;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((USER_ID == null) ? 0 : USER_ID.hashCode());
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
        EACH_USER other = (EACH_USER) obj;
        if (USER_ID == null) {
            if (other.USER_ID != null)
                return false;
        } else if (!USER_ID.equals(other.USER_ID))
            return false;
        return true;
    }


}
