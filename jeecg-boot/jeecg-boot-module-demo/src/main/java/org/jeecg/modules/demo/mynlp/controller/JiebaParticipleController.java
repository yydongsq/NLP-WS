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
    public Result<String> hello(@RequestParam("type") String type,@RequestParam("dataSet") String dataSet) {
        JiebaParticipleCommon participle = new JiebaParticipleCommon();
        String text = dataSet;
        System.out.println(type);
        String jiebaParticiple = participle.getJiebaParticiple(text,type);
        log.info("Jieba分词结果集jiebaParticiple：" + jiebaParticiple);
        Result<String> result = new Result<String>();
        result.setResult(jiebaParticiple);
        result.setSuccess(true);
        return result;
    }
}
