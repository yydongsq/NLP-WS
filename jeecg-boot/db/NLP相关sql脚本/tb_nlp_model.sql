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

 Date: 20/05/2025 15:52:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_nlp_model
-- ----------------------------
DROP TABLE IF EXISTS `tb_nlp_model`;
CREATE TABLE `tb_nlp_model`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '所属部门',
  `model_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型类型编码',
  `model_type_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型类型名称',
  `model_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型编号',
  `model_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型名称',
  `model_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型来源',
  `model_creat_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型创建人',
  `model_creat_time` datetime NOT NULL COMMENT '模型创建时间',
  `model_update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模型更新人',
  `model_update_time` datetime NULL DEFAULT NULL COMMENT '模型更新时间',
  `model_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模型状态',
  `model_pos_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '词性标注集ID',
  `model_pos_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '词性标注集名称',
  `memo` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '自然语言分词模型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_nlp_model
-- ----------------------------
INSERT INTO `tb_nlp_model` VALUES ('1915658745649025026', 'admin', '2025-04-25 14:46:51', 'admin', '2025-04-25 14:46:56', 'A01', 'M-1001', 'NLP分词', 'M-100101', 'Thulac', '清华', 'admin', '2025-04-25 00:00:00', 'admin', '2025-04-25 14:46:56', '已启用', '1915653855249944577', '863词性标注集', NULL);
INSERT INTO `tb_nlp_model` VALUES ('1915658745649025027', 'admin', '2025-04-25 14:46:51', 'admin', '2025-04-25 14:46:56', 'A01', 'M-1001', 'NLP分词', 'M-100103', 'HanLP', 'HanLP开源', 'admin', '2025-04-25 00:00:00', 'admin', '2025-04-25 14:46:56', '已启用', '1915653855249944577', '863词性标注集', NULL);
INSERT INTO `tb_nlp_model` VALUES ('1915658745649025028', 'admin', '2025-04-25 14:46:51', 'admin', '2025-04-25 14:46:56', 'A01', 'M-1001', 'NLP分词', 'M-100104', 'Jieba', 'Jieba开源', 'admin', '2025-04-25 00:00:00', 'admin', '2025-04-25 14:46:56', '已启用', '1915653855249944577', '863词性标注集', NULL);
INSERT INTO `tb_nlp_model` VALUES ('1915658745649025029', 'admin', '2025-04-25 14:46:51', 'admin', '2025-04-25 14:46:56', 'A01', 'M-1001', 'NLP分词', 'M-100102', 'Ltp', '哈工大', 'admin', '2025-04-25 00:00:00', 'admin', '2025-04-25 14:46:56', '已启用', '1915653855249944577', '863词性标注集', NULL);

SET FOREIGN_KEY_CHECKS = 1;
