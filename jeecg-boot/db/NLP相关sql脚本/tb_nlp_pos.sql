/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1_3306_57
 Source Server Type    : MySQL
 Source Server Version : 50735 (5.7.35-log)
 Source Host           : localhost:3306
 Source Schema         : jeecg-boot

 Target Server Type    : MySQL
 Target Server Version : 50735 (5.7.35-log)
 File Encoding         : 65001

 Date: 20/05/2025 15:52:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_nlp_pos
-- ----------------------------
DROP TABLE IF EXISTS `tb_nlp_pos`;
CREATE TABLE `tb_nlp_pos`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '所属部门',
  `pos_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '词性标注集编号',
  `pos_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '词性标注集名称',
  `pos_create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '词性标注集创建人',
  `pos_create_time` datetime NOT NULL COMMENT '词性标注集创建时间',
  `pos_update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '词性标注集更新人',
  `pos_update_time` datetime NULL DEFAULT NULL COMMENT '词性标注集更新时间',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '自然语言词性标注集' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_nlp_pos
-- ----------------------------
INSERT INTO `tb_nlp_pos` VALUES ('1915653855249944577', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', 'P-001', '863词性标注集', 'admin', '2025-04-25 00:00:00', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
