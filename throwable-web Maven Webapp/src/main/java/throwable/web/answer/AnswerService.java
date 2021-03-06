package throwable.web.answer;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.framework.client.ClientPools;
import throwable.server.framework.rpc.ResultCode;
import throwable.server.framework.rpc.ResultMsg;
import throwable.web.utils.BackTool;
import throwable.web.utils.api.ThirftCommon;
import throwable.web.utils.api.ThriftCallTool;

@IocBean
public class AnswerService {

	@Inject
	private ClientPools thriftPools;
	@Inject
	private ThirftCommon thirftCommon;
	@Inject
	private ThriftCallTool serverCall;
	
	@SuppressWarnings("rawtypes")
	public Map addAnswer(int question_id, String answer_abstract, String answer_description, int user_id){
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.A_ADD_ANSWER, thirftCommon.initParams(
					"question_id", question_id, 
					"answer_abstract", answer_abstract, "answer_description", answer_description, 
					"correct_type", 3, "user_id", user_id), 100);
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
	
	@SuppressWarnings("rawtypes")
	public Map getAnswerByQuestionId(long questionId, long userId){
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.A_GET_ANSWER, thirftCommon.initParams("questionId", questionId, "userId", userId), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map = msg.getRetMap();
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(map);
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	public Map getUserAnswers(String userId) {
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.A_GET_USER_ANSWER, thirftCommon.initParams("userId", userId), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map = msg.getRetMap();
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(map);
		return map;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map getUserAnswerNumber(int userId) {
		Map map = new HashMap();
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.A_GET_USER_ANSWER_NUM, thirftCommon.initParams("userId", userId), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map = msg.getRetMap();
				map.put("number", msg.retMsg);
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(map);
		return map;
	}
	
	/**
	 * 赞同答案
	 * @param answerId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map agreeAnswer(long answerId, long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("answerId", answerId);
		params.put("userId", userId);
		ResultMsg resultMsg = serverCall.baseCall("/answer/agreeAnswer", params);
		params.clear();
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			params.put("msgCode", resultMsg.errorCode);
			params.put("errorMsg", resultMsg.retMsg);
		} else {
			params.put("msgCode", 1);
		}
		return params;
	}
	
	/**
	 * 不赞同答案
	 * @param answerId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map disagreeAnswer(long answerId, long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("answerId", answerId);
		params.put("userId", userId);
		ResultMsg resultMsg = serverCall.baseCall("/answer/disagreeAnswer", params);
		params.clear();
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			params.put("msgCode", resultMsg.errorCode);
			params.put("errorMsg", resultMsg.retMsg);
		} else {
			params.put("msgCode", 1);
		}
		return params;
	}
	
	/**
	 * 接受答案
	 * @param questionId  问题id
	 * @param answerId    答案id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map acceptAnswer(long questionId, long answerId, long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionId", questionId);
		params.put("answerId", answerId);
		params.put("userId", userId);
		ResultMsg resultMsg = serverCall.baseCall("/answer/acceptAnswer", params);
		params.clear();
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			params.put("msgCode", resultMsg.errorCode);
			params.put("errorMsg", resultMsg.retMsg);
		} else {
			params.put("msgCode", 1);
		}
		return params;
	}
}
