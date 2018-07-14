package com.dongzhic.o2o.controller.frontend;

import com.dongzhic.o2o.dto.ProductExecution;
import com.dongzhic.o2o.pojo.Product;
import com.dongzhic.o2o.pojo.ProductCategory;
import com.dongzhic.o2o.pojo.Shop;
import com.dongzhic.o2o.service.ProductCategoryService;
import com.dongzhic.o2o.service.ProductService;
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
 * @date 2018/7/12 16:35
 */
@Controller
@RequestMapping("/frontend")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息，以及店铺下的商品类别列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listShopDetailPageInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShopDetailPageInfo (HttpServletRequest request) {
        Map<String, Object> modalMap = new HashMap<String, Object>(16);
        //获取从前台传来过的shopId
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1) {
            try {
                //获取店铺Id为shopId的店铺
                shop = shopService.getByShopId(shopId);
                //获取店铺下的商品列别列表
                productCategoryList = productCategoryService.getProductCategoryList(shopId);
                modalMap.put("shop", shop);
                modalMap.put("productCategoryList", productCategoryList);
                modalMap.put("success", true);
            } catch (Exception e) {
                modalMap.put("success", false);
                modalMap.put("errMsg", "listShopDetailPageInfo: "+e.getMessage());
            }
        } else {
            modalMap.put("success", false);
            modalMap.put("errMsg", "empty shopId");
        }
        return modalMap;
    }

    @RequestMapping(value = "/listProductByShop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listProductByShop (HttpServletRequest request) {
        Map<String, Object> modalMap = new HashMap<String, Object>(16);

        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取每页显示的条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //获取店铺Id
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1 && pageIndex > -1 && pageSize > -1) {
            //获取商品类别Id
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            //获取模糊查找的商品名
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modalMap.put("productList", pe.getProductList());
            modalMap.put("count", pe.getCount());
            modalMap.put("success", true);
        } else {
            modalMap.put("success", false);
            modalMap.put("errMsg", "");
        }
        return modalMap;
    }

    /**
     * 组合查询条件，并将条件封装到ProductCondition对象里返回
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        //查询某个商品类别下的商品列表
        if (productCategoryId != -1) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        //查询名字包含productName下的店铺列表
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        //只能选出状态为上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }


}
