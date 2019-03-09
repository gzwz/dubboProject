package test.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.shop.api.IShopAuditRecordService;
import cn.qumiandan.shop.api.IShopPictureService;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.mapper.ShopMapper;
import cn.qumiandan.shop.vo.ShopAuditRecordVO;
import cn.qumiandan.shop.vo.ShopBasicInfoVO;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.shop.vo.ShopDynamicVO;
import cn.qumiandan.shop.vo.ShopPictureVO;
import cn.qumiandan.shop.vo.ShopQueryParamVO;
import cn.qumiandan.shop.vo.ShopUserInfoVO;
import cn.qumiandan.shop.vo.ShopVO;
import lombok.extern.slf4j.Slf4j;
import test.BaseTest;
@Slf4j
public class ShopTest extends BaseTest{
	
	@Autowired
	private IShopPictureService shopPictureService;
	
	@Resource
	private IShopService shopService;
	
	@Resource
	private ShopMapper mapper;
	
	@Resource
	private IShopAuditRecordService shopAuditRecordService;
	
/*	
	@Test
	public void getIndexShopList() {
		System.out.println("---shopService.getIndexShopList()---");
		System.out.println(shopService.getIndexShopList(1,10));
	}
	@Test
	public void getIndexShopListByType() {
		System.out.println("---getIndexShopListByType---");
		String str = shopService.getIndexShopListByType(new Long(1),1,10).toString();
		System.out.println(str);
	}
	@Test
	public void getShopListLike() {
		System.out.println("---getIndexShopListByType---");
		System.out.println(shopService.getShopListLike("测试",1,10).toString());
	}
	
	
	@Test
	public void addShop() throws Exception {
		ShopVO shopVO=new ShopVO();
		List<Map<String,Object>> shopPictureVOList = new ArrayList<>();
		//ShopPictureVO shopPictureVO = new ShopPictureVO();
		shopVO.setName("去免单1号店铺");
		shopVO.setDescription("test");
		shopVO.setShopTypeId(3L);
		shopVO.setLicense("ABC");
		shopVO.setLogo("http://phr2czqqv.bkt.clouddn.com/test1113-5");
		shopVO.setLongitude("106.68663");
		shopVO.setLatitude("26.56275");
		shopVO.setProCode("520000");
		shopVO.setCityCode("520100");
		shopVO.setCountyCode("520102");
		shopVO.setTownCode("520102200");
		shopVO.setAddress("贵州省贵阳市南明区贵阳国际中心3号");
		shopVO.setPhone("0851-123456");
		shopVO.setBankAccount("000000");
		shopVO.setBankId(10L);
		shopVO.setCardHolder("admin");
		shopVO.setMaxScan(new BigDecimal(10000));
		shopVO.setOpenTime("08:00");
		shopVO.setItem("美食");
		shopVO.setIsOpenShut(ToolUtil.intToByte(0));
		shopVO.setIndustryId(13L);
		shopVO.setSupportPayment("1,2,3");
		shopVO.setMerchantNo("shanghuhao");
		//shopPictureParamsList
		Map<String,Object> map =  new HashMap<>();
		map.put("imageName","test1123-01");
		map.put("imageUrl","http://phr2czqqv.bkt.clouddn.com/FhypL0lCL3n14cPCLbgsMorYIpQi");
		map.put("imageType",5);
		map.put("imageSort",15);
		shopPictureVOList.add(map);

		shopVO.setShopPictureVOList(shopPictureVOList);
		shopVO.setMobile("18785273024");

		System.out.println("---addShop---");
		System.out.println(shopService.addShop(shopVO));
	}
	@Test
	public void updateShopById() throws Exception {
		ShopUpdateVO shopVO=new ShopUpdateVO();
		shopVO.setName("测试店铺2...");
		shopVO.setDescription("测试店铺2的描述...");
		shopVO.setTerminalId("EquipmentId");
		System.out.println("---updateShopById---");
		shopService.updateShopById(shopVO);
	}
	@Test
	public void closeShopById() throws Exception {
		System.out.println("---closeShopById---");
		System.out.println(shopService.closeShopById(1l));
	}





	@Test
	public void getShopDetailById(){
		System.out.println("---shopService.getShopDetailById---");
		System.out.println(shopService.getShopDetailById(20L));
	}

	@Test


	
	@Test
	public void getShopSimpleInfoByMangerUserId(){
		List<ShopSimpleVO> shopBasicVOList = shopService.getShopSimpleInfoByMangerUserId(1l);
		System.out.println(shopBasicVOList);
	}
		
	@Test
	public void getShopByCountyCode(){
		System.out.println("-------getShopByCountyCode--------");
		PageInfo<ShopBasicVO> shopBasicVOList = shopService.getShopByCode(520102, 3,1, 10);
		System.out.println(shopBasicVOList);
	}
	
	
	@Test
	public void auditUpdateShop(){
		System.out.println("-------auditUpdateShop--------");
		int i  = shopService.auditUpdateShop(1L, ToolUtil.intToByte(1));
		System.out.println(i);
	}
	

	@Test
	public void getShopByMerchantNo(){
		System.out.println("-------getShopByMerchantNo--------");
		ShopBasicVO shopBasicVO  = shopService.getShopByMerchantNo("111111");
		System.out.println(shopBasicVO);
	}
	
	@Test
	public void test() {
		Shop shop = new Shop();
		shop.setId(63l);
		shop.setAddress("222");
		mapper.update(null, new UpdateWrapper<Shop>(shop).set("s", ""));
	}
	
	

	
	
	@Test
	public void getShopDynamic() throws Exception {
		System.out.println("---shopService.getShopDynamic---");
		ShopDynamicVO shopVO=new ShopDynamicVO();
		shopVO.setInputInfo("店铺");
		shopVO.setUserLongitude("106.78663");
		shopVO.setUserLatitude("26.66275");
		shopVO.setSalemanName("18785273024");
//		shopVO.setSortByDistance(ToolUtil.intToByte(1));
		shopVO.setSortByCreateTime(ToolUtil.intToByte(1));
		shopVO.setCode("520102200");
		shopVO.setLevel(4);
		System.out.println(shopService.getShopDynamic(shopVO,1,10));
	}
	
	

	
	@Test
	public void auditUpdateShop(){
		System.out.println("-------auditUpdateShop--------");
		ShopAuditRecordVO vo = new ShopAuditRecordVO();
		vo.setShopId(1L);
		vo.setStatus(new Byte("6"));
		shopService.auditAddShop(vo);
		System.out.println("成功");
	}
	
	@Test
	public void getShopAuditRecordByShopId(){
		System.out.println("-------getShopAuditRecordByShopId--------");
		List<ShopAuditRecordVO> list = shopAuditRecordService.getShopAuditRecordByShopId(1L);
		System.out.println(list);
	}
	
	@Test
	public void updateShopByShopManager(){
		ManagerShopUpdateVO vo = new ManagerShopUpdateVO();
		vo.setId(69L);
		vo.setPhone("11111");
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		shopService.updateShopByShopManager(vo);
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}
	*/
	@Test
	public void getShopInfoById(){
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		System.out.println(shopService.getShopInfoById(1L));
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}
	/*@Test
	public void updateShopByShopManager(){
		ManagerShopUpdateVO vo = new ManagerShopUpdateVO();
		vo.setId(69L);
		vo.setPhone("11111");
		vo.setOpenTime(new Date());
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		shopService.updateShopByShopManager(vo);
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}*/
	
	
	@Test
	public void getaddShop(){
		ShopVO v = new ShopVO();
		v.setName("小明店铺******");
		v.setDescription("xxxxx");
		v.setShopTypeId(1L);
		v.setLicense("xxxxx");
		v.setLogo("xxxxx");
		v.setLongitude("1.1");
		v.setLatitude("1.1");
		v.setProCode("111");
		v.setCityCode("333");
		v.setCountyCode("333");
		v.setTownCode("123456");
		v.setAddress("xxxx");
		v.setPhone("13087804777");
		v.setSalemanId(1L);
		v.setBankAccount("5555555555");
		v.setBankId(3L);
		v.setCardHolder("XXXXX");
		v.setCorporation("DDDDXXX");
		v.setOpenTime(new Date());
		v.setRestTime(new Date());
		v.setItem("1");
		v.setIsOpenShut(new Byte("1"));
		v.setIndustryId(1L);
		v.setMobile("18785273024");
		v.setCreateId(1L);
		List<ShopPictureVO> shopPictureVOList = Lists.newArrayList();
		ShopPictureVO vv = new ShopPictureVO();
		vv.setCreateDate(new Date());
		vv.setImageName("xxxx");
		vv.setImageSize(123L);
		vv.setImageSort(1);
		vv.setImageType(new Byte("6"));
		vv.setIntro("xxxx");
		vv.setImageUrl("xxxx");
		vv.setSuffix("jpg");
		shopPictureVOList.add(vv);
		v.setShopPictureVOList(shopPictureVOList);
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		System.out.println(shopService.addShop(v));
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}
	
