package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-04-06-20:36
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.LtpWSCommon;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.util.WsUtils;
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

    /**
     * 对数据集进行分词
     * @param dataSetId
     * @param tbNlpDataset
     * @return
     */
    @PutMapping(value = "/ltpWS")
    public Result<String> ltpWS(@RequestParam(value = "dataSetId",required = false) String dataSetId,@RequestBody TbNlpDataset tbNlpDataset) {
        LtpWSCommon ws = new LtpWSCommon();
        String dataSetText = WsUtils.getRequestDataSet(dataSetId, tbNlpDataset);
        if(dataSetText == ""){
            return Result.error("未找到对应数据",null);
        }
        String ltpWs = ws.getLtpWS(dataSetText);
        log.info("Ltp调用成功");
        Result<String> result = new Result<String>();
        result.setResult(ltpWs);
        result.setSuccess(true);
        return result;
    }
}
