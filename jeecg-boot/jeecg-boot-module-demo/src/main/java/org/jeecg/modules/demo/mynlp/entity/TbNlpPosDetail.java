package org.jeecg.modules.demo.mynlp.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 自然语言词性标注集明细
 * @Author: jeecg-boot
 * @Date:   2022-04-26
 * @Version: V1.0
 */
@ApiModel(value="tb_nlp_pos_detail对象", description="自然语言词性标注集明细")
@Data
@TableName("tb_nlp_pos_detail")
public class TbNlpPosDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**外键*/
    @ApiModelProperty(value = "外键")
    private java.lang.String posMainId;
	/**词性标签*/
	@Excel(name = "词性标签", width = 15)
    @ApiModelProperty(value = "词性标签")
    private java.lang.String posTags;
	/**词性含义*/
	@Excel(name = "词性含义", width = 15)
    @ApiModelProperty(value = "词性含义")
    private java.lang.String posMeaning;
	/**词性描述*/
	@Excel(name = "词性描述", width = 15)
    @ApiModelProperty(value = "词性描述")
    private java.lang.String posDescription;
	/**词性创建人*/
	@Excel(name = "词性创建人", width = 15)
    @ApiModelProperty(value = "词性创建人")
    private java.lang.String posCreateBy;
	/**词性创建时间*/
	@Excel(name = "词性创建时间", width = 15)
    @ApiModelProperty(value = "词性创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private java.util.Date posCreateTime;
	/**词性更新人*/
	@Excel(name = "词性更新人", width = 15)
    @ApiModelProperty(value = "词性更新人")
    private java.lang.String posUpdateBy;
	/**词性更新时间*/
	@Excel(name = "词性更新时间", width = 15)
    @ApiModelProperty(value = "词性更新时间")
    private java.util.Date posUpdateTime;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String memo;
}
