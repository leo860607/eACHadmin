package com.fstop.infra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fstop.infra.entity.RPONBLOCKTAB;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.fstop.infra.entity.RPONBLOCKTABPK;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "RPONBLOCKTAB")
@Table(name = "MASTER_RPONBLOCKTAB")
public class RPONBLOCKTAB implements java.io.Serializable {
    private RPONBLOCKTABPK id;
    private String ACCTBAL;
    private String ACCTCODE;
    private String AVAILBAL;
    private String BANKRSPMSG;
    private String BIZDATE;
    private String CHECKTYPE;
    private String CLEARINGCODE;
    private String CLEARINGPHASE;
    private String CONRESULTCODE;
    private String EACHDT;
    private java.math.BigDecimal EACHFEE;
    private String FEE;
    private String INACCTNO;
    private String INACQUIRE;
    private String INBANKID;
    private String INCLEARING;
    private java.math.BigDecimal INFEE;
    private String INHEAD;
    private String INID;
    private String MERCHANTID;
    private String ORDERNO;
    private String OUTACCTNO;
    private String OUTACQUIRE;
    private String OUTBANKID;
    private String OUTCLEARING;
    private java.math.BigDecimal OUTFEE;
    private String OUTHEAD;
    private String OUTID;
    private String PCODE;
    private String PENDINGCODE;
    private String RC1;
    private String RC2;
    private String RC3;
    private String RC4;
    private String RC5;
    private String RC6;
    private String RECEIVERBANK;
    private String RECEIVERID;
    private String RECEIVERSTATUS;
    private String REFUNDDEADLINE;
    private String RESULTSTATUS;
    private String RRN;
    private String SENDERACQUIRE;
    private String SENDERBANK;
    private String SENDERBANKID;
    private String SENDERCLEARING;
    private java.math.BigDecimal SENDERFEE;
    private String SENDERHEAD;
    private String SENDERID;
    private String SENDERSTATUS;
    private String TIMEOUTCODE;
    private String TRMLCHECK;
    private String TRMLID;
    private String TRMLMCC;
    private String TXAMT;
    private String TXDT;
    private String TXID;


    // 新版手續費新增
    private String SENDERFEE_NW;
    private String INFEE_NW;
    private String OUTFEE_NW;
    private String EACHFEE_NW;
    private String WOFEE_NW;
    private String HANDLECHARGE_NW;
    private String FEE_TYPE;
    private String FEE_LVL_TYPE;
    private String SND_BANK_FEE_DISC_NW;
    private String IN_BANK_FEE_DISC_NW;
    private String OUT_BANK_FEE_DISC_NW;
    private String WO_BANK_FEE_DISC_NW;
    private String TCH_FEE_DISC_NW;


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
        com.fstop.infra.entity.RPONBLOCKTAB other = (com.fstop.infra.entity.RPONBLOCKTAB) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
