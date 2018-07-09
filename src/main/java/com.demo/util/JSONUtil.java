package com.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.LightBean;

/**
 * 用于处理JSON格式转换
 */
public class JSONUtil {

    public static String toJSONString(LightBean lightBean) {
        return JSONObject.toJSON(lightBean).toString();
    }

}
