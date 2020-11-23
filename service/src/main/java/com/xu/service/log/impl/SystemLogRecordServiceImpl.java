package com.xu.service.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.log.SystemLogRecord;
import com.xu.mapper.log.SystemLogRecordMapper;
import com.xu.service.log.SystemLogRecordService;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class SystemLogRecordServiceImpl extends ServiceImpl<SystemLogRecordMapper, SystemLogRecord> implements SystemLogRecordService {
}
