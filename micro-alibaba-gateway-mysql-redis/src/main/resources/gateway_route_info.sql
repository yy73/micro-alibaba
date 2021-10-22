/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : gateway

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 22/10/2021 16:25:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gateway_route_info
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route_info`;
CREATE TABLE `gateway_route_info`  (
  `id` int(20) NOT NULL,
  `service_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务id',
  `uri` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '转发地址',
  `predicates` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '访问路径',
  `filters` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '过滤条件',
  `sn` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gateway_route_info
-- ----------------------------
INSERT INTO `gateway_route_info` VALUES (1, 'userService1', 'bob-userservice', '/userService/**', '1', '0', '2020-07-15 10:46:39', '2020-07-15 10:46:39', '测试用户服务访问', '0');
INSERT INTO `gateway_route_info` VALUES (2, 'micro-producer', 'micro-producer', '/micro-producer/**', '0', '2', '2021-10-20 14:54:53', '2021-10-20 14:54:53', '1', '0');

SET FOREIGN_KEY_CHECKS = 1;
