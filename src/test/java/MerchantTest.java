import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.base.BaseModel;
import com.oriental.manage.pojo.institution.TransRight;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantInfo;
import com.oriental.manage.service.base.IGovAreaService;
import com.oriental.manage.service.merchant.baseinfo.IMerchantInfoService;
import com.oriental.manage.service.merchant.trans.IMerchantTransRightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/19
 * Time: 13:34
 * Desc：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/application.xml")
public class MerchantTest {

//    @Autowired
//    private IMerchantInfoService merchantService;
//
//
//    @Autowired
//    private IGovAreaService govAreaService;

    @Autowired
    private IMerchantTransRightService merchantTransRightService;

    @Test
    public void testInitSource(){
        List<TransRight> transRightList = new ArrayList<TransRight>();
        for (int i=0;i<3;i++){
            TransRight t =new TransRight();
            t.setCompanyCode("12121");
            t.setCompanyType("05");
            t.setTransCode("100001"+i);
            t.setConnChannel("01");
            transRightList.add(t);
        }
        merchantTransRightService.addMerchantTransRight(transRightList,null);
    }

//    @Test
//    public void testArea(){
//        System.out.println(govAreaService.selectAllArea());
//    }
//
//
//
//    @Test
//    public void test(){
//        Pagination<BaseModel> baseModelPagination = new Pagination<BaseModel>();
//        MerchantInfo baseModel = new MerchantInfo();
//        baseModel.setMerchantCode("01110010021008000");
////        merchantService.searchMerchantInfo(baseModelPagination,baseModel);
////        System.out.println("==========="+baseModelPagination);
//    }
}
