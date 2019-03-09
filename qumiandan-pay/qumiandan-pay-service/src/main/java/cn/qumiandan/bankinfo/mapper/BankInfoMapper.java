package cn.qumiandan.bankinfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.bankinfo.entity.BankInfo;
import cn.qumiandan.bankinfo.vo.BankInfoVO;
import cn.qumiandan.bankinfo.vo.HeadBankInfoVO;
@Mapper
public interface BankInfoMapper extends BaseMapper<BankInfo> {

    /**
	 * 查询总行 信息（headBankName为空时查询所有总行信息，不为空时根据名字模糊查询）
	 * @param headBankName
	 * @return
	 */
	List<HeadBankInfoVO> getHeadBankInfo(@Param("headBankName")String headBankName);
	
	/**
	 * 根据总行No查询支行（subBankName为空时查询所有支行信息，不为空时根据名字模糊查询）
	 * @param headBankNo
	 * @param subBankName
	 * @return
	 */
	List<BankInfo> getSubBankInfo(@Param("headBankNo")String headBankNo ,@Param("subBankName")String subBankName);
	
	/**
	 * 根据id查询银行信息
	 * @param id
	 * @return
	 */
//	BankInfoVO getBankInfoById(@Param("id") Long id);
	
	/**
	 * 查询所有银行信息
	 * @return
	 */
	List<BankInfoVO> getAllSubBankInfo(@Param("subBankName") String subBankName);
	
}