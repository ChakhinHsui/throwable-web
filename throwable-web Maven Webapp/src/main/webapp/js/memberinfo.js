var user_info = {
		getBaseUserInfo : function(id) {
			$("#username_area").html(throwable_base.userInfo.username);
			$.post(throwable_base.urls.userQuestionNumber, {userId : id}, function(result){
				$("#ask_num").html(result.number);
			},"json");
			$.post(throwable_base.urls.userAnswerNumber, {userId : id}, function(result){
				$("#answer_num").html(result.number);
			},"json");
			$.post(throwable_base.urls.userExtendInfo, {userId : id}, function(result){
				console.debug(result);
				if(1 == result.msgCode) {
					$("#live_location").html(result.live_address);
					$("#now_job").html(result.now_job);
					$("#graduate_school").html(result.graduate_school);
					$("#motto").html(result.motto);
					$("#interest").html(result.interest);
					$("#goodAt").html(result.goodAt);
					$("#register_time").html(result.create_time);
				}
			},"json");
		},
		getUserQuestion : function(id) {
			$.post(throwable_base.urls.userQuestion, {userId : id}, function(result) {
				console.debug(result);
				user_info.insertData.insertUserQuestion(result.questions, "user_question");
			}, "json");
		},
		getUserAnswer : function(id) {
			$.post(throwable_base.urls.userAnswer, {userId : id}, function(result) {
				console.debug(result);
				user_info.insertData.insertUserAnswer(result.answers, "user_answer");
			}, "json");
		},
		getUserFocusQuestion : function(id) {
			$.post(throwable_base.urls.userFocusQuestion, {userId : id}, function(result) {
				user_info.insertData2(result.questions, "user_focus_question");
			}, "json");
		},
		insertData : {
			insertUserQuestion : function(result, div) {
				var obj = eval('(' + result + ')');
				var str = "<ul>";
				for(var i = 0; i < obj.length; i++) {
					str += '<li><p class="c-gray f-16 pt-10 pb-10 border-bottom">问题: <a href="details.html?qid='
						+obj[i].id+'">'+obj[i].question_name+'</a><br>'
						+obj[i].question_type+' | '+obj[i].solved+' | 回答('+obj[i].answers+') | 关注('
						+obj[i].focuses+') | 收藏('+obj[i].collections+') | 提问时间 : ' + obj[i].create_time;
					if("已解决" == obj[i].solved) {
						str += '<br>正确答案 ' + obj[i].answer_abstract;
					}
					str += '</p></li>';
				}
				str += '</ul>';
				$("#" + div).html(str);
			},
			insertUserAnswer : function(result, div) {
				var obj = eval('(' + result + ')');
				var str = "<ul>";
				for(var i = 0; i < obj.length; i++) {
					str += '<li><div class="c-gray f-16 pt-10 pb-10 border-bottom">问题: <a href="details.html?qid='+obj[i].question_id+'">'
						+obj[i].question_name+'</a> <br>你的回答: '+obj[i].answer_abstract+' 赞同('
						+obj[i].agrees+') | 浏览('+obj[i].viewers+') | 回答时间 : '+obj[i].answer_time+'</div></li>';
				}
				str += '</ul>';
				$("#" + div).html(str);
			}
		},
		insertData2 : function(result, div) {
			var obj = eval('(' + result + ')');
			var str = "<ul>";
			for(var i = 0; i < obj.length; i++) {
				str += '<li><p class="c-gray f-16 pt-10 pb-10 border-bottom">问题: <a>'+obj[i].question_name+'</a><br>你的回答: <a>'+obj[i].answer_abstract+'</a></p></li>';
			}
			str += '</ul>';
			$("#" + div).html(str);
		},
		openEditDialog : function() {
			$("#add_live_location").val($("#live_location").html());
			$("#add_now_job").val($("#now_job").html());
			$("#add_graduate_school").val($("#graduate_school").html());
			$("#add_motto").val($("#motto").html());
			$("#add_interest").val($("#interest").html());
			$("#add_goodAt").val($("#goodAt").html());
			$("#myModal").modal("show");
		},
		saveExtendInfo : function() {
			var jsonObject = {
					userId: throwable_base.userInfo.id,
					live_address: $("#add_live_location").val(),
					now_job: $("#add_now_job").val(),
					graduate_school: $("#add_graduate_school").val(),
					motto: $("#add_motto").val(),
					interest: $("#add_interest").val(),
					goodAt: $("#add_goodAt").val()
			};
			$.post(throwable_base.urls.UserSaveExtendInfo, jsonObject, function(result){
				console.log(result);
				if(1 == result.msgCode) {
					throwable_util.url.refresh();
				}
			}, "json");
		},
		ajaxImageUpload : function() {
			$.ajaxFile
		}
};