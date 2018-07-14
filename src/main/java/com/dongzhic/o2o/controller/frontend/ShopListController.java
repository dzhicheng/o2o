package com.dongzhic.o2o.controller.frontend;

import com.dongzhic.o2o.dto.ShopExecution;
import com.dongzhic.o2o.pojo.Area;
import com.dongzhic.o2o.pojo.Shop;
import com.dongzhic.o2o.pojo.ShopCategory;
import com.dongzhic.o2o.service.AreaService;
import com.dongzhic.o2o.service.ShopCategoryService;
import com.dongzhic.o2o.service.ShopService;
import com.dongzhic.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongzc
 * @date 2018/7/9 23:38
 */
@Controller
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    /**
     * 返回商品列表页里的ShopCategory列表(二级或者一级)，以及区域信息列表
     * @param request
     * @return
     */
    @RequestMapping(value ="/listShopsPageInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShopsPageInfo (HttpServletRequest request) {
        Map<String, Object> modalMap = new HashMap<String, Object>(16);
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            //如果parentId存在，则取一级shopCategory下的二级shopCategory列表
            try {
                ShopCategory shopCondition = new ShopCategory();
                ShopCategory parentCategory = new ShopCategory();
                parentCategory.setShopCategoryId(parentId);
                shopCondition.setParent(parentCategory);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCondition);
            } catch (Exception e) {
                modalMap.put("success", true);
                modalMap.put("errMsg", e.getMessage());
            }
        } else {
            //parentId不存在，则取所有一级shopCategory(用户在首页显示的全是商店列表)
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modalMap.put("success", true);
                modalMap.put("errMsg", e.getMessage());
            }
        }
        modalMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();
            modalMap.put("areaList", areaList);
            modalMap.put("success", true);
        } catch (Exception e) {
            modalMap.put("success", true);
            modalMap.put("errMsg", e.getMessage());
        }
        return modalMap;
    }

    /**
     * 获取指定查询条件下的列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listShops", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShops (HttpServletRequest request) {
        Map<String, Object> modalMap = new HashMap<String, Object>(16);
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取一页显示的数据条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //非空判断
        if (pageIndex > -1 && pageSize > -1) {
            //获取一级类别Id
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            //获取特定的二级类别Id
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            //获取区域Id
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            //获取模糊查询的名字
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            //获取组合之后的查询条件
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            //根据查询条件和分页信息获取店铺列表，并返回总数
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modalMap.put("shopList", se.getShopList());
            modalMap.put("count", se.getCount());
            modalMap.put("success", true);
        } else {
            modalMap.put("success", false);
            modalMap.put("errMsg", "empty pageIndex or pageSize");
        }
        return modalMap;
    }

    /**
     * 组合查询条件，将查询条件封装到ShopCondition对象并返回
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        //查询某个一级shopCategory下的所有二级shopCategory里面的店铺列表
        if (parentId != -1) {
            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        //查询某个二级shopCategory下的店铺
        if (shopCategoryId != -1) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        //查询位于某个区域Id下的店铺
        if (areaId != -1) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        //查询名字包含shopName的店铺
        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        //前台展示的是审核通过的店铺
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }

}
