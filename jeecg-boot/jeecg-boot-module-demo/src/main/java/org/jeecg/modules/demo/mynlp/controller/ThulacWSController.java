package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-04-05-17:43
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.ThulacWSCommon;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.service.ITbNlpDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月05日 17:43
 */
@RestController
@RequestMapping("/mynlp/thulac")
@Slf4j
public class ThulacWSController {

    @Autowired
    private ITbNlpDatasetService tbNlpDatasetService;

    @PutMapping(value = "/thulacWS")
    public Result<String> thulacWS(@RequestParam("type") String type,@RequestParam(value = "dataSetId",required = false) String dataSetId,@RequestBody TbNlpDataset tbNlpDataset) {
        ThulacWSCommon participle = new ThulacWSCommon();
        String text = "";
        if(dataSetId == null && tbNlpDataset.getDtText() != null){
            text = tbNlpDataset.getDtText();
        }else{
            TbNlpDataset dataset = tbNlpDatasetService.getById(dataSetId);
            if(dataset == null) {
                return Result.error("未找到对应数据",null);
            }else{
                text = dataset.getDtText();
            }
        }
        log.info("Thulac数据集text：" + text);
        String thulacParticiple = participle.getThulacWS(text,type);
        log.info("Thulac分词结果集thulacParticiple：" + thulacParticiple);
        Result<String> result = new Result<String>();
        result.setResult(thulacParticiple);
        result.setSuccess(true);
        return result;
    }
}
