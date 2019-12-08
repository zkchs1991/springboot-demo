package com.zk.shiro.shiro;

import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import static org.apache.shiro.SecurityUtils.*;

@Component
public class ShiroTag {

    /**
     * 是否拥有该权限
     *
     * @param permission 权限标识
     * @return true：是     false：否
     */
    public boolean hasPermission(String permission) {
        Subject subject = getSubject();
        return subject != null && subject.isPermitted(permission);
    }

}
