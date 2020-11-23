package com.xu.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 操作日志
 * @author Administrator
 */
@Data
@ToString
@TableName("t_system_log_record")
public class SystemLogRecord {

    @TableId(type = IdType.AUTO)
    private int id;

    // 操作描述
    private String description;

    // 消耗时间
    private Long timeCost;

    // URL
    private String uri;

    // 请求类型
    private String httpMethod;

    // IP地址
    private String ipAddress;

    // 请求参数
    private String params;

    // 内容类型
    private String contentType;

    // 请求头参数
    private String headers;

    // 请求返回的结果
    private String result;

    // 操作类型
    private String methodType;

    // 请求状态
    private int requestStatus;

    //异常信息
    private String exceptionMsg;

    // 备注
    private String remark;

    // 请求时间
    private Date createTime;
}
