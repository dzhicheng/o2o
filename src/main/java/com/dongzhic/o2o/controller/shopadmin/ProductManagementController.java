package com.dongzhic.o2o.controller.shopadmin;

import com.dongzhic.o2o.dto.ImageHolder;
import com.dongzhic.o2o.dto.ProductExecution;
import com.dongzhic.o2o.dto.Result;
import com.dongzhic.o2o.enums.ProductCategoryStateEnum;
import com.dongzhic.o2o.enums.ProductStateEnum;
import com.dongzhic.o2o.exception.ProductOperationException;
import com.dongzhic.o2o.pojo.Product;
import com.dongzhic.o2o.pojo.ProductCategory;
import com.dongzhic.o2o.pojo.Shop;
import com.dongzhic.o2o.service.ProductCategoryService;
import com.dongzhic.o2o.service.ProductService;
import com.dongzhic.o2o.util.CodeUtil;
import com.dongzhic.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongzc
 * @date 2018.04.27
 */
@Controller
@RequestMapping("/shopAdmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 支持上传详细图的最大数量
     */
    private static final int IMAGEMAXCOUNT = 6;

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct (HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确的验证码");
            return modelMap;
        }
        //接收前端参数变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        //前台传的商品信息
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        //处理文件流
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> imageHolderList = new ArrayList<ImageHolder>();
        //从requestSession中获取文件流
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            //若请求中存在文件流，则取出相关的文件(包括缩略图和详细图)
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage((MultipartHttpServletRequest) request, imageHolderList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
            //将前台传过来的表单string流转换成Product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", toString());
            return modelMap;
        }
        //若product信息，缩略图以及详细图列表不为空，则开始进行商品添加工作
        if (product != null && thumbnail != null && imageHolderList.size()>0) {
            try {
                //从session获取当前店铺的Id并赋值给product，减少对前端数据的以来
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.addProduct(product, thumbnail, imageHolderList);
                if (ProductStateEnum.SUCCESS.getState() == pe.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.toString());
                    return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
            return modelMap;
        }
        return modelMap;
    }

    private ImageHolder handleImage(MultipartHttpServletRequest request, List<ImageHolder> imageHolderList) throws IOException {
        MultipartHttpServletRequest multipartRequest;
        ImageHolder thumbnail;
        multipartRequest = request;
        //取出缩略图并构建ImageHolder对象
        CommonsMultipartFile multipartFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        thumbnail = new ImageHolder(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        //取出详细图列表，并构建详细图List列表对象，最多支持6张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT ; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
                    .getFile("productImg"+i);
            if (productImgFile != null) {
                //若取出来的第i个详细图片文件流不为空，将其添加到详细图列表中
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
                        productImgFile.getInputStream());
                imageHolderList.add(productImg);
            } else {
                break;
            }
        }
        return thumbnail;
    }

    @RequestMapping(value ="/getProductListShop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductListShop (HttpServletRequest request) {
        Map<String, Object> modalMap = new HashMap<String, Object>(16);

        //获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取前台传过来的每页要求返回的商品数上限
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        ProductExecution pe = null;
        //空值判断
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() > 0) ) {
            //获取传入需要检索的条件，包括是否需要从某个商品类别以及模糊查找商品名去筛选某个商铺下的商品列表
            //筛选的条件可以进行排列组合
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);

            //根据传入的条件以及分页信息进行查询，返回相应商品列表以及总数
            pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modalMap.put("success", true);
            modalMap.put("count", pe.getCount());
            modalMap.put("productList", pe.getProductList());
        } else {
            modalMap.put("success", false);
            modalMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modalMap;
    }

    /**
     * 封装商品查询条件到Product实例中
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(Long shopId, long productCategoryId, String productName) {
        Shop shop = new Shop();
        shop.setShopId(shopId);

        Product productCondition = new Product();
        productCondition.setShop(shop);

        ProductCategory productCategory = new ProductCategory();
        //若指定类别，则添加进去
        if (productCategoryId != -1L) {
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        //若有商品名称模糊查询，则添加进去
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }

    @RequestMapping(value = "/getProductById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductById (@RequestParam Long productId) {
        Map<String, Object> modalMap = new HashMap<String, Object>(16);
        //非空判断
        if (productId > -1) {
            //获取商品信息
            Product product = productService.getProductById(productId);
            //获取店铺下商品类别列表
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
            modalMap.put("success", true);
            modalMap.put("product", product);
            modalMap.put("productCategoryList", productCategoryList);
        } else {
            modalMap.put("success", false);
            modalMap.put("errMsg", "empty productId");
        }
        return modalMap;
    }

    @RequestMapping(value = "/modifyProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyProduct (HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //商品编辑调用，还是上下架操作时调用
        //false:商品编辑，true：上下架,不需要验证码
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request) && !statusChange) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确的验证码");
            return modelMap;
        }
        //接收前端参数变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        //前台传的商品信息
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        //处理文件流
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> imageHolderList = new ArrayList<ImageHolder>();
        //从requestSession中获取文件流
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            //若请求中存在文件流，则取出相关的文件(包括缩略图和详细图)
            if (multipartResolver.isMultipart(request)) {
               thumbnail = handleImage((MultipartHttpServletRequest) request, imageHolderList);
            }
            //将前台传过来的表单string流转换成Product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", toString());
            return modelMap;
        }
        //若product信息，缩略图以及详细图列表不为空，则开始进行商品添加工作
        if (product != null) {
            try {
                //从session获取当前店铺的Id并赋值给product，减少对前端数据的以来
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //开始进行商品信息变更操作
                ProductExecution pe = productService.modifyProduct(product, thumbnail, imageHolderList);
                if (ProductStateEnum.SUCCESS.getState() == pe.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
            return modelMap;
        }
        return modelMap;
    }


}
