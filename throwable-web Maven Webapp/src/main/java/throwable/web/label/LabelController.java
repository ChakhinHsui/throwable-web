package throwable.web.label;

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

@At("label")
@IocBean
public class LabelController {

	@Inject
	private LabelService labelService;
	
	/**
	 * 添加标签
	 * @param userId         用户id
	 * @param name           标签名
	 * @param httpSession
	 * @return
	 */
	@At("/addLabel")
	public Map<String, Object> addLabel(int userId, String name, HttpSession httpSession) {
		if(StringTool.isEmpty(name) || userId < 1) {
			return BackTool.errorInfo("120001", "参数错误");
		}
		if(!LoginTool.isLogin(httpSession, userId)) {
			return BackTool.errorInfo("120002", "用户未登陆");
		}
		if(!LoginTool.isAllowedStrict(httpSession, Right.context)) {
			return BackTool.errorInfo("120003", "用户没有权限");
		}
		return labelService.addLabel(userId, name);
	}
	
	/**
	 * 查询所有的标签
	 * @param httpSession
	 * @return
	 */
	@Ok("json")
	@SuppressWarnings("rawtypes")
	@At("/queryAllLabels")
	public Map queryAllLabels(HttpSession httpSession) {
		return labelService.queryAllLabels();
	}
	
	/**
	 * 根据问题id查询标签
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@At("/queryLabelsByQuestionId")
	public Map queryLabelsByQuestionId(int questionId) {
		if(questionId < 1) {
			return BackTool.errorInfo("120003", "问题id不能为空");
		}
		return labelService.queryLabelsByQuestionId(questionId);
	}
	
	/**
	 * 根据用户id查询标签
	 * @param userId   用户id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map queryLabelsByUserId(int userId) {
		if(userId < 1) {
			return BackTool.errorInfo("120003", "用户id不能为空");
		}
		return labelService.queryLabelsByUserId(userId);
	}
}
