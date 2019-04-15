import com.bitedu.BitEduApplication;
import com.bitedu.common.SnowFlake;
import com.bitedu.dao.ChargeApplyMapper;
import com.bitedu.pojo.ChargeApply;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@SpringBootTest(classes = BitEduApplication.class)
@RunWith(SpringRunner.class)
public class MyTest {

    @Autowired
    private ChargeApplyMapper chargeApplyMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Test
    public void loadData() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Random random = new Random();
        for(int i=1; i<30; i++){
            String date = "";
            if(i<10){
                date = "2019-04-0"+i;
            }else{
                date = "2019-04-"+i;
            }
            ChargeApply chargeApply = new ChargeApply();
            chargeApply.setId(""+snowFlake.nextId());
            chargeApply.setEmail("test@zju.edu.cn");
            chargeApply.setIsApprove(1);
            chargeApply.setIsCharge(0);
            chargeApply.setAccount("15958017490");
            chargeApply.setStatus(0);
            chargeApply.setNums(random.nextInt(500));
            chargeApply.setCreateTime(sdf.parse(date));
            chargeApplyMapper.insert(chargeApply);
        }
    }

}
