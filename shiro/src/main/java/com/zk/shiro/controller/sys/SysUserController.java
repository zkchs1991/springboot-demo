package com.zk.shiro.controller.sys;

import com.zk.mp.dao.sys.SysUserDao;
import com.zk.shiro.common.utils.ShiroUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/sys")
public class SysUserController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private SysUserDao sysUserDao;

    @RequestMapping(value = "/login")
    public String login (@RequestParam String username,
                         @RequestParam String password){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
        } catch (Exception e){
            return e.getMessage();
        }

        if (subject.hasRole("admin")){
            return "有admin权限";
        }

        return "无admin权限";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "/testRole")
    public String testRole (HttpSession session){
        System.out.println(ShiroUtils.getSession().getId());
        System.out.println(session.getId());
        /** 输出结果表示  shiro和web容器用的是同一个session */
        return "testRole success";
    }

    @RequiresRoles("admin1")
    @RequestMapping(value = "/testRole1")
    public String testRole1 (){
        return "testRole1 success";
    }

    @RequiresPermissions("test:user:update")
    @RequestMapping(value = "/testPermission")
    public String testPermission (){
        return "testPermission success";
    }

    @RequiresPermissions({"test:user:update", "test:user:delete"})
    @RequestMapping(value = "/testPermission1")
    public String testPermission1 (){
        return "testPermission1 success";
    }

}
