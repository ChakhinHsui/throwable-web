var notice = {
		subscribe : function() {
			throwable_util.subPubTool.subscribe("l", function(obj){
				console.log(obj);
				var str = "<br>用户" + obj.fuo.un + "进入房间";
				$("#showArea").html($("#showArea").html() + str);
			});
			throwable_util.subPubTool.subscribe("c", function(obj){
				console.log(obj);
				var str = "<br>用户" + obj.fuo.un + ": " + obj.message;
				$("#showArea").html($("#showArea").html() + str);
			});
			throwable_util.subPubTool.subscribe("lo", function(obj){
				console.log(obj);
				var str = "<br>用户" + obj.fuo.un + "离开房间";
				$("#showArea").html($("#showArea").html() + str);
			});
			throwable_util.subPubTool.subscribe("notice", function(obj){
				console.log("=========notice===============");
				console.log(obj);
				$("#notice_num_area").html($("#notice_num_area").html() + 1);
			});
		},
		publish : function(channel, obj) {
			throwable_util.subPubTool.publish(channel, obj);
		},
		connect : function(url, userId, roomId, fromId) {
			socket = io.connect(url + '/chat?uid='+userId+'&fid='+fromId+'&rid='+roomId);
			socket.on('msg', function(msg){
				notice.publish(msg.fc, msg);
			}) ;
		},
		url : "http://localhost:861"
};