	@Test
	public void getPic() {
		List<ShopPictureVO> shopPictureByShopId = shopPictureService.getShopPictureByShopId(10L);
		System.out.println(shopPictureByShopId);
	}
	
	@Test
	public void getShopDetailById(){
		System.out.println("---shopService.getShopDetailById---");
		System.out.println(shopService.getShopDetailById(20L));
	}
	
	@Test
	public void getShopBasicInfoByShopId(){
		System.out.println("-------getShopBasicInfoByShopId------");
		ShopBasicInfoVO shopBasicInfoByShopId = shopService.getShopBasicInfoByShopId(10L);
		System.out.println(shopBasicInfoByShopId);
	}
	
	@Test
	public void getShopBasicById(){
		System.out.println("---shopService.getShopBasicById---");
		ShopBasicVO shopBasicById = shopService.getShopBasicById(20L);
		System.out.println(shopBasicById);
	}
	
	
	@Test
	public void getShopByShopQuery(){
		System.out.println("-------getShopByShopQuery------");
		ShopQueryParamVO paramVO = new ShopQueryParamVO();
//		paramVO.setSalemanMobile("18785273024");	
		paramVO.setShopName("测试");
		PageInfo<ShopBasicVO> info = shopService.getShopByShopQuery(paramVO);
		System.out.println(info);
	}
	
