package com.dongzhic.o2o.service;

import com.dongzhic.o2o.pojo.Area;

import java.util.List;

/**
 *
 * @author dongzc
 */
public interface AreaService {

    String AREALISTKEY = "areaList";

    /**
     * 获取区域列表
     * @return areaList
     */
    List<Area> getAreaList();
}
