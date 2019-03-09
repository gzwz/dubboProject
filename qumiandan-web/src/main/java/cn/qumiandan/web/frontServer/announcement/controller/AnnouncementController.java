package cn.qumiandan.web.frontServer.announcement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.announcement.api.IAnnouncementService;
import cn.qumiandan.announcement.enums.AnnouncementTypeEnum;
import cn.qumiandan.announcement.vo.AnnouncementPageInfoVO;
import cn.qumiandan.announcement.vo.AnnouncementVO;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.announcement.entity.AddAnnouncementParams;
import cn.qumiandan.web.frontServer.announcement.entity.AddShopAnnouncementParams;
import cn.qumiandan.web.frontServer.announcement.entity.GetAnnouncementByBelongIdParams;
import cn.qumiandan.web.frontServer.announcement.entity.GetCountParams;
import cn.qumiandan.web.frontServer.announcement.entity.GetIdParams;
import cn.qumiandan.web.frontServer.announcement.entity.GetShopAnnouncementParams;
import cn.qumiandan.web.frontServer.announcement.entity.UpdateAnnouncementParams;

/**
 * 公告管理
 * @author lrj
 * @version 创建时间：2018年11月26日 10:30
 */
@RestController
@RequestMapping("/announcement/")
public class AnnouncementController {
	@Reference()
	private IAnnouncementService  announcementService;

