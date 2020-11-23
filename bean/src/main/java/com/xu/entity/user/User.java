package com.xu.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xu.entity.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@TableName("t_user")
@Data
@ApiModel("用户实体类")
public class User extends BaseModel {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("用户Id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("电话")
    private String phone;
}
