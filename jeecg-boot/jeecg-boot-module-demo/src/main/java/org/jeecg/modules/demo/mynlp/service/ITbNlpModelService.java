package org.jeecg.modules.demo.mynlp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.mynlp.entity.TbNlpModel;

import java.util.List;

/**
 * @Description: 自然语言分词模型
 * @Author: jeecg-boot
 * @Date:   2022-04-13
 * @Version: V1.0
 */
public interface ITbNlpModelService extends IService<TbNlpModel> {


    /**
     * 保存
     * @param entity
     * @return
     */
    boolean save(TbNlpModel entity);

    /**
     * 编辑
     * @param entity
     * @return
     */
    @Override
    boolean updateById(TbNlpModel entity);

    /**
     * 通过模型状态查询列表
     * @param queryWrapper
     * @return
     */
    List<TbNlpModel> selectListByStatus(QueryWrapper<TbNlpModel> queryWrapper);
}
