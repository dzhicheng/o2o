package com.dongzhic.o2o.util;

/**
 * @author dongzc
 **/
public class PathUtil {

    /**
     * 获取系统文件的分隔符
     */
    private static String seperator = System.getProperty("file.separator");

    /**
     * 获取项目图片的根路径
     * @return 图片的路径
     */
    public static String getImgBasePath () {
        //获取系统名称
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "E:/resources/o2o/images";
        } else {
            basePath = "/home/work/o2o/image";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    /**
     * 返回项目图片的子路径
     * @param shopId
     * @return
     */
    public static String getShopImagePath (long shopId) {
        String imagePath = "/upload/item/shop"+shopId+"/";
        return imagePath.replace("/", seperator);
    }

}
