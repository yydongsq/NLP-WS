package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-04-06-20:36
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.JiebaParticipleCommon;
import org.jeecg.modules.demo.mynlp.common.LtpParticipleCommon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 20:36
 */
@RestController
@RequestMapping("/mynlp/ltp")
@Slf4j
public class LtpParticipleController {
    @GetMapping(value = "/ltpParticiple")
    public Result<String> hello(@RequestParam("type") String type) {
        LtpParticipleCommon participle = new LtpParticipleCommon();
        String text="我也想过过过儿过过的生活";
        System.out.println(type);
        String ltpParticiple = participle.getLtpParticiple(text,type);
        log.info("Ltp分词结果集ltpParticiple：" + ltpParticiple);
        Result<String> result = new Result<String>();
        result.setResult(ltpParticiple);
        result.setSuccess(true);
        return result;
    }
}
