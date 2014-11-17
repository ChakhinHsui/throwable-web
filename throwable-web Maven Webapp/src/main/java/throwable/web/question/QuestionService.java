/**
 * 秀吧网络科技有限公司版权所有
 * Copyright (C) xiu8 Corporation. All Rights Reserved
 */
package throwable.web.question;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.framework.client.ClientPools;
import throwable.server.framework.rpc.ResultCode;
import throwable.server.framework.rpc.ResultMsg;
import throwable.web.utils.BackTool;
import throwable.web.utils.api.ThirftCommon;

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
			int user_id){
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_ADD_QUESTION, thirftCommon.initParams(
					"question_name", question_name, 
					"question_description", question_description, "question_type", question_type, 
					"kind_id", kind_id, "user_id", user_id), 100);
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
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_GET_ALL_QUESTION, thirftCommon.initParams("question_type", 1), 100);
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
	public Map getOneQuestion(int questionId){
		Map map = null;
		try{
			ResultMsg msg = thirftCommon.getResult(thriftPools, ThirftCommon.Q_GET_ONE_QUESTION, thirftCommon.initParams("id", questionId), 100);
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
}
