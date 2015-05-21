/**
 * 秀吧网络科技有限公司版权所有
 * Copyright (C) xiu8 Corporation. All Rights Reserved
 */
package throwable.web.question;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.framework.client.ClientPools;
import throwable.server.framework.rpc.ResultCode;
import throwable.server.framework.rpc.ResultMsg;
import throwable.web.utils.BackTool;
import throwable.web.utils.api.ApiDirUtil;
import throwable.web.utils.api.ThirftCommon;
import throwable.web.utils.api.ThriftCallTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月29日
 */
@IocBean
public class QuestionService {
	
	@Inject
	private ClientPools thriftPools;
	@Inject
	private ThirftCommon thirftCommon;
	
	@Inject
	private ThriftCallTool serverCall;
	
	@Inject
	private ApiDirUtil apiDirUtil;
	
	/**
	 * 添加问题
	 * @param question_name          问题名称
	 * @param question_description   问题具体描述
	 * @param question_type          问题类型(1公开 0私有)
	 * @param kind_id                分类id
	 * @param user_id                用户id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map addQuestion(String question_name,
			String question_description,
			int question_type,
			int kind_id,
			int user_id,
			String label_names){
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_ADD_QUESTION, thirftCommon.initParams(
					"question_name", question_name, 
					"question_description", question_description, "question_type", question_type, 
					"kind_id", kind_id, "user_id", user_id, "label_names", label_names), 100);
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
	
	/**
	 * 获得所有的公开的问题 用于首页显示
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getPublicQuestion(){
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_GET_ALL_QUESTION, thirftCommon.initParams(), 100);
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
	
	/**
	 * 查询最热的问题  访问数最多
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getPublicHotQuestion(int page, int count) {
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_GET_HOT_QUESTION, thirftCommon.initParams("page",page,"count",count), 100);
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
	
	/**
	 * 查询关注最多的问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getMostFocusQuestion(int page, int count) {
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_GET_MOST_FOCUSED_QUESTION, thirftCommon.initParams("page",page,"count",count), 100);
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
	
	/**
	 * 查询10条最新回答最多的问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getNewMostAnswerQuestion() {
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_GET_NEW_MOST_ANSWER_QUESTION, thirftCommon.initParams(), 100);
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
	
	/**
	 * 根据问题id获得问题  用于详细页显示
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getOneQuestion(int questionId, int userId){
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_GET_ONE_QUESTION, thirftCommon.initParams("id", questionId, "userId",userId), 100);
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
	
	/**
	 * 根据用户id获得用户的提问
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getUserQuestion(int userId) {
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_GET_USER_QUESTIONS, thirftCommon.initParams("userId", userId), 100);
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
	
	/**
	 * 根据用户id获得用户关注的问题
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getUserFocus(String userId) {
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_GET_USER_FOCUS, thirftCommon.initParams("userId", userId), 100);
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
	
	/**
	 * 查询用户的问题数
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map getUserQuestionNumber(int userId) {
		Map map = new HashMap();
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_GET_USER_QUESTION_NUM, thirftCommon.initParams("userId", userId), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map.put("number", msg.retMsg);
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map addViewer(int questionId) {
		Map map = new HashMap();
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_ADD_VIEWER, thirftCommon.initParams("questionId", questionId), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map.put("number", msg.retMsg);
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map agreeQuestion(int questionId) {
		Map map = new HashMap();
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_AGREE_QUESTION, thirftCommon.initParams("questionId", questionId), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map.put("msgCode", 1);
				map.put("number", msg.retMsg);
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map disagreeQuestion(int questionId) {
		Map map = new HashMap();
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_DISAGREE_QUESTION, thirftCommon.initParams("questionId", questionId), 100);
			if(msg.retCode.getValue() == ResultCode.SUCCESS.getValue()){
				map.put("msgCode", 1);
				map.put("number", msg.retMsg);
			}else{
				return BackTool.errorInfo(msg.errorCode, msg.retMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 关注问题
	 * @param userId
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map focusQuestion(int userId, int questionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("questionId", questionId);
		ResultMsg resultMsg = serverCall.baseCall("/question/addFocus", params);
		if(null == resultMsg) {
			return BackTool.errorInfo("120015", "调用服务器出错");
		}
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			return BackTool.errorInfo(resultMsg.errorCode, resultMsg.retMsg);
		}
		Map map = new HashMap();
		map.put("msgCode", 1);
		return map;
	}
	
	/**
	 * 收藏问题
	 * @param userId
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map collectQuestion(int userId, int questionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("questionId", questionId);
		ResultMsg resultMsg = serverCall.baseCall("/question/addCollection", params);
		if(null == resultMsg) {
			return BackTool.errorInfo("120015", "调用服务器出错");
		}
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			return BackTool.errorInfo(resultMsg.errorCode, resultMsg.retMsg);
		}
		Map map = new HashMap();
		map.put("msgCode", 1);
		return map;
	}
	
	/**
	 * 查询问题总记录数
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map queryTotalQNum() {
		Map<String, Object> params = new HashMap<String, Object>();
		ResultMsg resultMsg = serverCall.baseCall("/questionApi/getTotalQuestion", params);
		if(null == resultMsg) {
			return BackTool.errorInfo("120015", "调用服务器出错");
		}
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			return BackTool.errorInfo(resultMsg.errorCode, resultMsg.retMsg);
		}
		Map map = new HashMap();
		map.put("msgCode", 1);
		map.put("total", Integer.parseInt(resultMsg.retMsg));
		return map;
	}
	
	/**
	 * 分页查询最新的问题
	 * @return
	 */
	@SuppressWarnings({"rawtypes"})
	public Map queryPublicQuestionsPage(int page, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		ResultMsg resultMsg = serverCall.baseCall("/questionApi/getPublicQsPage", params);
		if(null == resultMsg) {
			return BackTool.errorInfo("120015", "调用服务器出错");
		}
		if(ResultCode.SUCCESS != resultMsg.retCode) {
			return BackTool.errorInfo(resultMsg.errorCode, resultMsg.retMsg);
		}
		resultMsg.retMap.put("msgCode", "1");
		return resultMsg.retMap;
	}
}
