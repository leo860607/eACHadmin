package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tw.org.twntch.po.WK_DATE_CALENDAR")
@Table(name = "WK_DATE_CALENDAR")
public class WkDateCalendar implements Serializable {


    private static final long serialVersionUID = -2790938525294469301L;
    private String TXN_DATE;
    private Integer WEEKDAY;
    private String IS_TXN_DATE;
    private String CDATE;
    private String UDATE;

    @Id
    @OrderBy("TXN_DATE ASC")
    public String getTXN_DATE() {
        return TXN_DATE;
    }

}
