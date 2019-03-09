package cn.qumiandan.bankinfo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.bankinfo.api.IBankInfoService;
import cn.qumiandan.bankinfo.entity.BankInfo;
import cn.qumiandan.bankinfo.mapper.BankInfoMapper;
import cn.qumiandan.bankinfo.vo.BankInfoVO;
import cn.qumiandan.bankinfo.vo.HeadBankInfoVO;
import cn.qumiandan.utils.CopyBeanUtil;
/**
 * 扫呗银行信息管理实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IBankInfoService.class)
public class BankInfoServiceImpl implements IBankInfoService {
	
	@Autowired
	private BankInfoMapper bankInfoMapper;
	/**
	 * 查询总行 信息（headBankName为空时查询所有总行信息，不为空时根据名字模糊查询）
	 * @param headBankName
	 * @return
	 */
	@Override
	public List<HeadBankInfoVO> getHeadBankInfo(String headBankName) {
		List<HeadBankInfoVO> headBankInfoVOList = bankInfoMapper.getHeadBankInfo(headBankName);
		return headBankInfoVOList;
	}

	
	/**
	 * 根据总行No查询支行（subBankName为空时查询所有支行信息，不为空时根据名字模糊查询）
	 * @param headBankNo 总行code
	 * @param subBankName 支行名称
	 * @return
	 */
	@Override
	public List<BankInfoVO> getSubBankInfo(String headBankNo, String subBankName) {
		 List<BankInfo> bankInfoList = bankInfoMapper.getSubBankInfo(headBankNo, subBankName);
		 if(bankInfoList.size() <= 0) {
			 return null;
		 }		 
		 List<BankInfoVO> bankInfoVOList = CopyBeanUtil.copyList(bankInfoList, BankInfoVO.class);
		 return bankInfoVOList;
	}

	/**
	 * 通过id获取银行信息
	 * @param id
	 * @return
	 */
	@Override
	public BankInfoVO getBankInfoById(Long id) {
		 BankInfo selectById = bankInfoMapper.selectById(id);
		 if(selectById == null) {
			 return null;
		 }
		 BankInfoVO bankInfoVO = CopyBeanUtil.copyBean(selectById, BankInfoVO.class);
		 return bankInfoVO;
	}


	/**
	 * 查询所有银行信息
	 */
	@Override
	public PageInfo<BankInfoVO> getAllSubBankInfo( String subBankName,int pageNum,int pageSize ) {
		PageHelper.startPage(pageNum, pageSize);
		List<BankInfoVO> bankInfoVOs = bankInfoMapper.getAllSubBankInfo(subBankName);
		PageInfo<BankInfoVO> pageInfo = new PageInfo<>(bankInfoVOs);
		pageInfo.setTotal(pageInfo.getTotal());
		return pageInfo;
	}

}
