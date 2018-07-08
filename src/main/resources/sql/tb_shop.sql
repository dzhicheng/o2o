/*
Navicat MySQL Data Transfer

Source Server         : Ubuntu_Service
Source Server Version : 50721
Source Host           : 192.168.211.129:3306
Source Database       : o2o

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-02-01 16:11:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop` (
  `shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `owner_id` int(10) NOT NULL COMMENT '店铺创建人',
  `area_id` int(5) DEFAULT NULL,
  `shop_category_id` int(11) DEFAULT NULL,
  `shop_name` varchar(256) NOT NULL,
  `shop_desc` varchar(1024) DEFAULT NULL,
  `shop_addr` varchar(200) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `shop_img` varchar(1024) DEFAULT NULL,
  `priority` int(3) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `advice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_area` (`area_id`),
  KEY `fk_shop_profile` (`owner_id`),
  KEY `fk_shop_shopcate` (`shop_category_id`),
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_shop
-- ----------------------------
INSERT INTO `tb_shop` VALUES ('1', '1', '2', '1', '测试的店铺', '测试描述1', '测试地址1', 'test', 'upload/item/shop8/2018013111511587653.jpg', null, '2018-01-27 14:46:11', '2018-01-31 11:58:36', '1', '审核中');
INSERT INTO `tb_shop` VALUES ('11', '1', '2', '1', '测试的店铺', 'test', 'test', 'test', 'test', null, null, null, '1', '审核中');
INSERT INTO `tb_shop` VALUES ('13', '1', '2', '1', '测试店铺1', 'test1', null, 'test1', 'test1', null, '2018-01-31 12:41:08', '2018-01-31 12:41:08', '0', '审核中');
INSERT INTO `tb_shop` VALUES ('14', '1', '2', '1', '测试店铺1', 'test1', null, 'test1', 'upload/item/shop14/2018013112462791240.jpg', null, '2018-01-31 12:46:27', '2018-01-31 12:46:27', '0', '审核中');
INSERT INTO `tb_shop` VALUES ('15', '1', '2', '1', '测试店铺2', 'test2', null, 'test2', 'upload/item/shop15/2018013112473540381.jpg', null, '2018-01-31 12:47:35', '2018-01-31 12:47:35', '0', '审核中');
INSERT INTO `tb_shop` VALUES ('16', '1', '2', '1', '测试店铺2', 'test2', null, 'test2', 'test2', null, '2018-01-31 14:16:47', '2018-01-31 14:16:47', '0', '审核中');
INSERT INTO `tb_shop` VALUES ('17', '1', '2', '1', '测试店铺2', 'test2', null, 'test2', 'test2', null, '2018-01-31 14:17:53', '2018-01-31 14:17:53', '0', '审核中');
INSERT INTO `tb_shop` VALUES ('19', '1', '2', '1', '测试店铺2', 'test2', null, 'test2', 'test2', null, '2018-01-31 14:22:06', '2018-01-31 14:22:06', '0', '审核中');
INSERT INTO `tb_shop` VALUES ('20', '1', '2', '1', '测试店铺2', 'test2', null, 'test2', 'upload/item/shop20/2018013118025865917.jpg', null, '2018-01-31 18:02:57', '2018-01-31 18:02:57', '0', '审核中');
