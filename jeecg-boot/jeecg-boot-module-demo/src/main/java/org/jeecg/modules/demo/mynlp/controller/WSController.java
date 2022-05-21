package org.jeecg.modules.demo.mynlp.controller;/**
 * @author sq
 * @create 2022-03-28-20:54
 */

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.util.WsUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年03月28日 20:54
 */
@RestController
@RequestMapping("/mynlp")
@Slf4j
public class WSController {

    /**
     * 对数据集进行分词
     * @param dataSetId
     * @param tbNlpDataset
     * @return
     */
    @PutMapping(value = "/{modelWS}")
    public Result<String> hanLPWS(@RequestParam(value = "dataSetId",required = false) String dataSetId,
                                  @RequestBody TbNlpDataset tbNlpDataset,
                                  @PathVariable("modelWS") String modelWS) {
        Result<String> wsResult = WsUtils.getWSResult(modelWS, dataSetId, tbNlpDataset);
        return wsResult;
    }
}
