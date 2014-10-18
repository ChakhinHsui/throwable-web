package throwable.web.utils.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * 获取服务器端的api
 * 
 */
@IocBean
public class ApiDirUtil {

	@Inject
	private PropertiesProxy apiConfig;

	public String getValue(String key) {
		String val = apiConfig.get(key);
		return val == null ? "" : val;
	}
}
