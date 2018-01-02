package site.yourdiary.loghandle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.yourdiary.loghandle.entity.jpa.User;
import site.yourdiary.loghandle.pojo.ResponseInfo;
import site.yourdiary.loghandle.respository.jpa.UserRepository;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseInfo userRegister(String userName, String password, String email){
        logger.info("开始注册用户");
        try{
            User user = new User(userName, password, email);
            userRepository.save(user);
            logger.info("注册用户成功");
            return new ResponseInfo(ResponseInfo.OK, "注册成功");
        }catch (Exception e){
            logger.error("注册用户失败");
            return new ResponseInfo(ResponseInfo.ERROR, "注册用户失败" + e);
        }

    }

    public Boolean checkUser(String userName, String password){
        User user = userRepository.findUserByUserNameAndPassword(userName, password);
        if (user != null){
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    public User findUser(String userName, String password){
        User user = userRepository.findUserByUserNameAndPassword(userName, password);
        return user;
    }
}
