package com.hxh.Quesan.service;

import org.springframework.stereotype.Service;

@Service
public class IOCtest {//变量初始化通过依赖注入的概念，定义好了@service通过@Autowired就能处理出来，不需要初始化
    public String getMessage(int userId){
        return "zyl"+userId+"love U!";
    }
}
