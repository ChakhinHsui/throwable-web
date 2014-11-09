package throwable.web.beetl;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;



public class HelloWorld {
	
	public static void main(String[] args) throws Exception{
		String input = "hello, ${name}!";
		GroupTemplate group = new GroupTemplate();
		Template template = group.getTemplate(input);
	}
}
