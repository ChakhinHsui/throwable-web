package throwable.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.framework.client.ClientPools;
import throwable.server.framework.rpc.ResultCode;
import throwable.server.framework.rpc.ResultMsg;
import throwable.web.enums.LoginMark;
import throwable.web.utils.BackTool;
import throwable.web.utils.LoginTool;
import throwable.web.utils.api.ThirftCommon;

@IocBean
public class UserService {

	@Inject
	private ClientPools thriftPools;
	@Inject
	private ThirftCommon thirftCommon;
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public Map userLogin(String username, String password, String ip, HttpServletRequest req, HttpSession session) {
		if(LoginTool.isLogin(session)) {
			return BackTool.errorInfo("010303");
		}
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.USER_LOGIN, thirftCommon.initParams("username", username, "password", password, "ip", ip), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map = msg.getRetMap();
				map.put("msgCode", 1);
				LoginTool.addSession(req, Integer.parseInt(map.get("id").toString()), map.get("username").toString(), map.get("rights").toString(), map.get("user_state").toString(), LoginMark.hasLogin.getText());
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
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
	
	/**
	 * 获得用户扩展信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map getUserExtendInfo(int userId) {
		Map map = new HashMap();
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.GET_USER_EXTEND, thirftCommon.initParams("userId", userId), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map = msg.retMap;
				map.put("msgCode", 1);
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map saveUserExtendInfo(int userId, String live_address, 
			String now_job, String graduate_school, String motto,
			String interest, String goodAt) {
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.SAVE_USER_EXTEND, thirftCommon.initParams("user_id", userId, 
					"live_address", live_address, "now_job", now_job, "graduate_school", graduate_school, "motto", motto, 
					"interest", interest, "goodAt", goodAt), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map = msg.retMap;
				map.put("msgCode", 1);
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
}
