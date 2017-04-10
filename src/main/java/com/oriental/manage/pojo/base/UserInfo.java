package com.oriental.manage.pojo.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class UserInfo extends BaseModel{

    @Getter@Setter
    private List<UserRole> userRoles;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.USER_ID
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.USER_TYPE
     *
     * @mbggenerated
     */
    private String userType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.USER_NAME
     *
     * @mbggenerated
     */
    @NotBlank(message = "登录名不能为空")
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.USER_PWD
     *
     * @mbggenerated
     */
    @NotBlank(message = "密码不能为空")
    private String userPwd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.COMPANY_CODE
     *
     * @mbggenerated
     */
    private String companyCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.DEPT_CODE
     *
     * @mbggenerated
     */
    private String deptCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.USER_TELEPHONE
     *
     * @mbggenerated
     */
    private String userTelephone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.USER_MOBILE
     *
     * @mbggenerated
     */
    private String userMobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.USER_EMAIL
     *
     * @mbggenerated
     */
    private String userEmail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.USER_STATUS
     *
     * @mbggenerated
     */
    private String userStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.STATUS_LAST_UPD
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statusLastUpd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.HANDLER
     *
     * @mbggenerated
     */
    private String handler;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.PWD_LAST_UPD
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pwdLastUpd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.PWD_QUESTION
     *
     * @mbggenerated
     */
    private String pwdQuestion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.PWD_ANSWER
     *
     * @mbggenerated
     */
    private String pwdAnswer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.READONLY_FLAG
     *
     * @mbggenerated
     */
    private String readonlyFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.CREATE_TIME
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.LAST_UPD_TIME
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.USER_FULL_NAME
     *
     * @mbggenerated
     */
    private String userFullName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.SIGN_IMG_URL
     *
     * @mbggenerated
     */
    private String signImgUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.CREATOR
     *
     * @mbggenerated
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.MODIFIER
     *
     * @mbggenerated
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.SIGN_FLAG
     *
     * @mbggenerated
     */
    private String signFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.LAST_LOGIN_TIME
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.AUDITOR
     *
     * @mbggenerated
     */
    private String auditor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_info.AUDIT_DATE
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditDate;

    /**
     * 最后登录IP地址
     */
    private String lastLoginIpAddress;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.USER_ID
     *
     * @return the value of t_user_info.USER_ID
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.USER_ID
     *
     * @param userId the value for t_user_info.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.USER_TYPE
     *
     * @return the value of t_user_info.USER_TYPE
     *
     * @mbggenerated
     */
    public String getUserType() {
        return userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.USER_TYPE
     *
     * @param userType the value for t_user_info.USER_TYPE
     *
     * @mbggenerated
     */
    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.USER_NAME
     *
     * @return the value of t_user_info.USER_NAME
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.USER_NAME
     *
     * @param userName the value for t_user_info.USER_NAME
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.USER_PWD
     *
     * @return the value of t_user_info.USER_PWD
     *
     * @mbggenerated
     */
    public String getUserPwd() {
        return userPwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.USER_PWD
     *
     * @param userPwd the value for t_user_info.USER_PWD
     *
     * @mbggenerated
     */
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.COMPANY_CODE
     *
     * @return the value of t_user_info.COMPANY_CODE
     *
     * @mbggenerated
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.COMPANY_CODE
     *
     * @param companyCode the value for t_user_info.COMPANY_CODE
     *
     * @mbggenerated
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.DEPT_CODE
     *
     * @return the value of t_user_info.DEPT_CODE
     *
     * @mbggenerated
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.DEPT_CODE
     *
     * @param deptCode the value for t_user_info.DEPT_CODE
     *
     * @mbggenerated
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.USER_TELEPHONE
     *
     * @return the value of t_user_info.USER_TELEPHONE
     *
     * @mbggenerated
     */
    public String getUserTelephone() {
        return userTelephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.USER_TELEPHONE
     *
     * @param userTelephone the value for t_user_info.USER_TELEPHONE
     *
     * @mbggenerated
     */
    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone == null ? null : userTelephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.USER_MOBILE
     *
     * @return the value of t_user_info.USER_MOBILE
     *
     * @mbggenerated
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.USER_MOBILE
     *
     * @param userMobile the value for t_user_info.USER_MOBILE
     *
     * @mbggenerated
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.USER_EMAIL
     *
     * @return the value of t_user_info.USER_EMAIL
     *
     * @mbggenerated
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.USER_EMAIL
     *
     * @param userEmail the value for t_user_info.USER_EMAIL
     *
     * @mbggenerated
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.USER_STATUS
     *
     * @return the value of t_user_info.USER_STATUS
     *
     * @mbggenerated
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.USER_STATUS
     *
     * @param userStatus the value for t_user_info.USER_STATUS
     *
     * @mbggenerated
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.STATUS_LAST_UPD
     *
     * @return the value of t_user_info.STATUS_LAST_UPD
     *
     * @mbggenerated
     */
    public Date getStatusLastUpd() {
        return statusLastUpd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.STATUS_LAST_UPD
     *
     * @param statusLastUpd the value for t_user_info.STATUS_LAST_UPD
     *
     * @mbggenerated
     */
    public void setStatusLastUpd(Date statusLastUpd) {
        this.statusLastUpd = statusLastUpd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.HANDLER
     *
     * @return the value of t_user_info.HANDLER
     *
     * @mbggenerated
     */
    public String getHandler() {
        return handler;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.HANDLER
     *
     * @param handler the value for t_user_info.HANDLER
     *
     * @mbggenerated
     */
    public void setHandler(String handler) {
        this.handler = handler == null ? null : handler.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.PWD_LAST_UPD
     *
     * @return the value of t_user_info.PWD_LAST_UPD
     *
     * @mbggenerated
     */
    public Date getPwdLastUpd() {
        return pwdLastUpd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.PWD_LAST_UPD
     *
     * @param pwdLastUpd the value for t_user_info.PWD_LAST_UPD
     *
     * @mbggenerated
     */
    public void setPwdLastUpd(Date pwdLastUpd) {
        this.pwdLastUpd = pwdLastUpd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.PWD_QUESTION
     *
     * @return the value of t_user_info.PWD_QUESTION
     *
     * @mbggenerated
     */
    public String getPwdQuestion() {
        return pwdQuestion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.PWD_QUESTION
     *
     * @param pwdQuestion the value for t_user_info.PWD_QUESTION
     *
     * @mbggenerated
     */
    public void setPwdQuestion(String pwdQuestion) {
        this.pwdQuestion = pwdQuestion == null ? null : pwdQuestion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.PWD_ANSWER
     *
     * @return the value of t_user_info.PWD_ANSWER
     *
     * @mbggenerated
     */
    public String getPwdAnswer() {
        return pwdAnswer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.PWD_ANSWER
     *
     * @param pwdAnswer the value for t_user_info.PWD_ANSWER
     *
     * @mbggenerated
     */
    public void setPwdAnswer(String pwdAnswer) {
        this.pwdAnswer = pwdAnswer == null ? null : pwdAnswer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.READONLY_FLAG
     *
     * @return the value of t_user_info.READONLY_FLAG
     *
     * @mbggenerated
     */
    public String getReadonlyFlag() {
        return readonlyFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.READONLY_FLAG
     *
     * @param readonlyFlag the value for t_user_info.READONLY_FLAG
     *
     * @mbggenerated
     */
    public void setReadonlyFlag(String readonlyFlag) {
        this.readonlyFlag = readonlyFlag == null ? null : readonlyFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.CREATE_TIME
     *
     * @return the value of t_user_info.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.CREATE_TIME
     *
     * @param createTime the value for t_user_info.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.LAST_UPD_TIME
     *
     * @return the value of t_user_info.LAST_UPD_TIME
     *
     * @mbggenerated
     */
    public Date getLastUpdTime() {
        return lastUpdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.LAST_UPD_TIME
     *
     * @param lastUpdTime the value for t_user_info.LAST_UPD_TIME
     *
     * @mbggenerated
     */
    public void setLastUpdTime(Date lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.USER_FULL_NAME
     *
     * @return the value of t_user_info.USER_FULL_NAME
     *
     * @mbggenerated
     */
    public String getUserFullName() {
        return userFullName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.USER_FULL_NAME
     *
     * @param userFullName the value for t_user_info.USER_FULL_NAME
     *
     * @mbggenerated
     */
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName == null ? null : userFullName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.SIGN_IMG_URL
     *
     * @return the value of t_user_info.SIGN_IMG_URL
     *
     * @mbggenerated
     */
    public String getSignImgUrl() {
        return signImgUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.SIGN_IMG_URL
     *
     * @param signImgUrl the value for t_user_info.SIGN_IMG_URL
     *
     * @mbggenerated
     */
    public void setSignImgUrl(String signImgUrl) {
        this.signImgUrl = signImgUrl == null ? null : signImgUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.CREATOR
     *
     * @return the value of t_user_info.CREATOR
     *
     * @mbggenerated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.CREATOR
     *
     * @param creator the value for t_user_info.CREATOR
     *
     * @mbggenerated
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.MODIFIER
     *
     * @return the value of t_user_info.MODIFIER
     *
     * @mbggenerated
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.MODIFIER
     *
     * @param modifier the value for t_user_info.MODIFIER
     *
     * @mbggenerated
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.SIGN_FLAG
     *
     * @return the value of t_user_info.SIGN_FLAG
     *
     * @mbggenerated
     */
    public String getSignFlag() {
        return signFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.SIGN_FLAG
     *
     * @param signFlag the value for t_user_info.SIGN_FLAG
     *
     * @mbggenerated
     */
    public void setSignFlag(String signFlag) {
        this.signFlag = signFlag == null ? null : signFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.LAST_LOGIN_TIME
     *
     * @return the value of t_user_info.LAST_LOGIN_TIME
     *
     * @mbggenerated
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.LAST_LOGIN_TIME
     *
     * @param lastLoginTime the value for t_user_info.LAST_LOGIN_TIME
     *
     * @mbggenerated
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.AUDITOR
     *
     * @return the value of t_user_info.AUDITOR
     *
     * @mbggenerated
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.AUDITOR
     *
     * @param auditor the value for t_user_info.AUDITOR
     *
     * @mbggenerated
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_info.AUDIT_DATE
     *
     * @return the value of t_user_info.AUDIT_DATE
     *
     * @mbggenerated
     */
    public Date getAuditDate() {
        return auditDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_info.AUDIT_DATE
     *
     * @param auditDate the value for t_user_info.AUDIT_DATE
     *
     * @mbggenerated
     */
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }


    public String getLastLoginIpAddress() {
        return lastLoginIpAddress;
    }


    public void setLastLoginIpAddress(String lastLoginIpAddress) {
        this.lastLoginIpAddress = lastLoginIpAddress == null ? null : lastLoginIpAddress.trim();
    }


}