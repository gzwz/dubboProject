package cn.qumiandan.apply.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.apply.entity.Apply;
import cn.qumiandan.apply.vo.ApplyVO;
import cn.qumiandan.apply.vo.QueryApplyVO;

@Mapper
public interface ApplyMapper extends BaseMapper<Apply> {

	List<ApplyVO> queryApply(@Param("vo") QueryApplyVO vo);
}