package com.demo.model;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 这个类用于做数据存储的非持久化（内存）到持久化（数据库）的过渡
 */
public class LightBeanStore {

    //记录lightBeans里的数据被修改的次数，到达一定度后持久化
    private static int changeTimes = 0;
    //这是非持久化存储的载体，而且是线程安全的
    private static CopyOnWriteArrayList<LightBean> lightBeans = new CopyOnWriteArrayList<LightBean>();

    //增和更改
    public static boolean saveLightBean(LightBean lightBean) {
        try {
            for (LightBean alreadyLightBean: lightBeans) {
                if (alreadyLightBean.getLightPhoneId() == lightBean.getLightPhoneId()){
                    lightBeans.remove(alreadyLightBean);
                    lightBeans.add(lightBean);
                    changeTimes ++;
                    doMySQLThings(lightBean);   //进行数据库持久化，注意还没写 TODO
                    return true;
                }
            }
            lightBeans.add(lightBean);
            changeTimes ++;
            doMySQLThings(lightBean);   //进行数据库持久化，注意还没写 TODO
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;   //一般是指出现了其它情况，比如传入数据不全
        }
    }

    /**
     * 数据库的操作 TODO 计划使用 changeTimes/(lightBeans.size()) 来作为因子决定是否把当前数据同步到数据库
     * @param obj
     */
    private static void doMySQLThings(Object obj) {
    }

    /**
     * 从集合中去除某一元素
     * @param lightBean 去除与 lightBean 相同的元素
     * @return 删除成功则返回被删除的数据，否则返回NULL
     */
    public static LightBean removeLightBean(LightBean lightBean){
        try {
            lightBeans.remove(lightBean);
            changeTimes ++;
            doMySQLThings(lightBean);   //进行数据库持久化，注意还没写 TODO
            return lightBean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;    //删除失败则返回null
        }
    }

    /**
     * 上个方法的重载方法
     * @param lightPhoneId  手机的唯一标识
     * @return 删除成功则返回被删除的数据，否则返回NULL
     */
    public static LightBean removeLightBean(String lightPhoneId){
        try {
            for (LightBean lightBean:
                 lightBeans) {
                if (lightPhoneId == lightBean.getLightPhoneId()){
                    lightBeans.remove(lightBean);
                    changeTimes ++;
                    doMySQLThings(lightBeans);   //进行数据库持久化，注意还没写 TODO
                    return lightBean;
                }

            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;    //删除失败则返回null
        }
    }

    public static LightBean findLightBean(String lightPhoneId){
        try {
            for (LightBean lightBean: lightBeans) {
                if (lightPhoneId == lightBean.getLightPhoneId())
                    return lightBean;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean changeAllLightOnOrOff(Boolean flag) {
        try {
            for (LightBean lightBean : lightBeans) {
                lightBean.setState(flag);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean changeLightLuminance(String lightPhoneId, int luminance){
        try {
            if (null == LightBeanStore.findLightBean(lightPhoneId)){
                //只有不含这个灯时才需要添加
                LightBean lightBean1 = new LightBean(lightPhoneId,false,luminance);
                LightBeanStore.saveLightBean(lightBean1);
            } else {
                LightBean lightBean = LightBeanStore.findLightBean(lightPhoneId);
                lightBean.setLuminance(luminance);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
