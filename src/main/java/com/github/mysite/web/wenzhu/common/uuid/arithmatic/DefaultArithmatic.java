package com.github.mysite.web.wenzhu.common.uuid.arithmatic;

import com.github.mysite.web.wenzhu.common.uuid.dao.SnCodeDao;
import com.github.mysite.web.wenzhu.common.uuid.dao.SnCodeFactory;
import com.github.mysite.web.wenzhu.common.uuid.SnCode;

/**
 * description:默认的运算实现
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:58
 */
public class DefaultArithmatic implements IArithmatic {
    private SnCodeDao dao = SnCodeFactory.generateSnCodeDao();

    /**
     * 生成SnCode
     *
     * @param businessType 业务类型
     * @param cacheNum     缓存数量
     * @return
     */
    @Override
    public String genMaxCode(String businessType, int cacheNum) {
        // 1：按照businessType 去获取对应的 snCode
        Object obj = CacheManager.getInstance().getMapNow().get(businessType);
        int maxCode = 0;
        // 2：找到了
        if (null != obj) {
            maxCode = updateBusinessTypeMaxCode(businessType, cacheNum, (Integer) obj);
        } else {
            // 3：找不到
            maxCode = createBusinessTypeMaxCode(businessType, (Integer) obj);
        }
        return "" + maxCode;
    }

    /**
     * 创建
     * @param businessType  业务类型
     * @param cacheNum      缓存数量
     * @return
     */
    private int createBusinessTypeMaxCode(String businessType, Integer cacheNum) {
        // 3.1：那就设置 maxCode=1，这就是一个新的数据
        SnCode vo = new SnCode(1, businessType);
        // 3.2：然后把新的uuid 新增回到数据存储中
        dao.createSnCode(vo);

        //同时向缓存里面加数据
        CacheManager.getInstance().getMapDB().put(businessType, cacheNum);
        //缓存当前使用序列号初始为1
        CacheManager.getInstance().getMapNow().put(businessType, 1);

        return 1;
    }

    /**
     * 返回新序列号，并判断是否需要修改数据存储
     * @param businessType  业务类型
     * @param cacheNum      缓存的数量
     * @param uuid          现在使用的序列号maxCode
     * @return
     */
    private int updateBusinessTypeMaxCode(String businessType, int cacheNum, int uuid) {
        // 2.1：就把对应的uuid+1作为新的uuid
        int newUuid = uuid + 1;
        // 2.2：然后就把这个新的uuid修改回到数据存储中
        CacheManager.getInstance().getMapNow().put(businessType, newUuid);

        //		判断是否需要修改数据存储
        int dbUuid = CacheManager.getInstance().getMapDB().get(businessType);

        if (newUuid >= dbUuid) {
            SnCode snCodeVo = new SnCode(newUuid + cacheNum, businessType);

            dao.updateSnCode(snCodeVo);
            //同时更新mapDB
            CacheManager.getInstance().getMapDB().put(businessType, snCodeVo.getMaxCode());
        }

        return newUuid;
    }
}
