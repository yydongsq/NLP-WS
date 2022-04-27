package org.jeecg.modules.demo.mynlp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.mynlp.entity.TbNlpPos;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 自然语言词性标注集
 * @Author: jeecg-boot
 * @Date:   2022-04-26
 * @Version: V1.0
 */
@Repository
public interface TbNlpPosMapper extends BaseMapper<TbNlpPos> {

}
