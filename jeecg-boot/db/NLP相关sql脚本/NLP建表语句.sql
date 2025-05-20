 CREATE TABLE IF NOT EXISTS `tb_nlp_dataset` (
  `id` VARCHAR(36) NOT NULL COMMENT '主键',
	`create_by` VARCHAR(32) NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL COMMENT '创建日期',
  `update_by` VARCHAR(32) DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` VARCHAR(64) DEFAULT NULL COMMENT '所属部门',
  `dt_type_code` VARCHAR(32) NOT NULL COMMENT '数据集类型编码',
  `dt_type_name` VARCHAR(64) NOT NULL COMMENT '数据集类型名称',
  `dt_no` VARCHAR(32) NOT NULL COMMENT '数据集编号',
  `dt_name` VARCHAR(64) NOT NULL COMMENT '数据集名称',
  `dt_text` LONGTEXT NOT NULL COMMENT '数据集内容',
  `dt_size` DECIMAL NOT NULL COMMENT '数据集大小',
  `dt_create_by` VARCHAR(32) NOT NULL COMMENT '数据集创建人',
  `dt_create_time` DATETIME NOT NULL COMMENT '数据集创建时间',
  `dt_update_by` VARCHAR(32) DEFAULT NULL COMMENT '数据集更新人',
  `dt_update_time` DATETIME DEFAULT NULL COMMENT '数据集更新时间',
  `dt_memo` VARCHAR(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='自然语言分词数据集';


 CREATE TABLE IF NOT EXISTS `tb_nlp_model` (
  `id` VARCHAR(36) NOT NULL COMMENT '主键',
	`create_by` VARCHAR(32) NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL COMMENT '创建日期',
  `update_by` VARCHAR(32) DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` VARCHAR(64) DEFAULT NULL COMMENT '所属部门',
  `model_type_code` VARCHAR(32) NOT NULL COMMENT '模型类型编码',
  `model_type_name` VARCHAR(64) NOT NULL COMMENT '模型类型名称',
  `model_no` VARCHAR(32) NOT NULL COMMENT '模型编号',
  `model_name` VARCHAR(64) NOT NULL COMMENT '模型名称',
  `model_source` VARCHAR(255) NOT NULL COMMENT '模型来源',
  `model_creat_by` VARCHAR(32) NOT NULL COMMENT '模型创建人',
  `model_creat_time` DATETIME NOT NULL COMMENT '模型创建时间',
  `model_update_by` VARCHAR(32) DEFAULT NULL COMMENT '模型更新人',
  `model_update_time` DATETIME DEFAULT NULL COMMENT '模型更新时间',
  `model_status` VARCHAR(32) DEFAULT NULL COMMENT '模型状态',
  `model_pos_id` VARCHAR(36) DEFAULT NULL COMMENT '词性标注集ID',
  `model_pos_name` VARCHAR(32) DEFAULT NULL COMMENT '词性标注集名称',
  `memo` VARCHAR(32) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='自然语言分词模型';


 CREATE TABLE IF NOT EXISTS `tb_nlp_pos` (
  `id` VARCHAR(36) NOT NULL COMMENT '主键',
	`create_by` VARCHAR(32) NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL COMMENT '创建日期',
  `update_by` VARCHAR(32) DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` VARCHAR(64) DEFAULT NULL COMMENT '所属部门',
  `pos_no` VARCHAR(32) NOT NULL COMMENT '词性标注集编号',
  `pos_name` VARCHAR(32) NOT NULL COMMENT '词性标注集名称',
  `pos_create_by` VARCHAR(32) NOT NULL COMMENT '词性标注集创建人',
  `pos_create_time` DATETIME NOT NULL COMMENT '词性标注集创建时间',
  `pos_update_by` VARCHAR(32) DEFAULT NULL COMMENT '词性标注集更新人',
  `pos_update_time` DATETIME DEFAULT NULL COMMENT '词性标注集更新时间',
  `memo` VARCHAR(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='自然语言词性标注集';


 CREATE TABLE IF NOT EXISTS `tb_nlp_pos_detail` (
  `id` VARCHAR(36) NOT NULL COMMENT '主键',
	`create_by` VARCHAR(32) NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL COMMENT '创建日期',
  `update_by` VARCHAR(32) DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` VARCHAR(64) DEFAULT NULL COMMENT '所属部门',
  `pos_main_id` VARCHAR(32) NOT NULL COMMENT '外键',
  `pos_tags` VARCHAR(32) NOT NULL COMMENT '词性标签',
  `pos_meaning` VARCHAR(32) NOT NULL COMMENT '词性含义',
  `pos_description` VARCHAR(64) NOT NULL COMMENT '词性描述',
  `pos_create_by` VARCHAR(32) NOT NULL COMMENT '词性创建人',
  `pos_create_time` DATETIME NOT NULL COMMENT '词性创建时间',
  `pos_update_by` VARCHAR(32) DEFAULT NULL COMMENT '词性更新人',
  `pos_update_time` DATETIME DEFAULT NULL COMMENT '词性更新时间',
  `memo` VARCHAR(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='词性标注集明细表';

ALTER TABLE `tb_nlp_pos_detail`
ADD CONSTRAINT `fk_pos_main_id`
FOREIGN KEY (`pos_main_id`) REFERENCES `tb_nlp_pos`(`id`);

