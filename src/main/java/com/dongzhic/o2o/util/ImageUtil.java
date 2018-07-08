package com.dongzhic.o2o.util;

import com.dongzhic.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author dongzc
 **/
public class ImageUtil {
    /**
     * 获取项目名称
     */
    private static String basePath = Thread.currentThread().getContextClassLoader()
            .getResource("")
            .getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 将CommonsMultipartFile转换成File
     * @param cFile 文件
     * @return File类型文件
     */
    public static File transferCommonsMultipartFileToFile (CommonsMultipartFile cFile) {
         File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 处理缩略图，并返回新生成图片的相对路径
     * @param thumbnail 图片类
     * @param targetAddr 图片存储相对路径
     * @return 文件路径
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName  = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is :" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            //watermark(位置, 路径, 透明度):添加水印
            //outputQuality:压缩图片
            //toFile:定义输出路径
            Thumbnails.of(thumbnail.getImage())
                    .size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(basePath + "/watermark.jpg")), 0.2f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 处理缩略图，并返回新生成图片的相对路径
     * @param thumbnail 图片
     * @param targetAddr 文件相对路径
     * @return 文件路径
     */
    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        String realFileName  = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is :" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            //watermark(位置, 路径, 透明度):添加水印
            //outputQuality:压缩图片
            //toFile:定义输出路径
            Thumbnails.of(thumbnail.getImage())
                    .size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(basePath + "/watermark.jpg")), 0.2f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 生成随机文件名,当前年月日时分秒+五位随机数
     * @return 随机数
     */
    public static String getRandomFileName () {
        //获取随机五位数
        int rannum = random.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    /**
     * 获取输入文件流的扩展名
     * @param fileName 文件名称
     * @return 文件后缀
     */
    private static String getFileExtension (String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径所涉及到的目录,即/home/work/zhicheng/xxx.jpg,
     * 那么home work zhicheng 这三个文件夹都得自动创建
     * @param targetAddr 文件地址
     */
    private static void makeDirPath (String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr ;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 判断storePath是文件路径还是目录路径
     *  如果是文件路径则删除该文件
     *  如果是目录路径则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath (String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] file = fileOrPath.listFiles();
                for (int i=0; i<file.length; i++) {
                    file[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
