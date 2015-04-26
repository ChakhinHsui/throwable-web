var login_area = avalon.define({
	$id: "login_area",
	href: "register.html",
	text: "登陆"
});

var kind_area = avalon.define({
	$id: "kinds",
	gg : []
});

var question = {
	addQuestion : function() {
		$("#up_button").attr('disabled',"true");
		var jsonObject = {
				question_name:$("#name").val(),
					question_description:ue.getContent(),
					question_type:1,
					kind_id:$("#kind_select").val(),
					label_names:$("#lable_names").val(),
					user_id:throwable_base.getIdFromCookie("throwable")
		};
		$.post("../question/addQuestion", jsonObject, function(result){
			console.log(result);
			if(1 == result.msgCode) {
				$("#name").val("");
				//清空富文本的内容
				ue.execCommand('cleardoc');
				$("#lable_names").val("");
				throwable_util.url.location("index.html");
			} else {
				$("#up_button").removeAttr("disabled");
				alert(result.errorMsg);
			}
		}, "json");
	}	
};

$(document).ready(function(){
	throwable_base.login.isLogin("", function(isLogin){
		if(1 == isLogin) {
			login_area.text = throwable_base.getUserNameFromCookie("throwable");
			$.ajax({
				type: "post",
				url: "../kind/queryAllKinds",
				data: {},
				success: function(data){
					var obj = eval('(' + data + ')');
					kind_area.gg = obj.kinds;
				}
			});
		} else {
			throwable_util.url.location("register.html");
		}
	});
	
});

avalon.scan();