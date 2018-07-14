package com.dongzhic.o2o.controller.frontend;

import com.dongzhic.o2o.pojo.Product;
import com.dongzhic.o2o.service.ProductService;
import com.dongzhic.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongzc
 * @date 2018/7/14 10:24
 */
@Controller
@RequestMapping("/frontend")
public class ProductDetailController {

    @Autowired
    private ProductService productService;

    /**
     * 根据商品Id获取商品详细
     * @param request
     * @return
     */
    @RequestMapping(value = "/listProductDetailPageInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listProductDetailPageInfo (HttpServletRequest request) {
        Map<String, Object> modalMap = new HashMap<String, Object>(16);
        //获取前台传递productId
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        //空值判断
        if (productId != -1) {
            try {
                //根据商品Id获取商品详细，包括商品详情图列表
                Product product = productService.getProductById(productId);
                modalMap.put("product", product);
                modalMap.put("success", true);
            } catch (Exception e) {
                e.printStackTrace();
                modalMap.put("success", false);
                modalMap.put("errMsg", e.getMessage());
            }

        } else {
            modalMap.put("success", false);
            modalMap.put("errMsg", "empty productId");
        }
        return modalMap;
    }
}
