package org.jeecg.modules.demo.mynlp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.service.ITbNlpDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
* @Description: 自然语言分词数据集
* @Author: jeecg-boot
* @Date:   2022-04-05
* @Version: V1.0
*/
@Api(tags="自然语言分词数据集")
@RestController
@RequestMapping("/mynlp/tbNlpDataset")
@Slf4j
public class TbNlpDatasetController extends JeecgController<TbNlpDataset, ITbNlpDatasetService> {
   @Autowired
   private ITbNlpDatasetService tbNlpDatasetService;

   /**
    * 分页列表查询
    *
    * @param tbNlpDataset
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   //@AutoLog(value = "自然语言分词数据集-分页列表查询")
   @ApiOperation(value="自然语言分词数据集-分页列表查询", notes="自然语言分词数据集-分页列表查询")
   @GetMapping(value = "/list")
   public Result<IPage<TbNlpDataset>> queryPageList(TbNlpDataset tbNlpDataset,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<TbNlpDataset> queryWrapper = QueryGenerator.initQueryWrapper(tbNlpDataset, req.getParameterMap());
       Page<TbNlpDataset> page = new Page<TbNlpDataset>(pageNo, pageSize);
       IPage<TbNlpDataset> pageList = tbNlpDatasetService.page(page, queryWrapper);
       return Result.OK(pageList);
   }

   /**
    *   添加
    *
    * @param tbNlpDataset
    * @return
    */
   @AutoLog(value = "自然语言分词数据集-添加")
   @ApiOperation(value="自然语言分词数据集-添加", notes="自然语言分词数据集-添加")
   @PostMapping(value = "/add")
   public Result<String> add(@RequestBody TbNlpDataset tbNlpDataset) {
       tbNlpDatasetService.save(tbNlpDataset);
       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param tbNlpDataset
    * @return
    */
   @AutoLog(value = "自然语言分词数据集-编辑")
   @ApiOperation(value="自然语言分词数据集-编辑", notes="自然语言分词数据集-编辑")
   @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
   public Result<String> edit(@RequestBody TbNlpDataset tbNlpDataset) {
       tbNlpDatasetService.updateById(tbNlpDataset);
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "自然语言分词数据集-通过id删除")
   @ApiOperation(value="自然语言分词数据集-通过id删除", notes="自然语言分词数据集-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<String> delete(@RequestParam(name="id",required=true) String id) {
       tbNlpDatasetService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "自然语言分词数据集-批量删除")
   @ApiOperation(value="自然语言分词数据集-批量删除", notes="自然语言分词数据集-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.tbNlpDatasetService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   //@AutoLog(value = "自然语言分词数据集-通过id查询")
   @ApiOperation(value="自然语言分词数据集-通过id查询", notes="自然语言分词数据集-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<TbNlpDataset> queryById(@RequestParam(name="id",required=true) String id) {
       TbNlpDataset tbNlpDataset = tbNlpDatasetService.getById(id);
       if(tbNlpDataset==null) {
           return Result.error("未找到对应数据",null);
       }
       return Result.OK(tbNlpDataset);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param tbNlpDataset
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, TbNlpDataset tbNlpDataset) {
       return super.exportXls(request, tbNlpDataset, TbNlpDataset.class, "自然语言分词数据集");
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
       return super.importExcel(request, response, TbNlpDataset.class);
   }

}
