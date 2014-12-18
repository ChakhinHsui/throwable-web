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
	 * 查询最热问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getPublicHotQuestion")
	public Map getPublicHotQuestion() {
		return questionService.getPublicHotQuestion();
	}
	
	/**
	 * 查询关注最多的问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getMostFocusQuestion")
	public Map getMostFocusQuestion() {
		return questionService.getMostFocusQuestion();
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
	public Map getOneQuestion(@Param("questionId") int questionId){
		if(questionId < 0){
			return BackTool.errorInfo("0200", "问题id不合法");
		}
		return questionService.getOneQuestion(questionId);
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
			@Param("user_id") int user_id)
	{
		if(StringTool.isEmpty(question_name)){
			return BackTool.errorInfo("0100", "问题名称不能为空");
		}
		if(StringTool.isEmpty(question_description)){
			return BackTool.errorInfo("0101", "问题内容不能为空");
		}
		return questionService.addQuestion(question_name, question_description, question_type, kind_id, user_id);
	}
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/queryUserQuestion")
	public Map queryUserQuestion(@Param("userId") String userId) {
		if(StringTool.isEmpty(userId)) {
			return BackTool.errorInfo("0300", "用户id不能为空");
		}
		if(!StringTool.isNumber(userId)) {
			return BackTool.errorInfo("0301", "用户id必须为数字");
		}
		return questionService.getUserQuestion(userId);
	}
	
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
}
