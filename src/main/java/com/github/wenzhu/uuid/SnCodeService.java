package com.github.wenzhu.uuid;

import com.github.wenzhu.uuid.arithmatic.DefaultArithmatic;
import com.github.wenzhu.uuid.arithmatic.IArithmatic;
import com.github.wenzhu.uuid.workload.WorkLoadFactory;

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
     * @param str            字符串
     * @param len            填充的长度
     * @param needArithmatic 是否需要运算
     * @param ia             运算的接口
     * @param cacheNum       缓存的数量
     * @return
     */
    @Override
    public String genSnCode(String businessType, boolean needFormat, String formatStr, String str, int len,
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
        //2：得到一个流水号
        String uuid = arithmatic.genMaxCode(businessType, cacheNum);
        //3:判断是否需要格式化
        if (needFormat) {
            uuid = this.formatUuid(formatStr, str, len, uuid);
        }
        return uuid;
    }

    private String formatUuid(String formatStr, String c, int len, String uuid) {
        uuid = this.prepareUuid(c, len, uuid);
        return formatStr.replaceAll("#", uuid);
    }

    private String prepareUuid(String c, int len, String uuid) {
        if (uuid.length() > len) {
            uuid = uuid.substring(0, len);
        } else if (uuid.length() < len) {
            //需要填充
            String preStr = "";
            for (int i = 0; i < (len - uuid.length()); i++) {
                preStr += c;
            }
            uuid = preStr + uuid;
        }
        return uuid;
    }
}