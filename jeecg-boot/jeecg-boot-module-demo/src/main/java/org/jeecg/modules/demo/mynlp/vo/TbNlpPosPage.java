package org.jeecg.modules.demo.mynlp.vo;

import java.util.List;
import org.jeecg.modules.demo.mynlp.entity.TbNlpPos;
import org.jeecg.modules.demo.mynlp.entity.TbNlpPosDetail;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 自然语言词性标注集
 * @Author: jeecg-boot
 * @Date:   2022-04-26
 * @Version: V1.0
 */
@Data
@ApiModel(value="tb_nlp_posPage对象", description="自然语言词性标注集")
public class TbNlpPosPage {

	/**主键*/
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
	/**词性标注集编号*/
	@Excel(name = "词性标注集编号", width = 15)
	@ApiModelProperty(value = "词性标注集编号")
    private java.lang.String posNo;
	/**词性标注集名称*/
	@Excel(name = "词性标注集名称", width = 15)
	@ApiModelProperty(value = "词性标注集名称")
    private java.lang.String posName;
	/**词性标注集创建人*/
	@Excel(name = "词性标注集创建人", width = 15)
	@ApiModelProperty(value = "词性标注集创建人")
    private java.lang.String posCreateBy;
	/**词性标注集创建时间*/
	@Excel(name = "词性标注集创建时间", width = 15)
	@ApiModelProperty(value = "词性标注集创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private java.util.Date posCreateTime;
	/**词性标注集更新人*/
	@Excel(name = "词性标注集更新人", width = 15)
	@ApiModelProperty(value = "词性标注集更新人")
    private java.lang.String posUpdateBy;
	/**词性标注集更新时间*/
	@Excel(name = "词性标注集更新时间", width = 15)
	@ApiModelProperty(value = "词性标注集更新时间")
    private java.util.Date posUpdateTime;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
    private java.lang.String memo;

	@ExcelCollection(name="自然语言词性标注集明细")
	@ApiModelProperty(value = "自然语言词性标注集明细")
	private List<TbNlpPosDetail> tbNlpPosDetailList;

}
