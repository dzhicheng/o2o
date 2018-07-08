package com.dongzhic.o2o.util;

/**
 * 分页计算类
 * @author dongzc
 */
public class PageCalculator {

    /**
     * 将pageIndex获取rowIndex
     * @param pageIndex 第几页
     * @param pageSize 每页数据数
     * @return rowIndex
     */
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex-1)*pageSize : 0;
    }
}
