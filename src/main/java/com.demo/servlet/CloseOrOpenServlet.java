package com.demo.servlet;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.LightBean;
import com.demo.model.LightBeanStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CloseOrOpenServlet extends HttpServlet {

    private static final String ENCODING = "UTF8";
    /**
     * 灯的亮灭安卓WEB交互用POST,这里就不仅仅是开关了，还有亮度
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        String lightPhoneId = (String) request.getAttribute("lightPhoneId");
        // 设置编码格式
        response.setContentType("text/plain;charset=" + ENCODING);
        response.setCharacterEncoding(ENCODING);
        //如果没有这个lightBean，那么会添加
        if (null == LightBeanStore.findLightBean(lightPhoneId)){
            //只有不含这个灯时才需要添加
            LightBean lightBean1 = new LightBean(lightPhoneId,false,3);
            LightBeanStore.saveLightBean(lightBean1);
        }
        LightBean lightBean = LightBeanStore.findLightBean(lightPhoneId);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSONObject.toJSONString(lightBean).toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 灯的亮灭WEB交互用GET
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flagString  = request.getParameter("flag");
        Boolean flag = Boolean.valueOf(flagString);
        Boolean result = LightBeanStore.changeAllLightOnOrOff(flag);
        BufferedWriter writer = new BufferedWriter(response.getWriter());
        writer.write(result?"1":"0");
        writer.flush();
        writer.close();
    }
}
