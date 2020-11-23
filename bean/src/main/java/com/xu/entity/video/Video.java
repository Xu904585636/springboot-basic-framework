package com.xu.entity.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@TableName("t_video")
@ApiModel("视频")
@Data
public class Video {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("视频名字")
    private String name;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("创建时间")
    private Date createTime;
}