	/**
	 * 总后台添加公告信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addAnnouncement")
	public Result<Integer> addAnnouncement(@Valid @RequestBody(required = false) AddAnnouncementParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AnnouncementVO announcementVO = CopyBeanUtil.copyBean(params, AnnouncementVO.class);
		announcementService.addAnnouncement(announcementVO);
		return ResultUtils.success("添加公告信息成功");
	}
	
	/**
	 * 添加店铺公告信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addShopAnnouncement")
	public Result<Integer> addShopAnnouncement(@Valid @RequestBody(required = false) AddShopAnnouncementParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AnnouncementVO announcementVO = CopyBeanUtil.copyBean(params, AnnouncementVO.class);
		announcementVO.setType(AnnouncementTypeEnum.SHOPANNOUNCEMENT.getCode());
		announcementService.addAnnouncement(announcementVO);
		return ResultUtils.success("添加店铺公告信息成功");
	}
	
	/**
	 * 添加平台公告信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addPFAnnouncement")
	public Result<Integer> addPlatformAnnouncement(@Valid @RequestBody(required = false) AddAnnouncementParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AnnouncementVO announcementVO = CopyBeanUtil.copyBean(params, AnnouncementVO.class);
		announcementVO.setType(AnnouncementTypeEnum.PLATFORMANNOUNCEMENT.getCode());
		//如果未传归属地区编号，则默认为全平台公告
		if(announcementVO.getBelongId() == null) {
			announcementVO.setBelongId("0");
		}
		announcementService.addAnnouncement(announcementVO);
		return ResultUtils.success("添加平台公告信息成功");
	}
	
	/**
	 * 添加平台->业务员公告信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addPFToSalemanAnnouncement")
	public Result<Integer> addSalemanAnnouncement(@Valid @RequestBody(required = false) AddAnnouncementParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AnnouncementVO announcementVO = CopyBeanUtil.copyBean(params, AnnouncementVO.class);
		announcementVO.setType(AnnouncementTypeEnum.PFTOSALEMANANNOUNCEMENT.getCode());
		announcementVO.setBelongId("0");
		announcementService.addAnnouncement(announcementVO);
		return ResultUtils.success("添加平台->业务员公告信息成功");
	}
	
	/**
	 * 添加平台->商家公告信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addPFToShopAnnouncement")
	public Result<Integer> addPFToShopAnnouncement(@Valid @RequestBody(required = false) AddAnnouncementParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AnnouncementVO announcementVO =
		CopyBeanUtil.copyBean(params, AnnouncementVO.class);
		announcementVO.setType(AnnouncementTypeEnum.PFTOSHOPANNOUNCEMENT.getCode());
		announcementVO.setBelongId("0");
		announcementService.addAnnouncement(announcementVO);
		return ResultUtils.success("添加平台->商家公告信息成功");
	}
	
	/**
	 * 更新公告信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateAnnouncement")
	public Result<Integer> updateAnnouncement(@Valid @RequestBody(required = false) UpdateAnnouncementParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AnnouncementVO announcementVO =
		CopyBeanUtil.copyBean(params, AnnouncementVO.class);
		announcementService.updateAnnouncement(announcementVO);
		return ResultUtils.success("更新公告信息成功");
	}
	
	/**
	 * 删除公告信息（逻辑删除）
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("deleteAnnouncement")
	public Result<Integer> deleteAnnouncement(@Valid @RequestBody(required = false) GetIdParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		announcementService.deleteAnnouncement(params.getId());
		return ResultUtils.success("删除行业信息成功");
	}
	
	/**
	 * 查询店铺公告
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopAnnouncement")
	public Result<List<AnnouncementVO>> getShopAnnouncement(@Valid @RequestBody(required = false) GetShopAnnouncementParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<AnnouncementVO> announcementVOList = announcementService.getAnnouncementByBoLongId(AnnouncementTypeEnum.SHOPANNOUNCEMENT.getCode(), params.getBelongId(), params.getCount());
		Result<List<AnnouncementVO>> result = ResultUtils.success();
		result.setData(announcementVOList);
		return result;
	}
	
	/**
	 * 查询平台公告信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getPlatFormAnnouncement")
	public Result<List<AnnouncementVO>> getPlatFormAnnouncement(@Valid @RequestBody(required = false) GetCountParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		if(params.getBelongId() == null) {
			params.setBelongId("0");
		}
		List<AnnouncementVO> announcementVOList = announcementService.getAnnouncementByBoLongId(AnnouncementTypeEnum.PLATFORMANNOUNCEMENT.getCode(), params.getBelongId(), params.getCount());
		Result<List<AnnouncementVO>> result = ResultUtils.success();
		result.setData(announcementVOList);
		return result;
	}
	
	/**
	 * 查询平台->业务员公告信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getPFToSalemanAnnouncement")
	public Result<List<AnnouncementVO>> getPFToSalemanAnnouncement(@Valid @RequestBody(required = false) GetCountParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<AnnouncementVO> announcementVOList = announcementService.getAnnouncementByBoLongId(AnnouncementTypeEnum.PFTOSALEMANANNOUNCEMENT.getCode(), "0", params.getCount());
		Result<List<AnnouncementVO>> result = ResultUtils.success();
		result.setData(announcementVOList);
		return result;
	}
	
	
	/**
	 * 查询平台->店铺公告信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getPFToShopAnnouncement")
	public Result<List<AnnouncementVO>> getPFToShopAnnouncement(@Valid @RequestBody(required = false) GetCountParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<AnnouncementVO> announcementVOList = announcementService.getAnnouncementByBoLongId(AnnouncementTypeEnum.PFTOSHOPANNOUNCEMENT.getCode(), "0", params.getCount());
		Result<List<AnnouncementVO>> result = ResultUtils.success();
		result.setData(announcementVOList);
		return result;
	}
	
	/**
	 * 分页查询公告信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getAnnouncementByBoLongId")
	public Result<PageInfo<AnnouncementVO>> getAnnouncementByBoLongId(@Valid @RequestBody(required = false) GetAnnouncementByBelongIdParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AnnouncementPageInfoVO announcementPageInfoVO = CopyBeanUtil.copyBean(params, AnnouncementPageInfoVO.class);
		PageInfo<AnnouncementVO> announcementVOList = announcementService.getAnnouncementPageInfo(announcementPageInfoVO);
		Result<PageInfo<AnnouncementVO>> result = ResultUtils.success();
		result.setData(announcementVOList);
		return result;
	}
	
	/**
	 * 根据主键查询
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getAnnouncementById")
	public Result<AnnouncementVO> getAnnouncementById(@Valid @RequestBody(required = false) GetIdParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AnnouncementVO announcementVO = announcementService.getAnnouncementById(params.getId());
		Result<AnnouncementVO> result = ResultUtils.success();
		result.setData(announcementVO);
		return result;
	}
}
