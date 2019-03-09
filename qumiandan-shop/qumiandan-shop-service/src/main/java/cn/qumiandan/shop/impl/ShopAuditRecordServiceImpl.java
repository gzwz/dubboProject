package cn.qumiandan.shop.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.shop.api.IShopAuditRecordService;
import cn.qumiandan.shop.entity.ShopAuditRecord;
import cn.qumiandan.shop.mapper.ShopAuditRecordMapper;
import cn.qumiandan.shop.vo.ShopAuditRecordVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;

@Component
@Service(interfaceClass = IShopAuditRecordService.class)
public class ShopAuditRecordServiceImpl extends ServiceImpl<ShopAuditRecordMapper, ShopAuditRecord> implements IShopAuditRecordService {

	@Autowired
	private ShopAuditRecordMapper shopAuditRecordMapper;
	
	@Override
	public ShopAuditRecordVO addShopAuditRecord(ShopAuditRecordVO vo) {
		AssertUtil.isNull(vo, "ShopAuditRecordServiceImpl|addShopAuditRecord|传入参数vo为空");
		ShopAuditRecord entity = CopyBeanUtil.copyBean(vo, ShopAuditRecord.class);
		if (!checkCUD(shopAuditRecordMapper.insert(entity))) {
			throw new QmdException("添加审核记录失败");
		}
		vo.setId(entity.getId());
		return vo;
	}

	@Override
	public ShopAuditRecordVO getNewestAuditRecord(Long shopId) {
		return shopAuditRecordMapper.getNewestAuditRecord(shopId);
	}

	
	/**
	 * 根据店铺id查询店铺审核记录
	 * @param shopId
	 * @return
	 */
	@Override
	public List<ShopAuditRecordVO> getShopAuditRecordByShopId(Long shopId) {
		List<ShopAuditRecord> list =  shopAuditRecordMapper.selectList(new QueryWrapper<ShopAuditRecord>().eq("shop_id", shopId).orderByDesc("audit_date"));
		List<ShopAuditRecordVO> auditRecordVOList = new ArrayList<>();
		if(list.isEmpty()) {
			auditRecordVOList = CopyBeanUtil.copyList(list, ShopAuditRecordVO.class);
		}
		return auditRecordVOList;
	}
	
}
