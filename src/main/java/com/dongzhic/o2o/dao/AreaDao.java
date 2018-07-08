package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.pojo.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author dongzc
 */
@Repository
public interface AreaDao {
    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryArea();
}
