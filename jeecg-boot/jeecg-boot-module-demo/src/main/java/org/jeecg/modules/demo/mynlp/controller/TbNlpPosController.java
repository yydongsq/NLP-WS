package org.jeecg.modules.demo.mynlp.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.mynlp.entity.TbNlpPosDetail;
import org.jeecg.modules.demo.mynlp.entity.TbNlpPos;
import org.jeecg.modules.demo.mynlp.vo.TbNlpPosPage;
import org.jeecg.modules.demo.mynlp.service.ITbNlpPosService;
import org.jeecg.modules.demo.mynlp.service.ITbNlpPosDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 自然语言词性标注集
 * @Author: jeecg-boot
 * @Date:   2022-04-26
 * @Version: V1.0
 */
@Api(tags="自然语言词性标注集")
@RestController
@RequestMapping("/mynlp/tbNlpPos")
@Slf4j
public class TbNlpPosController {
	@Autowired
	private ITbNlpPosService tbNlpPosService;
	@Autowired
	private ITbNlpPosDetailService tbNlpPosDetailService;

	/**
	 * 分页列表查询
	 *
	 * @param tbNlpPos
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "自然语言词性标注集-分页列表查询")
	@ApiOperation(value="自然语言词性标注集-分页列表查询", notes="自然语言词性标注集-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TbNlpPos tbNlpPos,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TbNlpPos> queryWrapper = QueryGenerator.initQueryWrapper(tbNlpPos, req.getParameterMap());
		Page<TbNlpPos> page = new Page<TbNlpPos>(pageNo, pageSize);
		IPage<TbNlpPos> pageList = tbNlpPosService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param tbNlpPosPage
	 * @return
	 */
	@AutoLog(value = "自然语言词性标注集-添加")
	@ApiOperation(value="自然语言词性标注集-添加", notes="自然语言词性标注集-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TbNlpPosPage tbNlpPosPage) {
		TbNlpPos tbNlpPos = new TbNlpPos();
		BeanUtils.copyProperties(tbNlpPosPage, tbNlpPos);
		tbNlpPosService.saveMain(tbNlpPos, tbNlpPosPage.getTbNlpPosDetailList());
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param tbNlpPosPage
	 * @return
	 */
	@AutoLog(value = "自然语言词性标注集-编辑")
	@ApiOperation(value="自然语言词性标注集-编辑", notes="自然语言词性标注集-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<?> edit(@RequestBody TbNlpPosPage tbNlpPosPage) {
		TbNlpPos tbNlpPos = new TbNlpPos();
		BeanUtils.copyProperties(tbNlpPosPage, tbNlpPos);
		TbNlpPos tbNlpPosEntity = tbNlpPosService.getById(tbNlpPos.getId());
		if(tbNlpPosEntity==null) {
			return Result.error("未找到对应数据");
		}
		tbNlpPos.setPosUpdateBy(tbNlpPos.getPosCreateBy());
		tbNlpPos.setPosUpdateTime(new Date());
		tbNlpPosService.updateMain(tbNlpPos, tbNlpPosPage.getTbNlpPosDetailList());
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "自然语言词性标注集-通过id删除")
	@ApiOperation(value="自然语言词性标注集-通过id删除", notes="自然语言词性标注集-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		tbNlpPosService.delMain(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "自然语言词性标注集-批量删除")
	@ApiOperation(value="自然语言词性标注集-批量删除", notes="自然语言词性标注集-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.tbNlpPosService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "自然语言词性标注集-通过id查询")
	@ApiOperation(value="自然语言词性标注集-通过id查询", notes="自然语言词性标注集-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TbNlpPos tbNlpPos = tbNlpPosService.getById(id);
		if(tbNlpPos==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(tbNlpPos);

	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "自然语言词性标注集明细通过主表ID查询")
	@ApiOperation(value="自然语言词性标注集明细主表ID查询", notes="自然语言词性标注集明细-通主表ID查询")
	@GetMapping(value = "/queryTbNlpPosDetailByMainId")
	public Result<?> queryTbNlpPosDetailListByMainId(@RequestParam(name="id",required=true) String id) {
		List<TbNlpPosDetail> tbNlpPosDetailList = tbNlpPosDetailService.selectByMainId(id);
		return Result.OK(tbNlpPosDetailList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tbNlpPos
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TbNlpPos tbNlpPos) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<TbNlpPos> queryWrapper = QueryGenerator.initQueryWrapper(tbNlpPos, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<TbNlpPos> queryList = tbNlpPosService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<TbNlpPos> tbNlpPosList = new ArrayList<TbNlpPos>();
      if(oConvertUtils.isEmpty(selections)) {
          tbNlpPosList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          tbNlpPosList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<TbNlpPosPage> pageList = new ArrayList<TbNlpPosPage>();
      for (TbNlpPos main : tbNlpPosList) {
          TbNlpPosPage vo = new TbNlpPosPage();
          BeanUtils.copyProperties(main, vo);
          List<TbNlpPosDetail> tbNlpPosDetailList = tbNlpPosDetailService.selectByMainId(main.getId());
          vo.setTbNlpPosDetailList(tbNlpPosDetailList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "自然语言词性标注集列表");
      mv.addObject(NormalExcelConstants.CLASS, TbNlpPosPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("自然语言词性标注集数据", "导出人:"+sysUser.getRealname(), "自然语言词性标注集"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<TbNlpPosPage> list = ExcelImportUtil.importExcel(file.getInputStream(), TbNlpPosPage.class, params);
              for (TbNlpPosPage page : list) {
                  TbNlpPos po = new TbNlpPos();
                  BeanUtils.copyProperties(page, po);
                  tbNlpPosService.saveMain(po, page.getTbNlpPosDetailList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
