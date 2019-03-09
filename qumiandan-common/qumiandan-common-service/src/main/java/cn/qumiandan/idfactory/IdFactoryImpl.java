package cn.qumiandan.idfactory;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.utils.SnowflakeIdWorker;
import cn.qumiandan.idfactory.api.IdFactory;
import cn.qumiandan.utils.DateUtil;

@Component
@Service(interfaceClass = IdFactory.class)
public class IdFactoryImpl implements IdFactory {

	//自定义前缀使用该实力
	private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker();
	private static Calendar calender = Calendar.getInstance();
	
	@Override
	public String getId() {
		String shortDate = DateUtil.getShortDate();
		if (!DateUtil.isSameDayWithToday(new Date(idWorker.getStartTime()))) {
			calender.add(Calendar.DAY_OF_YEAR, -1);
			long startTime = DateUtil.strToDate(DateUtil.getReqDateyyyyMMdd(calender.getTime())).getTime();
			idWorker.setStartTime(startTime);
		}
		String nextId = String.valueOf(idWorker.nextId());
		String id = null ;
		int len = nextId.length() + shortDate.length();
		if (len <= 32) {
			id = shortDate + nextId;
		}else if (len > 32){
			len = len - 32 ;
			nextId = StringUtils.substring(nextId, len);
			id = shortDate + nextId;
		}
		return id;
	}

 

/*	@Override
	public String getPrefixId(String pre) throws CsException {
		
		if (StringUtils.isBlank(pre) || pre.length() > 5) {
			throw new CsException("前缀不能为空，且长度不能超过英文5不字符");
		}
		String nextId = String.valueOf(idWorker.nextId());
		String id = null ;
		int len = nextId.length() + pre.length();
		if (len <= 32) {
			id = pre + nextId;
		}else if (len > 32){
			len = len - 32 ;
			nextId = StringUtils.substring(nextId, len);
			id = pre + nextId;
		}
		return id;
	}
	*/
}
