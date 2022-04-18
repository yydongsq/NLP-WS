package org.jeecg.modules.demo.mynlp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;

/**
 * @Description: 自然语言分词数据集
 * @Author: jeecg-boot
 * @Date:   2022-04-05
 * @Version: V1.0
 */
public interface ITbNlpDatasetService extends IService<TbNlpDataset> {
    /**
     * 保存
     * @param entity
     * @return
     */
    boolean save(TbNlpDataset entity);
}
