package throwable.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;

@IocBean
public class DateUtil {

	public Map<String, String> getDate() {
		Map<String, String> map = new HashMap<String, String>();
		Date endDate = new Date();
		Calendar theCa = Calendar.getInstance();
		theCa.setTime(endDate);
		theCa.add(Calendar.DATE, -30);
		Date startDate = theCa.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strEndDate = sdf.format(endDate);
		String strStartDate = sdf.format(startDate);
		map.put("endDate", strEndDate);
		map.put("startDate", strStartDate);
		return map;
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @notice startDate 为空时 取结束时间+30天 endDate 为空时 取开始时间+30天 都为空时 取 现在时间+30天
	 * @return
	 * @throws ParseException
	 */
	public Map<String, String> getDate(String startDate, String endDate) throws ParseException {
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String strEndDate = new String();
		String strStartDate = new String();
		if (StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
			endDate = endDate + " 23:59:59";
			Date eDate = sdf.parse(endDate);
			Calendar theCa = Calendar.getInstance();
			theCa.setTime(eDate);
			theCa.add(Calendar.DATE, -30);
			Date sDate = theCa.getTime();
			strEndDate = sdf.format(eDate);
			strStartDate = sdf1.format(sDate) + " 00:00:00";
		} else if (!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
			startDate = startDate + " 00:00:00";
			Date sDate = sdf.parse(startDate);
			Calendar theCa = Calendar.getInstance();
			theCa.setTime(sDate);
			theCa.add(Calendar.DATE, +30);
			Date eDate = theCa.getTime();
			strEndDate = sdf.format(eDate);
			strStartDate = sdf1.format(sDate) + " 00:00:00";
		} else {
			Date eDate = new Date();
			Calendar theCa = Calendar.getInstance();
			theCa.setTime(eDate);
			theCa.add(Calendar.DATE, -30);
			Date sDate = theCa.getTime();
			strEndDate = sdf.format(eDate);
			strStartDate = sdf1.format(sDate) + " 00:00:00";
		}
		map.put("endDate", strEndDate);
		map.put("startDate", strStartDate);
		return map;
	}

	/**
	 * 返回本周一到当前日期
	 * 
	 * @return
	 */
	public static Date[] getCurrWeek() {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis());
		int week = startDate.getDay();
		week = week == 0 ? 6 : week - 1;
		startDate.setTime(endDate.getTime() - week * 1000 * 60 * 60 * 24);
		return new Date[] { startDate, endDate };
	}

	/**
	 * 获取上周一到周日的时间段。返回日期类型
	 * 
	 * @return
	 */
	public static Date[] getLastWeek() {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis());
		int week = startDate.getDay();
		week = week == 0 ? 6 : week - 1;
		int oneDay = 1000 * 60 * 60 * 24;
		endDate.setTime(startDate.getTime() - week * oneDay - oneDay);
		startDate.setTime(endDate.getTime() - 6 * oneDay);
		return new Date[] { startDate, endDate };
	}

	public static void main(String[] args) {
		System.err.println(Json.toJson(getLastWeek()));
	}

}
