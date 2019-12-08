package com.zk.mp.dao.users;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zk.mp.entity.users.Developer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperDao extends BaseMapper<Developer> {

    List<Developer> queryAll();

}
