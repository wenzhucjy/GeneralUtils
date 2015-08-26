package com.github.mysite.common.uuid.dao;

import com.github.mysite.common.uuid.SnCode;
import com.github.mysite.common.file.FileHelper;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * description: 文件实现存储订单序列号
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:50
 */
public class FileImpl implements SnCodeDao {

    private static final String FILE_PATHNAME = "Uuid.txt";
    private Map<String, SnCode> map = null;

    private void initMap() {
        System.out.println("init map ===================");
        if (map == null) {
            map = (Map<String, SnCode>) FileHelper.readFile(FILE_PATHNAME);
            if (map == null) {
                map = Maps.newHashMap();
            }
        }
    }

    /**
     * 获取SnCode
     *
     * @param businessType 业务类型
     * @return SnCode
     */
    @Override
    public SnCode getByBusinessType(String businessType) {
        initMap();

        return map.get(businessType);
    }

    /**
     * 更新SnCode
     *
     * @param snCode SnCode
     */
    @Override
    public void updateSnCode(SnCode snCode) {
        initMap();
        map.put(snCode.getBussinessType(), snCode);
        // 写文件
        FileHelper.writeFile(FILE_PATHNAME, map);
    }

    @Override
    public void createSnCode(SnCode SnCode) {
        this.updateSnCode(SnCode);
    }

    @Override
    public Map<String, Integer> getAll() {
        initMap();
        Map<String, Integer> retMap = Maps.newHashMap();

        for (String key : map.keySet()) {
            retMap.put(key, map.get(key).getMaxCode());
        }
        return retMap;
    }
}
