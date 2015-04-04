package throwable.web.comment;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.framework.rpc.ResultCode;
import throwable.server.framework.rpc.ResultMsg;
import throwable.web.utils.BackTool;
import throwable.web.utils.api.ThriftCallTool;

@IocBean
public class CommentService {

	@Inject
	private ThriftCallTool serverCall;
	
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
	public Map<String, Object> addComment(int belongId, int belongType, int toUserId,
			String toUserName, int fromUserId, String fromUserName, String comment) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("belongId", belongId);
		params.put("belongType", 1 == belongType ? 0 : 1);
		params.put("toUserId", toUserId);
		params.put("toUserName", toUserName);
		params.put("fromUserId", fromUserId);
		params.put("fromUserName", fromUserName);
		params.put("comment", comment);
		ResultMsg resultMsg = serverCall.baseCall("/comment/addComment", params);
		if(null == resultMsg) {
			return BackTool.errorInfo("120015", "调用服务器出错");
		}
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			return BackTool.errorInfo(resultMsg.errorCode, resultMsg.retMsg);
		}
		return BackTool.successInfo();
	}
}
