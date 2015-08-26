package com.github.mysite.web.wenzhu.common.uuid.workload;

/**
 * description:观察者模式，监控序列号编号情况
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 22:09
 */
public interface IWorkLoad {

	public void addWork(String businessType);
}