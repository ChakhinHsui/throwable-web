var question_title_model = avalon.define({
	$id: "question_title",
	title: "",
	author: "",
	time: "",
	focus: "",
	collect:  ""
});

var question_detail_model = avalon.define({
	$id: "question_detail",
	agree: "",
	disagree: "",
	details: "",
	comments: []
}); 

var question_comment_model = avalon.define("question_comment_list", function(vm){
	vm.comment_list = [];
});


var answer_model = avalon.define("answer_list", function(vm){
	vm.answers = [];
});

$(document).ready(function(){
	var jsonObject = {
			questionId: throwable_util.url.getUrlParam("qid"),
			userId: throwable_base.getIdFromCookie("throwable")
	};
	$.post("question/getOneQuestion", jsonObject, function(result){
		question_title_model.title = result.question_name;
		question_title_model.author = result.username;
		question_title_model.time = result.create_time;
		question_detail_model.agree = result.agrees;
		question_detail_model.disagree = result.degrees;
		question_detail_model.details = result.question_description;
		question_detail_model.comments = eval('(' + result.comments + ')');
		
		if (result.user_id == throwable_base.getIdFromCookie("throwable")) {
			question_title_model.focus = '关注数  ' + result.focuses;
			question_title_model.collect = '收藏数  '+ result.collections;
		} else {
			if(result.focused && result.focused == 1) {
				question_title_model.focus = "已关注";
			} else {
				question_title_model.focus = '<button class="btn radius radius btn-info mt-10" onclick="throwable_details.focusQuestion()" type="button">关注</button>';
			}
			if(result.collected && result.collected == 1) {
				question_title_model.collect = "已收藏";
			} else {
				if(result.solved == 1 && (!result.collected || result.collected == 0)) {
					question_title_model.collect = '<button class="btn radius radius btn-info mt-10" onclick="throwable_details.collectQuestion()" type="button">收藏</button>';
				}
			}
		}
		
	}, "json");
	$.post("answer/getAnswers", jsonObject, function(result){
		answer_model.answers = eval('(' + result.answers + ')');
	}, "json");
});

avalon.scan();