package site.yourdiary.solrdemo.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.yourdiary.solrdemo.respository.LoginInfoResporitory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginInfoTest {
    @Autowired
    private LoginInfoResporitory loginInfoResporitory;

    @Test
    public void test() {
        LoginInfo loginInfo = loginInfoResporitory.findById("8aea7904604e3ed101604e3f28e20004");
        System.out.println(loginInfo.toString());
    }
}
