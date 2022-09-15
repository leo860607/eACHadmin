package com.fstop.infra.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ONBLOCKTAB_PK implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -7923386334434878672L;
    private  String     TXDATE          ;
    private  String     STAN            ;
    public ONBLOCKTAB_PK() {
        super();
    }
    public ONBLOCKTAB_PK(String tXDATE, String sTAN) {
        super();
        TXDATE = tXDATE;
        STAN = sTAN;
    }

}

