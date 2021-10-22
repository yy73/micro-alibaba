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

 Date: 22/10/2021 16:21:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_route
-- ----------------------------
DROP TABLE IF EXISTS `app_route`;
CREATE TABLE `app_route`  (
  `id` int(11) NOT NULL,
  `routeId` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order` int(11) NOT NULL,
  `uri` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `predicates` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `filters` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updateTime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `delete` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
