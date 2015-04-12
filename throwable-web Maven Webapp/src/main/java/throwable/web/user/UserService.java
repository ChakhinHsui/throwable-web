package throwable.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;

import throwable.server.framework.client.ClientPools;
import throwable.server.framework.rpc.ResultCode;
import throwable.server.framework.rpc.ResultMsg;
import throwable.web.enums.LoginMark;
import throwable.web.utils.BackTool;
import throwable.web.utils.LoginTool;
import throwable.web.utils.api.ThirftCommon;
import throwable.web.utils.api.ThriftCallTool;

@IocBean
public class UserService {

	@Inject
	private ThriftCallTool userCall;
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
			String interest, String goodAt, String photo) {
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.SAVE_USER_EXTEND, thirftCommon.initParams("user_id", userId, 
					"live_address", live_address, "now_job", now_job, "graduate_school", graduate_school, "motto", motto, 
					"interest", interest, "goodAt", goodAt, "image", photo), 100);
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
	
	
	
	/**
	 * ****************************************************
	 * 重构之后
	 * ****************************************************
	 */
	
	
	/**
	 * 用户登陆
	 * @param username   用户名
	 * @param password   密码
	 * @param ip         ip
	 * @param req       
	 * @param session
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map userLogin_new(String username, String password, String ip, HttpServletRequest req, HttpSession session) {
		if(LoginTool.isLogin(session)) {
			return BackTool.errorInfo("010303");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "throwable");
		params.put("username", username);
		params.put("password", password);
		params.put("ip", ip);
		ResultMsg resultMsg = userCall.baseCall("/user/login", params);
		if(null == resultMsg) {
			return BackTool.errorInfo("120015", "调用服务器出错");
		}
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			return BackTool.errorInfo(resultMsg.errorCode, resultMsg.retMsg);
		}
		Map map = Json.fromJson(Map.class, resultMsg.retMsg);
		map.put("msgCode", 1);
		LoginTool.addSession(req, Integer.parseInt(map.get("id").toString()), username,  map.get("rights").toString(), map.get("user_state").toString(), LoginMark.hasLogin.getText());
		return map;
	}
	
	/**
	 * 用户注册
	 * @param username   用户名
	 * @param password   密码
	 * @param email      邮箱
	 * @param nickname   昵称
	 * @param ip
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map userRegister_new(String username, String password, String email, String nickname, String ip){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "throwable");
		params.put("username", username);
		params.put("password", password);
		params.put("email", email);
		params.put("ip", ip);
		ResultMsg resultMsg = userCall.baseCall("/user/register", params);
		if(null == resultMsg) {
			return BackTool.errorInfo("120015", "调用服务器出错");
		}
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			return BackTool.errorInfo(resultMsg.errorCode, resultMsg.retMsg);
		}
		if("1".equals(resultMsg.retMsg)) {
			return BackTool.successInfo();
		}
		return BackTool.errorInfo("12221", "注册失败");
	}
	
	/**
	 * 根据链接激活账户
	 * @param key
	 * @return
	 */
	@SuppressWarnings({"rawtypes"})
	public Map userActive_new(String key) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "throwable");
		params.put("key", key);
		ResultMsg resultMsg = userCall.baseCall("/user/activeUser", params);
		if(null == resultMsg) {
			return BackTool.errorInfo("120015", "调用服务器出错");
		}
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			return BackTool.errorInfo(resultMsg.errorCode, resultMsg.retMsg);
		}
		if("1".equals(resultMsg.retMsg)) {
			return BackTool.successInfo();
		}
		return BackTool.errorInfo("12221", "激活失败");
	}
}
