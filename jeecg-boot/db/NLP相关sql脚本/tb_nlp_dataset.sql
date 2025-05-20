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

 Date: 20/05/2025 15:51:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_nlp_dataset
-- ----------------------------
DROP TABLE IF EXISTS `tb_nlp_dataset`;
CREATE TABLE `tb_nlp_dataset`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '所属部门',
  `dt_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据集类型编码',
  `dt_type_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据集类型名称',
  `dt_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据集编号',
  `dt_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据集名称',
  `dt_text` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据集内容',
  `dt_size` decimal(10, 0) NOT NULL COMMENT '数据集大小',
  `dt_create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据集创建人',
  `dt_create_time` datetime NOT NULL COMMENT '数据集创建时间',
  `dt_update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据集更新人',
  `dt_update_time` datetime NULL DEFAULT NULL COMMENT '数据集更新时间',
  `dt_memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '自然语言分词数据集' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_nlp_dataset
-- ----------------------------
INSERT INTO `tb_nlp_dataset` VALUES ('1915660567738568705', 'admin', '2025-04-25 14:54:05', 'admin', '2025-04-25 15:01:48', 'A01', 'D-1001', '文学', 'D-1001001', '朱自清《春》', '盼望着，盼望着，东风来了，春天的脚步近了。\n\n　　一切都像刚睡醒的样子，欣欣然张开了眼。山朗润起来了，水涨起来了，太阳的脸红起来了。\n\n　　小草偷偷地从土里钻出来，嫩嫩的，绿绿的。园子里，田野里，瞧去，一大片一大片满是的。坐着，躺着，打两个滚，踢几脚球，赛几趟跑，捉几回迷藏。风轻悄悄的，草软绵绵的。\n\n　　桃树、杏树、梨树，你不让我，我不让你，都开满了花赶趟儿。红的像火，粉的像霞，白的像雪。花里带着甜味儿；闭了眼，树上仿佛已经满是桃儿、杏儿、梨儿。花下成千成百的蜜蜂嗡嗡地闹着，大小的蝴蝶飞来飞去。野花遍地是：杂样儿，有名字的，没名字的，散在草丛里，像眼睛，像星星，还眨呀眨的。\n\n　　“吹面不寒杨柳风”，不错的，像母亲的手抚摸着你。风里带来些新翻的泥土的气息，混着青草味儿，还有各种花的香，都在微微润湿的空气里酝酿。鸟儿将巢安在繁花嫩叶当中，高兴起来了，呼朋引伴地卖弄清脆的喉咙，唱出宛转的曲子，跟轻风流水应和着。牛背上牧童的短笛，这时候也成天嘹亮地响着。\n\n　　雨是最寻常的，一下就是三两天。可别恼。看，像牛毛，像花针，像细丝，密密地斜织着，人家屋顶上全笼着一层薄烟。树叶儿却绿得发亮，小草儿也青得逼你的眼。傍晚时候，上灯了，一点点黄晕的光，烘托出一片安静而和平的夜。在乡下，小路上，石桥边，有撑起伞慢慢走着的人，地里还有工作的农民，披着蓑戴着笠。他们的房屋，稀稀疏疏的，在雨里静默着。\n\n　　天上风筝渐渐多了，地上孩子也多了。城里乡下，家家户户，老老小小，也赶趟儿似的，一个个都出来了。舒活舒活筋骨，抖擞抖擞精神，各做各的一份儿事去。“一年之计在于春”，刚起头儿，有的是工夫，有的是希望。\n\n　　春天像刚落地的娃娃，从头到脚都是新的，它生长着。\n\n　　春天像小姑娘，花枝招展的，笑着，走着。\n\n　　春天像健壮的青年，有铁一般的胳膊和腰脚，领着我们上前去。', 10, 'admin', '2025-04-25 00:00:00', 'admin', '2025-04-25 15:01:48', NULL);
INSERT INTO `tb_nlp_dataset` VALUES ('1915662206931292162', 'admin', '2025-04-25 15:00:36', 'admin', '2025-04-25 15:02:09', 'A01', 'D-1002', '资讯', 'D-1002001', '神舟二十号成功发射', '神舟二十号3名航天员顺利进驻中国空间站。在轨执行任务的神舟十九号航天员乘组打开“家门”，欢迎远道而来的神舟二十号航天员乘组入驻中国空间站，这是中国航天史上第6次“太空会师”。\n\n随后，两个航天员乘组拍下“全家福”，共同向牵挂他们的全国人民报平安。\n\n后续，两个航天员乘组将在空间站进行在轨轮换。期间，6名航天员将共同在空间站工作生活约5天时间，完成各项既定工作。', 20, 'admin', '2025-04-25 00:00:00', 'admin', '2025-04-25 15:02:09', NULL);
INSERT INTO `tb_nlp_dataset` VALUES ('1915663623318392833', 'admin', '2025-04-25 15:06:14', 'admin', '2025-04-25 15:06:34', 'A01', 'D-1003', '文艺', 'D-1003001', '绕口令', '下雨天留客天留我不留。\n我一把把把抓住了。\n我也想过过过儿过过的生活。\n说你行你就行不行也行。\n说不行就不行行也不行\n不服不行', 30, 'admin', '2025-04-25 00:00:00', 'admin', '2025-04-25 15:06:34', NULL);

SET FOREIGN_KEY_CHECKS = 1;
