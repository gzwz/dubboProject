/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.101.80-tomcat
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 192.168.101.80:3306
 Source Schema         : shop_center

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 07/11/2018 15:00:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint(20) NOT NULL COMMENT '商品编号',
  `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品名称',
  `marque` smallint(6) DEFAULT NULL COMMENT '商品型号',
  `barcode` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '仓库条码',
  `type_id` smallint(6) DEFAULT NULL COMMENT '类型编号',
  `category_id` bigint(20) DEFAULT NULL COMMENT '类别编号',
  `brand_id` bigint(20) DEFAULT NULL COMMENT '品牌编号',
  `price` decimal(11, 2) DEFAULT NULL COMMENT '商品价格（分）',
  `market_price` decimal(11, 2) DEFAULT NULL COMMENT '市场价格（分）',
  `cost_price` decimal(11, 2) DEFAULT NULL COMMENT '成本价格（分），进货价格',
  `stock` int(11) DEFAULT NULL COMMENT '库存量',
  `warning_stock` int(11) DEFAULT NULL COMMENT '告警库存',
  `integral` int(11) DEFAULT NULL COMMENT '商品积分',
  `url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品图片',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态（1：下架:2：上架:3：预售）',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_album
-- ----------------------------
DROP TABLE IF EXISTS `product_album`;
CREATE TABLE `product_album`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片名称',
  `image_url` blob COMMENT '图片url',
  `suffix` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片后缀名',
  `image_size` int(11) DEFAULT NULL COMMENT '图片大小',
  `width` int(11) DEFAULT NULL COMMENT '图片宽度',
  `height` int(11) DEFAULT NULL COMMENT '图片高度',
  `intro` blob COMMENT '图片介绍',
  `sort` int(11) DEFAULT NULL COMMENT '排列次序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品相册表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_and_attribute
-- ----------------------------
DROP TABLE IF EXISTS `product_and_attribute`;
CREATE TABLE `product_and_attribute`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `pro_id` bigint(20) DEFAULT NULL COMMENT '商品编号',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品编号',
  `product_attribute_id` bigint(20) DEFAULT NULL COMMENT '属性编号',
  `attibute_value` blob COMMENT '属性值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品与属性关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_and_promotion
-- ----------------------------
DROP TABLE IF EXISTS `product_and_promotion`;
CREATE TABLE `product_and_promotion`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品编号',
  `promotion_id` bigint(20) DEFAULT NULL COMMENT '关联商品促销活动表',
  `ording` int(11) DEFAULT NULL COMMENT '排列次序',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品与促销活动关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_and_specification
-- ----------------------------
DROP TABLE IF EXISTS `product_and_specification`;
CREATE TABLE `product_and_specification`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品编号',
  `product_specification_id` bigint(20) DEFAULT NULL COMMENT '商品规格编号',
  `stock` int(11) DEFAULT NULL COMMENT '商品库存',
  `price` decimal(11, 2) DEFAULT NULL COMMENT '商品价格',
  `introduction` blob COMMENT '商品简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品与商品规格关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_attribute
-- ----------------------------
DROP TABLE IF EXISTS `product_attribute`;
CREATE TABLE `product_attribute`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `product_category_id` bigint(20) DEFAULT NULL COMMENT '商品类别编号(商品属性归属于商品类别)',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性标题',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性全称',
  `code` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性代码',
  `description` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性描述',
  `content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性预设内容',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性默认值',
  `ordering` int(11) DEFAULT NULL COMMENT '排列次序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品属性表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_brand
-- ----------------------------
DROP TABLE IF EXISTS `product_brand`;
CREATE TABLE `product_brand`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `category_id` bigint(20) DEFAULT NULL COMMENT '商品类别编号 (商品品牌归属于商品类别)商品类别编号 (商品品牌归属于商品类别)\r\n            商品类别编号 (商品品牌归属于商品类别)\r\n            商品类别编号 (商品品牌归属于商品类别)\r\n            ',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '品牌名称',
  `image_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片url',
  `ording` int(11) DEFAULT NULL COMMENT '排列次序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_date` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品品牌表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_cagegory
