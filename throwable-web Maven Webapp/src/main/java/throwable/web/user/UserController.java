package throwable.web.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import throwable.web.WebConf;
import throwable.web.utils.AddressUtil;
import throwable.web.utils.BackTool;
import throwable.web.utils.StringTool;

@At("/user")
@IocBean
public class UserController {

	@Inject
	private UserService userService;
	
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
		return userService.userLogin(username, password, email, nickname, AddressUtil.getIpAddr(req));
	}
}
