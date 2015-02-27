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
		}
};