-- ----------------------------
DROP TABLE IF EXISTS `product_cagegory`;
CREATE TABLE `product_cagegory`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类别名称',
  `code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类别代码',
  `parent_one_code` int(11) DEFAULT NULL COMMENT '父1级编号',
  `parent_two_code` int(11) DEFAULT NULL COMMENT '父2级编号',
  `parent_three_code` int(11) DEFAULT NULL COMMENT '父3级编号',
  `quantity_included` int(11) DEFAULT NULL COMMENT '包含商品数量',
  `meta_keyword` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'meta关键词',
  `meta_description` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'meta描述',
  `is_lock` tinyint(4) DEFAULT 0 COMMENT '是否锁定（0：未锁定，1：锁定）',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '封面图片url',
  `ordering` int(11) DEFAULT NULL COMMENT '排列次序',
  `status` smallint(6) DEFAULT NULL COMMENT '状态( 0：正常，1：被删除)',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_comment
-- ----------------------------
DROP TABLE IF EXISTS `product_comment`;
CREATE TABLE `product_comment`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品编号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户编号',
  `auditor_id` bigint(20) DEFAULT NULL COMMENT '审核员编号',
  `parent_comment_id` bigint(20) DEFAULT NULL COMMENT '父级评论编号',
  `content` blob COMMENT '评论内容',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(1：待审核，2：显示，3：隐藏)',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `audit_date` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `audit_remark` blob COMMENT '审核备注',
  `support_count` int(11) DEFAULT NULL COMMENT '支持数量',
  `oppose_count` int(11) DEFAULT NULL COMMENT '反对数量',
  `star_level` smallint(6) DEFAULT NULL COMMENT '星星级别',
  `report_count` int(11) DEFAULT NULL COMMENT '举报',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_description
-- ----------------------------
DROP TABLE IF EXISTS `product_description`;
CREATE TABLE `product_description`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品编号',
  `title` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'title, 商品描述、商品规格、售后保障等',
  `content` longblob COMMENT '商品详细内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品描述表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_log
-- ----------------------------
DROP TABLE IF EXISTS `product_log`;
CREATE TABLE `product_log`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品编号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户编号',
  `operation_description` blob COMMENT '操作描述',
  `user_ip` blob COMMENT '用户IP',
  `status` tinyint(4) DEFAULT NULL COMMENT '操作状态 (status,成功,失败)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_promotion_event
-- ----------------------------
DROP TABLE IF EXISTS `product_promotion_event`;
CREATE TABLE `product_promotion_event`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `discount` decimal(11, 3) DEFAULT NULL COMMENT '折扣',
  `derate_money` decimal(11, 3) DEFAULT NULL COMMENT '减免金额',
  `promotion_rule` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '促销规则',
  `name` int(11) DEFAULT NULL COMMENT '活动名称( 橱窗名称, 最新,热门,推荐,清仓,换季等)',
  `code` blob COMMENT '活动代码(英文唯一)',
  `image_url` blob COMMENT '封面图片url',
  `product_count` blob COMMENT '商品数量统计',
  `remark` blob COMMENT '备注',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束时间',
  `ording` int(11) DEFAULT NULL COMMENT '排列次序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_date` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品促销活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_specification
-- ----------------------------
DROP TABLE IF EXISTS `product_specification`;
CREATE TABLE `product_specification`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `product_category_id` bigint(20) DEFAULT NULL COMMENT '商品类别编号 (商品规格归属于商品类别)',
  `type` int(11) DEFAULT NULL COMMENT '规格类型 (颜色、尺码、包装等)',
  `value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品规格表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_statistics
-- ----------------------------
DROP TABLE IF EXISTS `product_statistics`;
CREATE TABLE `product_statistics`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品编号',
  `visit_count` int(11) DEFAULT NULL COMMENT '浏览次数',
  `reply_count` int(11) DEFAULT NULL COMMENT '评论次数',
  `sale_quantity` int(11) DEFAULT NULL COMMENT '销售总量',
  `sale_amount` decimal(10, 0) DEFAULT NULL COMMENT '销售总额',
  `purchase_quantity` int(11) DEFAULT NULL COMMENT '进货总量',
  `purchase_amount` decimal(10, 0) DEFAULT NULL COMMENT '进货总额',
  `cost_price` decimal(10, 0) DEFAULT NULL COMMENT '成本均价',
  `gross_profit` decimal(10, 0) DEFAULT NULL COMMENT '毛利润金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qmd_shop
-- ----------------------------
DROP TABLE IF EXISTS `qmd_shop`;
CREATE TABLE `qmd_shop`  (
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺名称',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺简介',
  `shop_type_id` int(11) DEFAULT NULL COMMENT '店铺类型',
  `open_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺营业起始时间',
  `rest_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺营业结束时间',
  `status` int(11) DEFAULT NULL COMMENT '店铺状态',
  `license` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '营业执照',
  `max_scan` decimal(11, 3) DEFAULT NULL COMMENT '最大扫码付款金额',
  `max_sale` decimal(11, 3) DEFAULT NULL COMMENT '最大商品金额',
  `max_free` decimal(11, 3) DEFAULT NULL COMMENT '最大免单金额',
  `longitude` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '经度',
  `latitude` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '纬度',
  `announcement` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公告',
  `corporation` blob COMMENT '法人',
  `pro_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '省',
  `city_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '市',
  `county_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '县',
  `salesman_id` int(11) DEFAULT NULL COMMENT '业务员id',
  `user_id` int(11) DEFAULT NULL COMMENT '店铺主账号',
  `phone` blob COMMENT '联系电话',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`shop_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '店铺表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qmd_shop_category
-- ----------------------------
DROP TABLE IF EXISTS `qmd_shop_category`;
CREATE TABLE `qmd_shop_category`  (
  `id` bigint(20) NOT NULL COMMENT '编号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
  `fee` decimal(11, 3) DEFAULT NULL COMMENT '手续费',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建者',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '店铺分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qmd_shop_picture
-- ----------------------------
DROP TABLE IF EXISTS `qmd_shop_picture`;
CREATE TABLE `qmd_shop_picture`  (
  `id` bigint(20) NOT NULL COMMENT '编号',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '店铺图片表（暂时未确定字段）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qmd_user_shop_relation
-- ----------------------------
DROP TABLE IF EXISTS `qmd_user_shop_relation`;
CREATE TABLE `qmd_user_shop_relation`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户店铺关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_profit
-- ----------------------------
DROP TABLE IF EXISTS `shop_profit`;
CREATE TABLE `shop_profit`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺编号',
  `settle` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `accounts_type` tinyint(4) DEFAULT NULL COMMENT '结算类型',
  `accounts_percent` decimal(11, 3) DEFAULT NULL COMMENT '结算百分比',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '店铺分润表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
