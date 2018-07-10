package com.demo.model;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 这个类用于做数据存储的非持久化（内存）到持久化（数据库）的过渡
 */
public class LightBeanStore {

    //记录lightBeans里的数据被修改的次数，到达一定度后持久化
    private static int changeTimes = 0;
    //默认的灯的状态
    public static final boolean DEFAULT_FLAG = false;
    //默认的屏幕亮度
    public static final int DEFAULT_LUMINANCE = 3;
    //当没有lightPhoneId传来时的默认值
    public static final String LIGHTPHONEID_WRONG = "000000";
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

    /**
     * 这个方法主要是为了查找一个设备
     * @param lightPhoneId 设备的唯一ID
     * @return 如果成功查找到设备则返回设备对象，否则返回NULL
     */
    public static LightBean findLightBean(String lightPhoneId){
        try {
            for (LightBean lightBean: lightBeans) {
                if (lightPhoneId.equals(lightBean.getLightPhoneId()))
                    return lightBean;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 这个方法用于控制所有灯的开关和屏幕的亮度
     * @param flag
     * @param luminance
     * @return
     */
    public static Boolean changeAllLightOnOrOffAndLuminance(Boolean flag, int luminance) {
        try {
            for (LightBean lightBean : lightBeans) {
                lightBean.setState(flag);
                lightBean.setLuminance(luminance);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 这是上面方法的重载方法，用于更改所有设备的灯的开关
     * @param flag
     * @return
     */
    public static Boolean changeAllLightOnOrOffAndLuminance(Boolean flag) {
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

    /**
     * 用于更改屏幕的亮度
     * @param lightPhoneId
     * @param luminance
     * @return
     */
    public static Boolean changeSingleLight(String lightPhoneId, int luminance){
        try {
            if (null == LightBeanStore.findLightBean(lightPhoneId)){
                //只有不含这个灯时才需要添加
                LightBean lightBean1 = new LightBean(lightPhoneId,DEFAULT_FLAG,luminance,System.currentTimeMillis());
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

    /**
     * 这是上面那个方法的重载方法，用于更改屏幕的亮度
     * @param lightPhoneId
     * @param flag
     * @return
     */
    public static Boolean changeSingleLight(String lightPhoneId, boolean flag){
        try {
            if (null == LightBeanStore.findLightBean(lightPhoneId)){
                //只有不含这个灯时才需要添加
                LightBean lightBean1 = new LightBean(lightPhoneId,flag,DEFAULT_LUMINANCE,System.currentTimeMillis());
                LightBeanStore.saveLightBean(lightBean1);
            } else {
                LightBean lightBean = LightBeanStore.findLightBean(lightPhoneId);
                lightBean.setState(flag);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean changeSingleLight(String lightPhoneId, boolean flag,int luminance){
        try {
            //此时不需要再判断已有该设备上线
            LightBean lightBean1 = new LightBean(lightPhoneId,flag, luminance, System.currentTimeMillis());
            LightBeanStore.saveLightBean(lightBean1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public static CopyOnWriteArrayList<LightBean> getLightBeans() {
        return lightBeans;
    }
}
