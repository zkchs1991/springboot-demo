package com.zk.mp.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zk.mp.dao.users.DeveloperDao;
import com.zk.mp.entity.users.Developer;
import org.springframework.stereotype.Service;

@Service
public class DeveloperServiceImpl extends ServiceImpl<DeveloperDao, Developer> implements DeveloperService {



}
