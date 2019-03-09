package cn.qumiandan.shopcategory.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.shopcategory.api.IShopCategoryService;
import cn.qumiandan.shopcategory.entity.ShopCategory;
import cn.qumiandan.shopcategory.mapper.ShopCategoryMapper;
import cn.qumiandan.shopcategory.vo.ShopCategoryPageListVO;
import cn.qumiandan.shopcategory.vo.ShopCategoryVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ToolUtil;

/**
 * @description：店铺分类接口实现类
 * @author：zhuyangyong
 * @date: 2018/11/8 14:35
 */

@Component
@Service(interfaceClass = IShopCategoryService.class)
public class ShopCategoryServiceImpl implements IShopCategoryService {

    @Autowired
    private ShopCategoryMapper shopCategoryMapper;

    /**
     * 添加店铺分类信息
     * @return ShopCategoryVO
     */
    @Override
    public int addShopCategory(ShopCategoryVO cagegoryVO) throws QmdException{
        //判断店铺分类名是否已存在
        ShopCategory nameCategory = shopCategoryMapper.selectByName(cagegoryVO.getName());
        if(nameCategory != null){
            throw new QmdException("添加失败，店铺分类名已存在");
        }
        ShopCategory shopCategory =
        CopyBeanUtil.copyBean(cagegoryVO, ShopCategory.class);
        shopCategory.setCreateDate(new Date());
        shopCategory.setUpdateDate(new Date());
        if(shopCategory.getSort() == null){
            //排序自增
            Integer maxSort = shopCategoryMapper.getMaxSort();
            shopCategory.setSort((maxSort!=null?maxSort:0 )+ 1);
        }
        int ret = shopCategoryMapper.insert(shopCategory);
        if(ret < 1){
            throw new QmdException("添加失败");
        }
        return ret;
    }

    /**
     * 修改分类
     * @param cagegoryVO
     * @return
     */
    @Override
    public int updateShopCategoryById(ShopCategoryVO cagegoryVO) throws QmdException{
        ShopCategory shopCategory = shopCategoryMapper.selectById(cagegoryVO.getId());
        //判断店铺分类名是否已存在
        ShopCategory nameCategory = shopCategoryMapper.selectByName(cagegoryVO.getName());
        if(null == shopCategory){
            throw new QmdException("修改失败，分类信息不存在");
        }else if(nameCategory != null && !nameCategory.getId().equals(cagegoryVO.getId())){
            throw new QmdException("添加失败，店铺分类名已存在");
        }else{
        	shopCategory = CopyBeanUtil.copyBean(cagegoryVO, ShopCategory.class);
            shopCategory.setUpdateDate(new Date());
            int ret = shopCategoryMapper.updateById(shopCategory);
            if(ret < 1){
                throw new QmdException("修改失败");
            }
            return ret;
        }
    }
    /**
     * 根据id删除店铺分类
     * @param id
     * @return
     */
    @Override
    public int deleteShopCategoryById(Long id) throws QmdException{
        ShopCategory shopCategory = shopCategoryMapper.selectById(id);
        if(null == shopCategory){
            throw new QmdException("删除失败，分类信息不存在");
        }else{
            shopCategory.setStatus(ToolUtil.intToByte(0));
            shopCategory.setUpdateDate(new Date());
            int ret = shopCategoryMapper.updateById(shopCategory);
            if(ret < 1){
                throw new QmdException("删除失败");
            }
            return ret;
        }
    }

    /**
     * 根据ID获取店铺分类详细信息
     * @return ShopCategoryVO 分类详细信息
     */
    @Override
    public ShopCategoryVO getShopCategoryById(Long id) {
    	ShopCategory shopCategory = shopCategoryMapper.selectById(id);
    	if(shopCategory == null) {
    		return null;
    	}
    	return CopyBeanUtil.copyBean(shopCategory, ShopCategoryVO.class);
    }

    @Override
    public PageInfo<ShopCategoryVO> getShopCategoryPageListByParentId(ShopCategoryPageListVO shopCategoryPageListVO){
        PageHelper.startPage(shopCategoryPageListVO.getPageNum(), shopCategoryPageListVO.getPageSize());
        if(shopCategoryPageListVO.getParentId() == null){
            shopCategoryPageListVO.setParentId(0L);
        }
        //条件查询
        List<ShopCategoryVO> shopCategoryList = shopCategoryMapper.getShopCategoryListByParentId(shopCategoryPageListVO.getParentId());
        //封装分页结果
        PageInfo<ShopCategoryVO> pageInfo = new PageInfo<>(shopCategoryList);
        pageInfo.setTotal(pageInfo.getTotal());
        //返回分页对象
        return pageInfo;
    }

    @Override
    public List<Map<String,Object>> getAllShopCategoryList(){
        //条件查询
        List<Map<String,Object>> shopCategoryList = new ArrayList<>();
        //一级分类列表
        List<ShopCategoryVO> fistShopCategoryList = shopCategoryMapper.getFistShopCategoryList(0L);
        //二级分类列表
        List<ShopCategoryVO> secondShopCategoryList = shopCategoryMapper.getSecondShopCategoryList();
        if(fistShopCategoryList.size() > 0){
            for(ShopCategoryVO firstVO : fistShopCategoryList){
                Map<String,Object> firstShopCategoryMap = new LinkedHashMap<String,Object>();
                firstShopCategoryMap.put("id",firstVO.getId());
                firstShopCategoryMap.put("name",firstVO.getName());
                firstShopCategoryMap.put("sort",firstVO.getSort());
                firstShopCategoryMap.put("parentId",firstVO.getParentId());
                firstShopCategoryMap.put("status",firstVO.getStatus());
                List<Map<String,Object>> children = new ArrayList<>();
                if(secondShopCategoryList.size() > 0){
                    for(ShopCategoryVO secondVO : secondShopCategoryList){
                        if(secondVO.getParentId().equals(firstVO.getId())){
                            Map<String,Object> secondShopCategoryMap = new LinkedHashMap<String,Object>();
                            secondShopCategoryMap.put("id",secondVO.getId());
                            secondShopCategoryMap.put("name",secondVO.getName());
                            secondShopCategoryMap.put("sort",secondVO.getSort());
                            secondShopCategoryMap.put("parentId",secondVO.getParentId());
                            secondShopCategoryMap.put("status",secondVO.getStatus());
                            children.add(secondShopCategoryMap);
                        }
                    }
                }
                firstShopCategoryMap.put("children",children);
                shopCategoryList.add(firstShopCategoryMap);
            }
        }

        //返回分页对象
        return shopCategoryList;
    }

}
