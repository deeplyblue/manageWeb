package com.oriental.manage.service.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.MailInfo;

/**
 * Created by wangjun on 2016/5/16.
 */
public interface IMailInfoSeivice {

    void queryPage(Pagination<MailInfo> pagination,MailInfo mailInfo);

    void addMailInfo(ResponseDTO<String> responseDTO,MailInfo mailInfo);

    void updateMailInfo(ResponseDTO<String> responseDTO,MailInfo mailInfo);

    void deleteMailInfo(ResponseDTO<String> responseDTO,MailInfo mailInfo);
}
