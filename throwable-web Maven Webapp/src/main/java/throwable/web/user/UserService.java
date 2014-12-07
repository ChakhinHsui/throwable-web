package throwable.web.user;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.framework.client.ClientPools;
import throwable.server.framework.rpc.ResultCode;
import throwable.server.framework.rpc.ResultMsg;
import throwable.web.utils.BackTool;
import throwable.web.utils.api.ThirftCommon;

@IocBean
public class UserService {

	@Inject
	private ClientPools thriftPools;
	@Inject
	private ThirftCommon thirftCommon;
	
	@SuppressWarnings("rawtypes")
	public Map userRegister(String username, String password, String email, String nickname, String ip){
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.USER_REGISTER, thirftCommon.initParams("username", username, "password", password, "email", email, "nickname", nickname, "last_active_ip", ip), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map = msg.getRetMap();
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map userActive(String key) {
		Map map = new HashMap();
		String retMsg = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.USER_ACTIVE, thirftCommon.initParams("key", key), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				retMsg = msg.getRetMsg();
				map.put("active", retMsg);
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
}
