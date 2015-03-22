package throwable.web.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nutz.json.Json;

public class StringTool {

	public static String repaymentDate2Str(int repaymentDate) {
		String s = String.valueOf(repaymentDate);
		return s.substring(0, 4) + "-" + s.substring(4, 6) + "-"
				+ s.substring(6, 8);
	}

	public static String float2Str1(float f) {
		return new DecimalFormat("#.0").format(f);
	}

	public static Set<Long> commaStringToLongSet(String commaString) {
		Set<Long> testUserIds = new HashSet<Long>();
		if (StringTool.isNotEmpty(commaString)) {
			String[] userIdArray = commaString.split(",");
			if (userIdArray.length > 0) {
				for (int i = 0; i < userIdArray.length; i++) {
					testUserIds.add(Long.parseLong(userIdArray[i]));
				}
			} else {
				testUserIds.add(Long.parseLong(commaString));
			}
		}
		return testUserIds;
	}
	
	public static Set<Integer> commaStringToIntegerSet(String commaString) {
		Set<Integer> testUserIds = new HashSet<Integer>();
		if (StringTool.isNotEmpty(commaString)) {
			String[] userIdArray = commaString.split(",");
			if (userIdArray.length > 0) {
				for (int i = 0; i < userIdArray.length; i++) {
					testUserIds.add(Integer.parseInt(userIdArray[i]));
				}
			} else {
				testUserIds.add(Integer.parseInt(commaString));
			}
		}
		return testUserIds;
	}

	public static BigDecimal str2BigDecimal(String str) {
		if (isEmpty(str)) {
			return new BigDecimal(0);
		}
		BigDecimal l;
		try {
			l = new BigDecimal(str);
		} catch (Exception e) {
			l = new BigDecimal(0);
		}
		return l;
	}

	public static float str2float0Null(String str) {
		if (isEmpty(str)) {
			return 0;
		}
		float l;
		try {
			l = Float.parseFloat(str);
		} catch (Exception e) {
			l = 0;
		}
		return l;
	}
	
	public static double str2double0Null(String str) {
		if (isEmpty(str)) {
			return 0;
		}
		double l;
		try {
			l = Double.parseDouble(str);
		} catch (Exception e) {
			l = 0;
		}
		return l;
	}

	public static int str2int0Null(String str) {
		if (isEmpty(str)) {
			return 0;
		}
		int l;
		try {
			l = Integer.parseInt(str);
		} catch (Exception e) {
			l = 0;
		}
		return l;
	}

	public static long str2long0Null(String str) {
		if (isEmpty(str)) {
			return 0;
		}
		long l;
		try {
			l = Long.parseLong(str);
		} catch (Exception e) {
			l = 0;
		}
		return l;
	}
	/**
	 * 把date转换成一个时间戳字符串yyyyMMddHHmmssSSS。
	 * @param date
	 * @return
	 */
	public static String date2long(Date date){
		if(date==null) return null;
		SimpleDateFormat t = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String s = t.format(date);
		return s;
	}
	
	public static boolean isEmpty(Object object) {
		if(null == object) {
			return true;
		}
		if(object instanceof String) {
			return "".equals(((String)object).trim());
		}
		if(object instanceof Map) {
			return ((Map<?,?>)object).isEmpty();
		}
		if(object instanceof List) {
			return ((List<?>)object).isEmpty();
		}
		if(object instanceof Set) {
			return ((Set<?>)object).isEmpty();
		}
		return false;
	}
	
	/**
	 * 将map的Object转成String
	 * @param map
	 * @return
	 */
	public static Map<String, String> mapStr2Obj(Map<String, Object> map) {
		Map<String, String> retMap = new HashMap<String, String>();
		for(String key : map.keySet()) {
			retMap.put(key, map.get(key).toString());
		}
		return retMap;
	}
	
	/**
	 * 将List中的str转为Object
	 * @param list
	 * @param type
	 * @return
	 */
	public static <T>List<T> listStr2Obj(List<String> list, Class<T> type) {
		List<T> retList = new ArrayList<T>();
		for(String str : list) {
			retList.add(Json.fromJson(type, str));
		}
		return retList;
	}
	
