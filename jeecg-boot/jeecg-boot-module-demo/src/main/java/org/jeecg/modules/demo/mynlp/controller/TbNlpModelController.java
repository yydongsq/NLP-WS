package org.jeecg.modules.demo.mynlp.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.mynlp.entity.TbNlpModel;
import org.jeecg.modules.demo.mynlp.service.ITbNlpModelService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 自然语言分词模型
 * @Author: jeecg-boot
 * @Date:   2022-04-13
 * @Version: V1.0
 */
@Api(tags="自然语言分词模型")
@RestController
@RequestMapping("/mynlp/tbNlpModel")
@Slf4j
public class TbNlpModelController extends JeecgController<TbNlpModel, ITbNlpModelService> {
	@Autowired
	private ITbNlpModelService tbNlpModelService;

	/**
	 * 分页列表查询
	 *
	 * @param tbNlpModel
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "自然语言分词模型-分页列表查询")
	@ApiOperation(value="自然语言分词模型-分页列表查询", notes="自然语言分词模型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TbNlpModel>> queryPageList(TbNlpModel tbNlpModel,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TbNlpModel> queryWrapper = QueryGenerator.initQueryWrapper(tbNlpModel, req.getParameterMap());
		Page<TbNlpModel> page = new Page<TbNlpModel>(pageNo, pageSize);
		IPage<TbNlpModel> pageList = tbNlpModelService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param tbNlpModel
	 * @return
	 */
	@AutoLog(value = "自然语言分词模型-添加")
	@ApiOperation(value="自然语言分词模型-添加", notes="自然语言分词模型-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TbNlpModel tbNlpModel) {
		tbNlpModelService.save(tbNlpModel);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param tbNlpModel
	 * @return
	 */
	@AutoLog(value = "自然语言分词模型-编辑")
	@ApiOperation(value="自然语言分词模型-编辑", notes="自然语言分词模型-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TbNlpModel tbNlpModel) {
		tbNlpModelService.updateById(tbNlpModel);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "自然语言分词模型-通过id删除")
	@ApiOperation(value="自然语言分词模型-通过id删除", notes="自然语言分词模型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		tbNlpModelService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "自然语言分词模型-批量删除")
	@ApiOperation(value="自然语言分词模型-批量删除", notes="自然语言分词模型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.tbNlpModelService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "自然语言分词模型-通过id查询")
	@ApiOperation(value="自然语言分词模型-通过id查询", notes="自然语言分词模型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TbNlpModel> queryById(@RequestParam(name="id",required=true) String id) {
		TbNlpModel tbNlpModel = tbNlpModelService.getById(id);
		if(tbNlpModel==null) {
			return Result.error("未找到对应数据",null);
		}
		return Result.OK(tbNlpModel);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tbNlpModel
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TbNlpModel tbNlpModel) {
        return super.exportXls(request, tbNlpModel, TbNlpModel.class, "自然语言分词模型");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TbNlpModel.class);
    }

}
