package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-04-05-17:43
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.HanLPParticipleCommon;
import org.jeecg.modules.demo.mynlp.common.ThulacParticipleCommon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月05日 17:43
 */
@RestController
@RequestMapping("/mynlp/thulac")
@Slf4j
public class ThulacParticipleController {
    @GetMapping(value = "/thulacParticiple")
    public Result<String> hello(@RequestParam("type") String type,@RequestParam("dataSet") String dataSet) {
        ThulacParticipleCommon participle = new ThulacParticipleCommon();
        String text = dataSet;
        System.out.println(type);
        String thulacParticiple = participle.getThulacParticiple(text,type);
        log.info("Thulac分词结果集thulacParticiple：" + thulacParticiple);
        Result<String> result = new Result<String>();
        result.setResult(thulacParticiple);
        result.setSuccess(true);
        return result;
    }
}
