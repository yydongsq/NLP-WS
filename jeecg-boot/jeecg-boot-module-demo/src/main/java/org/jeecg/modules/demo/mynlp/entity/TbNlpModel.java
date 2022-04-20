package org.jeecg.modules.demo.mynlp.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 自然语言分词模型
 * @Author: jeecg-boot
 * @Date:   2022-04-13
 * @Version: V1.0
 */
@Data
@TableName("tb_nlp_model")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_nlp_model对象", description="自然语言分词模型")
public class TbNlpModel implements Serializable {
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
	/**模型类型编码*/
	@Excel(name = "模型类型编码", width = 15)
    @ApiModelProperty(value = "模型类型编码")
    private String modelTypeCode;
	/**模型类型名称*/
	@Excel(name = "模型类型名称", width = 15)
    @ApiModelProperty(value = "模型类型名称")
    private String modelTypeName;
	/**模型编号*/
	@Excel(name = "模型编号", width = 15)
    @ApiModelProperty(value = "模型编号")
    private String modelNo;
	/**模型名称*/
	@Excel(name = "模型名称", width = 15)
    @ApiModelProperty(value = "模型名称")
    private String modelName;
	/**模型来源*/
	@Excel(name = "模型来源", width = 15)
    @ApiModelProperty(value = "模型来源")
    private String modelSource;
	/**模型创建人*/
	@Excel(name = "模型创建人", width = 15)
    @ApiModelProperty(value = "模型创建人")
    private String modelCreatBy;
	/**模型创建时间*/
	@Excel(name = "模型创建时间", width = 15)
    @ApiModelProperty(value = "模型创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date modelCreatTime;
	/**模型更新人*/
	@Excel(name = "模型更新人", width = 15)
    @ApiModelProperty(value = "模型更新人")
    private String modelUpdateBy;
	/**模型更新时间*/
	@Excel(name = "模型更新时间", width = 15)
    @ApiModelProperty(value = "模型更新时间")
    private Date modelUpdateTime;
	/**模型状态*/
	@Excel(name = "模型状态", width = 15)
    @ApiModelProperty(value = "模型状态")
    private String modelStatus;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String memo;
}
