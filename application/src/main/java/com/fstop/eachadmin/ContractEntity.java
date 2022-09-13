package com.fstop.eachadmin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ContractEntity implements Serializable {

    private static final long serialVersionUID = -9180267794196864281L;

    /**
     * 合同编码
     * */
    private String contractCode;
    /**
     * 合同原始编码
     * */
    private String contractOriginalCode;
    /**
     * 合同名称
     * */
    private String contractName;
    /**
     * 合同类型
     * */
    private String contractType;
    /**
     * 合同明细类型
     * */
    private String contractDetailType;
    /**
     * 合同生效日期
     * */
    private String effectiveDate;
    /**
     * 合同到期日
     * */
    private String expiredDate;
    /**
     * 合同原币含税金额
     * */
    private BigDecimal originalCcyTaxIncludedAmt;
    /**
     * 合同本币含税金额
     * */
    private BigDecimal localCcyTaxIncludedAmt;
    /**
     * 供应商名称
     * */
    private String supplierName;
    /**
     * 经办人名字
     * */
    private String operatorName;
    /**
     * 经办人部门名字
     * */
    private String operatorDeptName;
    /**
     * 经办人机构名字
     * */
    private String operatorOrgName;

}
