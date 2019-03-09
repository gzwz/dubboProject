package test.indexpic;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.indexpicture.api.IIndexPictureService;
import cn.qumiandan.indexpicture.vo.IndexPictureVO;
import cn.qumiandan.indexpicture.vo.QueryIndexPictureParamsVO;
import test.BaseTest;

/**
 * @description：首页轮播图片接口测试
 * @author：zhuyangyong
 * @date: 2018/11/10 15:25
 */
public class IndexPictureTest extends BaseTest {

    @Resource
    private IIndexPictureService indexPictureService;

    
    @Test
    public void  updateIndexPictureById() {
    	System.out.println("======updateIndexPictureById======");
    	IndexPictureVO indexPictureVO = new IndexPictureVO();
    	indexPictureVO.setId(7L);
    	indexPictureVO.setIntro("首页轮播图片1");
    	Integer i = indexPictureService.updateIndexPictureById(indexPictureVO);
    	System.out.println(i);
    }
    
    @Test
    public void queryIndexPicture() {
    	System.out.println("======QueryIndexPicture======");
    	QueryIndexPictureParamsVO paramsVO = new QueryIndexPictureParamsVO();
//    	paramsVO.setAreaCode("110101");
//    	paramsVO.setIsValid(new Byte("1"));
    	PageInfo<IndexPictureVO> queryIndexPicture = indexPictureService.queryIndexPicture(paramsVO);
    	System.out.println(queryIndexPicture);
    }
    @Test
    public void getIndexPictureList(){
        System.out.println("---indexPictureService.getIndexPictureList---");
        //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           // Date effectiveTime = formatter.parse("2018-11-16 10:11:50");
          //  Date loseTime = formatter.parse("2018-11-16 19:11:50");
            System.out.println(indexPictureService.getIndexPictureList("520103").toString());
    }

    @Test
    public void addIndexPicture() throws QmdException,ParseException {
        System.out.println("---indexPictureService.addIndexPicture---");
        IndexPictureVO indexPictureVO = new IndexPictureVO();
        indexPictureVO.setName("test1115-01");
        indexPictureVO.setImageUrl("/test111501");
        indexPictureVO.setImageSize(7096L);
        indexPictureVO.setSuffix("png");
        indexPictureVO.setIntro("云岩区首页轮播图");
        indexPictureVO.setAreaCode("0");
        indexPictureVO.setCreateDate(new Date());
        System.out.println(indexPictureService.addIndexPicture(indexPictureVO));
    }
}
