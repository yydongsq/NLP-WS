package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-03-28-20:54
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.HanLPParticipleCommon;
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
public class HanLPParticipleController {
    @GetMapping(value = "/hello")
    public Result<String> hello(@RequestParam("type") String type) {
        HanLPParticipleCommon participle = new HanLPParticipleCommon();
        String text="中国和美国都有自己的发展目标。中国坚持改革开放，科学发展，力争成为一个富裕强大文明的国家，造福于中国人民和世界人民，同时坚持独立自主外交，维护世界和平。";
        System.out.println(type);
        String hanLPParticiple = participle.getHanLPParticiple(text,type);
        log.info("HanLP分词结果集hanLPParticiple：" + hanLPParticiple);
        Result<String> result = new Result<String>();
        result.setResult(hanLPParticiple);
        result.setSuccess(true);
        return result;
    }
}
