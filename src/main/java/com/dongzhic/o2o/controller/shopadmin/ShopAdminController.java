package com.dongzhic.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 主要用来解析路由并转发到相应的html
 * @author dongzc
 */
@Controller
@RequestMapping(value = "/shopAdmin", method = RequestMethod.GET)
public class ShopAdminController {

    /**
     * 转发到商铺信息页面
     * @return
     */
    @RequestMapping(value = "/shopOperation", method = RequestMethod.GET)
    public String shopOperation () {
        return "/shop/shopOperation";
    }

    /**
     * 转发到商铺列表页面
     * @return
     */
    @RequestMapping(value = "/shopList", method = RequestMethod.GET)
    public String shopList () {
        return "/shop/shopList";
    }

    /**
     * 转发到商铺管理页面
     * @return
     */
    @RequestMapping(value = "/shopManagement", method = RequestMethod.GET)
    public String shopManagement () {
        return "/shop/shopManagement";
    }

    /**
     * 转发到商品类型管理页面
     * @return
     */
    @RequestMapping(value = "/productCategoryManagement", method = RequestMethod.GET)
    public String productCategoryManagement () {
        return "/shop/productCategoryManagement";
    }

    /**
     * 转发到商品列表页面
     * @return
     */
    @RequestMapping(value = "/productManagement", method = RequestMethod.GET)
    public String productManagement () {
        return "/shop/productManagement";
    }

    /**
     * 转发到商品添加/编辑页面
     * @return
     */
    @RequestMapping(value = "/productOperation")
    public String productOperation () {
        return "/shop/productOperation";
    }


}
