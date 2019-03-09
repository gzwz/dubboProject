package cn.qumiandan.deliveryaddress.api;

import java.util.List;

import cn.qumiandan.deliveryaddress.vo.DeliveryAddressVO;

/**
 * 收货地址管理接口
 * @author lrj
 *
 */
public interface IDeliveryAddressService {

	/**
	 * 添加收货地址
	 * @param deliveryAddressVO
	 * @return
	 */
	int addDeliveryAddress(DeliveryAddressVO deliveryAddressVO);
	
	/**
	 * 更新收货地址
	 * @param deliveryAddressVO
	 * @return
	 */
	int updateDeliveryAddress(DeliveryAddressVO deliveryAddressVO);
	
	/**
	 * 删除收货地址（逻辑删除）
	 * @param id
	 * @return
	 */
	int deleteDeliveryAddress(Long id);
	
	/**
	 * 根据id查询收货地址
	 * @param id
	 * @return
	 */
	DeliveryAddressVO getDeliveryAddressById(Long id);
	
	/**
	 * 根据用户id查询收货地址
	 * @param userId
	 * @return
	 */
	List<DeliveryAddressVO> getDeliveryAddressByUserId(Long userId);

}
