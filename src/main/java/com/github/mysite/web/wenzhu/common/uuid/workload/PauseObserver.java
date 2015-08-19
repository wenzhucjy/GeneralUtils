package com.github.mysite.web.wenzhu.common.uuid.workload;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * description:暂停业务监控
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 22:18
 */
public class PauseObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        WorkLoadService wls = (WorkLoadService) o;
        Map<String, Integer> mapCount = wls.getMapCount();

        for (String key : mapCount.keySet()) {
            if (key.equals("Doc")) {
                if (mapCount.get(key) > 20) {
                    System.out.println("需要暂停 Doc 业务");
                }
            }
        }

    }
}
