import com.bitedu.BitEduApplication;
import com.bitedu.common.PathUtils;
import com.bitedu.common.RedisUtil;
import com.bitedu.dao.BalanceMapper;

import com.bitedu.pojo.TopService;
import com.bitedu.service.redis.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.jms.Queue;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BitEduApplication.class)
@MapperScan("com.bitedu.mapper")
public class ApplicationTest {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PathUtils pathUtils;


    @Autowired
    private BalanceMapper balanceMapper;

    @Autowired
    RedisLock redisLock;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue q;


    @Test
    public void testJms(){
        jmsMessagingTemplate.convertAndSend(q, "hahahahahahahahhh");
        LinkedHashMap l = new LinkedHashMap();

    }


}