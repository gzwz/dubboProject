package cn.qumiandanweb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import cn.qumiandan.utils.DateUtil;
import cn.qumiandan.web.countServer.sellerIndexData.entity.request.BackGroundDataParams;

public class Test {

	
	public static void main(String[] args) {
		BackGroundDataParams data = new BackGroundDataParams();
		Date startTime = DateUtil.parseStrToDate("2019-01-03 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date endTime = DateUtil.parseStrToDate("2019-01-03 23:59:59", "yyyy-MM-dd HH:mm:ss ");
		data.setStartTime(startTime);
		data.setEndTime(endTime);
		List<Long> shopId = new ArrayList<Long>();
		shopId.add(23L);
		shopId.add(345L);
		data.setShopIds(shopId);

		System.out.println(new Gson().toJson(data));

	}
	 

}
