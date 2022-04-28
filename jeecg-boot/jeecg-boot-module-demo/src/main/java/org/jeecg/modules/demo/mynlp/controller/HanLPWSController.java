package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-03-28-20:54
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.HanLPWSCommon;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.util.WsUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年03月28日 20:54
 */
@RestController
@RequestMapping("/mynlp/hanlp")
@Slf4j
public class HanLPWSController {

    /**
     * 对数据集进行分词
     * @param type
     * @param dataSetId
     * @param tbNlpDataset
     * @return
     */
    @PutMapping(value = "/hanLPWS")
    public Result<String> hanLPWS(@RequestParam("type") String type,@RequestParam(value = "dataSetId",required = false) String dataSetId,@RequestBody TbNlpDataset tbNlpDataset) {
        HanLPWSCommon ws = new HanLPWSCommon();
        String dataSetText = WsUtils.getRequestDataSet(dataSetId, tbNlpDataset);
        if(dataSetText == ""){
            return Result.error("未找到对应数据",null);
        }
        String hanlpWs = ws.getHanLPWSByMyAPI(dataSetText,type);
        log.info("HanLP调用成功");
        Result<String> result = new Result<String>();
        result.setResult(hanlpWs);
        result.setSuccess(true);
        return result;
    }
}
