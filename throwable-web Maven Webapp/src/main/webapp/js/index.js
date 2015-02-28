var throwable_index = {
		ask : function(){
			throwable_base.login.isLogin("", function(isLogin){
				if(1 == isLogin) {
					console.log(throwable_base.userInfo.id);
					$("#myModal").modal("show");
				} else {
					throwable_util.url.location(throwable_base.urls.userLogin);
				}
			});
		},
		addQuestion : function() {
			var jsonObject = {
					question_name:$("#name").val(),
  					question_description:ue.getContent(),
  					question_type:$("#type2").val(),
  					kind_id:1,
  					user_id:throwable_base.userInfo.id
			};
			console.log(jsonObject);
			$.post("question/addQuestion", jsonObject, function(result){
				console.log(result);
				if(1 == result.msgCode) {
					$("#name").val("");
					//清空富文本的内容
					ue.execCommand('cleardoc');
				}
			}, "json");
			/*$.ajax({
  				type: "post",
  				url: "question/addQuestion",
  				data: jsonObject,
  				success: function(data){
  					console.log(data);
  				}
  			});*/
		}
};