	@Test
	public void getShopDynamic() {
		System.out.println("---shopService.getShopDynamic---");
		ShopDynamicVO shopVO=new ShopDynamicVO();
//		shopVO.setStatusList(Lists.newArrayList((byte)1,(byte)2,(byte)3));
//		shopVO.setInputInfo("店铺");
//		shopVO.setUserLongitude("106.78663");
		shopVO.setUserLatitude("26.66275");
//		shopVO.setSortByDistance(new Byte("1"));
		shopVO.setIsNearby(true);
		shopVO.setPhone("18602182076");
		shopVO.setMerchantPerson("asd");
		shopVO.setId(108L);
		PageInfo<ShopBasicVO> shopDynamic = shopService.getShopDynamic(shopVO,1,10);
		System.out.println(shopDynamic);
	}
	
	@Test
	public void auditAddShop(){
		ShopAuditRecordVO vo = new ShopAuditRecordVO();
		vo.setShopId(98L);
		vo.setStatus(new Byte("7"));;
		shopService.auditAddShop(vo);
	}
	
	@Test
	public void getShopByManager(){
		System.out.println("---shopService.getShopByManager---");
		List<ShopUserInfoVO> shopUserInfoVOList = new ArrayList<>();
		ShopUserInfoVO shopUserInfoVO = new ShopUserInfoVO();
		shopUserInfoVO.setShopId(98L);
		shopUserInfoVOList.add(shopUserInfoVO);
		List<ShopBasicVO> shopBasicVOList = shopService.getUserShopByManager(shopUserInfoVOList);
		System.out.println(shopBasicVOList);
	}
	
	/*
	 * @Test public void getShopNumBySalemanId() { ShopStatisticVO
	 * shopNumBySalemanId = shopService.getShopNumBySalemanId(1L);
	 * System.out.println(shopNumBySalemanId); }
	 */
}
