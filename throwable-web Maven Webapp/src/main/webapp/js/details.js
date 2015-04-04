var throwable_details = {
		addAnswer : function() {
			throwable_base.login.isLogin("", function(result){
				if(1 == result) {
					var description = ue.getContent();
					var length = description.length > 50 ? 50 : description.length;
					var jsonObject = {
							question_id:throwable_util.url.getUrlParam("qid"),
							answer_abstract:description.substring(0, length),
							answer_description:description,
							user_id:throwable_base.userInfo.id
					};
					$.ajax({
							type: "post",
							url: "answer/addAnswer",
							data: jsonObject,
							success: function(data){
								var obj = eval('(' + data + ')');
								if(1 == obj.msgCode) {
									throwable_util.url.refresh();
								}
							}
						});
				} else {
					throwable_util.url.location("login.html");
				}
			});
		},
		agreeQuestion : function(){
			var jsonObject = {questionId:throwable_util.url.getUrlParam("qid")};
			$.post("question/agreeQuestion", 
					jsonObject, 
					function(result){
						if(1 == result.msgCode) {
							var num = Number($("#agreeQ").html());
							num = num + 1;
							$("#agreeQ").html(num);
						}
					}, "json");
		},
		disagreeQuestion : function(){
			var jsonObject = {questionId:throwable_util.url.getUrlParam("qid")};
			$.post("question/disagreeQuestion", 
					jsonObject, 
					function(result){
						if(1 == result.msgCode) {
							var num = Number($("#disagreeQ").html());
							num = num + 1;
							$("#disagreeQ").html(num);
						}
					}, "json");
		},
		focusQuestion : function() {
			throwable_base.login.isLogin("", function(result){
				if(1 == result) {
					console.log(throwable_base.userInfo.id);
					var jsonObject = {
							questionId:throwable_util.url.getUrlParam("qid"),
							userId : throwable_base.getIdFromCookie("throwable")
							};
					$.post("question/focusQuestion", 
							jsonObject, 
							function(result){
								console.log(result);
								if(1 == result.msgCode) {
									$("#focus_button").html("已关注");
								}
							}, "json");
				} else {
					throwable_util.url.location("login.html");
				}
			});
		},
		collectQuestion : function() {
			throwable_base.login.isLogin("", function(result){
				if(1 == result) {
					var jsonObject = {
							questionId:throwable_util.url.getUrlParam("qid"),
							userId : throwable_base.getIdFromCookie("throwable")
							};
					$.post("question/collectQuestion", 
							jsonObject, 
							function(result){
								if(1 == result.msgCode) {
									$("#focus_button").html("已关注");
								}
							}, "json");
				} else {
					throwable_util.url.location("login.html");
				}
			});
		},
		openComment : function(belongid, userId, type) {
			throwable_base.login.isLogin("", function(result){
				if(1 == result) {
					$("#to_user_area").html("<h4>评论 @" + throwable_details.users[type+"_"+belongid] + "</h4>");
					throwable_details.temp['belongId'] = belongid;
					throwable_details.temp['belongType'] = type;
					throwable_details.temp['toUserId'] = userId;
					throwable_details.temp['toUserName'] = throwable_details.users[type+"_"+belongid];
					throwable_details.temp['fromUserId'] = throwable_base.getIdFromCookie("throwable");
					throwable_details.temp['fromUserName'] = throwable_base.getUserNameFromCookie("throwable");
					$("#myModal").modal("show");
				} else {
					throwable_util.url.location("login.html");
				}
			});
		},
		addComment : function() {
			var comment = ue2.getContent();
			throwable_details.temp["comment"] = comment;
			$.post("comment/addComment", throwable_details.temp, function(result){
				console.log(result);
				if(1 == result.msgCode) {
					$("#name").val("");
					//清空富文本的内容
					ue2.execCommand('cleardoc');
					throwable_util.url.refresh();
					throwable_details.temp = {};
				}
			}, "json");
		},
		users : {
			
		},
		temp : {
			
		}
};