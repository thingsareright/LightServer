package com.demo.servlet;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.LightBeanStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * 获取所有灯的状态
 */
public class AllPhoneInfoServlet extends HttpServlet {

    private static final String ENCODING = "UTF8";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码格式
        response.setContentType("text/plain;charset=" + ENCODING);
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            Collections.sort(LightBeanStore.getLightBeans());
            out.write(JSONObject.toJSONString(LightBeanStore.getLightBeans()));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
