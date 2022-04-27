package org.jeecg.modules.demo.mynlp.service;

import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.entity.TbNlpPosDetail;
import org.jeecg.modules.demo.mynlp.entity.TbNlpPos;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 自然语言词性标注集
 * @Author: jeecg-boot
 * @Date:   2022-04-26
 * @Version: V1.0
 */
public interface ITbNlpPosService extends IService<TbNlpPos> {

	/**
	 * 添加一对多
	 *
	 */
	public void saveMain(TbNlpPos tbNlpPos,List<TbNlpPosDetail> tbNlpPosDetailList) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(TbNlpPos tbNlpPos,List<TbNlpPosDetail> tbNlpPosDetailList);

	/**
	 * 删除一对多
	 */
	public void delMain (String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	boolean save(TbNlpPos entity);

	/**
	 * 编辑
	 * @param entity
	 * @return
	 */
	@Override
	boolean updateById(TbNlpPos entity);
}
