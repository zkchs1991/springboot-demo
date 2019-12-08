package com.zk.mp.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zk.mp.entity.sys.SysUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    @Select("select role_name from user_roles where username = #{username}")
    Set<String> queryAllRoles(String username);

    Set<String> queryAllPermissions(@Param("roleNames") Set<String> roleNames);

}
