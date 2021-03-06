package throwable.web.utils.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.framework.client.ClientPools;
import throwable.server.framework.rpc.ResultMsg;


/**
 * 通用方法
 * 
 */
@IocBean
public class ThirftCommon {

	private static final Log log = Logs.getLog(ThirftCommon.class);
	
	@Inject
	private ApiDirUtil apiDirUtil;

	/********************** 用户相关接口 ***************************/
	public static final String GET_USER_INFO = "user.getUserInfo";//获取用户信息
	public static final String USER_LOGIN = "user.login"; //用户登陆
	public static final String USER_ACTIVE = "user.active"; //用户激活 通过邮件的链接激活
	public static final String USER_REGISTER = "user.register";  //用户注册
	public static final String Get_All_USER = "user.getAllUserInfo";  //查询所有用户
	public static final String GET_USER_EXTEND = "user.getUserExtendInfo";  //查询用户扩展信息
	public static final String SAVE_USER_EXTEND = "user.saveUserExtendInfo";  //保存用户扩展信息
	
	
	/********************** 问题相关接口 ***************************/
	public static final String Q_ADD_QUESTION = "question.addQuestion";   //添加问题
	public static final String Q_GET_ALL_QUESTION = "question.getQuestions";  //查询所有公开的问题
	public static final String Q_GET_ONE_QUESTION = "question.getOneQuestion"; //根据id查询问题 用户详细页显示
	public static final String Q_GET_HOT_QUESTION = "question.getHotQuestions"; //查询所有最热(访问数最多)的问题
	public static final String Q_GET_MOST_FOCUSED_QUESTION = "question.getMostFocusedQuestions"; //查询被关注最多的问题
	public static final String Q_GET_NEW_MOST_ANSWER_QUESTION = "question.getNewMostAnswerQuestions"; //查询10条最新回答最多的问题
	public static final String Q_GET_USER_QUESTIONS = "question.getUserQuestions";  //查询用户的问题
	public static final String Q_GET_USER_FOCUS = "question.getUserFocusQuestion";  //查询用户关注的问题
	public static final String Q_GET_USER_QUESTION_NUM = "question.getUserQuestionNumber";  //查询用户提问的问题数
	public static final String Q_ADD_VIEWER = "question.addViewer";  //增加访问数
	public static final String Q_AGREE_QUESTION = "question.agreeQuestion";  //顶问题
	public static final String Q_DISAGREE_QUESTION = "question.disagreeQuestion"; //踩问题
	
	
	/********************** 答案相关接口 ***************************/
	public static final String A_ADD_ANSWER = "answer.addAnswer";  //添加答案
	public static final String A_GET_ANSWER = "answer.getAnswer";  //根据问题id获得answer 用于详细页显示
	public static final String A_GET_USER_ANSWER = "answer.getUserAnswer";  //查询用户回答过得问题
	public static final String A_GET_USER_ANSWER_NUM = "answer.getUserAnswerNumber";  //查询用户回答问题的个数
	
	/********************** 分类相关接口 ***************************/
	public static final String KIND_ADD = "kind.addKind";  //添加分类
	public static final String KIND_QUERYALL = "kind.queryAllKinds";  //查询所有分类
	
	
	/********************** 标签相关接口 ***************************/
	public static final String LABEL_ADD = "label.addLabel";  //添加标签
	public static final String LABEL_QUERYALL = "label.queryAllLabels";  //查询所有标签
	public static final String LABEL_QUERY_BY_QUESTIONID = "label.queryLabelsByQuestionId";  //根据问题id查询所有标签
	public static final String LABEL_QUERY_BY_USERID = "label.queryLabelsByUserId";  //根据用户id查询所有标签
	
	
	
	/**
	 * 根据传递参数 组织成 thrift需要的参数 MAP， 省去每次都写一个初始化MAP的工作
	 * 
	 * @param item
	 *            必须成对出现，否则返回空.KEY,VALUE,KEY1,VALUE1,KEY2,VALUE2...
	 * @return
	 */
	public Map<String, String> initParams(Object... item) {
		Map<String, String> result = new HashMap<String, String>();
		// 参数不能为空，并且参数必须成对出现，否则返回空
		if (item == null || item.length % 2 != 0) {
			return result;
		}
		for (int i = 0; i < item.length; i += 2) {
			Object value = item[i + 1];
			if (value == null) {
				continue;
			}
			result.put(item[i].toString(), value.toString());
		}
		return result;
	}

	/**
	 * 通过 Thrift 调用API获取返回值
	 * 
	 * @param thriftPools
	 *            ClientPools客户端
	 * @param uri
	 *            請求地址代号
	 * @param params
	 *            请求参数
	 * @param uid
	 *            发起方UID
	 * @throws Exception
	 */
	public ResultMsg getResult(ClientPools thriftPools, String uri, Map<String, String> params, long uid) throws Exception {
		log.info("调用 " + uri + "  参数： " + Json.toJson(params));
		ResultMsg resultMsg = thriftPools.call("W" + System.nanoTime(), apiDirUtil.getValue(uri), params);
		log.info(Json.toJson(resultMsg));
		return resultMsg;
	}

	public List<Object> getResultByList(ClientPools thriftPools, String uri, Map<String, String> params, long uid) throws Exception {
		log.info("调用 " + uri + "  参数： " + Json.toJson(params));
		List<String> list = getResult(thriftPools, uri, params, uid).getRetList();
		log.info(Json.toJson(list));
		return Json.fromJsonAsList(Object.class, list.toString());
	}
}
