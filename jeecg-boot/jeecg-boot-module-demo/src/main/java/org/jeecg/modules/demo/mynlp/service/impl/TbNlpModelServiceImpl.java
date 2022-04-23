package org.jeecg.modules.demo.mynlp.service.impl;

import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.entity.TbNlpModel;
import org.jeecg.modules.demo.mynlp.mapper.TbNlpDatasetMapper;
import org.jeecg.modules.demo.mynlp.mapper.TbNlpModelMapper;
import org.jeecg.modules.demo.mynlp.service.ITbNlpModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;

/**
 * @Description: 自然语言分词模型
 * @Author: jeecg-boot
 * @Date:   2022-04-13
 * @Version: V1.0
 */
@Service
public class TbNlpModelServiceImpl extends ServiceImpl<TbNlpModelMapper, TbNlpModel> implements ITbNlpModelService {

    @Autowired
    TbNlpModelMapper tbNlpModelMapper;
    @Override
    public boolean save(TbNlpModel entity) {
        entity.setModelUpdateBy(entity.getModelCreatBy());
        entity.setModelUpdateTime(new Date());
        entity.setModelStatus("未封装");
        tbNlpModelMapper.insert(entity);
        return false;
    }

    @Override
    public boolean updateById(TbNlpModel entity) {
        entity.setModelUpdateBy(entity.getModelCreatBy());
        entity.setModelUpdateTime(new Date());
        tbNlpModelMapper.updateById(entity);
        return false;
    }
}
