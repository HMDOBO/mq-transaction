/*
Navicat MySQL Data Transfer

Source Server         : 192.168.25.201
Source Server Version : 50635
Source Host           : 192.168.25.201:3306
Source Database       : mq_transaction

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-07-17 14:07:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mq_item
-- ----------------------------
DROP TABLE IF EXISTS `mq_item`;
CREATE TABLE `mq_item` (
  `id` bigint(20) NOT NULL,
  `name` varchar(30) DEFAULT NULL COMMENT '商品名称',
  `price` bigint(20) DEFAULT NULL COMMENT '价格（单位：分）',
  `img` varchar(300) DEFAULT NULL COMMENT '图片',
  `stock` int(11) DEFAULT NULL COMMENT '库存',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Table structure for mq_item_user_record
-- ----------------------------
DROP TABLE IF EXISTS `mq_item_user_record`;
CREATE TABLE `mq_item_user_record` (
  `id` bigint(20) NOT NULL,
  `item_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `username` varchar(20) DEFAULT NULL COMMENT '记录用户姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '记录手机号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录商品购买信息';

-- ----------------------------
-- Table structure for mq_message
-- ----------------------------
DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message` (
  `id` bigint(20) NOT NULL,
  `queue_name` varchar(100) DEFAULT NULL COMMENT '队列名称',
  `message_body` text COMMENT '消息内容',
  `message_data_type` varchar(100) DEFAULT NULL COMMENT '消息数据类型',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次编辑时间',
  `message_send_times` int(6) DEFAULT NULL COMMENT '消息发送次数',
  `areadly_dead` varchar(20) DEFAULT NULL COMMENT '是否死亡',
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='可靠消息表';

-- ----------------------------
-- Table structure for mq_order
-- ----------------------------
DROP TABLE IF EXISTS `mq_order`;
CREATE TABLE `mq_order` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `item_name` varchar(30) DEFAULT NULL COMMENT '商品名称',
  `item_price` bigint(20) DEFAULT NULL COMMENT '商品价格',
  `item_count` int(11) DEFAULT NULL COMMENT '购买数量',
  `total_price` bigint(20) DEFAULT NULL COMMENT '订单总价',
  `status` int(11) DEFAULT NULL COMMENT '订单状态（1000待付款，2000已取消，3000已付款，4000已完成）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for mq_user
-- ----------------------------
DROP TABLE IF EXISTS `mq_user`;
CREATE TABLE `mq_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
