package com.fstop.infra.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ONBLOCKTABPK implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -7923386334434878672L;
    private  String     TXDATE          ;
    private  String     STAN            ;
    public ONBLOCKTABPK() {
        super();
    }
    public ONBLOCKTABPK(String tXDATE, String sTAN) {
        super();
        TXDATE = tXDATE;
        STAN = sTAN;
    }

}

