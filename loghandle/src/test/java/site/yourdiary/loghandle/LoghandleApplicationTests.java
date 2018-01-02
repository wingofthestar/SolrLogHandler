package site.yourdiary.loghandle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.yourdiary.loghandle.entity.jpa.LogInfoData;
import site.yourdiary.loghandle.respository.jpa.DataBaseLogInfoRepository;

import javax.persistence.Table;
import javax.xml.ws.soap.Addressing;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoghandleApplicationTests {

	@Autowired
	private DataBaseLogInfoRepository dataBaseLogInfoRepository;

	@Test
	public void contextLoads() {
	}

}
