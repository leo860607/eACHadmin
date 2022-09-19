package com.fstop.infra.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class EACHUSERPK implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2670450735562697162L;
    private String USER_COMPANY;
    private String USER_ID;

    public EACHUSERPK() {
    }

    public EACHUSERPK(String uSER_ID, String uSER_COMPANY) {
        USER_COMPANY = uSER_COMPANY;
        USER_ID = uSER_ID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((USER_COMPANY == null) ? 0 : USER_COMPANY.hashCode());
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
        EACHUSERPK other = (EACHUSERPK) obj;
        if (USER_COMPANY == null) {
            if (other.USER_COMPANY != null)
                return false;
        } else if (!USER_COMPANY.equals(other.USER_COMPANY))
            return false;
        if (USER_ID == null) {
            if (other.USER_ID != null)
                return false;
        } else if (!USER_ID.equals(other.USER_ID))
            return false;
        return true;
    }


}
