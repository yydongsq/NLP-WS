package org.jeecg.modules.demo.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年03月15日 14:59
 */
@RestController
@RequestMapping("/test/jeecgDemo")
@Slf4j
public class JeecgDemoController {
    @GetMapping(value = "/hello")
    public Result<String> hello() {
        Result<String> result = new Result<String>();
        result.setResult("I LOVE CYH!");
        result.setSuccess(true);
        return result;
    }
}
