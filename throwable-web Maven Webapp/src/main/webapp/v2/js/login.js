var login_area = avalon.define({
	$id: "login_area",
	href: "register.html",
	text: "登陆"
});

$("#signin-form").submit(function(){
	if(!$("#login-name").val()) {
		alert("输入用户名");
		$("#login-name").focus();
		return false;
	}
	if(!$("#login-pass").val()) {
		alert("输入密码");
		$("#login-pass").focus();
		return false;
	}
	var jsonObject = {
			username : $("#login-name").val(),
			password : $("#login-pass").val()
	};
	$.post("../user/login", jsonObject, function(result){
		console.log(result);
		if(1 == result.msgCode) {
			throwable_util.url.location("index.html");
		}else{
			alert(result.errorMsg);
		}
	}, "json");
	return false;
});

$("#register-form").submit(function(){
	if(!$("#register-name").val()){
		alert("用户名不能为空");
		$("#register-name").focus();
		return false;
	}
	if(!$("#register-pass").val()){
		alert("密码不能为空");
		$("#register-pass").focus();
		return false;
	}
	if(!$("#register-email").val()){
		alert("邮箱不能为空");
		$("#register-email").focus();
		return false;
	}
	if(!throwable_util.checkValue.isEmail($("#register-email").val())){
		alert("邮箱格式不对");
		$("#register-email").focus();
		return false;
	}
	var jsonObject = {
			username:$("#register-name").val(),
			password:$("#register-pass").val(),
			email:$("#register-email").val(),
			nickname:$("#register-name").val()
	};
	console.log(JSON.stringify(jsonObject));
	$.post("../user/register", jsonObject, function(result){
		if(result.msgCode == 1) {
			throwable_util.url.location("register.html");
		}
		console.log(JSON.stringify(result));
	}, "json");
	return false;
});
avalon.scan();