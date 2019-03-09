package cn.qumiandan.shop.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.SHErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.shop.api.IShopGameSwitchService;
import cn.qumiandan.shop.entity.ShopExtend;
import cn.qumiandan.shop.mapper.ShopExtendMapper;
import cn.qumiandan.shop.vo.ShopExtendVO;
import cn.qumiandan.utils.CopyBeanUtil;

@Component
@Service(interfaceClass=IShopGameSwitchService.class)
public class ShopGameSwitchServiceImpl implements IShopGameSwitchService{
	@Autowired
	private ShopExtendMapper shopExtendMapper;
	/**
	 * 店铺游戏开关接口实现
	 * 
	 */
	@Override
	public Integer gameSwitch(Long shopId,Byte gameSwitch){
		ShopExtend shopExtend = shopExtendMapper.selectOne(new QueryWrapper<ShopExtend>().eq("shop_id", shopId));
		if(null==shopExtend) {
			throw new QmdException(SHErrorCode.SH1002);
		}
		ShopExtend entity=new ShopExtend();
		entity.setId(shopExtend.getId());
		entity.setGameSwitch(gameSwitch);
		Integer update = shopExtendMapper.updateById(entity);
		return update;
		
		
	}
	@Override
	public ShopExtendVO getShopExtendByShopId(Long shopId) {
		
		ShopExtend shopExtend = shopExtendMapper.selectOne(new QueryWrapper<ShopExtend>().eq("shop_id", shopId));
		if(null!=shopExtend) {
			return CopyBeanUtil.copyBean(shopExtend, ShopExtendVO.class);
		}
		return null;
	}
	
	/**
	 * 店铺游戏开关是否开启接口实现
	 */
	/*public boolean checkSwitch(Long shopId) {
		ShopExtend selectByIdBean = shopExtendMapper.selectByShopId(shopId);
		if(StatusEnum.normal.getCode()==selectByIdBean.getGameSwitch()) {
			return true;
		}
		return false;
	}*/
	
	
	
}
