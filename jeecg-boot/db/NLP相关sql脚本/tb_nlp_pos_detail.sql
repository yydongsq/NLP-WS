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

 Date: 20/05/2025 15:52:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_nlp_pos_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_nlp_pos_detail`;
CREATE TABLE `tb_nlp_pos_detail`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '所属部门',
  `pos_main_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '外键',
  `pos_tags` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '词性标签',
  `pos_meaning` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '词性含义',
  `pos_description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '词性描述',
  `pos_create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '词性创建人',
  `pos_create_time` datetime NOT NULL COMMENT '词性创建时间',
  `pos_update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '词性更新人',
  `pos_update_time` datetime NULL DEFAULT NULL COMMENT '词性更新时间',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pos_main_id`(`pos_main_id`) USING BTREE,
  CONSTRAINT `fk_pos_main_id` FOREIGN KEY (`pos_main_id`) REFERENCES `tb_nlp_pos` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '词性标注集明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_nlp_pos_detail
-- ----------------------------
INSERT INTO `tb_nlp_pos_detail` VALUES ('10', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'k', '后缀', 'suffix', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('11', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'm', '数字', 'number', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('12', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'n', '一般名词', 'general noun', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('13', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'nd', '方位名词', 'direction noun', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('14', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'nh', '人名', 'person name', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('15', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'ni', '机构名', 'organization name', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('16', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'nl', '位置名词', 'location name', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('17', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'ns', '地点名词', 'geographical name', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('18', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'nt', '时间名词', 'temporal noun', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('19', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'nz', '其他专有名词', 'other proper noun', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('1915653856285937666', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'a', '形容词', 'adjective', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('2', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'b', '其他名词修饰词', 'other noun-modifier', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('20', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'o', '拟声词', 'onomatopoeia', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('21', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'p', '介词、前置词', 'preposition', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('22', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'q', '量词', 'quantity', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('23', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'r', '代词', 'pronoun', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('24', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'u', '助词', 'auxiliary', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('25', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'v', '动词', 'verb', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('26', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'wp', '标点', 'punctuation', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('27', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'ws', '外文词', 'foreign words', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('28', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'x', '非词根', 'non-lexeme', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('3', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'c', '连词', 'conjunction', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('4', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'd', '副词', 'adverb', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('5', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'e', '叹词', 'exclamation', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('6', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'g', '语素', 'morpheme', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('7', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'h', '前缀', 'prefix', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('8', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'i', '成语、习语', 'idiom', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');
INSERT INTO `tb_nlp_pos_detail` VALUES ('9', 'admin', '2025-04-25 14:27:25', NULL, NULL, 'A01', '1915653855249944577', 'j', '缩写', 'abbreviation', 'admin', '2025-04-25 00:00:00', NULL, NULL, '');

SET FOREIGN_KEY_CHECKS = 1;
