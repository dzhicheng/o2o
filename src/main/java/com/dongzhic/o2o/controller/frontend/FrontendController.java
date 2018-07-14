package com.dongzhic.o2o.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author dongzc
 * @date 2018/7/9 14:53
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {

    /**
     * 跳转到首页
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index () {
        return "/frontend/index";
    }

    /**
     * 跳转商品列表页
     * @return
     */
    @RequestMapping(value = "/shopList", method = RequestMethod.GET)
    public String shopList () {
        return "/frontend/shopList";
    }

    /**
     * 跳转店铺详情页
     * @return
     */
    @RequestMapping(value = "/shopDetail", method = RequestMethod.GET)
    public String shopDetail () {
        return "/frontend/shopDetail";
    }

    /**
     * 跳转商品详情页
     * @return
     */
    @RequestMapping(value = "/productDetail", method = RequestMethod.GET)
    public String productDetail () {
        return "/frontend/productDetail";
    }

}
