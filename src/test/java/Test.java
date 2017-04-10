import com.oriental.manage.core.utils.DateUtils;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lupf on 2016/8/15.
 */
public class Test {

    public static void main(String[] args) {

        DateTime dateTime = DateTime.now();

        dateTime.minusMonths(3);

        dateTime.toDate();
    }

    @org.junit.Test
    public void test(){
        Pattern pattern = Pattern.compile("[^//]+(?=/manage)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("https://manage.orientpay.com/manage/login.jsp");
        matcher.find();

        System.out.println(matcher.start());
        System.out.println(matcher.end());
        String domain = matcher.group(0);
        System.out.println(domain);
    }
}
