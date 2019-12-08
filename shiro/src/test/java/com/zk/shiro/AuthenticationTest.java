package com.zk.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = RunningApplication.class)
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser (){
        simpleAccountRealm.addAccount("Mark", "123456", "admin", "users");
    }

    @Test
    public void testAuthentication (){
        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");

        subject.login(token);
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");
        subject.checkRoles("admin", "users");

        subject.logout();
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
    }

}
