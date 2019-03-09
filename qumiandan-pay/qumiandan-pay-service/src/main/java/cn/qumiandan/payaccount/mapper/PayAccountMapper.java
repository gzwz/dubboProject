package cn.qumiandan.payaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.payaccount.entity.PayAccount;
import cn.qumiandan.payaccount.vo.AcountMoneyVO;
import cn.qumiandan.payaccount.vo.PayAccountVO;

/**
 * 支付账户mapper
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午10:36:57
 */
@Mapper
public interface PayAccountMapper extends BaseMapper<PayAccount>{

	//------------------------------以下接口涉及数据库在InnoDB 行级锁操作(慎用) start------------------------------------------------
	/**
	 * 查询账户信息(加锁★★★★★)
	 * @param id
	 * @return
	 */
	PayAccount getPayAccountInfoByIdLock(@Param("id") Long id);
	
	/**
	 * 
	 * 批量更新可提现金额 (批量更新慎用★★★★★)
	 * @return
	 */
	Integer updateBatchIncreaseSettBalance();
	
	
	//------------------------------以下接口涉及数据库在InnoDB 行级锁操作(慎用) end------------------------------------------------


	/**
	 * 根据id 查询账户信息
	 * @param id
	 * @return
	 */
	PayAccountVO getPayAccountInfoById(@Param("id") Long id);

	/**
	 * 根据店铺id查询账户和银行卡信息
	 * @param shopIdList
	 * @return
	 */
	List<AcountMoneyVO> getAcountMoneyByShopList(@Param("shopIdList")List<Long> shopIdList);
	
}
