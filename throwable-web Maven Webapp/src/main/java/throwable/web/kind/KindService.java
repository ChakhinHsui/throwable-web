package throwable.web.kind;

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
public class KindService {

	@Inject
	private ThriftCallTool serverCall;
	@Inject
	private ApiDirUtil apiDirUtil;
	
	/**
	 * 添加分类
	 * @param user_id      用户id
	 * @param kind_name    分类名
	 * @return
	 */
	public Map<String, Object> addKind(int user_id, String kind_name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kind_name", kind_name);
		params.put("user_id", user_id);
		ResultMsg resultMsg = serverCall.baseCall(apiDirUtil.getValue(ThirftCommon.KIND_ADD), params);
		if(null == resultMsg) {
			return BackTool.errorInfo("110015", "调用服务器出错");
		}
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			return BackTool.errorInfo(resultMsg.errorCode, resultMsg.retMsg);
		}
		return BackTool.successInfo();
	}
	
	/**
	 * 查询所有的分类
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map queryAllKinds() {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map> list = serverCall.call2ListObj(apiDirUtil.getValue(ThirftCommon.KIND_QUERYALL), params, Map.class);
		Map map = new HashMap();
		map.put("msgCode", 1);
		map.put("kinds", list);
		return map;
	}
}
