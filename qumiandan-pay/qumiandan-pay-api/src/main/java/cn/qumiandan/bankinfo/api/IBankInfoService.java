package cn.qumiandan.bankinfo.api;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.bankinfo.vo.BankInfoVO;
import cn.qumiandan.bankinfo.vo.HeadBankInfoVO;

/**
 * 扫呗银行信息管理接口
 * @author lrj
 *
 */
public interface IBankInfoService {

	/**
	 * 通过id获取银行信息
	 * @param id
	 * @return
	 */
	BankInfoVO getBankInfoById(Long id);
	
	/**
	 * 查询总行 信息（headBankName为空时查询所有总行信息，不为空时根据名字模糊查询）
	 * @param headBankName
	 * @return
	 */
	List<HeadBankInfoVO> getHeadBankInfo(String headBankName);
	
	/**
	 * 根据总行No查询支行（subBankName为空时查询所有支行信息，不为空时根据名字模糊查询）
	 * @param headBankNo
	 * @param subBankName
	 * @return
	 */
	List<BankInfoVO> getSubBankInfo(String headBankNo ,String subBankName);
	
	/**
	 * 查询所有银行信息
	 * @param subBankName
	 * @return
	 */
	PageInfo<BankInfoVO> getAllSubBankInfo( String subBankName,int pageNum,int pageSize);
}
