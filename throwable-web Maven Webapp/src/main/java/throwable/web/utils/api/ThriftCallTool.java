package throwable.web.utils.api;

import java.util.List;
import java.util.Map;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.framework.client.ClientPools;
import throwable.server.framework.rpc.ResultCode;
import throwable.server.framework.rpc.ResultMsg;
import throwable.web.utils.StringTool;

/**
 * 调用服务器的thrift方法
 * @author WaterHsu
 *
 */
public class ThriftCallTool {

	private static final Log log = Logs.getLog(ThriftCallTool.class);
	
	private ClientPools thriftPools;
	
	public ThriftCallTool(ClientPools thriftPools) {
		this.thriftPools = thriftPools;
	}
	
	/**
	 * 调用服务器的方法
	 * @param url     功能码
	 * @param params  参数
	 * @return
	 */
	public ResultMsg baseCall(String url, Map<String, Object> params) {
		ResultMsg resultMsg = null;
		try {
			resultMsg = thriftPools.call("W" + System.nanoTime(), url, StringTool.mapStr2Obj(params));
		} catch(Exception e) {
			log.error("调用服务器出错 " + e);
		}
		return resultMsg;
	}
	
	/**
	 * 调用服务器的方法  返回map类型
	 * @param url
	 * @param params
	 * @return
	 */
	public Map<String, String> call2Map(String url, Map<String, Object> params) {
		ResultMsg resultMsg = baseCall(url, params);
		if(null == resultMsg || ResultCode.SUCCESS != resultMsg.retCode) {
			return null;
		}
		return resultMsg.retMap;
	}
	
	/**
	 * 调用服务器的方法  返回List类型
	 * @param url
	 * @param params
	 * @return
	 */
	public List<String> call2List(String url, Map<String, Object> params) {
		ResultMsg resultMsg = baseCall(url, params);
		if(null == resultMsg || ResultCode.SUCCESS != resultMsg.retCode) {
			return null;
		}
		return resultMsg.retList;
	}
	
	/**
	 * 调用服务器的方法  返回String类型
	 * @param url
	 * @param params
	 * @return
	 */
	public String call2Str(String url, Map<String, Object> params) {
		ResultMsg resultMsg = baseCall(url, params);
		if(null == resultMsg || ResultCode.SUCCESS != resultMsg.retCode) {
			return null;
		}
		return resultMsg.retMsg;
	}
	
	/**
	 * 调用服务器的方法  返回Object类型
	 * @param url      功能码
	 * @param params   传入的参数
	 * @param type     返回的类型
	 * @return
	 */
	public <T>List<T> call2ListObj(String url, Map<String, Object> params, Class<T> type) {
		ResultMsg resultMsg = baseCall(url, params);
		if(null == resultMsg || ResultCode.SUCCESS != resultMsg.retCode) {
			return null;
		}
		return StringTool.listStr2Obj(resultMsg.retList, type);
	}
	
	/**
	 * 调用服务器的方法  返回int类型
	 * @param url
	 * @param params
	 * @return
	 */
	public int call2Int(String url, Map<String, Object> params) {
		ResultMsg resultMsg = baseCall(url, params);
		if(null == resultMsg || ResultCode.SUCCESS != resultMsg.retCode) {
			return -1;
		}
		return Integer.parseInt(resultMsg.retMsg);
	}
}
