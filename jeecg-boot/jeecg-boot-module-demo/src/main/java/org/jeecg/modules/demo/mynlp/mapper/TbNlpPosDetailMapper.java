package org.jeecg.modules.demo.mynlp.mapper;

import java.util.List;
import org.jeecg.modules.demo.mynlp.entity.TbNlpPosDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 自然语言词性标注集明细
 * @Author: jeecg-boot
 * @Date:   2022-04-26
 * @Version: V1.0
 */
public interface TbNlpPosDetailMapper extends BaseMapper<TbNlpPosDetail> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<TbNlpPosDetail> selectByMainId(@Param("mainId") String mainId);
}
