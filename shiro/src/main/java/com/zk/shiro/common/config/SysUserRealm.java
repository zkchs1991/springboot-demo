package com.zk.shiro.common.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zk.mp.dao.sys.SysUserDao;
import com.zk.mp.entity.sys.SysUserEntity;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.apache.shiro.util.ByteSource.Util.*;

@Component
public class SysUserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        SysUserEntity sysUserEntity = (SysUserEntity) principals.getPrimaryPrincipal();

        Set<String> roles = sysUserDao.queryAllRoles(sysUserEntity.getUsername());
        info.setRoles(roles);
        if (roles.size() > 0){
            Set<String> permissions = sysUserDao.queryAllPermissions(roles);
            info.setStringPermissions(permissions);
        }

        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 1.从主体传过来的认证信息中，获得用户名
        String username = (String) token.getPrincipal();

        // 2.通过用户名到数据库中获取凭证
        SysUserEntity sysUser = sysUserDao.selectOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getUsername, username));
        if (sysUser == null){
            return null;
        }
        String salt = sysUser.getSalt();
        sysUser.setSalt("");

        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), bytes(salt), getName());
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName("md5");
        shaCredentialsMatcher.setHashIterations(1);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }

}
