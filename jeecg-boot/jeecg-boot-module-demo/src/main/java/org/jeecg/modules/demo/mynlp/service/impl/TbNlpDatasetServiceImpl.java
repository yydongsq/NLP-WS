package org.jeecg.modules.demo.mynlp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.saxon.expr.instruct.ForEach;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.mapper.TbNlpDatasetMapper;
import org.jeecg.modules.demo.mynlp.service.ITbNlpDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description: 自然语言分词数据集
 * @Author: jeecg-boot
 * @Date:   2022-04-05
 * @Version: V1.0
 */
@Service
public class TbNlpDatasetServiceImpl extends ServiceImpl<TbNlpDatasetMapper, TbNlpDataset> implements ITbNlpDatasetService {

    @Autowired
    TbNlpDatasetMapper tbNlpDatasetMapper;
    @Override
    public boolean save(TbNlpDataset entity) {
        entity.setDtUpdateBy(entity.getDtCreateBy());
        entity.setDtUpdateTime(new Date());
        List<TbNlpDataset> tbNlpDatasets = tbNlpDatasetMapper.selectList(null);
        for (TbNlpDataset tbNlpDataset : tbNlpDatasets) {
            if(tbNlpDataset.getDtText().equals(entity.getDtText())){
                return false;
            }
        }
        tbNlpDatasetMapper.insert(entity);
        return true;
    }

    @Override
    public boolean updateById(TbNlpDataset entity) {
        entity.setDtUpdateBy(entity.getDtCreateBy());
        entity.setDtUpdateTime(new Date());
        List<TbNlpDataset> tbNlpDatasets = tbNlpDatasetMapper.selectList(null);
        for (TbNlpDataset tbNlpDataset : tbNlpDatasets) {
            if(!tbNlpDataset.getId().equals(entity.getId()) && tbNlpDataset.getDtText().equals(entity.getDtText())){
                return false;
            }
        }
        tbNlpDatasetMapper.updateById(entity);
        return true;
    }
}
