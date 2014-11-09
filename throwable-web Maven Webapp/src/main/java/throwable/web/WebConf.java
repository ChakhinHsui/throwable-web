package throwable.web;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.IocBean;

@IocBean
public class WebConf {

	public static Ioc ioc;//IOC容器
	public static PropertiesProxy webProp = new PropertiesProxy();
	public static PropertiesProxy errorMsg = new PropertiesProxy();
	
	/**
	 * 文件配置的信息
	 */
	public void run(Ioc ioc) {
		initPro(ioc);
	}
	
	private void initPro(Ioc ioc){
		WebConf.ioc = ioc;
		webProp.setPaths(new String[] { "application.properties" });
		errorMsg.setPaths(new String[] { "errorMsg.properties" });
	}
}
