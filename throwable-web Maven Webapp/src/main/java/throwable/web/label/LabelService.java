package throwable.web.label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.framework.rpc.ResultCode;
import throwable.server.framework.rpc.ResultMsg;
import throwable.web.utils.BackTool;
import throwable.web.utils.api.ApiDirUtil;
import throwable.web.utils.api.ThirftCommon;
import throwable.web.utils.api.ThriftCallTool;

@IocBean
public class LabelService {

	@Inject
	private ThriftCallTool serverCall;
	
	@Inject
	private ApiDirUtil apiDirUtil;
	
	/**
	 * 添加标签
	 * @param userId  用户id
	 * @param name    标签名
	 * @return
	 */
	public Map<String, Object> addLabel(int userId, String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("name", name);
		params.put("type", "网站公有");
		ResultMsg resultMsg = serverCall.baseCall(apiDirUtil.getValue(ThirftCommon.LABEL_ADD), params);
		if(null == resultMsg) {
			return BackTool.errorInfo("120015", "调用服务器出错");
		}
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			return BackTool.errorInfo(resultMsg.errorCode, resultMsg.retMsg);
		}
		return BackTool.successInfo();
	}
	
	/**
	 * 查询所有的标签
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map queryAllLabels() {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map> list = serverCall.call2ListObj(apiDirUtil.getValue(ThirftCommon.LABEL_QUERYALL), params, Map.class);
		Map map = new HashMap();
		map.put("labels", list);
		return map;
	}
	
	/**
	 * 根据问题id查询标签
	 * @param questionId  问题id
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map queryLabelsByQuestionId(int questionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionId", questionId);
		List<Map> list = serverCall.call2ListObj(apiDirUtil.getValue(ThirftCommon.LABEL_QUERY_BY_QUESTIONID), params, Map.class);
		Map map = new HashMap();
		map.put("qlabels", list);
		return map;
	}
	
	/**
	 * 根据用户id查询标签
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map queryLabelsByUserId(int userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		List<Map> list = serverCall.call2ListObj(apiDirUtil.getValue(ThirftCommon.LABEL_QUERY_BY_USERID), params, Map.class);
		Map map = new HashMap();
		map.put("ulabels", list);
		return map;
	}
}
