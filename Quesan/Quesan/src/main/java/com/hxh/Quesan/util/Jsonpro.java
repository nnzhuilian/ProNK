package com.hxh.Quesan.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nowcoder on 2016/7/3.
 */
public class Jsonpro {
    private static final Logger logger = LoggerFactory.getLogger(Jsonpro.class);
    public static int ANONYMOUS_UERID=3;
    public static int ADMIN_UERID=1;
    public static String getJsonString(int code,String msg) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        return jsonObject.toJSONString();
    }
    public static String getJsonString(int code) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",code);
        return jsonObject.toJSONString();
    }
}
