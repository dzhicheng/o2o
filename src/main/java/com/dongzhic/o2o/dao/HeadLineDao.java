package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.pojo.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dongzc
 * @date 2018/7/9 11:24
 */
public interface HeadLineDao {

    /**
     * 根据传入的查询条件（头条名查询头条）
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
