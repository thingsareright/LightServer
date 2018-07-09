package com.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.model.LightBean;
import com.demo.model.LightBeanStore;

/**
 * 用于处理JSON格式转换
 */
public class JSONUtil {

    public static String toJSONString(LightBean lightBean) {
        return JSONObject.toJSON(lightBean).toString();
    }

    static {
        //因为目前是所有灯状态都一样，所以只往数组里加一个
        LightBean lightBean = new LightBean("1",false,3);
        LightBeanStore.saveLightBean(lightBean);
    }
}
