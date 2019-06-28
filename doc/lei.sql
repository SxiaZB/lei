/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : lei

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 28/06/2019 18:08:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lei_user
-- ----------------------------
DROP TABLE IF EXISTS `lei_user`;
CREATE TABLE `lei_user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
