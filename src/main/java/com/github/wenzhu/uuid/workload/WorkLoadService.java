package com.github.wenzhu.uuid.workload;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Observable;

/**
 * description:
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 22:09
 */
public class WorkLoadService extends Observable implements IWorkLoad{

	private WorkLoadService(){

	}
	private static WorkLoadService wls = new WorkLoadService();
	public static WorkLoadService getInstance(){
		return wls;
	}

	private Map<String, Integer> mapCount = Maps.newHashMap();
	
	public Map<String, Integer> getMapCount() {
		return mapCount;
	}
	@Override
	public void addWork(String businessType) {
		Object obj = mapCount.get(businessType);
		int count = 1;
		if(obj!=null){
			count = (Integer)obj + 1;
		}
		mapCount.put(businessType, count);
		
		this.setChanged();
		this.notifyObservers();
	}

}
