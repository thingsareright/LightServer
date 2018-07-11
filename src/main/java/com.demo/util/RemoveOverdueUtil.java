package com.demo.util;

import com.demo.model.LightBean;
import com.demo.model.LightBeanStore;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 这个类起到定时清理掉线设备的作用
 */
public class RemoveOverdueUtil implements ServletContextListener{

    TimerManager timerManager;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        timerManager = new TimerManager();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        timerManager.cancelTimerTask();
    }


}

//要执行的清理任务的代码
class RemoveOverdueTimerTask extends TimerTask{

    public void run() {
        for (LightBean lightBean: LightBeanStore.getLightBeans()) {
            if (10000 < (System.currentTimeMillis() - lightBean.getLastActiveTime())){
                LightBeanStore.removeLightBean(lightBean);
            }
        }
    }

}

class TimerManager {
    private static final long PERIOD_DAY = 1000;    //每隔1s执行一次
    private Timer timer;

    public TimerManager() {
        Timer timer = new Timer();  //定时器实例化
        RemoveOverdueTimerTask removeOverdueTimerTask = new RemoveOverdueTimerTask();   //要执行的任务
        //安排指定的任务在指定的时间开始重复的固定延迟执行
        timer.schedule(removeOverdueTimerTask, new Date(), PERIOD_DAY);
    }

    public boolean cancelTimerTask(){
        timer.cancel();
        return true;
    }
}
