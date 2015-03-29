var throwable_index = {
		ask : function(){
			throwable_base.login.isLogin("", function(isLogin){
				if(1 == isLogin) {
					console.log(throwable_base.userInfo.id);
					throwable_index.getKinds();
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
  					kind_id:$("#kinds_select").val(),
  					label_names:$("#lable_names").val(),
  					user_id:throwable_base.userInfo.id
			};
			console.log(jsonObject);
			$.post("question/addQuestion", jsonObject, function(result){
				console.log(result);
				if(1 == result.msgCode) {
					$("#name").val("");
					//清空富文本的内容
					ue.execCommand('cleardoc');
					throwable_util.url.refresh();
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
		},
		getKinds : function() {
			console.log("oook");
			$.ajax({
  				type: "post",
  				url: "kind/queryAllKinds",
  				data: {},
  				success: function(data){
  					console.log("receive  allkinds ");
  					var obj = eval('(' + data + ')');
  					console.log(obj);
  					var insertData = '问题所属分类&nbsp;&nbsp;<select id="kinds_select">';
  					for(var i = 0; i < obj.kinds.length; i++) {
  						insertData += '<option value='+obj.kinds[i].id+'>'+obj.kinds[i].kind_name+'</option>';
  					}
  					insertData += '</select>';
  					$("#kind_area").html(insertData);
  				}
  			});
		},
		getAllLabels : function() {
			$.post("label/queryAllLabels", {}, function(result){
				console.log(result);
				console.log(result.labels);
				var insertData = '';
				for(var i = 0; i < result.labels.length; i++) {
					insertData += '<a href="#"><span class="main-right-bottom-tag ml-10 mt-10">'+result.labels[i].name+'</span></a>'
				}
				$("#allLabels_area").html(insertData);
			}, "json");
		}
};
