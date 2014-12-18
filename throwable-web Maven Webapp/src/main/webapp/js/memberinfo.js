var user_info = {
		getBaseUserInfo : function(id) {
		},
		getUserQuestion : function(id) {
			$.post(throwable_base.urls.userQuestion, {userId : id}, function(result) {
				user_info.insertData(result.questions, "user_question");
			}, "json");
		},
		getUserAnswer : function(id) {
			$.post(throwable_base.urls.userAnswer, {userId : id}, function(result) {
				user_info.insertData(result.answers, "user_answer");
			}, "json");
		},
		getUserFocusQuestion : function(id) {
			$.post(throwable_base.urls.userFocusQuestion, {userId : id}, function(result) {
				user_info.insertData(result.questions, "user_focus_question");
			}, "json");
		},
		insertData : function(result, div) {
			var obj = eval('(' + result + ')');
			var str = "<ul>";
			for(var i = 0; i < obj.length; i++) {
				str += '<li><p class="c-gray f-16 pt-10 pb-10 border-bottom">问题: <a>'+obj[i].question_name+'</a><br>你的回答: <a>'+obj[i].answer_abstract+'</a></p></li>';
			}
			str += '</ul>';
			$("#" + div).html(str);
		}
};