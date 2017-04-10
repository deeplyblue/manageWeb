package com.oriental.manage.dao.merchant;

import com.oriental.manage.pojo.merchant.SeqNames;

import java.util.List;

/**
 * Created by wangjun on 2016/5/23.
 */
public interface SeqNamesMapper {

    int updateByKey (SeqNames seqNames);

    int insert(SeqNames seqNames);

    int delete(SeqNames seqNames);

    List<SeqNames> queryPage (SeqNames seqNames);

    int queryByOrg(SeqNames seqNames);

}
