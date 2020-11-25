package com.xu.admin.api.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.config.aspect.SystemLog;
import com.xu.entity.user.User;
import com.xu.service.user.UserService;
import com.xu.util.redis.RedisUtil;
import com.xu.util.result.ResultInfo;
import com.xu.util.result.ResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("user")
@Api(tags = "用户管理模块")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    RedisUtil redisUtil;

    public UserController(){
        log.info("UserController构造方法");
    }

    {
        log.info("加载代码块");
    }

    static {
        log.info("加载静态代码块");
    }

    /**
     * 加载顺序
     * 启动的时候 先初始化静态代码块 然后代码块 然后构造方法 然后init方法
     */
    @PostConstruct
    public void init(){
        log.info("userController init methor");
    }

    @RequestMapping("userList")
    @ApiOperation(value = "获取所有用户列表",notes = "9999")
    @SystemLog(description = "111",methodType = "select")
    public ResultInfo userList(HttpServletRequest request){
        UserController userController = new UserController();

        HttpSession session = request.getSession();

        List<User> list = userService.list();
        User user = userService.getUserById(1);
        ResultInfo resultInfo = new ResultInfo(ResultStatus.SUCCESS);

        boolean set = redisUtil.set("name", "xuzhen", 0);
        if(set){
            log.info(redisUtil.get("name").toString());
        }
//        int a= 1/0;
        resultInfo.result(list);
        return resultInfo;
    }

    @GetMapping("pageList")
    @ApiOperation(value = "分页获取用户列表",notes = "WWWWWWWWW")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",type = "int",value = "每页数量",defaultValue = "10",required = true),
            @ApiImplicitParam(name = "pageNo",type = "int",value = "页码",defaultValue = "1",required = true)
    })
    @SystemLog(description = "4444")
    public ResultInfo pageList(){
        ResultInfo resultInfo = new ResultInfo(ResultStatus.SUCCESS);
        Page<User> page = new Page<>(1,2);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        //当条件成立的时候 则按update_time倒序,create_user升序
//        userQueryWrapper.orderByDesc(2 == 2, "update_time");
        userQueryWrapper.orderByDesc(2 == 2, "update_time").orderByAsc("create_user");
        Page<User> page1 = userService.page(page,userQueryWrapper);

        resultInfo.result(page1);
        return resultInfo;
    }


    @RequestMapping("testLock")
    public ResultInfo testLock() {
        String threadId = String.valueOf(Thread.currentThread().getId());
        ResultInfo resultInfo = new ResultInfo(ResultStatus.SUCCESS);
        String key = "oneLock";
        try {
            redisUtil.lock(key, threadId, 10);
            Integer count = Integer.valueOf(redisUtil.get("count").toString());

            if (count > 0) {
                redisUtil.set("count", --count);
                resultInfo.setMessage("成功获取1个");
            } else {
                resultInfo.setCode(ResultStatus.WARN.code);
                resultInfo.setMessage("已空");
            }
            redisUtil.unLock(key, threadId);
        } catch (Exception e) {
            resultInfo.setCode(ResultStatus.SYSTEM_ERROR.code);
            resultInfo.setMessage(ResultStatus.SYSTEM_ERROR.message);
            e.printStackTrace();
        }
        return resultInfo;
    }


}
