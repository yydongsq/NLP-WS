package org.jeecg.modules.demo.mynlp.service.impl;

import org.jeecg.modules.demo.mynlp.entity.TbNlpPosDetail;
import org.jeecg.modules.demo.mynlp.mapper.TbNlpPosDetailMapper;
import org.jeecg.modules.demo.mynlp.service.ITbNlpPosDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 自然语言词性标注集明细
 * @Author: jeecg-boot
 * @Date:   2022-04-26
 * @Version: V1.0
 */
@Service
public class TbNlpPosDetailServiceImpl extends ServiceImpl<TbNlpPosDetailMapper, TbNlpPosDetail> implements ITbNlpPosDetailService {
	
	@Autowired
	private TbNlpPosDetailMapper tbNlpPosDetailMapper;
	
	@Override
	public List<TbNlpPosDetail> selectByMainId(String mainId) {
		return tbNlpPosDetailMapper.selectByMainId(mainId);
	}
}
