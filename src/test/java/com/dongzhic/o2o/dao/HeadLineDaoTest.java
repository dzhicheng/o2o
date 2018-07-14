package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.pojo.HeadLine;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dongzc
 * @date 2018/7/9 11:53
 */
public class HeadLineDaoTest extends BaseTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testAQueryHeadLine () {
        HeadLine headLineCondition = new HeadLine();
        List<HeadLine> headLines = headLineDao.queryHeadLine(headLineCondition);
        Assert.assertEquals(2, headLines.size());
    }
}
