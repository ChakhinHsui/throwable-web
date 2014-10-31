package throwable.web.question;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@At("/question")
@IocBean
public class QuestionController {
//
	@Inject
	private QuestionService questionService;
	
	@SuppressWarnings("rawtypes")
	@Ok("json")
	@At("/getQuestion")
	public Map getQuestion(@Param("id") int id){
		System.out.println("HelloWorld");
		System.out.println("throwable-web" + "  " + id);
		
		return questionService.getQuestion(id);
	}
}