	/**
	 * 将List中的Object转为String
	 * @param list
	 * @return
	 */
	public static List<String> listObj2Str(List<Object> list) {
		List<String> retList = new ArrayList<String>();
		for(Object o : list) {
			retList.add(Json.toJson(o));
		}
		return retList;
	}

	public static boolean isEmptyOrLong(String s, int length) {
		if (isEmpty(s) || s.length() > length) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String s) {
		if (null != s && !"".equals(s.trim())) {
			return true;
		}
		return false;
	}
	
	public static boolean isNumber(String number) {

		boolean isNumber = false;
		if(null==number || "".equals(number)){
			return false;
		}
		int index = number.indexOf(",");
		if (index >= 0) {
			// 有逗号等分隔符的数字
			isNumber = number.matches("[1-9]+[0-9]*(,[0-9]{3})+(\\.[0-9]+)?");
		} else {
			isNumber = number.matches("[1-9]+[0-9]*(\\.[0-9]+)?");

		}
		return isNumber;
	}

	/**
	 * 转换错误栈为为字符串。
	 */
	public static String getExceptionStack(Throwable e) {
		if (e == null)
			return "";
		OutputStream ou = new ByteArrayOutputStream();
		PrintStream o = new PrintStream(ou);
		e.printStackTrace(o);
		return ou.toString();
	}

	/**
	 * 验证邮箱地址是否正确
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[_|-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 */
	public static boolean isMobileNo(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断对象是否为空，为空则返回传来的默认值，不是空则返回对象的字符串形式
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @param defStr
	 *            默认值
	 * @return String
	 */
	public static String getString(Object obj, Object defStr) {
		return obj == null ? String.valueOf(defStr) : obj.toString();
	}

	/**
	 * 判断对象是否为空，为空则返回传来的0，不是空则返回对象的Long形式
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @return Long
	 */
	public static long getLong(Object obj) {
		return getLong(obj, 0);
	}

	/**
	 * 判断对象是否为空，为空则返回传来的defArg，不是空则返回对象的Long形式
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @param defArg
	 *            默认的Long值
	 * @return Long
	 */
	public static long getLong(Object obj, long defArg) {
		return Long.parseLong(getString(obj, defArg));
	}

	/**
	 * 判断对象是否为空，为空则返回传来的0，不是空则返回对象的Long形式
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @return Long
	 */
	public static int getInt(Object obj) {
		return getInt(obj, 0);
	}

	/**
	 * 判断对象是否为空，为空则返回传来的defArg，不是空则返回对象的Long形式
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @param defArg
	 *            默认的Long值
	 * @return Long
	 */
	public static int getInt(Object obj, long defArg) {
		return Integer.parseInt(getString(obj, defArg));
	}

	/**
	 * 检查当前对象是否不为空 包括NULL和空字符串 如果不为空返回true
	 * 
	 * @param obj
	 *            要检查的对象
	 * @return boolean 返回true或false
	 */
	public static boolean isNotEmpty(Object obj) {
		boolean isTrue = false;
		if (null != obj) {
			isTrue = true;
		}
		if (obj instanceof String) {
			if (((String) obj).trim().length() > 0)
				isTrue = true;
			else
				isTrue = false;
		}
		if (obj instanceof List<?>) {
			if (((List<?>) obj).size() > 0) {
				isTrue = true;
			}
		}
		if (obj instanceof Map<?, ?>) {
			if (((Map<?, ?>) obj).size() > 0) {
				isTrue = true;
			}
		}
		return isTrue;
	}

	public static String formatuserName(String username){
    	if(isEmpty(username)) return "";
    	return username.length() <=12 ? username : username.substring(0, 12);
    }
	
	/**
	 * 判断是否是ip
	 * @param ip
	 * @return
	 */
	public static boolean isIp(String ip){
		if(ip.trim().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
			String[] s = ip.split("\\.");
			if(Integer.parseInt(s[0])<255)  
                if(Integer.parseInt(s[1])<255)  
                    if(Integer.parseInt(s[2])<255)  
                        if(Integer.parseInt(s[3])<255){
                        	return true;
                        }
		}
		return false;
	}
}
