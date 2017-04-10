package com.oriental.manage.dao.merchant;

import com.oriental.manage.pojo.merchant.MailInfo;

import java.util.List;

/**
 * Created by wangjun on 2016/5/16.
 */
public interface MailInfoMapper {

    int insertMailInfo(MailInfo mailInfo);

    int checkConfig(MailInfo mailInfo);

    List<MailInfo> queryPage(MailInfo mailInfo);

    int checkMchntCode(MailInfo mailInfo);

    int updateMailInfo(MailInfo mailInfo);

     int deleteMailInfo(MailInfo mailInfo);



}
