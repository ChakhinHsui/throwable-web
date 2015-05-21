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
			});
			throwable_util.subPubTool.subscribe("init", function(obj){
				throwable_index.initPagigation(1,"new_pagination_area");
				throwable_index.initPagigation(2,"hot_pagination_area");
				throwable_index.initPagigation(3,"focus_pagination_area");
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
						for(var i = 0; i < throwable_index.NewPaginationValue.oneClicks; i++) {
							$("#1_pag_" + (throwable_index.NewPaginationValue.minClicks + i + 1)).removeClass("active");
//							console.log("FFF");
						}
						$("#1_pag_" + page).addClass("active");
						throwable_index.NewPaginationValue.nowClicks = page;
					}, "json");
		},
		getHotQuestion : function(page, count) {
			$.post("../question/getPublicHotQuestion", 
					{page:page,count:count}, 
					function(result){
						throwable_index.publish("hot", eval('(' + result.questions + ')'));
						for(var i = 0; i < throwable_index.HotPaginationValue.oneClicks; i++) {
							$("#2_pag_" + (throwable_index.HotPaginationValue.minClicks + i + 1)).removeClass("active");
//							console.log("FFF");
						}
						$("#2_pag_" + page).addClass("active");
						throwable_index.HotPaginationValue.nowClicks = page;
					}, "json");
		},
		getMostFocusQuestion : function(page, count) {
			$.post("../question/getMostFocusQuestion", 
					{page:page,count:count}, 
					function(result){
						throwable_index.publish("wait", eval('(' + result.questions + ')'));
						for(var i = 0; i < throwable_index.MostFocusPaginationValue.oneClicks; i++) {
							$("#3_pag_" + (throwable_index.MostFocusPaginationValue.minClicks + i + 1)).removeClass("active");
//							console.log("FFF");
						}
						$("#3_pag_" + page).addClass("active");
						throwable_index.MostFocusPaginationValue.nowClicks = page;
					}, "json");
		},
		getTotal : function() {
			$.post("../question/getTotal", 
					{}, 
					function(result){
						if(result.msgCode == 1) {
							throwable_index.initAllValue(result.total, throwable_index.count);
						}
					}, "json");
		},
		//初始化所有的分页插件
		initAllValue : function(totalNum, count) {
			throwable_index.totalNum = totalNum;
			if(totalNum % count == 0) {
				throwable_index.totalPage = parseInt(totalNum / count);
			} else {
				throwable_index.totalPage = parseInt(totalNum / count) + 1;
			}
			throwable_index.NewPaginationValue.oneClicks = throwable_index.totalPage >= 10 ? 10 : throwable_index.totalPage;
			throwable_index.HotPaginationValue.oneClicks = throwable_index.totalPage >= 10 ? 10 : throwable_index.totalPage;
			throwable_index.MostFocusPaginationValue.oneClicks = throwable_index.totalPage >= 10 ? 10 : throwable_index.totalPage;
			if(throwable_index.totalPage % 10 == 0) {
				throwable_index.NewPaginationValue.tenClicks = parseInt(throwable_index.totalPage / 10);
				throwable_index.HotPaginationValue.tenClicks = parseInt(throwable_index.totalPage / 10);
				throwable_index.MostFocusPaginationValue.tenClicks = parseInt(throwable_index.totalPage / 10);
			} else {
				throwable_index.NewPaginationValue.tenClicks = parseInt(throwable_index.totalPage / 10) + 1;
				throwable_index.HotPaginationValue.tenClicks = parseInt(throwable_index.totalPage / 10) + 1;
				throwable_index.MostFocusPaginationValue.tenClicks = parseInt(throwable_index.totalPage / 10) + 1;
			}
			throwable_index.NewPaginationValue.minClicks = 0;
			throwable_index.HotPaginationValue.minClicks = 0;
			throwable_index.MostFocusPaginationValue.minClicks = 0;
			throwable_index.NewPaginationValue.maxClicks = throwable_index.NewPaginationValue.oneClicks;
			throwable_index.HotPaginationValue.maxClicks = throwable_index.HotPaginationValue.oneClicks;
			throwable_index.MostFocusPaginationValue.maxClicks = throwable_index.MostFocusPaginationValue.oneClicks;
			throwable_index.NewPaginationValue.nowClicks = throwable_index.NewPaginationValue.minClicks;
			throwable_index.HotPaginationValue.nowClicks = throwable_index.HotPaginationValue.minClicks;
			throwable_index.MostFocusPaginationValue.nowClicks = throwable_index.MostFocusPaginationValue.minClicks;
			throwable_index.publish("init", 10);
		},
		initPagigation : function(type,div) {
			if(1 == type){
				console.log(throwable_index.NewPaginationValue.oneClicks);
				var str = '<ul><li class="previous"><a href="javascript:void(0);" onclick="throwable_index.previous('+type+');" class="fui-arrow-left"></a></li>';
				for(var i = 0; i < throwable_index.NewPaginationValue.oneClicks; i++) {
					str += '<li id="'+type+'_pag_'+(throwable_index.NewPaginationValue.minClicks+i+1)+'"><a href="javascript:void(0)" onclick="throwable_index.getNewQuestion('+(throwable_index.NewPaginationValue.minClicks + i+1)+', '+throwable_index.count+')">'+(throwable_index.NewPaginationValue.minClicks + i + 1)+'</a></li>';
				}
				str += '<li class="pagination-dropdown dropup">';
				str += '<a href="#fakelink" class="dropdown-toggle" data-toggle="dropdown">';
				str += '<i class="fui-triangle-up"></i></a>';
				str += '<ul class="dropdown-menu">';
				for(var i = 0; i < throwable_index.NewPaginationValue.tenClicks; i++) {
					str += '<li><a href="javascript:void(0)" onclick="throwable_index.change10Pag('+type+','+(i * 10 + 1)+', '+((i + 1) * 10)+')">'+(i * 10 + 1)+'-'+((i + 1) * 10)+'</a></li>';
				}
				str += '</ul></li>';
				str += '<li class="next"><a href="javascript:void(0);" onclick="throwable_index.next('+type+');" class="fui-arrow-right"></a></li>';
				str += "</ul>";
				$("#new_pagination_area").html(str);
				$("#1_pag_" + 1).addClass("active");
				return;
			}
			if(2 == type) {
				var str = '<ul><li class="previous"><a href="javascript:void(0);" onclick="throwable_index.previous('+type+');" class="fui-arrow-left"></a></li>';
				for(var i = 0; i < throwable_index.HotPaginationValue.oneClicks; i++) {
					str += '<li id="'+type+'_pag_'+(throwable_index.HotPaginationValue.minClicks+i+1)+'"><a href="javascript:void(0)" onclick="throwable_index.getHotQuestion('+(throwable_index.HotPaginationValue.minClicks + i+1)+', '+throwable_index.count+')">'+(throwable_index.HotPaginationValue.minClicks + i + 1)+'</a></li>';
				}
				str += '<li class="pagination-dropdown dropup">';
				str += '<a href="#fakelink" class="dropdown-toggle" data-toggle="dropdown">';
				str += '<i class="fui-triangle-up"></i></a>';
				str += '<ul class="dropdown-menu">';
				for(var i = 0; i < throwable_index.HotPaginationValue.tenClicks; i++) {
					str += '<li><a href="javascript:void(0)" onclick="throwable_index.change10Pag('+type+','+(i * 10 + 1)+', '+((i + 1) * 10)+')">'+(i * 10 + 1)+'-'+((i + 1) * 10)+'</a></li>';
				}
				str += '</ul></li>';
				str += '<li class="next"><a href="javascript:void(0);" onclick="throwable_index.next('+type+');" class="fui-arrow-right"></a></li>';
				str += "</ul>";
				$("#hot_pagination_area").html(str);
				$("#2_pag_" + 1).addClass("active");
				return;
			}
			if(3 == type) {
				var str = '<ul><li class="previous"><a href="javascript:void(0);" onclick="throwable_index.previous('+type+');" class="fui-arrow-left"></a></li>';
				for(var i = 0; i < throwable_index.MostFocusPaginationValue.oneClicks; i++) {
					str += '<li id="'+type+'_pag_'+(throwable_index.MostFocusPaginationValue.minClicks+i+1)+'"><a href="javascript:void(0)" onclick="throwable_index.getMostFocusQuestion('+(throwable_index.MostFocusPaginationValue.minClicks + i+1)+', '+throwable_index.count+')">'+(throwable_index.MostFocusPaginationValue.minClicks + i + 1)+'</a></li>';
				}
				str += '<li class="pagination-dropdown dropup">';
				str += '<a href="#fakelink" class="dropdown-toggle" data-toggle="dropdown">';
				str += '<i class="fui-triangle-up"></i></a>';
				str += '<ul class="dropdown-menu">';
				for(var i = 0; i < throwable_index.MostFocusPaginationValue.tenClicks; i++) {
					str += '<li><a href="javascript:void(0)" onclick="throwable_index.change10Pag('+type+','+(i * 10 + 1)+', '+((i + 1) * 10)+')">'+(i * 10 + 1)+'-'+((i + 1) * 10)+'</a></li>';
				}
				str += '</ul></li>';
				str += '<li class="next"><a href="javascript:void(0);" onclick="throwable_index.next('+type+');" class="fui-arrow-right"></a></li>';
				str += "</ul>";
				$("#focus_pagination_area").html(str);
				$("#3_pag_" + 1).addClass("active");
				return;
			}
			
		},
		previous : function(type) {
			if(type == 1) {
				if(throwable_index.NewPaginationValue.nowClicks == (throwable_index.NewPaginationValue.minClicks +1)) {
					return;
				}
				throwable_index.getNewQuestion(throwable_index.NewPaginationValue.nowClicks - 1, throwable_index.count);
				return;
			}
			if(type == 2) {
				if(throwable_index.HotPaginationValue.nowClicks == (throwable_index.HotPaginationValue.minClicks +1)) {
					return;
				}
				throwable_index.getHotQuestion(throwable_index.HotPaginationValue.nowClicks - 1, throwable_index.count);
				return;
			}
			if(type == 3) {
				if(throwable_index.MostFocusPaginationValue.nowClicks == (throwable_index.MostFocusPaginationValue.minClicks +1)) {
					return;
				}
				throwable_index.getMostFocusQuestion(throwable_index.MostFocusPaginationValue.nowClicks - 1, throwable_index.count);
				return;
			}
		},
		next : function(type) {
			if(type == 1) {
				if(throwable_index.NewPaginationValue.nowClicks == (throwable_index.NewPaginationValue.maxClicks)) {
					return;
				}
				throwable_index.getNewQuestion(throwable_index.NewPaginationValue.nowClicks + 1, throwable_index.count);
				return;
			}
			if(type == 2) {
				if(throwable_index.HotPaginationValue.nowClicks == (throwable_index.HotPaginationValue.maxClicks)) {
					return;
				}
				throwable_index.getHotQuestion(throwable_index.HotPaginationValue.nowClicks + 1, throwable_index.count);
				return;
			}
			if(type == 3) {
				if(throwable_index.MostFocusPaginationValue.nowClicks == (throwable_index.MostFocusPaginationValue.maxClicks)) {
					return;
				}
				throwable_index.getMostFocusQuestion(throwable_index.MostFocusPaginationValue.nowClicks + 1, throwable_index.count);
				return;
			}
		},
		change10Pag : function(type, min, max) {
			if(type == 1) {
				var temp = parseInt((throwable_index.totalNum - (throwable_index.count * (min - 1))) / throwable_index.count);
				throwable_index.NewPaginationValue.oneClicks = temp >= 10 ? 10 : temp;
				throwable_index.NewPaginationValue.minClicks = min - 1;
				throwable_index.NewPaginationValue.maxClicks = min - 1 + throwable_index.NewPaginationValue.oneClicks;
				throwable_index.publish("init", 10);
				throwable_index.initPagigation(1, "new_pagination_area");
				throwable_index.getNewQuestion(min, throwable_index.count);
				return;
			}
			if(type == 2) {
				var temp = parseInt((throwable_index.totalNum - (throwable_index.count * (min - 1))) / throwable_index.count);
				throwable_index.HotPaginationValue.oneClicks = temp >= 10 ? 10 : temp;
				throwable_index.HotPaginationValue.minClicks = min - 1;
				throwable_index.HotPaginationValue.maxClicks = min - 1 + throwable_index.HotPaginationValue.oneClicks;
				throwable_index.initPagigation(2, "hot_pagination_area");
				throwable_index.getHotQuestion(min, throwable_index.count);
				return;
			}
			if(type == 3){
				var temp = parseInt((throwable_index.totalNum - (throwable_index.count * (min - 1))) / throwable_index.count);
				throwable_index.MostFocusPaginationValue.oneClicks = temp >= 10 ? 10 : temp;
				throwable_index.MostFocusPaginationValue.minClicks = min - 1;
				throwable_index.MostFocusPaginationValue.maxClicks = min - 1 + throwable_index.MostFocusPaginationValue.oneClicks;
				throwable_index.initPagigation(3, "focus_pagination_area");
				throwable_index.getMostFocusQuestion(min, throwable_index.count);
				return;
			}
		},
		totalNum : 0,  //所有问题总记录
		count : 1,   //每页显示的记录
		page : 1,     //当前第几页
		totalPage : 0,  //总页数
		oneClicks:10,  //分页中单个按钮个数
		tenClicks:1,   //分页中最后一个选择1-10 11-20的个数
		minClicks:0,   //分页中最小的那个按钮序号
		maxClicks:10,   //分页中最大的那个按钮序号
		nowClicks:1,     //分页按钮当前位置
		NewPaginationValue : {
			page : 1,     //当前第几页
			oneClicks:10,  //分页中单个按钮个数
			tenClicks:1,   //分页中最后一个选择1-10 11-20的个数
			minClicks:0,   //分页中最小的那个按钮序号
			maxClicks:10,   //分页中最大的那个按钮序号
			nowClicks:1,     //分页按钮当前位置
		},
		HotPaginationValue : {
			page : 1,     //当前第几页
			oneClicks:10,  //分页中单个按钮个数
			tenClicks:1,   //分页中最后一个选择1-10 11-20的个数
			minClicks:0,   //分页中最小的那个按钮序号
			maxClicks:10,   //分页中最大的那个按钮序号
			nowClicks:1,     //分页按钮当前位置
		},
		MostFocusPaginationValue : {
			page : 1,     //当前第几页
			oneClicks:10,  //分页中单个按钮个数
			tenClicks:1,   //分页中最后一个选择1-10 11-20的个数
			minClicks:0,   //分页中最小的那个按钮序号
			maxClicks:10,   //分页中最大的那个按钮序号
			nowClicks:1,     //分页按钮当前位置
		}
};


$(document).ready(function(){
	throwable_index.subscribe();
	throwable_index.getNewQuestion(throwable_index.page, throwable_index.count);
	throwable_index.getTotal();
//	$.post("../question/getPublicHotQuestion", 
//			{}, 
//			function(result){
//				throwable_index.publish("hot", eval('(' + result.questions + ')'));
//			}, "json");
//	$.post("../question/getMostFocusQuestion", 
//			{}, 
//			function(result){
//				throwable_index.publish("wait", eval('(' + result.questions + ')'));
//			}, "json");
	throwable_index.getHotQuestion(throwable_index.page, throwable_index.count);
	throwable_index.getMostFocusQuestion(throwable_index.page, throwable_index.count);
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
	throwable_base.login.isLogin("", function(isLogin){
		if(1 == isLogin) {
			login_area.href = "memberinfo.html?id=" + throwable_base.getIdFromCookie("throwable");
			login_area.text = throwable_base.getUserNameFromCookie("throwable");
		}
	});
});


avalon.scan();