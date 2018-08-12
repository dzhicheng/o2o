package com.dongzhic.o2o.service;

import com.dongzhic.o2o.pojo.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * @author dongzc
 * @date 2018/7/9 12:36
 */
public interface HeadLineService {

    String HLLISTKEY = "headLineList";

    /**
     * 根据传入的条件返回指定的头条列表
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
