var chat = {
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
		},
		publish : function(channel, obj) {
			throwable_util.subPubTool.publish(channel, obj);
		},
		url : "http://localhost:861"
};

var userId = 0;
$("document").ready(function(){
	chat.subscribe();
	userId = Math.floor(Math.random() * 10);
	socket = io.connect(chat.url + '/chat?uid='+userId+'&fid=1&rid=100');
	console.log(socket);
	socket.on('msg', function(msg) {
		chat.publish(msg.fc, msg);
	});
});


function send(){
		var text = $("#speakArea").val();
		var jsonObject = {
				fc : 'c',
				message : text
		};
		socket.emit('req', jsonObject);
		console.log(jsonObject);
}