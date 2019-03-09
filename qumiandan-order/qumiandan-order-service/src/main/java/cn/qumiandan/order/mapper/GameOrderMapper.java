package cn.qumiandan.order.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.qumiandan.order.entity.GameOrder;
import cn.qumiandan.order.vo.GameOrderDetailVO;

@Mapper
public interface GameOrderMapper extends BaseMapper<GameOrder>{

	GameOrderDetailVO selectGameOrderByGameId(String gameId);
}