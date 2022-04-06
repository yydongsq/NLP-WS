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
    public Result<String> hello(@RequestParam("type") String type) {
        ThulacParticipleCommon participle = new ThulacParticipleCommon();
        String text="本文转自【央视新闻客户端】；\n" +
                "\n" +
                "当地时间4月5日，美国白宫新闻发言人普萨基在简报会表示，美国总统拜登签署的8亿美元对乌援助计划正在进行中，目前已经完成大部分交付。\n" +
                "\n" +
                "普萨基称，美国、欧盟及七国集团预计将于6日宣布对俄新制裁措施，包括禁止在俄罗斯的所有新投资，加大对俄罗斯的金融机构和国有企业的制裁，以及对俄罗斯政府官员及其家庭成员的制裁。\n" +
                "\n" +
                "普萨基指出，美国还将禁止俄罗斯使用美国银行储备偿还债务，此举的部分目标是消耗俄罗斯的财政资源。（央视记者 许弢）";
        System.out.println(type);
        String thulacParticiple = participle.getThulacParticiple(text,type);
        log.info("Thulac分词结果集thulacParticiple：" + thulacParticiple);
        Result<String> result = new Result<String>();
        result.setResult(thulacParticiple);
        result.setSuccess(true);
        return result;
    }
}
