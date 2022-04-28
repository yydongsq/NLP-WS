package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-04-06-16:14
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.JiebaWSCommon;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.util.WsUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 16:14
 */
@RestController
@RequestMapping("/mynlp/jieba")
@Slf4j
public class JiebaWSController {

    /**
     * 对数据集进行分词
     * @param type
     * @param dataSetId
     * @param tbNlpDataset
     * @return
     */
    @PutMapping(value = "/jiebaWS")
    public Result<String> jiebaWS(@RequestParam("type") String type,@RequestParam(value = "dataSetId",required = false) String dataSetId,@RequestBody TbNlpDataset tbNlpDataset) {
        JiebaWSCommon ws = new JiebaWSCommon();
        String dataSetText = WsUtils.getRequestDataSet(dataSetId, tbNlpDataset);
        if(dataSetText == ""){
            return Result.error("未找到对应数据",null);
        }
        String jiebaWs = ws.getJiebaWS(dataSetText,type);
        log.info("Jieba调用成功");
        Result<String> result = new Result<String>();
        result.setResult(jiebaWs);
        result.setSuccess(true);
        return result;
    }
}
