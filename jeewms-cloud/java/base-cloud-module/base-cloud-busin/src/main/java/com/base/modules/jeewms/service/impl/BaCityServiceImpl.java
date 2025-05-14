package com.base.modules.jeewms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.entity.BaCity;
import com.base.modules.jeewms.mapper.BaCityMapper;
import com.base.modules.jeewms.service.IBaCityService;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description: 地区信息
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
@Service
public class BaCityServiceImpl extends ServiceImpl<BaCityMapper, BaCity> implements IBaCityService {

	@Override
	public void addBaCity(BaCity baCity) {
		if(oConvertUtils.isEmpty(baCity.getPid())){
			baCity.setPid(IBaCityService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			BaCity parent = baseMapper.selectById(baCity.getPid());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(baCity);
	}

	@Override
	public void updateBaCity(BaCity baCity) {
		BaCity entity = this.getById(baCity.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getPid();
		String new_pid = baCity.getPid();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				baCity.setPid(IBaCityService.ROOT_PID_VALUE);
			}
			if(!IBaCityService.ROOT_PID_VALUE.equals(baCity.getPid())) {
				baseMapper.updateTreeNodeStatus(baCity.getPid(), IBaCityService.HASCHILD);
			}
		}
		baseMapper.updateById(baCity);
	}

	@Override
	public void deleteBaCity(String id) throws JeecgBootException {
		BaCity baCity = this.getById(id);
		if(baCity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		updateOldParentNode(baCity.getPid());
		baseMapper.deleteById(id);
	}

	/**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!IBaCityService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<BaCity>().eq("pid", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, IBaCityService.NOCHILD);
			}
		}
	}

	/**
	 * @Describe : 地区下拉数据查询
	 * @Author: zly
	 * @Create Date: 2021-05-18
	 */
	@Override
	public Result<?> getChild() {
		//获取父级
		QueryWrapper queryWrapper = new QueryWrapper();
		List<BaCity> baCityList = baseMapper.selectList(queryWrapper);

		List<BaCity> listUser = baCityList.stream().filter(baCity -> "0".equals(baCity.getPid()))
				.peek(baCity -> baCity.setBaCityChild(getChildrens(baCity,baCityList))).collect(Collectors.toList());

		return Result.ok(listUser);
	}

	/**
	 * 获取子集
	 * @param baCity
	 * @param baCityList
	 * @return
	 */
	private List<BaCity> getChildrens(BaCity baCity, List<BaCity> baCityList) {
		List<BaCity> childrens = baCityList.stream().filter(u -> Objects.equals(u.getPid(),baCity.getId())).map(
				u -> {
					u.setBaCityChild(getChildrens(u,baCityList));
					return u;
				}
		).collect(Collectors.toList());
		return childrens;
	}
}
