package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-04-06-20:36
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.LtpWSCommon;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
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
    @PutMapping(value = "/ltpWS")
    public Result<String> ltpWS(@RequestParam("type") String type,@RequestParam(value = "dataSetId",required = false) String dataSetId,@RequestBody TbNlpDataset tbNlpDataset) {
        LtpWSCommon participle = new LtpWSCommon();
        String text = dataSetId;
        System.out.println(type);
        System.out.println(tbNlpDataset);
        String ltpParticiple = participle.getLtpWS(text,type);
        log.info("Ltp分词结果集ltpParticiple：" + ltpParticiple);
        Result<String> result = new Result<String>();
        result.setResult(ltpParticiple);
        result.setSuccess(true);
        return result;
    }
}
