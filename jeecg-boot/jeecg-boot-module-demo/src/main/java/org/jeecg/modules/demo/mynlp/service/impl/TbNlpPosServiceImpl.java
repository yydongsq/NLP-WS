package org.jeecg.modules.demo.mynlp.service.impl;

import org.jeecg.modules.demo.mynlp.entity.TbNlpPos;
import org.jeecg.modules.demo.mynlp.entity.TbNlpPosDetail;
import org.jeecg.modules.demo.mynlp.mapper.TbNlpPosDetailMapper;
import org.jeecg.modules.demo.mynlp.mapper.TbNlpPosMapper;
import org.jeecg.modules.demo.mynlp.service.ITbNlpPosService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 自然语言词性标注集
 * @Author: jeecg-boot
 * @Date:   2022-04-26
 * @Version: V1.0
 */
@Service
public class TbNlpPosServiceImpl extends ServiceImpl<TbNlpPosMapper, TbNlpPos> implements ITbNlpPosService {

	@Autowired
	private TbNlpPosMapper tbNlpPosMapper;
	@Autowired
	private TbNlpPosDetailMapper tbNlpPosDetailMapper;

	@Override
	@Transactional
	public void saveMain(TbNlpPos tbNlpPos, List<TbNlpPosDetail> tbNlpPosDetailList) {
		tbNlpPosMapper.insert(tbNlpPos);
		if(tbNlpPosDetailList!=null && tbNlpPosDetailList.size()>0) {
			for(TbNlpPosDetail entity:tbNlpPosDetailList) {
				//外键设置
				entity.setPosMainId(tbNlpPos.getId());
				tbNlpPosDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(TbNlpPos tbNlpPos,List<TbNlpPosDetail> tbNlpPosDetailList) {
		tbNlpPosMapper.updateById(tbNlpPos);

		//1.先删除子表数据
		tbNlpPosDetailMapper.deleteByMainId(tbNlpPos.getId());

		//2.子表数据重新插入
		if(tbNlpPosDetailList!=null && tbNlpPosDetailList.size()>0) {
			for(TbNlpPosDetail entity:tbNlpPosDetailList) {
				//外键设置
				entity.setPosMainId(tbNlpPos.getId());
				tbNlpPosDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		tbNlpPosDetailMapper.deleteByMainId(id);
		tbNlpPosMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			tbNlpPosDetailMapper.deleteByMainId(id.toString());
			tbNlpPosMapper.deleteById(id);
		}
	}

	@Override
	public boolean save(TbNlpPos entity) {
		entity.setPosUpdateBy(entity.getPosCreateBy());
		entity.setPosUpdateTime(new Date());
		return false;
	}

	@Override
	public boolean updateById(TbNlpPos entity) {
		entity.setPosUpdateBy(entity.getPosCreateBy());
		entity.setPosUpdateTime(new Date());
		return false;
	}

}
