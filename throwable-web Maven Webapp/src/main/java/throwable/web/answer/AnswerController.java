package throwable.web.answer;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import throwable.web.utils.BackTool;
import throwable.web.utils.StringTool;

@At("/answer")
@IocBean
public class AnswerController {

	@Inject
	private AnswerService answerService;
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/addAnswer")
	public Map addAnswer(@Param("question_id") int question_id,
			@Param("answer_abstract") String answer_abstract,
			@Param("answer_description") String answer_description,
			@Param("user_id") int user_id){
		if(question_id < 0){
			return BackTool.errorInfo("030101", "问题id不合法");
		}
		if(user_id < 0){
			return BackTool.errorInfo("030102", "用户id不合法");
		}
		if(StringTool.isEmpty(answer_abstract)){
			return BackTool.errorInfo("030103", "答案摘要不能为空");
		}
		if(StringTool.isEmpty(answer_description)){
			return BackTool.errorInfo("030104", "答案描述不能为空");
		}
		return answerService.addAnswer(question_id, answer_abstract, answer_description, user_id);
	}
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getAnswers")
	public Map getAnswerByQuestionId(@Param("questionId") int questionId){
		if(questionId < 0){
			return BackTool.errorInfo("030201", "问题id不合法");
		}
		return answerService.getAnswerByQuestionId(questionId);
	}
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getUserAnswers")
	public Map getUserAnswers(@Param("userId") String userId) {
		if(StringTool.isEmpty(userId)) {
			return BackTool.errorInfo("030301", "用户id不能为空");
		}
		if(!StringTool.isNumber(userId)) {
			return BackTool.errorInfo("030302", "用户id必须为数字");
		}
		return answerService.getUserAnswers(userId);
	}
	
	/**
	 * 获得用户回答的个数
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getUserAnswerNum")
	public Map getUserAnserNumber(int userId) {
		if(userId < 1) {
			return BackTool.errorInfo("030301", "用户id不能为空");
		}
		return answerService.getUserAnswerNumber(userId);
	}
}
