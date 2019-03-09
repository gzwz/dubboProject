package test.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Sets;

import cn.qumiandan.product.api.IProductService;
import cn.qumiandan.product.vo.ProductAlbumVO;
import cn.qumiandan.product.vo.ProductAndShopVO;
import cn.qumiandan.product.vo.ProductVO;
import cn.qumiandan.product.vo.ShopProductQueryVO;
import cn.qumiandan.utils.ToolUtil;
import test.BaseTest;

/**
 * @description：商品接口单元测试
 * @author：zhuyangyong
 * @date: 2018/11/8 15:13
 */
public class ProductTest  extends BaseTest {
    @Resource
    private IProductService productService;
    
    @Test
    public void addProduct() throws Exception{
        System.out.println("---productService.addProduct()---");
        ProductVO productVO = new ProductVO();
        productVO.setName("测试商品名1");
        productVO.setShopId(1L);
        productVO.setTypeId(1L);
        productVO.setBrandId(1L);
        productVO.setCategoryId(1L);
        productVO.setBrandId(1L);
        productVO.setMainImage("http://phr2czqqv.bkt.clouddn.com/test1113-5");
        productVO.setOriginalPrice(new BigDecimal(1.0));
        productVO.setPresentPrice(new BigDecimal(1.0));
        productVO.setCost(new BigDecimal(1.0));
        productVO.setIsQmd(ToolUtil.intToByte(1));
        productVO.setBarcode("abc");
        productVO.setProductDescribe("def");
        productVO.setMetaKeyword("aaa");
        productVO.setMetaDescription("zzz");
        productVO.setAttribute("xxx");
        List<ProductAlbumVO> productPictureVOList = new ArrayList<>();
        ProductAlbumVO alVo = new ProductAlbumVO();
        alVo.setImageName("test1120-01");
        alVo.setImageUrl("http://phr2czqqv.bkt.clouddn.com/FhypL0lCL3n14cPCLbgsMorYIpQi");
        alVo.setImageType(new Byte("1"));
        productPictureVOList.add(alVo);
        productVO.setProductPictureVOList(productPictureVOList);
        productVO.setCreateId(1L);
        System.out.println(productService.addProduct(productVO));
    }
 /*   @Test
    public void addProduct() throws Exception{
        System.out.println("---productService.addProduct()---");
        ProductVO productVO = new ProductVO();
        productVO.setName("测试商品名1");
        productVO.setShopId(1L);
        productVO.setTypeId(1L);
        productVO.setBrandId(1L);
        productVO.setCategoryId(1L);
        productVO.setBrandId(1L);
        productVO.setMainImage("http://phr2czqqv.bkt.clouddn.com/test1113-5");
        productVO.setOriginalPrice(new BigDecimal(1.0));
        productVO.setPresentPrice(new BigDecimal(1.0));
        productVO.setCost(new BigDecimal(1.0));
        productVO.setIsQmd(ToolUtil.intToByte(1));
        productVO.setBarcode("abc");
        productVO.setProductDescribe("def");
        productVO.setMetaKeyword("aaa");
        productVO.setMetaDescription("zzz");
        productVO.setAttribute("xxx");
        List<ProductAlbumVO> productPictureVOList = new ArrayList<>();
        ProductAlbumVO alVo = new ProductAlbumVO();
        alVo.setImageName("test1120-01");
        alVo.setImageUrl("http://phr2czqqv.bkt.clouddn.com/FhypL0lCL3n14cPCLbgsMorYIpQi");
        alVo.setImageType(new Byte("1"));
        productPictureVOList.add(alVo);
        productVO.setProductPictureVOList(productPictureVOList);
        productVO.setCreateId(1L);
        System.out.println(productService.addProduct(productVO));
    }
    




    @Test
    public void getCustomProductListByShopId() {
        System.out.println("---productService.getCustomProductListByShopId()---");
        System.out.println(productService.getCustomProductListByShopId(10L));
    }

    @Test
    public void updateProductById() throws Exception{
        System.out.println("---productService.updateProductById()---");
        ProductUpdateVO productUpdateVO = new ProductUpdateVO();
        productUpdateVO.setId(36L);
        productUpdateVO.setName("测试商品名11");
        productUpdateVO.setTypeId(1L);
        productUpdateVO.setBrandId(3L);
        productUpdateVO.setCategoryId(3L);
        productUpdateVO.setBrandId(3L);
        productUpdateVO.setMainImage("http://phr2czqqv.bkt.clouddn.com/test1126-2");
        productUpdateVO.setOriginalPrice(new BigDecimal(3.0));
        productUpdateVO.setPresentPrice(new BigDecimal(3.0));
        productUpdateVO.setCost(new BigDecimal(3.0));
        productUpdateVO.setIsQmd(ToolUtil.intToByte(0));
        productUpdateVO.setBarcode("abcde");
        productUpdateVO.setProductDescribe("defgh");
        productUpdateVO.setMetaKeyword("aaaaa");
        productUpdateVO.setMetaDescription("zzzzz");
        productUpdateVO.setAttribute("xxxxx");
        List<Map<String,Object>> productPictureVOList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("imageName","test1126-02");
        map.put("imageUrl","http://phr2czqqv.bkt.clouddn.com/FhypL0lCL3n14cPCLbgsMorYIpQi");
        map.put("imageType",2);
        productPictureVOList.add(map);
        productUpdateVO.setProductPictureVOList(productPictureVOList);
        productUpdateVO.setUpdateId(1L);
        productService.updateProductById(productUpdateVO);
    }

    

    @Test
    public void auditProduct() {
        System.out.println("---productService.auditProduct()---");
        System.out.println(productService.auditProduct(1L, ToolUtil.intToByte(0)));
    }
    
    
    @Test
    public void getCustomProductListByShopId() {
        System.out.println("---productService.getProductDetailById()---");
        System.out.println(productService.getCustomProductListByShopId(1L));
    }
    

    @Test
    public void getProductBasicById() {
        System.out.println("---productService.getProductBasicById()---");
        System.out.println(productService.getProductBasicById(60L));
    }
    
    @Test
    public void getCustomProductListByShopId() {
        System.out.println("---productService.getCustomProductListByShopId()---");
        System.out.println(productService.getCustomProductListByShopId(1L));
    }
    
    
    @Test
    public void getProductPageListByShopId() {
        System.out.println("---productService.getProductPageListByShopId()---");
        ShopProductQueryVO productQueryVO = new ShopProductQueryVO();
        productQueryVO.setShopId(1L);
        System.out.println(productService.getProductPageListByShopId(productQueryVO));
    }
*/
    @Test
    public void getProductBasicById() {
        System.out.println("---productService.getProductBasicById()---");
        System.out.println(productService.getProductBasicById(60L));
    }
    
