package throwable.web.utils.api;

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
	private PropertiesProxy config;

	public String getValue(String key) {
		String val = config.get(key);
		return val == null ? "" : val;
	}
}
