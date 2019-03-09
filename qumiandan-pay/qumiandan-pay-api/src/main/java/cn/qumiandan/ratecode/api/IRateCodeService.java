package cn.qumiandan.ratecode.api;

import java.util.List;

import cn.qumiandan.ratecode.vo.RateCodeVO;

/**
 * 费率管理接口
 * @author lrj
 *
 */
public interface IRateCodeService {
	
	/**
	 * 根据code查询费率详情
	 * @param id
	 * @return
	 */
	RateCodeVO getRateCode(String code);
	
	/**
	 * 获取所有费率信息
	 * @param typeList
	 * @return
	 */
	List<RateCodeVO> getAllRateCode(List<Byte> typeList);
	
	/**
	 * 添加费率
	 * @param rateCodeVO
	 * @return
	 */
	int addRateCode(RateCodeVO rateCodeVO);
	
	/**
	 * 修改费率
	 * @param rateCodeVO
	 * @return
	 */
	int updateRateCode(RateCodeVO rateCodeVO);
	
	/**
	 * 删除费率
	 * @param rateCodeVO
	 * @return
	 */
	int deleteRateCode(Long id);
}
