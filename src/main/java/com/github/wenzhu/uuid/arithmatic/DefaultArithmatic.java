package com.github.wenzhu.uuid.arithmatic;

import com.github.wenzhu.uuid.SnCode;
import com.github.wenzhu.uuid.SnCodeDao;
import com.github.wenzhu.uuid.SnCodeFactory;

/**
 * description:默认的运算接口
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:58
 */
public class DefaultArithmatic implements IArithmatic {
	private SnCodeDao dao = SnCodeFactory.generateSnCodeDao();

	/**
	 * 通过业务类型计算出sncode
	 *
	 * @param businessType 业务类型
	 * @param cacheNum     缓存数量
	 * @return
	 */
	@Override
	public String genSnCode(String businessType, int cacheNum) {
        // 1：按照businessType 去获取对应的 snCode
        SnCode snCode = dao.getByBusinessType(businessType,"");
        int uuid = 0;
        // 2：一种是找到了
        if (null != snCode) {
            uuid = updateBusinessTypeSnCode(snCode);
        }else {
            // 3：找不到
            uuid = createBusinessTypeSnCode(businessType);
        }
        return "";
    }

    private int createBusinessTypeSnCode(String businessType) {
        // 3.1：那就设置 uuid=1，这就是一个新的数据
        SnCode vo = new SnCode(1, businessType, "");
        // 3.2：然后把新的uuid 新增回到数据存储中
        dao.createUuid(vo);

        return 0;
    }

    private int updateBusinessTypeSnCode(SnCode snCode) {

        return 0;

    }
}
