package com.xu.config.Filter;

import com.xu.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class MyFilter implements Filter {

    //fiter中抛出的异常全局异常无法捕获,感觉好像fiter在全局捕获异常执行前执行,所以判断request数据的时候可以放在拦截器中感觉会被捕获

    @Autowired
    private UserService userService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("filter初始化" + filterConfig.getFilterName());
        log.info("filter初始化" + filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("filter的doFileter方法before");
        //将request、response的类型强转为Http
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        log.info("请求路径为:{}",req.getRequestURI());
//        String token = req.getHeader("token");
//        if(StringUtils.isBlank(token)){
//            ResultInfo resultInfo = new ResultInfo(ResultStatus.SYSTEM_ERROR);
//            OutputStream out = servletResponse.getOutputStream();
//            out.write(JSON.toJSONString(resultInfo).getBytes("UTF-8"));
//            out.flush();
////            throw new ServletException("token不能为空");
//        }else{
//            filterChain.doFilter(servletRequest,servletResponse);
//        }
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("filter的doFileter方法after");
    }

    @Override
    public void destroy() {
        log.info("filter的destroy方法");
    }
}
