package com.xu.entity.base;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class BaseModel {
    private Date createTime;
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;
    @TableLogic
    private Integer flag;
}
