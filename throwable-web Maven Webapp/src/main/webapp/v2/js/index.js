var question_model = avalon.define("index_list", function(vm){
	vm.new_question_list = [];
	vm.hot_question_list = [];
	vm.wait_question_list = [];
});

var right_show_model = avalon.define("right-index", function(vm){
	vm.new_hot_list = [];
	vm.label_list = [];
});

var login_area = avalon.define({
	$id: "login_area",
	href: "register.html",
	text: "登陆"
});

var throwable_index = {
		subscribe : function() {
			throwable_util.subPubTool.subscribe("new", function(obj){
				console.log("new");
				console.log(obj);
				question_model.new_question_list = obj;
			});
			throwable_util.subPubTool.subscribe("hot", function(obj){
				question_model.hot_question_list = obj;
			});
			throwable_util.subPubTool.subscribe("wait", function(obj){
				question_model.wait_question_list = obj;
			});
			throwable_util.subPubTool.subscribe("mostAnswer", function(obj){
				right_show_model.new_hot_list = obj;
			});
			throwable_util.subPubTool.subscribe("label", function(obj){
				right_show_model.label_list = obj;
				console.log(obj);
			});
		},
		publish : function(channel, obj) {
			throwable_util.subPubTool.publish(channel, obj);
		},
		getNewQuestion : function(page, count) {
			$.post("../question/getPublicQuestionPage", 
					{page:page,count:count}, 
					function(result){
						throwable_index.publish("new", eval('(' + result.questions + ')'));
					}, "json");
		},
		getTotal : function() {
			$.post("../question/getTotal", 
					{}, 
					function(result){
						if(result.msgCode == 1) {
							console.log(result);
						}
					}, "json");
		}
};

$(document).ready(function(){
	throwable_index.subscribe();
	throwable_index.getNewQuestion(1, 5);
	throwable_index.getTotal();
	$.post("../question/getPublicHotQuestion", 
			{}, 
			function(result){
				throwable_index.publish("hot", eval('(' + result.questions + ')'));
			}, "json");
	$.post("../question/getMostFocusQuestion", 
			{}, 
			function(result){
				throwable_index.publish("wait", eval('(' + result.questions + ')'));
			}, "json");
	$.post("../question/getNewMostAnswerQuestion", 
			{}, 
			function(result){
				throwable_index.publish("mostAnswer", eval('(' + result.questions + ')'));
			}, "json");
	$.post("../label/queryAllLabels", 
			{}, 
			function(result){
				throwable_index.publish("label", result.labels);
				console.log(result.labels);
			}, "json");
});


avalon.scan();