package com.demo.servlet;

import com.demo.model.LightBean;
import com.demo.model.LightBeanStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * 用于更改单个手机的状态
 * http://localhost:8080/ChangeSingleLightBeanServlet?lightPhoneId=1&luminance=2&state=false
 */
public class ChangeSingleLightBeanServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean result = false;

        String lightPhoneId = request.getParameter("lightPhoneId");
        LightBean lightBean = null;
        try {
            lightBean = LightBeanStore.findLightBean(lightPhoneId.toString());
        } catch (Exception e) {
            lightPhoneId = LightBeanStore.LIGHTPHONEID_WRONG;
        }
        try {
            lightBean.toString();   //检测其是否为空
        } catch (Exception e) {
            lightBean = new LightBean(lightPhoneId, LightBeanStore.DEFAULT_FLAG, LightBeanStore.DEFAULT_LUMINANCE,System.currentTimeMillis());
        }
        try {
            boolean state = Boolean.parseBoolean((request.getParameter("state")));
            lightBean.setState(state);
        } catch (NumberFormatException e) {
        }
        try {
             int luminance = Integer.parseInt(request.getParameter("luminance"));
             lightBean.setLuminance(luminance);
        } catch (NumberFormatException e) {
        }

        result = LightBeanStore.saveLightBean(lightBean);
        BufferedWriter writer = new BufferedWriter(response.getWriter());
        writer.write(result?"1":"0");
        writer.flush();
        writer.close();
    }
}
