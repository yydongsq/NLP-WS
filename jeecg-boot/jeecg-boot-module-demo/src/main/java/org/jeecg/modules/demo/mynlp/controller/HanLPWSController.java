package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-03-28-20:54
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.HanLPWSCommon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年03月28日 20:54
 */
@RestController
@RequestMapping("/mynlp/hanlp")
@Slf4j
public class HanLPWSController {
    @GetMapping(value = "/hanLPWS")
    public Result<String> hanLPWS(@RequestParam("type") String type,@RequestParam("dataSet") String dataSet) {
        HanLPWSCommon participle = new HanLPWSCommon();
        String text = dataSet;
        System.out.println(type);
        String hanLPParticiple = participle.getHanLPWSByMyAPI(text,type);
        log.info("HanLP分词结果集hanLPParticiple：" + hanLPParticiple);
        Result<String> result = new Result<String>();
        result.setResult(hanLPParticiple);
        result.setSuccess(true);
        return result;
    }
}