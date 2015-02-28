var throwable_index = {
		ask : function(){
			throwable_base.login.isLogin("", function(temp){
				if(temp == 1) {
					$("#myModal").modal("show");
				} else {
					$("#myModal").modal("show");
				}
			});
		},
		addQuestion : function() {
			var jsonObject = {
					question_name:$("#name").val(),
					question_description:ue.getContent(),
					question_type:$("#type").val(),
					kind_id:1,
					user_id:$("#user_id").val()
			};
			$.post("question/addQuestion", jsonObject, function(result){
				alert(result);
			}, "json");
		}
};