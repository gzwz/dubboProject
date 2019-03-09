package cn.qumiandan.deliveryaddress.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.deliveryaddress.api.IDeliveryAddressService;
import cn.qumiandan.deliveryaddress.entity.DeliveryAddress;
import cn.qumiandan.deliveryaddress.mapper.DeliveryAddressMapper;
import cn.qumiandan.deliveryaddress.vo.DeliveryAddressVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ToolUtil;

/**
 * 收货地址管理实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IDeliveryAddressService.class)
public class DeliveryAddressServiceImpl implements IDeliveryAddressService{

	@Autowired
    private DeliveryAddressMapper deliveryAddressMapper;
	/**
	 * 添加收货地址
	 * @param deliveryAddressVO
	 * @return
	 */
	@Override
	public int addDeliveryAddress(DeliveryAddressVO deliveryAddressVO) {
		DeliveryAddress deliveryAddress = 
		CopyBeanUtil.copyBean(deliveryAddressVO, DeliveryAddress.class);
		//是否是默认地址若未传参数则默认为否
		if(deliveryAddress.getIsDefaultAddress()==null) {
			deliveryAddress.setIsDefaultAddress(ToolUtil.intToByte(0));
		}
		//如果新加的地址为默认地址，则将该用户其他地址设置为非默认地址
		if(deliveryAddress.getIsDefaultAddress().equals(StatusEnum.TRUE.getCode())) {
			deliveryAddressMapper.setDefaultAddress(deliveryAddressVO.getUserId());
		}
		if(deliveryAddress.getCountry()==null) {
			deliveryAddress.setCountry("中国");
		}
		deliveryAddress.setCreateDate(new Date());
		return deliveryAddressMapper.insert(deliveryAddress);
	}

	/**
	 * 更新收货地址
	 * @param deliveryAddressVO
	 * @return
	 */
	@Override
	public int updateDeliveryAddress(DeliveryAddressVO deliveryAddressVO) {
		DeliveryAddress deliveryAddress = 
		CopyBeanUtil.copyBean(deliveryAddressVO, DeliveryAddress.class);
		deliveryAddress.setUpdateDate(new Date());
		//如果讲改收货地址设置为默认地址，则该条将该用户所有地址设置为非默认地址
		if(deliveryAddress.getIsDefaultAddress()!=null&&deliveryAddress.getIsDefaultAddress().equals(StatusEnum.TRUE.getCode())) {
			deliveryAddressMapper.setDefaultAddress(deliveryAddressVO.getUserId());
		}
		return deliveryAddressMapper.updateById(deliveryAddress);
	}

	/**
	 * 删除收货地址（逻辑删除）
	 * @param id
	 * @return
	 */
	@Override
	public int deleteDeliveryAddress(Long id) {
		DeliveryAddress deliveryAddress = new DeliveryAddress();
		deliveryAddress.setStatus(StatusEnum.deleted.getCode());
		deliveryAddress.setId(id);
		return deliveryAddressMapper.updateById(deliveryAddress);
	}

	/**
	 * 根据id查询收获地址
	 * @param id
	 * @return
	 */
	@Override
	public DeliveryAddressVO getDeliveryAddressById(Long id) {
		DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(id);
		DeliveryAddressVO deliveryAddressVO = null;
		if(deliveryAddress!=null) {
			deliveryAddressVO = CopyBeanUtil.copyBean(deliveryAddress, DeliveryAddressVO.class);
		}else{
			return null;
		}
		deliveryAddressVO = CopyBeanUtil.copyBean(deliveryAddress, DeliveryAddressVO.class);
		return deliveryAddressVO;
	}

	/**
	 * 根据用户id查询收获地址
	 * @param userId
	 * @return
	 */
	@Override
	public List<DeliveryAddressVO> getDeliveryAddressByUserId(Long userId) {
        List<DeliveryAddress> deliveryAddressList = deliveryAddressMapper.getDeliveryAddressByUserId(userId);
        List<DeliveryAddressVO> deliveryAddressVOList = null;
        CopyBeanUtil.copyList(deliveryAddressList, DeliveryAddressVO.class);
       if(deliveryAddressList.size()<=0) {
    	   return null; 
       }
       deliveryAddressVOList = CopyBeanUtil.copyList(deliveryAddressList,DeliveryAddressVO.class);
        return deliveryAddressVOList;
	}
}
