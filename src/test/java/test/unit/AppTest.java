package test.unit;

import com.eyes.authentication.database.models.UserEntity;
import com.eyes.authentication.database.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-security.xml"})
@WebAppConfiguration
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public class AppTest {
    private static Logger logger = Logger.getLogger(AppTest.class);

    @Qualifier("userRepository")
    @Autowired
    UserRepository userRepository;


    @Test
    public void testInMemoryDbWorks(){
        UserEntity userEntity = new UserEntity();
        userRepository.save(userEntity);
        assert userRepository.findOne(userEntity.getUserId()) != null;
    }

}
