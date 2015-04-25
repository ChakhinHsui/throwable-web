var question_model = avalon.define({
	$id: "question",
	title: "",
	author: "",
	time: "",
	focus: "",
	collect:  "",
	agree: "",
	disagree: "",
	details: "",
	comments: [],
	answers: []
});

$(document).ready(function(){
	var jsonObject = {
			questionId: throwable_util.url.getUrlParam("qid"),
			userId: throwable_base.getIdFromCookie("throwable")
	};
	$.post("../question/getOneQuestion", jsonObject, function(result){
		console.log(result);
		question_model.title = result.question_name;
		question_model.author = result.username;
		question_model.time = result.create_time;
		question_model.agree = result.agrees;
		question_model.disagree = result.degrees;
		question_model.details = result.question_description;
		question_model.comments = eval('(' + result.comments + ')');
	}, "json");
	
	$.post("../answer/getAnswers", jsonObject, function(result){
		question_model.answers = eval('(' + result.answers + ')');
	}, "json");
	
});


avalon.scan();