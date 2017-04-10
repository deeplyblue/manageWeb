import com.oriental.manage.core.utils.EncryptionTools;

import javax.crypto.SecretKey;

/**
 * Created by lupf on 2017/1/10.
 */
public class PwdTest {

    @org.junit.Test
    public void test() throws Exception {
        String desStr = new String("88".getBytes(), "UTF-8");

        SecretKey secretKey = EncryptionTools.genDESKey("$1#2@f3&4~6%7!a+*cd(e-h)");

        byte[] res = EncryptionTools.desEncrypt(secretKey, desStr.getBytes());

        System.out.println(EncryptionTools.base64Encode(res));
    }
}
