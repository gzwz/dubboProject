package cn.qumiandan.complain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.complain.entity.Complain;
import cn.qumiandan.complain.vo.ComplainVO;
import cn.qumiandan.complain.vo.QueryComplainVO;
@Mapper
public interface ComplainMapper extends BaseMapper<Complain>{
	
	List<ComplainVO> queryComplain(@Param("params")QueryComplainVO params);
    
}