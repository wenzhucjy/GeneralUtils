package com.github.wenzhu.uuid.workload;

/**
 * description:
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 22:18
 */
public class WorkLoadFactory {
	private WorkLoadFactory(){}
	
	public static IWorkLoad createIWorkLoad(){
		
		WorkLoadService wl = WorkLoadService.getInsatance();
		if(wl.countObservers() == 0){
			wl.addObserver(new CheckObserver());
			wl.addObserver(new PauseObserver());
		}
		
		return wl;
	}
}
