package throwable.web.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import throwable.web.WebConf;
import throwable.web.enums.Right;
import throwable.web.utils.AddressUtil;
import throwable.web.utils.BackTool;
import throwable.web.utils.LoginTool;
import throwable.web.utils.StringTool;

@At("/user")
@IocBean
public class UserController {

	@Inject
	private UserService userService;
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/login")
	public Map login(@Param("username") String username, @Param("password") String password, HttpServletRequest req, HttpSession session) {
		if(StringTool.isEmpty(username)) {
			return BackTool.errorInfo("010301", WebConf.errorMsg);
		}
		if(StringTool.isEmpty(password)) {
			return BackTool.errorInfo("010302", WebConf.errorMsg);
		}
		return userService.userLogin(username, password, AddressUtil.getIpAddr(req), req, session);
	}
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/register")
	public Map register(@Param("username") String username, @Param("password") String password, @Param("email") String email, @Param("nickname") String nickname, HttpServletRequest req){
		if(StringTool.isEmpty(username)){
			return BackTool.errorInfo("010101", WebConf.errorMsg);
		}
		if(StringTool.isEmpty(password)){
			return BackTool.errorInfo("010102", WebConf.errorMsg);
		}
		if(StringTool.isEmpty(email)){
			return BackTool.errorInfo("010103", WebConf.errorMsg);
		}
		if(StringTool.isEmpty(nickname)){
			return BackTool.errorInfo("010104", WebConf.errorMsg);
		}
		if(StringTool.isNumber(username)){
			return BackTool.errorInfo("010106", WebConf.errorMsg);
		}
		if(!StringTool.checkEmail(email)){
			return BackTool.errorInfo("010105", WebConf.errorMsg);
		}
		return userService.userRegister(username, password, email, nickname, AddressUtil.getIpAddr(req));
	}
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/activeUser")
	public Map active(@Param("key") String key) {
		if(StringTool.isEmpty(key)) {
			return BackTool.errorInfo("010201", WebConf.errorMsg);
		}
		return userService.userActive(key);
	}
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/userIsLogin")
	public Map isLogin(@Param("userId") String userId, HttpSession httpSession) {
		boolean ret = false;
		if(StringTool.isEmpty(userId)) {
			ret = LoginTool.isLogin(httpSession);
		} else {
			ret = LoginTool.isLogin(httpSession, Integer.parseInt(userId));
		}
		if(!ret) {
			return BackTool.errorInfo("010401");
		}
		Map<String, Object> res = BackTool.successInfo();
		LoginTool.putMap(httpSession, res);
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/isAllowed")
	public Map hasRight(@Param("right") String right, HttpSession httpSession) {
		if(StringTool.isEmpty(right)) {
			return BackTool.errorInfo("010501");
		}
		Right rights = Right.getRight(right);
		if(rights == null) {
			return BackTool.errorInfo("010502");
		}
		if(!LoginTool.isAllowed(httpSession, rights)) {
			return BackTool.errorInfo("010503");
		}
		return BackTool.successInfo();
	}
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getUserExtendInfo")
	public Map getUserExtendInfo(int userId) {
		if(userId < 1) {
			return BackTool.errorInfo("010501");
		}
		return userService.getUserExtendInfo(userId);
	}
	
	public Map saveUserExtend(int userId, String live_address, 
			String now_job, String graduate_school, String motto,
			String interest, String goodAt) {
		if(userId < 1) {
			return BackTool.errorInfo("010501");
		}
		
		return null;
	}
}
