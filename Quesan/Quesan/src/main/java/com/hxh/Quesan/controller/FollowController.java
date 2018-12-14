package com.hxh.Quesan.controller;


import com.hxh.Quesan.model.HostHolder;
import com.hxh.Quesan.service.FollowService;
import com.hxh.Quesan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FollowController {
    private static final Logger logger=LoggerFactory.getLogger(FollowController.class);
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;

    @RequestMapping
}
