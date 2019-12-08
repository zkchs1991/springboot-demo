package com.zk.shiro.controller.sys;

import com.zk.mp.dao.sys.SysUserDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sys")
public class SysUserController {

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
    public String testRole (){
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
