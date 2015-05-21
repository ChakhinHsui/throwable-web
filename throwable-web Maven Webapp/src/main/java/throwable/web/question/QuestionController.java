package throwable.web.question;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import throwable.web.utils.BackTool;
import throwable.web.utils.StringTool;

@At("/question")
@IocBean
public class QuestionController {

	@Inject
	private QuestionService questionService;
	
	/**
	 * 首页查询最新的问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getPublicQuestion")
	public Map getPublicQuestion(){
		return questionService.getPublicQuestion();
	}
	
	/**
	 * 首页分页查询最新的问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getPublicQuestionPage")
	public Map getPublicQuestionPage(int page, int count){
		if(page < 0 || count < 0) {
			return BackTool.errorInfo("0200", "参数非法");
		}
		return questionService.queryPublicQuestionsPage(page, count);
	}
	
	/**
	 * 查询问题的总数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getTotal")
	public Map getTotal(){
		return questionService.queryTotalQNum();
	}
	
	/**
	 * 查询最热问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getPublicHotQuestion")
	public Map getPublicHotQuestion(int page, int count) {
		if(page < 0 || count < 0) {
			return BackTool.errorInfo("0200", "参数非法");
		}
		return questionService.getPublicHotQuestion(page, count);
	}
	
	/**
	 * 查询关注最多的问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getMostFocusQuestion")
	public Map getMostFocusQuestion(int page, int count) {
		if(page < 0 || count < 0) {
			return BackTool.errorInfo("0200", "参数非法");
		}
		return questionService.getMostFocusQuestion(page, count);
	}
	
	/**
	 * 查询10条最新回答最多的问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getNewMostAnswerQuestion")
	public Map getNewMostAnswerQuestion() {
		return questionService.getNewMostAnswerQuestion();
	}
	
	/**
	 * 详细页 查询问题具体内容
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getOneQuestion")
	public Map getOneQuestion(@Param("questionId") int questionId, @Param("userId") int userId){
		if(questionId < 0){
			return BackTool.errorInfo("0200", "问题id不合法");
		}
		return questionService.getOneQuestion(questionId, userId);
	}
	
	/**
	 * 添加问题
	 * @param question_name          问题名
	 * @param question_description   问题描述
	 * @param question_type          问题类型 
	 * @param kind_id                问题所属分类id
	 * @param user_id                问题所属用户id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/addQuestion")
	public Map addQuestion(@Param("question_name") String question_name,
			@Param("question_description") String question_description,
			@Param("question_type") int question_type,
			@Param("kind_id") int kind_id,
			@Param("user_id") int user_id,
			@Param("label_names") String label_names)
	{
		if(kind_id < 1 || user_id < 1 || StringTool.isEmpty(question_name) || StringTool.isEmpty(question_description)) {
			return BackTool.errorInfo("0101", "参数有错");
		}
		return questionService.addQuestion(question_name, question_description, question_type, kind_id, user_id, label_names);
	}
	
	/**
	 * 查询用户的问题  我的提问
	 * @param userId   用户id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/queryUserQuestion")
	public Map queryUserQuestion(int userId) {
		if(userId < 1) {
			return BackTool.errorInfo("0300", "用户id不能为空");
		}
		return questionService.getUserQuestion(userId);
	}
	
	/**
	 * 查询用户关注的问题   我的关注
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/queryUserFocusQuestion")
	public Map queryUserFocus(@Param("userId") String userId) {
		if(StringTool.isEmpty(userId)) {
			return BackTool.errorInfo("0400", "用户id不能为空");
		}
		if(!StringTool.isNumber(userId)) {
			return BackTool.errorInfo("0401", "用户id必须为数字");
		}
		return questionService.getUserFocus(userId);
	}
	
	/**
	 * 查询用户问题个数
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/queryUserQuestionNum")
	public Map queryUserQuestionNumber(int userId) {
		if(userId < 1) {
			return BackTool.errorInfo("0500", "用户id不能为空");
		}
		return questionService.getUserQuestionNumber(userId);
	}
	
	/**
	 * 增加访问数
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/addViewer")
	public Map addViewer(int questionId) {
		if(questionId < 1) {
			return BackTool.errorInfo("0200", "问题id不合法");
		}
		return questionService.addViewer(questionId);
	}
	
	/**
	 * 增加顶的个数
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/agreeQuestion")
	public Map agreeQuestion(int questionId) {
		if(questionId < 1) {
			return BackTool.errorInfo("0200", "问题id不合法");
		}
		return questionService.agreeQuestion(questionId);
	}
	
	/**
	 * 增加踩的个数
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/disagreeQuestion")
	public Map disagreeQuestion(int questionId) {
		if(questionId < 1) {
			return BackTool.errorInfo("0200", "问题id不合法");
		}
		return questionService.disagreeQuestion(questionId);
	}
	
	/**
	 * 关注问题
	 * @param userId
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/focusQuestion")
	public Map focusQuestion(int userId, int questionId) {
		if(userId < 1 || questionId < 1) {
			return BackTool.errorInfo("0200", "参数不正确");
		}
		return questionService.focusQuestion(userId, questionId);
	}
	
	/**
	 * 收藏问题
	 * @param userId
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/collectQuestion")
	public Map collectQuestion(int userId, int questionId) {
		if(userId < 1 || questionId < 1) {
			return BackTool.errorInfo("0200", "参数不正确");
		}
		return questionService.collectQuestion(userId, questionId);
	}
}
