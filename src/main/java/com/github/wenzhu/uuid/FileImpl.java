package com.github.wenzhu.uuid;

import com.github.wenzhu.file.FileHelper;
import com.google.common.collect.Maps;

import java.util.Map;


/**
 * description:
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:50
 */
public class FileImpl implements SnCodeDao {

    private static final String FILE_PATHNAME = "Uuid.txt";
    private Map<String, SnCode> map = null;

    private void initMap() {
        if (map == null) {
            map = (Map<String, SnCode>) FileHelper.readFile(FILE_PATHNAME);
            if (map == null) {
                map = Maps.newHashMap();
            }
        }
    }

    @Override
    public SnCode getByBusinessType(String businessType, String modifyDate) {
        initMap();

        return map.get(businessType);
    }

    @Override
    public void updateUuid(SnCode snCode) {
        initMap();
        map.put(snCode.getBussinessType(), snCode);
        //写文件
        FileHelper.writeFile(FILE_PATHNAME, map);
    }

    @Override
    public void createUuid(SnCode SnCode) {
        this.updateUuid(SnCode);
    }
}
