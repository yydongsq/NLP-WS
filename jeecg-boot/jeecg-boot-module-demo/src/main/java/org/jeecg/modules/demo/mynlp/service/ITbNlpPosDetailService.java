package org.jeecg.modules.demo.mynlp.service;

import org.jeecg.modules.demo.mynlp.entity.TbNlpPosDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 自然语言词性标注集明细
 * @Author: jeecg-boot
 * @Date:   2022-04-26
 * @Version: V1.0
 */
public interface ITbNlpPosDetailService extends IService<TbNlpPosDetail> {

	public List<TbNlpPosDetail> selectByMainId(String mainId);

	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	boolean save(TbNlpPosDetail entity);

	/**
	 * 编辑
	 * @param entity
	 * @return
	 */
	@Override
	boolean updateById(TbNlpPosDetail entity);
}
