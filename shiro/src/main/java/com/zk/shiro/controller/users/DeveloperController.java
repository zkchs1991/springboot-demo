package com.zk.shiro.controller.users;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.zk.mp.dao.users.DeveloperDao;
import com.zk.mp.entity.users.Developer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/developer")
public class DeveloperController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private DeveloperDao developerDao;

    @RequestMapping("/insert")
    public Developer insert (){
        Developer developer = new Developer();
        developer.setCreateTime(new Date().getTime());
        developer.setDeveloperName("qcon");
        developerDao.insert(developer);
        return developer;
    }

    @RequestMapping("/all")
    public List<Developer> allDeveloper (){
//        return developerDao.queryAll();
        return developerDao.selectList(null);
    }

    @RequestMapping("/select")
    public List<Developer> select (){
        Map<String, Object> map = new HashMap<>();
        map.put("developer_name", "qcon");
        List<Developer> developerList = developerDao.selectByMap(map);

        List<String> nameList = new ArrayList<>();
        nameList.add("asdasdas");
        QueryWrapper<Developer> queryWrapper = new QueryWrapper<>();
        /** condition来决定是否加入查询，可用于非空判断，但是要小心全部不加入，这样会查询全部 */
        queryWrapper.select("id", "developer_name")
                .in(!nameList.isEmpty(),"developer_name", nameList)
                .like("developer_name", "con")
                .eq("create_at", 1493034123121L);
        developerList.addAll(developerDao.selectList(queryWrapper));

        /** 当前台接收实体对象时可选择使用 */
        Developer developer = new Developer();
        developer.setDeveloperName("zkchs1991");
        queryWrapper = new QueryWrapper<>(developer);
        developerList.addAll(developerDao.selectList(queryWrapper));

        developerList.addAll(new LambdaQueryChainWrapper<>(developerDao)
                            .like(Developer::getDeveloperName, "con")
                            .or().eq(Developer::getId, 1)
                            .list());

        return developerList;
    }

    @RequestMapping("/update")
    public List<Developer> update (){
        Developer developer = new Developer();
        developer.setId(1);
        developer.setCreateTime(new Date().getTime());
        int rows = developerDao.updateById(developer);
        log.info("影响记录数：" + rows);

        developer = new Developer();
        developer.setCreateTime(new Date().getTime());
        UpdateWrapper<Developer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("developer_name", "qcon");
        /** update方法使用时，第一个参数不为null的字段作为set，第二个wrapper的参数作为where */
        rows = developerDao.update(developer, updateWrapper);
        log.info("影响记录数：" + rows);

        /** 也可以直接用wrapper的set */
        updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("developer_name", "zkchs1991").set("create_at", new Date().getTime());
        rows = developerDao.update(developer, updateWrapper);
        log.info("影响记录数：" + rows);

        /** lambda的updateWrapper写法 */
        LambdaUpdateWrapper<Developer> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Developer::getDeveloperName, "qcon").set(Developer::getCreateTime, new Date().getTime());
        rows = developerDao.update(developer, lambdaUpdateWrapper);
        log.info("影响记录数：" + rows);

        boolean status = new LambdaUpdateChainWrapper<>(developerDao)
                .eq(Developer::getDeveloperName, "zkchs1991")
                .set(Developer::getCreateTime, new Date().getTime())
                .update();
        log.info("更新结果：" + status);

        return null;
    }

}
