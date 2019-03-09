package cn.qumiandan.coupon.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import cn.qumiandan.common.exception.OrErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.coupon.api.ICouponService;
import cn.qumiandan.coupon.api.ICouponTempleteService;
import cn.qumiandan.coupon.constant.CouponEnum;
import cn.qumiandan.coupon.entity.Coupon;
import cn.qumiandan.coupon.entity.CouponTakeRecord;
import cn.qumiandan.coupon.entity.CouponUseRecord;
import cn.qumiandan.coupon.mapper.CouponMapper;
import cn.qumiandan.coupon.mapper.CouponTakeRecordMapper;
import cn.qumiandan.coupon.mapper.CouponUseRecordMapper;
import cn.qumiandan.coupon.service.ICouponTakeRulesReader;
import cn.qumiandan.coupon.service.beanname.TakeRuleBeanNames;
import cn.qumiandan.coupon.vo.CouponTakeRecordVO;
import cn.qumiandan.coupon.vo.CouponTempleteVO;
import cn.qumiandan.coupon.vo.CouponUseRecordVO;
import cn.qumiandan.coupon.vo.CouponVO;
import cn.qumiandan.coupon.vo.GetCouponResponseParamsVO;
import cn.qumiandan.coupon.vo.QueryCouponParamsVO;
import cn.qumiandan.coupon.vo.QueryCouponTakeRecordParamsVO;
import cn.qumiandan.coupon.vo.QueryUsefulCouponParamsVO;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.utils.SpringContextUtils;

