package site.yourdiary.loghandle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.yourdiary.loghandle.entity.jpa.LogInfoData;
import site.yourdiary.loghandle.respository.jpa.DataBaseLogInfoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogHandleTest {

    @Autowired
    private DataBaseLogInfoRepository dataBaseLogInfoRepository;

    @Test
    public void test_getTimeFromRepository(){
        LogInfoData logInfoData = dataBaseLogInfoRepository.findById("8aea268060b4cceb0160b4cf341f0000");
        System.out.println(logInfoData);
    }
}
