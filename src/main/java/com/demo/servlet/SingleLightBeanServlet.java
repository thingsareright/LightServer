package com.demo.servlet;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.LightBean;
import com.demo.model.LightBeanStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 获取单个灯的状态
 */
public class SingleLightBeanServlet extends HttpServlet {

    private static final String ENCODING = "UTF8";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 单个安卓的请求
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lightPhoneId = (String) request.getParameter("lightPhoneId");
        // 设置编码格式
        response.setContentType("text/plain;charset=" + ENCODING);
        response.setCharacterEncoding(ENCODING);
        //如果没有这个lightBean，那么会添加
        if (null == LightBeanStore.findLightBean(lightPhoneId)){
            //只有不含这个灯时才需要添加
            LightBean lightBean1 = new LightBean(lightPhoneId,false,3, System.currentTimeMillis());
            LightBeanStore.saveLightBean(lightBean1);
        }
        LightBean lightBean = LightBeanStore.findLightBean(lightPhoneId);
        lightBean.setLastActiveTime(System.currentTimeMillis());    //别忘了刷新时间
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSONObject.toJSONString(lightBean).toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