@Component
@Service(interfaceClass = ICouponService.class)
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {

	@Autowired
	private ICouponTempleteService templateService;
	
	@Autowired
	private CouponTakeRecordMapper recordMapper;
	
	@Autowired
	private CouponMapper couponMapper;
	
	@Autowired
	private CouponUseRecordMapper useRecordMapper;
	
	@Reference
	private IUserService userService;
	
	@Override
	public CouponVO createCoupon(CouponVO vo) {
		AssertUtil.isNull(vo, OrErrorCode.OR1001.getMsg());
		// 查询此模板是否存在
		if (vo.getTempleteId() == null) {
			throw new QmdException(OrErrorCode.OR1006);
		}
		CouponTempleteVO templeteVO = templateService.getTempleteForId(vo.getTempleteId());
		if (null == templeteVO) {
			throw new QmdException(OrErrorCode.OR1006);
		}
		// 验证创建的优惠是是否包含了模板指定的内容
		validateRules(vo, templeteVO);
		Coupon entity = CopyBeanUtil.copyBean(vo, Coupon.class);
		// 领取规则，暂时不需要设值，所以直接读取模板的内容
		entity.setTakeRule(templeteVO.getTakeRule());
		entity.setStatus(StatusEnum.normal.getCode());
		entity.setRemainNumber(entity.getTotalNumber());
		String json = new Gson().toJson(vo.getExcludeProductIds());
		entity.setExcProductIds(json);
		entity.setCreateDate(new Date());
		entity.setCreateId(entity.getPublisher());
		this.save(entity);
		vo.setId(entity.getId());
		return vo;
	}

	/**
	 * 验证创建的优惠是是否包含了模板指定的内容
	 * @param vo
	 * @param templeteVO
	 */
	private void validateRules(CouponVO vo, CouponTempleteVO templeteVO) {
		// 判断闯过来的优惠券信息中是否包含了模板的规则
		// 1.创建规则
		
		// 2.领取规则
		/*String takeRules = templeteVO.getTakeRule();
		if (StringUtils.isNotBlank(takeRules)) {
			// 模板有规则，但是创建优惠的时候没有填写，则直接抛异常
			if (StringUtils.isBlank(vo.getTakeRule())) {
				throw new QmdException(OrErrorCode.OR1008);
			}
			// 优惠券有该规则，则一一对比。
			for (String takeRule : takeRules.split(",")) {
				// 如果不包含该规则，说直接抛出异常
				if(!vo.getTakeRule().contains(takeRule.trim())) {
					throw new QmdException(OrErrorCode.OR1008);
				}
			}
		}*/
		// 2.使用规则
		String useRules = templeteVO.getUseRule();
		if (StringUtils.isNotBlank(useRules)) {
			// 模板有规则，但是创建优惠的时候没有填写，则直接抛异常
			if (StringUtils.isBlank(vo.getUseRule())) {
				throw new QmdException(OrErrorCode.OR1009);
			}
			// 该规则存在，则创建优惠券是为必填;
			for (String useRule : useRules.split(",")) {
				// 如果不包含该规则，说直接抛出异常
				if(!vo.getUseRule().contains(useRule.trim())) {
					throw new QmdException(OrErrorCode.OR1009);
				}
			}
		}
	}
	
	@Override
	public List<CouponVO> queryCouponForShop(Long id) {
		if (id == null) {
			throw new QmdException(OrErrorCode.OR1001);
		}
		QueryWrapper<Coupon> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("publisher", id);
		queryWrapper.eq("status", StatusEnum.normal.getCode());
		List<Coupon> list = this.list(queryWrapper);
		if (list == null) {
			return null;
		}
		List<CouponVO> target = CopyBeanUtil.copyList(list, CouponVO.class);
		
		for(int i = 0; i< list.size(); i++) {
			list.get(i).getExcProductIds();
			Gson gson  = new Gson();
			List<Long> fromJson= gson.fromJson(list.get(i).getExcProductIds(), new TypeToken<List<Long>>() {
				private static final long serialVersionUID = 1L;}.getType());
			target.get(i).setExcludeProductIds(fromJson);
		}
		return target;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CouponTakeRecordVO takeCouponForUser(Long couponId, Long userId) {
		// 领取之前判断是否已经领取
		List<CouponTakeRecordVO> record = queryTakeCouponRecord(couponId, userId);
		if (record != null && record.size() > 0) {
			throw new QmdException(OrErrorCode.OR1005);
		}
		QueryWrapper<Coupon> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", couponId);
		queryWrapper.eq("status", StatusEnum.normal.getCode()); 
		
		Coupon c = this.getOne(queryWrapper);
		if (null == c) {
			throw new QmdException(OrErrorCode.OR1003);
		}
		// 以后添加所有优惠券规则都在此遍历
		for (TakeRuleBeanNames ruleName : TakeRuleBeanNames.values()) {
			ICouponTakeRulesReader takeReader = SpringContextUtils.getBean(ruleName.name());
			takeReader.readRules(c, userId);
		}
		// 领取
		CouponTakeRecord entity = new CouponTakeRecord();
		entity.setCouponId(couponId);
		entity.setUserId(userId);
		entity.setCreateDate(new Date());
		entity.setStatus(CouponEnum.UnUse.getCode());
		// 减去优惠券张数 -- 只允许减去剩余张数，不允许减去总数量
		UpdateWrapper<Coupon> updateWrapper = new UpdateWrapper<Coupon>();
		//updateWrapper.set("total_number", (c.getTotalNumber()-1L));
		updateWrapper.eq("id", c.getId());
		Coupon cc = new Coupon();
		cc.setRemainNumber(c.getRemainNumber()- 1);
		updateWrapper.last(" and remain_number > 1");
		int i = couponMapper.update(cc, updateWrapper);
		// 插入获取记录
		if (i > 0) {
			recordMapper.insert(entity);
		}else {
			return null;
		}
		CouponTakeRecordVO vo = CopyBeanUtil.copyBean(entity, CouponTakeRecordVO.class);
		return vo;
	}

	@Override
	public CouponVO queryCouponById(Long couponId) {
		if (couponId == null) {
			throw new QmdException(OrErrorCode.OR1001);
		}
		QueryWrapper<Coupon> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", couponId);
		// 查询状态为正常的
		queryWrapper.eq("status", StatusEnum.normal.getCode());
		Coupon c = this.getOne(queryWrapper);
		if (c == null) {
			return null;
		}
		CouponVO target = CopyBeanUtil.copyBean(c, CouponVO.class);
		Gson gson  = new Gson();
		List<Long> fromJson= gson.fromJson(c.getExcProductIds(), new TypeToken<List<Long>>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;}.getType());
		target.setExcludeProductIds(fromJson);
		return target;
	}

	@Override
	public List<CouponTakeRecordVO> queryTakeCouponRecord(Long couponId, Long userId) {
		QueryWrapper<CouponTakeRecord> queryWrapper = new QueryWrapper<>();
		// 查询状态为正常的 且未使用过的
		queryWrapper.eq("coupon_id", couponId);
		queryWrapper.eq("user_id", userId);
		queryWrapper.eq("status", CouponEnum.UnUse.getCode());
		List<CouponTakeRecord> selectList = recordMapper.selectList(queryWrapper);
		if (null == selectList) {
			return null;
		}
		List<CouponTakeRecordVO> target = CopyBeanUtil.copyList(selectList, CouponTakeRecordVO.class);
		return target;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void useCouponRecord(CouponUseRecordVO param) {
		// 将领取的优惠券设置为已使用
		CouponTakeRecord entity = new CouponTakeRecord();
		UpdateWrapper<CouponTakeRecord> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("coupon_id", param.getCouponId());
		updateWrapper.eq("user_id",  param.getUserId());
		updateWrapper.set("status", CouponEnum.HaveBeenUsed.getCode());
		recordMapper.update(entity , updateWrapper);
		// 记录优惠券使用记录（和订单关联）
		CouponUseRecord useEntity = CopyBeanUtil.copyBean(param, CouponUseRecord.class);
		useEntity.setCreateDate(new Date());
		useRecordMapper.insert(useEntity);
		//修改优惠券主编中使用次数字段
		Coupon coupon = couponMapper.selectById(param.getCouponId());
		if(coupon == null ) {
			throw new QmdException("该优惠券不存在");
		}
		coupon.setId(param.getCouponId());
		coupon.setUsedNumber(coupon.getUsedNumber()+1);
		couponMapper.updateById(coupon);
	}

	/**
	 * 增加优惠券数量
	 * @param couponId
	 * @param number
	 * @return
	 */
	@Override
	public CouponVO increaseCouponNum(Long couponId, Long number) {
		Coupon coupon = couponMapper.selectById(couponId);
		if(coupon == null) {
			throw new QmdException("该优惠券不存在");
		}
		coupon.setTotalNumber(coupon.getTotalNumber()+number);
		coupon.setRemainNumber(coupon.getRemainNumber()+number);
		int i = couponMapper.updateById(coupon);
		if(i != 1) {
			throw new QmdException("修改优惠券数量失败");
		}
		CouponVO couponVO = CopyBeanUtil.copyBean(coupon, CouponVO.class);
		Gson gson  = new Gson();
		List<Long> fromJson= gson.fromJson(coupon.getExcProductIds(), new TypeToken<List<Long>>() {
			private static final long serialVersionUID = 1L;}.getType());
		couponVO.setExcludeProductIds(fromJson);
		return couponVO;
	}

	
	/**
	 * 修改优惠券排除的商品集合
	 * @param couponId
	 * @param excludeProductIds
	 * @return
	 */
	@Override
	public CouponVO updateCouponProduct(Long couponId, List<Long> excludeProductIds) {
		Coupon coupon = couponMapper.selectById(couponId);
		if(coupon == null) {
			throw new QmdException("该优惠券不存在");
		}
		int i = 0;
		String productIds = "";
		if(excludeProductIds != null ) {
			productIds = new Gson().toJson(excludeProductIds);
		}
		coupon.setExcProductIds(productIds);
		i = couponMapper.update(coupon, new UpdateWrapper<Coupon>().set("exc_product_ids", productIds).eq("id", coupon.getId()));
		if(i != 1) {
			throw new QmdException("修改优惠券数量失败");
		}
		CouponVO couponVO = CopyBeanUtil.copyBean(coupon, CouponVO.class);
		couponVO.setExcludeProductIds(excludeProductIds);
		return couponVO;
	}

	/**
	 * 根据店铺id和用户id查询优惠券
	 * @param shopId
	 * @param userId
	 * @return
	 */
	@Override
	public List<GetCouponResponseParamsVO> getCouponByShopIdAndUserId(Long publisher, Long userId) {
		//根据发行者id查询优惠券
		List<Coupon> couponVOs = couponMapper.selectList(new QueryWrapper<Coupon>().eq("publisher", publisher)
				.eq("status", new Byte("1"))
				.ge("end_time", new Date())
				.ge("remain_number", 1L).orderByDesc("create_date"));
		//couponResponseParamsVOs为返回参数
		if(couponVOs .isEmpty()) {
			return null;
		}
		List<GetCouponResponseParamsVO> couponResponseParamsVOs = CopyBeanUtil.copyList(couponVOs,GetCouponResponseParamsVO.class);
		//当userId不为空时，需要查询用户的领取记录
		if(userId != null) {
			List<Long> couponIds = new ArrayList<>();
			for(Coupon coupon:couponVOs) {
				couponIds.add(coupon.getId());
			}
			//若领取记录为空则返回优惠券信息，不需组装数据
			List<CouponTakeRecord> couponTakeRecords = recordMapper.selectList(new QueryWrapper<CouponTakeRecord>().in("coupon_id", couponIds).eq("user_id", userId).eq("status", new Byte("1")));
			if(couponTakeRecords == null || couponTakeRecords.size() <= 0) {
				return couponResponseParamsVOs;
			}
			//领取记录不为空，组装数据
			List<CouponTakeRecordVO> couponTakeRecordVOs = CopyBeanUtil.copyList(couponTakeRecords, CouponTakeRecordVO.class);
			for(GetCouponResponseParamsVO paramsVO:couponResponseParamsVOs) {
				for(CouponTakeRecordVO recordVO : couponTakeRecordVOs) {
					if(paramsVO.getId().equals(recordVO.getCouponId())) {
						paramsVO.setCouponTakeRecord(recordVO);
					}
				}
			}
		}		
		return couponResponseParamsVOs;
	}

	/**
	 * 根据优惠券id删除优惠券
	 * @param couponId
	 * @param publisher
	 * @return
	 */
	@Override
	public Integer deleteCouponByCouponId(Long couponId, Long publisher) {
		Coupon coupon = couponMapper.selectById(couponId);
		if(coupon == null) {
			throw new QmdException("该优惠券不存在");
		}
		if(coupon.getPublisher()!= publisher) {
			throw new QmdException("无法删除该优惠券");
		}
		coupon.setStatus(StatusEnum.deleted.getCode());
		int i = couponMapper.updateById(coupon);
		if(i!=1) {
			throw new QmdException("删除失败");
		}
		return i;
	}

	
	/**
	 * 根据条件查询领取记录
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<CouponTakeRecordVO> getCouponTakeRecord(QueryCouponTakeRecordParamsVO params, int pageNum, int pageSize) {
		QueryWrapper<CouponTakeRecord> queryWrapper = new QueryWrapper<>();
		if(params.getId() != null) {
			queryWrapper.eq("id", params.getId() );
		}
		if(params.getCouponId()!=null) {
			queryWrapper.eq("coupon_id",params.getCouponId());
		}
		if(params.getMobile()!=null) {
			UserVO userVO = userService.getUserByUsername(params.getMobile());
			if(userVO == null) {
				throw new  QmdException("该用户不存在");
			}
			queryWrapper.eq("user_id",userVO.getId());
		}
		if(params.getStatusList() != null) {
			queryWrapper.in("status",params.getStatusList());
		}else {
			queryWrapper.in("status",Lists.newArrayList(CouponEnum.UnUse.getCode(),
					CouponEnum.HaveBeenUsed.getCode(),
					CouponEnum.Expired.getCode(),
					CouponEnum.Destroyed.getCode()));
		}
		PageHelper.startPage(pageNum, pageSize);
		List<CouponTakeRecord> couponTakeRecords = recordMapper.selectList(queryWrapper);
		if(couponTakeRecords == null || couponTakeRecords.size()<=0) {
			return null;
		}
		List<CouponTakeRecordVO> couponTakeRecordVOs = CopyBeanUtil.copyList(couponTakeRecords, CouponTakeRecordVO.class);
		//设置用户名参数
		queryCouponTakeRecordUserName(couponTakeRecordVOs);
		PageInfo<CouponTakeRecordVO> pageInfo = new PageInfo<>(couponTakeRecordVOs);
		return pageInfo;
	}

	/**
	 * 设置用户名参数
	 * @param couponTakeRecordVOs
	 */
	private void queryCouponTakeRecordUserName(List<CouponTakeRecordVO> couponTakeRecordVOs){
		List<Long> ids = new ArrayList<>();
		for(CouponTakeRecordVO recordVO:couponTakeRecordVOs) {
			ids.add(recordVO.getUserId());
		}
		List<UserVO> userVOs = userService.getUserByUserIds(ids);
		for(CouponTakeRecordVO recordVO:couponTakeRecordVOs) {
			for(UserVO userVO:userVOs) {
				if(recordVO.getUserId().equals(userVO.getId())) {
					recordVO.setUserName(userVO.getUserName());
				}
			}
		}
	}

	/**
	 * 总后台查询优惠券
	 * @param paramsVO
	 * @return
	 */
	@Override
	public PageInfo<CouponVO> backstageQueryCoupon(QueryCouponParamsVO paramsVO) {
		AssertUtil.isNull(paramsVO, "查询参数不能为空");
		QueryWrapper<Coupon> queryWrapper = new QueryWrapper<Coupon>();
		queryWrapper.eq("status", StatusEnum.normal.getCode());
		if(paramsVO.getId() !=null) {
			queryWrapper.eq("id", paramsVO.getId());
		}
		if(paramsVO.getName() !=null) {
			queryWrapper.likeLeft("name", paramsVO.getName());
		}
		if(paramsVO.getPublisher() !=null) {
			queryWrapper.eq("publisher", paramsVO.getPublisher());
		}
		if(paramsVO.getPublisherName() !=null) {
			queryWrapper.likeLeft("publisher_name", paramsVO.getPublisherName());
		}
		if(paramsVO.getTempleteId() !=null) {
			queryWrapper.eq("templete_id", paramsVO.getTempleteId());
		}
		if(paramsVO.getStartValidTime() !=null) {
			queryWrapper.ge("start_time", paramsVO.getStartValidTime());
		}
		if(paramsVO.getEndValidTime() !=null) {
			queryWrapper.lt("start_time", paramsVO.getEndValidTime());
		}
		if(paramsVO.getStartInvalidTime() !=null) {
			queryWrapper.ge("end_time", paramsVO.getStartInvalidTime());
		}
		if(paramsVO.getEndValidTime() !=null) {
			queryWrapper.lt("end_time", paramsVO.getEndValidTime());
		}
		PageHelper.startPage(paramsVO.getPageNum(), paramsVO.getPageSize());
		List<Coupon> coupons = couponMapper.selectList(queryWrapper);
		if( coupons.isEmpty()) {
			return null;
		}
		List<CouponVO> couponVOs = CopyBeanUtil.copyList(coupons, CouponVO.class);
		for(int i = 0;i<coupons.size() ; i++) {
			Gson gson  = new Gson();
			List<Long> fromJson= gson.fromJson(coupons.get(i).getExcProductIds(), new TypeToken<List<Long>>() {
				private static final long serialVersionUID = 1L;}.getType());
			couponVOs.get(i).setExcludeProductIds(fromJson);
		}
		PageInfo<CouponVO> pageInfo = new PageInfo<>(couponVOs);
		return pageInfo;
	}

	/**
	 * 查询用户可使用的优惠券
	 */
	@Override
	public PageInfo<CouponVO> queryUsefulCoupon(QueryUsefulCouponParamsVO couponParamsVO) {
		PageHelper.startPage(couponParamsVO.getPageNum(), couponParamsVO.getPageSize());
		List<Coupon> coupons = couponMapper.queryUsefulCoupon(couponParamsVO.getUserId(), couponParamsVO.getPublisherList(), couponParamsVO.getDate());
		if(ObjectUtils.isEmpty(coupons)) {
			return null;
		}
		List<CouponVO> copyList = CopyBeanUtil.copyList(coupons, CouponVO.class);
		for(int i = 0;i<coupons.size() ; i++) {
			Gson gson  = new Gson();
			List<Long> fromJson= gson.fromJson(coupons.get(i).getExcProductIds(), new TypeToken<List<Long>>() {
				private static final long serialVersionUID = 1L;}.getType());
			copyList.get(i).setExcludeProductIds(fromJson);
		}
		PageInfo<CouponVO> pageInfo = new PageInfo<CouponVO>(copyList);
		return pageInfo;
	}

}
