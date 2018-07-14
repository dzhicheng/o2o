package com.dongzhic.o2o.controller.frontend;

import com.dongzhic.o2o.pojo.HeadLine;
import com.dongzhic.o2o.pojo.ShopCategory;
import com.dongzhic.o2o.service.HeadLineService;
import com.dongzhic.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongzc
 * @date 2018/7/9 12:44
 */
@Controller
@RequestMapping("/frontend")
public class MainPageController {

    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;

    /**
     * 初始化前端展示系统的主页信息，包括获取一级店铺类别列表以及头条列表
     * @return
     */
    @RequestMapping(value = "/listMainPageInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listMainPageInfo () {
        System.out.println();
        Map<String, Object> modalMap = new HashMap<String, Object>(16);
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        try {
            //获取一级店铺列表（parentId为空的ShopCategory）
            shopCategoryList = shopCategoryService.getShopCategoryList(null);
            modalMap.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {
            modalMap.put("success", false);
            modalMap.put("errMsg", e.getMessage());
            return modalMap;
        }
        List<HeadLine> headLineList = new ArrayList<HeadLine>();
        try {
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modalMap.put("headLineList", headLineList);
        } catch (Exception e) {
            modalMap.put("success", false);
            modalMap.put("errMsg", e.getMessage());
            return modalMap;
        }
        modalMap.put("success", true);
        return modalMap;
    }


}
