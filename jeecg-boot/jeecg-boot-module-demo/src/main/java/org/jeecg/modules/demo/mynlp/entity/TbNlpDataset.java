package org.jeecg.modules.demo.mynlp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 自然语言分词数据集
 * @Author: jeecg-boot
 * @Date:   2022-04-05
 * @Version: V1.0
 */
@Data
@TableName("tb_nlp_dataset")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_nlp_dataset对象", description="自然语言分词数据集")
public class TbNlpDataset implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**数据集类型编码*/
	@Excel(name = "数据集类型编码", width = 15)
    @ApiModelProperty(value = "数据集类型编码")
    private String dtTypeCode;
	/**数据集类型名称*/
	@Excel(name = "数据集类型名称", width = 15)
    @ApiModelProperty(value = "数据集类型名称")
    private String dtTypeName;
	/**数据集编号*/
	@Excel(name = "数据集编号", width = 15)
    @ApiModelProperty(value = "数据集编号")
    private String dtNo;
	/**数据集名称*/
	@Excel(name = "数据集名称", width = 15)
    @ApiModelProperty(value = "数据集名称")
    private String dtName;
	/**数据集内容*/
	@Excel(name = "数据集内容", width = 15)
    @ApiModelProperty(value = "数据集内容")
    private String dtText;
	/**数据集大小*/
	@Excel(name = "数据集大小", width = 15)
    @ApiModelProperty(value = "数据集大小")
    private BigDecimal dtSize;
	/**数据集创建人*/
	@Excel(name = "数据集创建人", width = 15)
    @ApiModelProperty(value = "数据集创建人")
    private String dtCreateBy;
	/**数据集创建时间*/
	@Excel(name = "数据集创建时间", width = 15)
    @ApiModelProperty(value = "数据集创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date dtCreateTime;
	/**数据集更新人*/
	@Excel(name = "数据集更新人", width = 15)
    @ApiModelProperty(value = "数据集更新人")
    private String dtUpdateBy;
	/**数据集更新时间*/
	@Excel(name = "数据集更新时间", width = 15)
    @ApiModelProperty(value = "数据集更新时间")
    private Date dtUpdateTime;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String dtMemo;
}
