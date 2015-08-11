package com.github.wenzhu.uuid.workload;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * description:
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 22:18
 */
public class PauseObserver implements Observer{
    @Override
    public void update(Observable observable, Object o) {
        WorkLoadService wls = (WorkLoadService) o;
        Map<String, Integer> mapCount = wls.getMapCount();

        for (String key : mapCount.keySet()) {
            if (StringUtils.equals("Doc", key)) {
                if (mapCount.get(key) > 20) {
                    System.out.println("需要暂停 Doc 业务");
                }
            }
        }

    }
}
