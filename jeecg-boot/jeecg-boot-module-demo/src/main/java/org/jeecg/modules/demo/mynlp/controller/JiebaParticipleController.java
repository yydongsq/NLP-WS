package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-04-06-16:14
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.JiebaParticipleCommon;
import org.jeecg.modules.demo.mynlp.common.ThulacParticipleCommon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 16:14
 */
@RestController
@RequestMapping("/mynlp/jieba")
@Slf4j
public class JiebaParticipleController {
    @GetMapping(value = "/jiebaParticiple")
    public Result<String> hello(@RequestParam("type") String type) {
        JiebaParticipleCommon participle = new JiebaParticipleCommon();
        String text="我也想过过过儿过过的生活";
        System.out.println(type);
        String thulacParticiple = participle.getThulacParticiple(text,type);
        log.info("Thulac分词结果集thulacParticiple：" + thulacParticiple);
        Result<String> result = new Result<String>();
        result.setResult(thulacParticiple);
        result.setSuccess(true);
        return result;
    }
}
