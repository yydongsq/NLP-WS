package org.jeecg.modules.demo.mynlp.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.entity.TbNlpModel;
import org.springframework.stereotype.Repository;

/**
 * @Description: 自然语言分词模型
 * @Author: jeecg-boot
 * @Date:   2022-04-13
 * @Version: V1.0
 */
@Repository
public interface TbNlpModelMapper extends BaseMapper<TbNlpModel> {
}
