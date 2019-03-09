package cn.qumiandan.saobeishopinfo.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.saobeishopinfo.api.ISaoBeiShopInfoServicce;
import cn.qumiandan.saobeishopinfo.entity.SaoBeiShopInfo;
import cn.qumiandan.saobeishopinfo.mapper.SaoBeiShopInfoMapper;
import cn.qumiandan.saobeishopinfo.vo.SaoBeiShopInfoVO;
import cn.qumiandan.utils.CopyBeanUtil;

@Component
@Service(interfaceClass = ISaoBeiShopInfoServicce.class)
public class SaoBeiShopInfoServiceImpl implements ISaoBeiShopInfoServicce{

	@Autowired
	private SaoBeiShopInfoMapper saoBeiShopInfoMapper;
	
	/**
	 * 添加扫呗商户信息
	 * @param saoBeiShopInfoVO
	 * @return
	 */
	@Override
	public int addSaoBeiShopInfo(SaoBeiShopInfoVO saoBeiShopInfoVO) {
		if(saoBeiShopInfoVO == null) {
			throw new QmdException("创建扫呗商户时信息不能为空");
		}
		SaoBeiShopInfo saoBeiShopInfo = CopyBeanUtil.copyBean(saoBeiShopInfoVO, SaoBeiShopInfo.class);
		saoBeiShopInfo.setCreateDate(new Date());
		return saoBeiShopInfoMapper.insertSelective(saoBeiShopInfo);
	}

	
	/**
	 * 修改扫呗商户信息(根据店铺id修改)
	 * @param saoBeiShopInfoVO
	 * @return
	 */
	@Override
	public int updateSaoBeiShopInfo(SaoBeiShopInfoVO saoBeiShopInfoVO) {
		SaoBeiShopInfo saoBeiShopInfo = saoBeiShopInfoMapper.selectByShopId(saoBeiShopInfoVO.getShopId());
		if(saoBeiShopInfo == null) {
			throw new QmdException("该商户不存在");
		}
		saoBeiShopInfo = CopyBeanUtil.copyBean(saoBeiShopInfoVO, SaoBeiShopInfo.class);
		return saoBeiShopInfoMapper.updateByPrimaryKeySelective(saoBeiShopInfo);
	}
	
	/**
	 * 删除扫呗商户信息
	 * @param shopId
	 * @return
	 */
	@Override
	public int deleteSaoBeiShopInfo(Long shopId) {
		return saoBeiShopInfoMapper.deleteByPrimaryKey(shopId);
	}

	
	/**
	 * 根据店铺id查询扫呗商户信息
	 * @param shopId
	 * @return
	 */
	@Override
	public SaoBeiShopInfoVO getSaoBeiShopInfo(Long shopId) {
		SaoBeiShopInfo saoBeiShopInfo = saoBeiShopInfoMapper.selectByShopId(shopId);
		if(saoBeiShopInfo == null) {
			return null;
		}
		SaoBeiShopInfoVO saoBeiShopInfoVO =
		CopyBeanUtil.copyBean(saoBeiShopInfo, SaoBeiShopInfoVO.class);
		return saoBeiShopInfoVO;
	}

}
