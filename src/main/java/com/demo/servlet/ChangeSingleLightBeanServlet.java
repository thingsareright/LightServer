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
        try {
            int luminance = Integer.parseInt(request.getParameter("luminance"));
            String lightPhoneId = request.getParameter("lightPhoneId");
            boolean state = Boolean.parseBoolean(request.getParameter("state"));

            LightBean lightBean = new LightBean(lightPhoneId, state, luminance, System.currentTimeMillis());
            result = LightBeanStore.saveLightBean(lightBean);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        BufferedWriter writer = new BufferedWriter(response.getWriter());
        writer.write(result?"1":"0");
        writer.flush();
        writer.close();
    }
}
