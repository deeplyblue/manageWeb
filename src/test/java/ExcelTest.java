import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.fileUtils.HeaderExt;
import org.junit.Test;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/19
 * Time: 13:34
 * Desc：
 */
public class ExcelTest {

    @Test
    public void testWriteWithTemplate() throws Exception {

        List<Header> headers = new LinkedList<>();

        List<Object> datas = new ArrayList<>();

        headers.add(new HeaderExt("年级", "grade"));
        headers.add(new HeaderExt("班级", "classes"));
        headers.add(new HeaderExt("性别", "sex"));
        headers.add(new HeaderExt("姓名", "name"));
        headers.add(new HeaderExt("年龄", "age"));
        headers.add(new HeaderExt("备注", "remark"));

        //创建模拟数据
        for (int i = 0; i < 20; i++) {
            final int t = i;
            datas.add(new HashMap<String, Object>() {
                {
                    put("grade", "一年级");
                    put("classes", "2班");
                    put("sex", "男");
                    put("name", "张三" + t);
                    put("age", t);
                    put("remark", "测试2");
                }
            });
        }

        for (int i = 0; i < 100; i++) {
            final int t = i;
            datas.add(new HashMap<String, Object>() {
                {
                    put("grade", "一年级");
                    put("classes", "3班");
                    put("sex", "女");
                    put("name", "李四__" + t);
                    put("age", t);
                    put("remark", "测试2");
                }
            });
        }


        for (int i = 0; i < 100; i++) {
            final int t = i;
            datas.add(new HashMap<String, Object>() {
                {
                    put("grade", "二年级");
                    put("classes", "1班");
                    put("name", "测试11" + t);
                    put("age", 12.3);
                    put("remark", "测试2");
                }
            });
        }

        try (InputStream inputStream = new FileInputStream("C:\\Users/aaa/Desktop/test.xlsx")

        ) {
            OutputStream outputStream = new FileOutputStream("target/test_.xlsx");


//            processor.setSheetIndex(1);

            ExcelWriterConfig config = new ExcelWriterConfig();
            //设置表头和数据
            config.setHeaders(headers);
            config.setDatas(datas);
            //1、自动合并年级和班级相同的列
            config.mergeColumn("grade", "classes", "sex");
            //2、从第2行开始写出
            config.setStartWith(1);


            ExcelTemplateUtil.write(inputStream, outputStream, config);
        }
    }


}
