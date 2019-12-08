package com.zk.mp.entity.users;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class Developer {

    /**
     *  mybatis-plus的字段别名注解，只能在使用它默认提供的方法时才生效，
     *  比如 {@link com.baomidou.mybatisplus.core.mapper.BaseMapper}的selectList(), save()方法
     */

    @TableId
    private Integer id;
    @TableField("create_at")
    private Long createTime;
    @TableField("developer_name")
    private String developerName;
    @TableField(exist = false)
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
