package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-03-28-20:54
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.HanLPWSCommon;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.service.ITbNlpDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年03月28日 20:54
 */
@RestController
@RequestMapping("/mynlp/hanlp")
@Slf4j
public class HanLPWSController {

    @Autowired
    private ITbNlpDatasetService tbNlpDatasetService;

    @PutMapping(value = "/hanLPWS")
    public Result<String> hanLPWS(@RequestParam("type") String type,@RequestParam(value = "dataSetId",required = false) String dataSetId,@RequestBody TbNlpDataset tbNlpDataset) {
        HanLPWSCommon participle = new HanLPWSCommon();
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
        log.info("HanLP数据集text：" + text);
        String hanLPParticiple = participle.getHanLPWSByMyAPI(text,type);
        log.info("HanLP分词结果集hanLPParticiple：" + hanLPParticiple);
        Result<String> result = new Result<String>();
        result.setResult(hanLPParticiple);
        result.setSuccess(true);
        return result;
    }
}
