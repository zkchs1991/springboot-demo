package com.zk.shiro;

import com.zk.shiro.common.config.SysUserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShiroApplication.class)
public class SysUserRealmTest {

    @Autowired
    private SysUserRealm sysUserRealm;

    @Test
    public void testAuthentication (){
        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(sysUserRealm);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        sysUserRealm.setCredentialsMatcher(matcher);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("qcon", "aa123456");

        subject.login(token);
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");
//        subject.checkRoles("admin", "users");

        subject.checkPermission("test:user:delete");
    }

}