    @Test
    public void getProductPageListByShopId(){
        System.out.println("---productService.getProductPageListByShopId()---");
        ShopProductQueryVO shopProductQueryVO = new ShopProductQueryVO();
        shopProductQueryVO.setShopId(1L);
//        shopProductQueryVO.setCategoryId(1L);
//        shopProductQueryVO.setIsQmd(ToolUtil.intToByte(0));
//        shopProductQueryVO.setStatus(ToolUtil.intToByte(3));
        System.out.println(productService.getProductPageListByShopId(shopProductQueryVO,1,10));
    }
    
    
    @Test
    public void getProductByProductIdSet() {
        System.out.println("---productService.getProductByProductIdSet()---");
        System.out.println(productService.getProductByProductIdSet(Sets.newHashSet(1L,2L,3L,4L)));
    }
    @Test
    public void setIsShelf(){
    	 System.out.println("---productService.setIsShelf()---");
    	 productService.setIsShelf(63L,10L, ToolUtil.intToByte(0));
    }
    
    @Test
    public void getProductDetail() {
        System.out.println("---productService.getProductDetailById()---");
        System.out.println(productService.getProductDetailById(63L));
    }
    
    @Test
    public void queryProduct() {
    	System.out.println("=======queryProduct======");
    	ShopProductQueryVO productQueryVO = new ShopProductQueryVO();
    	productQueryVO.setInputInfo("趣免单");
    	productQueryVO.setPageNum(1);
    	productQueryVO.setPageSize(10);
//    	productQueryVO.setBrandId(1L);
    	PageInfo<ProductAndShopVO> queryProduct = productService.queryProduct(productQueryVO);
    	System.out.println(queryProduct);
    }
}
