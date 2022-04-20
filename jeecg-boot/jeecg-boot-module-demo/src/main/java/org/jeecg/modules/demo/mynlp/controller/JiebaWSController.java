package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-04-06-16:14
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.JiebaWSCommon;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.service.ITbNlpDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 16:14
 */
@RestController
@RequestMapping("/mynlp/jieba")
@Slf4j
public class JiebaWSController {

    @Autowired
    private ITbNlpDatasetService tbNlpDatasetService;

    @PutMapping(value = "/jiebaWS")
    public Result<String> jiebaWS(@RequestParam("type") String type,@RequestParam(value = "dataSetId",required = false) String dataSetId,@RequestBody TbNlpDataset tbNlpDataset) {
        JiebaWSCommon participle = new JiebaWSCommon();
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
        log.info("Jieba数据集text：" + text);
        String jiebaParticiple = participle.getJiebaWS(text,type);
        log.info("Jieba分词结果集jiebaParticiple：" + jiebaParticiple);
        Result<String> result = new Result<String>();
        result.setResult(jiebaParticiple);
        result.setSuccess(true);
        return result;
    }
}
