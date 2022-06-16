import com.aliyuncs.exceptions.ClientException;
import com.leyou.LeyouSmsApplication;
import com.leyou.sms.utils.SmsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LeyouSmsApplication.class)
@Import({LeyouSmsApplication.class})
public class SmsTest {

    @Autowired
    private SmsUtils smsUtils;

    @Test
    public void smsTest() throws ClientException {
         smsUtils.sendSms("18395998532", "465742", "阿里云短信测试", "SMS_154950909");
    }


}
