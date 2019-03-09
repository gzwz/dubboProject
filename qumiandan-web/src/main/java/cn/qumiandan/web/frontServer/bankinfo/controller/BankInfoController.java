package cn.qumiandan.web.frontServer.bankinfo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.bankinfo.api.IBankInfoService;
import cn.qumiandan.bankinfo.vo.BankInfoVO;
import cn.qumiandan.bankinfo.vo.HeadBankInfoVO;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.bankinfo.entity.request.GetAllSubBankInfoParams;
import cn.qumiandan.web.frontServer.bankinfo.entity.request.GetBankInfoByIdParams;
import cn.qumiandan.web.frontServer.bankinfo.entity.request.GetHeadBankInfoParams;
import cn.qumiandan.web.frontServer.bankinfo.entity.request.GetSubBankInfoParams;

/**
 * 收货地址管理
 * @author lrj
 *	创建时间：2018-11-29 14:30
 */

@RestController
@RequestMapping("/bankInfo/")
public class BankInfoController {

	@Reference()
	private IBankInfoService bankInfoService;
	
	/**
	 * 查询总行 信息（headBankName为空时查询所有总行信息，不为空时根据名字模糊查询）
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getHeadBankInfo")
	public Result<List<HeadBankInfoVO>> getHeadBankInfo(@Valid @RequestBody(required = false) GetHeadBankInfoParams params,  BindingResult bindingResult)   {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		List<HeadBankInfoVO> headBankInfoVOList = bankInfoService.getHeadBankInfo(params.getHeadBankName());
		Result<List<HeadBankInfoVO>> result = ResultUtils.success();
		result.setData(headBankInfoVOList);
		return result;
	}
	
	
	/**
	 * 根据总行No查询支行（subBankName为空时查询所有支行信息，不为空时根据名字模糊查询）
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getSubBankInfo")
	public Result<List<BankInfoVO>> getSubBankInfo(@Valid @RequestBody(required = false) GetSubBankInfoParams params,  BindingResult bindingResult)   {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		List<BankInfoVO> bankInfoVOList = bankInfoService.getSubBankInfo(params.getHeadBankNo(),params.getSubBankName());
		Result<List<BankInfoVO>> result = ResultUtils.success();
		result.setData(bankInfoVOList);
		return result;
	}
	
	/**
	 * 查询支行
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getAllSubBankInfo")
	public Result<PageInfo<BankInfoVO>> getAllSubBankInfo(@Valid @RequestBody(required = false) GetAllSubBankInfoParams params,  BindingResult bindingResult)   {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		PageInfo<BankInfoVO> pageInfo = bankInfoService.getAllSubBankInfo(params.getSubBankName(),params.getPageNum(),params.getPageSize());
		Result<PageInfo<BankInfoVO>> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;
	}
	
	/**
	 * 根据id查询银行信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getBankInfoById")
	public Result<BankInfoVO> getBankInfoById(@Valid @RequestBody(required = false) GetBankInfoByIdParams params,  BindingResult bindingResult)   {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		BankInfoVO bankInfoVO = bankInfoService.getBankInfoById(params.getId());
		Result<BankInfoVO> result = ResultUtils.success();
		result.setData(bankInfoVO);
		return result;
	}
}
