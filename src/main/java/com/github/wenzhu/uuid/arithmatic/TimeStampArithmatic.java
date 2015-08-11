package com.github.wenzhu.uuid.arithmatic;

import com.github.wenzhu.jodatime.DateHelper;

import java.util.Date;

public class TimeStampArithmatic implements IArithmatic{

	/**
	 * 时间格式组装sncode
	 *
	 * @param businessType 业务类型
	 * @param cacheNum     缓存数量
	 * @return
	 */
	@Override
	public String genMaxCode(String businessType, int cacheNum) {
		return businessType + DateHelper.formateDate(new Date(),"yyyyMMdd");
	}
}