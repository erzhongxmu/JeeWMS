package com.base.modules.jeewms.service;

import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.jeewms.vo.EditWmOmNoticeIListVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description: 出货通知项目
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
public interface IWmOmNoticeIService extends IService<WmOmNoticeI> {

	public List<WmOmNoticeI> selectByMainId(String mainId);

	/**
	 * @Describe : 批量修改出货通知项目
	 * @Author: zly
	 * @Create Date: 2021-05-18
	 */
	public Result<?> editWmOmNoticeIList(List<EditWmOmNoticeIListVo> voList);

	/**
	 * @Describe : 添加出库项目明细
	 * @Author: zly
	 * @Create Date: 2021-05-28
	 */
	public Result<?> saveWmOmQmIM(@RequestBody WmOmNoticeI wmOmNoticeI);

	public List<WmOmNoticeI> selectByMainNo(String mainNo);
}
