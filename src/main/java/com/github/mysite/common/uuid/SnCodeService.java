package com.github.mysite.common.uuid;

import com.github.mysite.common.uuid.arithmatic.IArithmatic;
import com.github.mysite.common.uuid.arithmatic.DefaultArithmatic;
import com.github.mysite.common.uuid.workload.WorkLoadFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * description:
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 11:10
 */
public class SnCodeService implements ISnCode {
    /**
     * @param businessType   业务类型
     * @param needFormat     是否需要format
     * @param formatStr      format的格式
     * @param padStr         填充的字符串
     * @param len            填充后长度
     * @param needArithmatic 是否需要运算
     * @param ia             运算的接口
     * @param cacheNum       缓存的数量
     * @return
     */
    @Override
    public String genSnCode(String businessType, boolean needFormat, String formatStr, String padStr, int len,
                            boolean needArithmatic, IArithmatic ia, int cacheNum) {
        //调用workLoad
        WorkLoadFactory.createIWorkLoad().addWork(businessType);

        IArithmatic arithmatic = null;
        //1：判断是否需要采用客户指定的算法
        if (needArithmatic) {
            arithmatic = ia;
        } else {
            //若不需要，则用默认的算法
            arithmatic = new DefaultArithmatic();
        }
        //2：运算得到一个流水号
        String uuid = arithmatic.genMaxCode(businessType, cacheNum);
        //3:判断是否需要格式化
        if (needFormat) {
            //根据指定格式,格式化流水号
            uuid = this.formatUuid(formatStr, padStr, len, uuid);
        }
        return uuid;
    }

    private String formatUuid(String formatStr, String padStr, int len, String uuid) {
        uuid = this.prepareUuid(padStr, len, uuid);
        return StringUtils.replace(formatStr,"#",uuid);
    }

    private String prepareUuid(String padStr, int len, String uuid) {
        if (uuid.length() > len) {
            uuid = uuid.substring(0, len);
        } else if (uuid.length() < len) {
            //需要填充
            uuid = StringUtils.leftPad(uuid, len, padStr);
        }
        return uuid;
    }
}