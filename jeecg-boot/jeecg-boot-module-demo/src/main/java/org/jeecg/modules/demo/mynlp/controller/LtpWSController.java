package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-04-06-20:36
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.LtpWSCommon;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.service.ITbNlpDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 20:36
 */
@RestController
@RequestMapping("/mynlp/ltp")
@Slf4j
public class LtpWSController {

    @Autowired
    private ITbNlpDatasetService tbNlpDatasetService;

    @PutMapping(value = "/ltpWS")
    public Result<String> ltpWS(@RequestParam("type") String type,@RequestParam(value = "dataSetId",required = false) String dataSetId,@RequestBody TbNlpDataset tbNlpDataset) {
        LtpWSCommon participle = new LtpWSCommon();
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
        log.info("Ltp数据集text：" + text);
        String ltpParticiple = participle.getLtpWS(text,type);
        log.info("Ltp分词结果集ltpParticiple：" + ltpParticiple);
        Result<String> result = new Result<String>();
        result.setResult(ltpParticiple);
        result.setSuccess(true);
        return result;
    }
}
