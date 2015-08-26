package com.github.mysite.common.uuid.workload;

/**
 * description:业务监控工厂类
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 22:18
 */
public class WorkLoadFactory {
	private WorkLoadFactory(){}
	
	public static IWorkLoad createIWorkLoad(){
		
		WorkLoadService wl = WorkLoadService.getInstance();
		if(wl.countObservers() == 0){
			wl.addObserver(new CheckObserver());
			wl.addObserver(new PauseObserver());
		}
		
		return wl;
	}
}
