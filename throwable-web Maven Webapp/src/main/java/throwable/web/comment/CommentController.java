package throwable.web.comment;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import throwable.web.utils.BackTool;
import throwable.web.utils.StringTool;

@At("comment")
@IocBean
public class CommentController {

	@Inject
	private CommentService commentService;
	
	/**
	 * 增加评论
	 * @param belongId
	 * @param belongType
	 * @param toUserId
	 * @param toUserName
	 * @param fromUserId
	 * @param fromUserName
	 * @param comment
	 * @return
	 */
	@Ok("json")
	@At("/addComment")
	public Map<String, Object> addComment(int belongId, int belongType, int toUserId,
			String toUserName, int fromUserId, String fromUserName, String comment) {
		if(belongId < 1 || (belongType != 1 && belongType != 2) || toUserId < 1
				|| fromUserId < 1 || StringTool.isEmpty(toUserName) 
				|| StringTool.isEmpty(fromUserName) || StringTool.isEmpty(comment)) {
			return BackTool.errorInfo("300001", "参数错误");
		}
		return commentService.addComment(belongId,  belongType, toUserId, toUserName, fromUserId, fromUserName, comment);
	}
	
}
