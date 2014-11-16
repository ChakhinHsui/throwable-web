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
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getPublicQuestion")
	public Map getPublicQuestion(){
		return questionService.getPublicQuestion();
	}
	
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
}
