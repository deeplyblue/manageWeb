package com.oriental.manage.service.merchant.itconfig;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.itconfig.KeyInfo;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Author: Yang xp
 * Date: 2016/5/30
 * Time: 19:13
 * Desc：商户密钥
 */
public interface IMerchantKeyInfoService {

    void search(Pagination<KeyInfo> pagination, KeyInfo keyInfo);

    void update(KeyInfo keyInfo, ResponseDTO<String> responseDTO);

    void add(KeyInfo keyInfo, ResponseDTO<String> responseDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;

    KeyInfo searchDataKey(KeyInfo keyInfo);

    KeyInfo searchRsaKey(KeyInfo keyInfo);
}
