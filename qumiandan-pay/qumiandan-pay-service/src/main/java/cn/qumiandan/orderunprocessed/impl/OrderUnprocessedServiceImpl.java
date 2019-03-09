package cn.qumiandan.orderunprocessed.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.orderunprocessed.api.IOrderUnprocessedService;
import cn.qumiandan.orderunprocessed.entity.Orderunprocessed;
import cn.qumiandan.orderunprocessed.mapper.OrderunprocessedMapper;
import cn.qumiandan.orderunprocessed.vo.OrderUnprocessedVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;

/**
 * 未处理成功的订单实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IOrderUnprocessedService.class)
public class OrderUnprocessedServiceImpl implements IOrderUnprocessedService{

	@Autowired
	private OrderunprocessedMapper orderunprocessedMapper ;
	/**
	 * 添加未处理成功的订单信息
	 * @param orderUnprocessedVO
	 * @return
	 */
	@Override
	public OrderUnprocessedVO addOrderUnprocessed(OrderUnprocessedVO orderUnprocessedVO) {
		AssertUtil.isNull(orderUnprocessedVO, "参数不能为空");
		Orderunprocessed orderunprocessed  = CopyBeanUtil.copyBean(orderUnprocessedVO, Orderunprocessed.class);
		orderunprocessed.setCreateDate(new Date());
		int i = orderunprocessedMapper.insert(orderunprocessed);
		if(i != 1) {
			throw new QmdException("添加未处理成功的订单信息失败");
		}
		orderUnprocessedVO.setId(orderunprocessed.getId());
		return orderUnprocessedVO;
	}

	/**
	 * 修改未处理成功的订单信息
	 * @param orderUnprocessedVO
	 * @return
	 */
	@Override
	public void updateOrderUnprocessedById(OrderUnprocessedVO orderUnprocessedVO) {
		AssertUtil.isNull(orderUnprocessedVO, "参数不能为空");
		Orderunprocessed orderunprocessed = orderunprocessedMapper.selectById(orderUnprocessedVO.getId());
		if(orderunprocessed == null) {
			throw new QmdException("不存在该条未处理成功的订单信息");
		}
		orderunprocessed = CopyBeanUtil.copyBean(orderUnprocessedVO, Orderunprocessed.class);
		orderunprocessed.setUpdateDate(new Date());
		int  i = orderunprocessedMapper.updateById(orderunprocessed);
		if(i !=1 ) {
			throw new QmdException("修改未处理成功的订单信息失败");
		}
	}

	/**
	 * 删除未处理成功的订单信息
	 * @param id
	 */
	@Override
	public void deleteOrderUnprocessedById(Long id) {
		int i =orderunprocessedMapper.deleteById(id);
		if(i !=1 ) {
			throw new QmdException("删除未处理成功的订单信息失败");
		}
	}

	/**
	 * 根据id查询未处理成功的订单信息
	 * @param id
	 * @return
	 */
	@Override
	public OrderUnprocessedVO getOrderUnprocessedById(Long id) {
		Orderunprocessed orderunprocessed =  orderunprocessedMapper.selectById(id);
		if(orderunprocessed == null ) {
			return null ;
		}
		OrderUnprocessedVO orderUnprocessedVO = CopyBeanUtil.copyBean(orderunprocessed, OrderUnprocessedVO.class);
		return orderUnprocessedVO;
	}

	/**
	 * 分页查询未处理成功的订单信息
	 * @param commonParams
	 * @return
	 */
	@Override
	public PageInfo<OrderUnprocessedVO> queryOrderUnprocessed(CommonParams commonParams) {
		PageHelper.startPage(commonParams.getPageNum(), commonParams.getPageSize());
		List<Orderunprocessed> orderUnprocesseds = orderunprocessedMapper.selectList(new QueryWrapper<Orderunprocessed>());
		if(ObjectUtils.isEmpty(orderUnprocesseds)) {
			return null;
		}
		List<OrderUnprocessedVO> orderUnprocessedVOs = CopyBeanUtil.copyList(orderUnprocesseds, OrderUnprocessedVO.class);
		PageInfo<OrderUnprocessedVO> pageInfo = new PageInfo<>(orderUnprocessedVOs);
		return pageInfo;
	}

}
