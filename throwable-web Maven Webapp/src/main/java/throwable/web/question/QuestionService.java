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
import throwable.server.framework.client.ThriftConnect;
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
	
	public Map<String, String> getQuestion(int id){
		try {
			return thirftCommon.getResult(thriftPools, ThirftCommon.QUESTION_GET_QUESTION, thirftCommon.initParams("id", 100), 100).getRetMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
