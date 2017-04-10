package com.oriental.manage.pojo.opcif;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Created by jinxin on 2017/2/17.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TCustomerBankPojo extends BaseModel {

    private String id;

    private String agreementNo;

    private String customerNo;

    private String entityNo;

    private String agreementType;

    private Date signDatetime;

    private String signOperator;

    private String agreementStatus;

    private String authApplyId;

    private String accountName;

    private String cardnosuf;

    private String bankCode;

    private String orgBankCode;

    private String cardType;

    private String orgpersonFlag;

    private String relInfo;

    private String endReason;

    private String bankCardType;

    private String cardNo;

    private String mobile;

    private String authStatus;

    private String accountValiddatetime;

    private String cardIssuersCity;

    private String bankName;

    private String certificateNo;

    private Date startDate;

    private Date endDate;

}
