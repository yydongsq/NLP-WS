package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-04-05-17:43
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.ThulacWSCommon;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.util.WsUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月05日 17:43
 */
@RestController
@RequestMapping("/mynlp/thulac")
@Slf4j
public class ThulacWSController {

    /**
     * 对数据集进行分词
     * @param type
     * @param dataSetId
     * @param tbNlpDataset
     * @return
     */
    @PutMapping(value = "/thulacWS")
    public Result<String> thulacWS(@RequestParam("type") String type,@RequestParam(value = "dataSetId",required = false) String dataSetId,@RequestBody TbNlpDataset tbNlpDataset) {
        ThulacWSCommon ws = new ThulacWSCommon();
        String dataSetText = WsUtils.getRequestDataSet(dataSetId, tbNlpDataset);
        if(dataSetText == ""){
            return Result.error("未找到对应数据",null);
        }
        String thulacWs = ws.getThulacWS(dataSetText,type);
        log.info("Thulac调用成功");
        Result<String> result = new Result<String>();
        result.setResult(thulacWs);
        result.setSuccess(true);
        return result;
    }
}
