package com.xu.config.aspect;

import com.alibaba.fastjson.JSON;
import com.xu.log.SystemLogRecord;
import com.xu.service.log.SystemLogRecordService;
import com.xu.util.result.ResultInfo;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {

    @Autowired
    SystemLogRecordService systemLogRecordService;

    /**
     * 记录日志的数据
     */
    private ThreadLocal<SystemLogRecord> logRecord = new ThreadLocal<>();

    /**
     * 执行顺序
     * 1.around 的proceed 前半部分
     * 2.before
     * 3.接口里面
     * 4.around 的proceed 后半部分
     * 5.after
     * 6.正常执行afterReturning 异常执行afterThrowing
     */

    /**
     *  配置切点,被SystemLog注解的类
     */
    @Pointcut("@annotation(com.xu.config.aspect.SystemLog)")
    public void webLog(){

    }

    @Before("webLog()")
    public void before(){
        log.info("切点before");
    }

    @After("webLog()")
    public void after(){
        log.info("切点after");
    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        SystemLogRecord systemLogInfo = getSystemLogInfo(joinPoint);
        long start = System.currentTimeMillis();
        //如果正常返回,则重新设置消耗的时间,即end-start
        systemLogInfo.setTimeCost(start);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("\n"+"***************************start " + sdf.format(start) + " *************************************************");

        log.info("\n" +
                        "请求地址  >>>  {}\n" +
                        "请求方法  >>>  {}\n" +
                        "请求参数  >>>  {}\n" +
                        "请求来源  >>>  {}\n" +
                        "内容类型  >>>  {}\n" +
                        "请求头部  >>>  {}\n",
                systemLogInfo.getUri(),
                systemLogInfo.getHttpMethod(),
                systemLogInfo.getParams(),
                systemLogInfo.getIpAddress(),
                systemLogInfo.getContentType(),
                systemLogInfo.getHeaders());
        //记录日志信息
        logRecord.set(systemLogInfo);
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long timeCost = end - start;
        log.info("\n" + "请求结束 " + systemLogInfo.getUri() + " " + sdf.format(end) + " 耗时 " + timeCost + "ms" + "\n" +
                JSON.toJSONString(result, true));
        systemLogInfo.setTimeCost(timeCost);
        systemLogInfo.setResult(JSON.toJSONString(result));
        //如果接口正常返回则,更新日志信息
        logRecord.set(systemLogInfo);
        return result;
    }

    @AfterReturning("webLog()")
    public void afterReturning(JoinPoint joinPoint){
        log.info("执行完毕AfterReturning");
        SystemLogRecord systemLogRecord = logRecord.get();
        systemLogRecord.setRequestStatus(1);
        systemLogRecordService.save(systemLogRecord);
        //使用结束后,记得remove,避免内存泄漏
        logRecord.remove();
    }

    @AfterThrowing(value = "webLog()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Exception e){
        log.info("抛出了异常");
        SystemLogRecord systemLogRecord = logRecord.get();
        systemLogRecord.setRequestStatus(2);
        systemLogRecord.setExceptionMsg(e.getMessage());
        systemLogRecord.setTimeCost(System.currentTimeMillis() - systemLogRecord.getTimeCost());
        systemLogRecordService.save(systemLogRecord);
        //使用结束后,记得remove,避免内存泄漏
        logRecord.remove();
    }

    /**
     * 获取方法上的注解
     */
    private SystemLog getAnnotation(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return method.getAnnotation(SystemLog.class);
    }


    /**
     * 获取日志参数
     * @param joinPoint
     * @return
     */
    private SystemLogRecord getSystemLogInfo(JoinPoint joinPoint){
        SystemLogRecord systemLogRecord = new SystemLogRecord();
        SystemLog annotation = getAnnotation(joinPoint);
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, Object> headerMap = new HashMap<>(10);
        do {
            String header = headerNames.nextElement();
            headerMap.put(header, request.getHeader(header));
        } while (headerNames.hasMoreElements());
        //构造日志记录对象
        systemLogRecord.setCreateTime(new Date());
        systemLogRecord.setDescription(annotation.description());
        systemLogRecord.setMethodType(annotation.methodType());
        systemLogRecord.setHttpMethod(request.getMethod());
        systemLogRecord.setContentType(request.getContentType());
        systemLogRecord.setIpAddress(request.getRemoteAddr());
        systemLogRecord.setUri(request.getRequestURI());
        systemLogRecord.setParams(JSON.toJSONString(request.getParameterMap()));
        systemLogRecord.setHeaders(JSON.toJSONString(headerMap));
        return systemLogRecord;
    }
}
