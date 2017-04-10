package com.oriental.manage.service.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.SeqNames;

/**
 * Created by wangjun on 2016/5/23.
 */
public interface ISeqNamesService {

    void queryPage(Pagination<SeqNames> pagination, SeqNames seqNames);

    void addSeqNames(ResponseDTO<String> responseDTO, SeqNames seqNames);

    void updateSeqName(ResponseDTO<String> responseDTO, SeqNames seqName);

    void deleteSeqName(ResponseDTO<String> responseDTO, SeqNames seqName);

}
