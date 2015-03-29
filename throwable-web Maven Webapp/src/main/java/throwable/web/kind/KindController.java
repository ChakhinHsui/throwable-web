package throwable.web.kind;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import throwable.web.enums.Right;
import throwable.web.utils.BackTool;
import throwable.web.utils.LoginTool;
import throwable.web.utils.StringTool;

@At("kind")
@IocBean
public class KindController {

	@Inject
	private KindService kindService;
	
	/**
	 * 添加分类
	 * @param user_id       用户id
	 * @param kind_name     分类名
	 * @param httpSession  
	 * @return
	 */
	@At("/addKind")
	public Map<String, Object> addKind(int user_id, String kind_name, HttpSession httpSession) {
		if(user_id < 1 || StringTool.isEmpty(kind_name)) {
			return BackTool.errorInfo("110011", "参数错误");
		}
		if(!LoginTool.isLogin(httpSession, user_id)) {
			return BackTool.errorInfo("110012", "用户未登陆");
		}
		if(!LoginTool.isAllowedStrict(httpSession, Right.context)) {
			return BackTool.errorInfo("110013", "用户权限不够");
		}
		return kindService.addKind(user_id, kind_name);
	}
	
	/**
	 * 查询所有的分类
	 * @param httpSession
	 * @return
	 */
	@Ok("json")
	@SuppressWarnings("rawtypes")
	@At("/queryAllKinds")
	public Map queryAllList(HttpSession httpSession) {
		if(!LoginTool.isLogin(httpSession)) {
			return BackTool.errorInfo("110012", "用户未登陆");
		}
		return kindService.queryAllKinds();
	}
}
