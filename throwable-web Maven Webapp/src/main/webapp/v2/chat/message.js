var chat_model = avalon.define("chat", function(vm){
	vm.room_desc = '';
	vm.room_name = '';
	vm.fact_count = '';
	vm.speak_count = '';
	vm.active_time = '';
	vm.user_list = [];
});

var chat = {
		subscribe : function() {
			throwable_util.subPubTool.subscribe("l", function(obj){
				console.log(obj);
				chat_model.speak_count = obj.roomInfo.speakCount;
				chat_model.fact_count = obj.roomInfo.factCount;
				chat_model.active_time = obj.activeTime;
				chat_model.user_list = obj.userList;
				if (obj.fuo.uid == chat.userId && chat.userId != 0) {
					chat_model.room_desc = obj.roomInfo.roomDesc;
					chat_model.room_name = obj.roomInfo.roomName;
					for (var i = obj.chatHistory.length - 1; i >= 0; i--) {
						var avatar = 'chat/images/user_b.gif';
						var content2 = obj.chatHistory[i].fromUserId + ":<br>" + obj.chatHistory[i].content;
						sendMsg_new2(content2, avatar, 0, obj.chatHistory[i].time);
					}
					return;
				} else {
					var avatar = 'chat/images/user_b.gif';
					var content2 = obj.fuo.un + obj.fuo.uid + ":<br>进入房间";
					sendMsg_new(content2, avatar,0);
				}
			});
			throwable_util.subPubTool.subscribe("c", function(obj){
				console.log(obj.fuo.uid);
				console.log(throwable_util.cookie.getCookie(chat.cookieName));
				if (obj.fuo.uid == chat.userId && chat.userId != 0) {
					return;
				}
				var str = "<br>用户" + obj.fuo.un + ": " + obj.message;
				$("#showArea").html($("#showArea").html() + str);
				var avatar = 'chat/images/user_b.gif';
				var content2 = obj.fuo.un + obj.fuo.uid + ":<br>" + obj.message;
				sendMsg_new(content2, avatar,0);
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
		url : "http://localhost:861",
		cookieName : "throwable_message_chat_userId",
		userId : 0
};

function send(context){
		var jsonObject = {
				fc : 'c',
				message : context
		};
		socket.emit('req', jsonObject);
		console.log(jsonObject);
}

var sendMsg = function() {
    var contentElement = jQuery("[name='content']"),
        content = jQuery.trim(contentElement.val()),
        avatar = 'chat/images/user_a.jpg';
	sendMsg_new(content, avatar, 1);
	contentElement.val('');
	send(content);
};
var classType = ["message-receive", "message-reply"];

var sendMsg_new = function(content, avatar, type) {
	if(content) {
        tempContent = '<div class="'+classType[type]+'"><div class="message-time">' + new Date().toLocaleTimeString() + '</div>'
                    + '<div class="message-info">'
                    + '<div class="user-info_chat"><img class="user-avatar" src="' + avatar + '"></div>'
                    + '<div class="message-content-box"><div class="arrow"></div><div class="item-pics-box"></div>'
                    + '<div class="message-content">' + content + '</div></div></div></div>';
        jQuery('.message-history').append(tempContent);
		 jQuery('.message-history').animate({
			scrollTop: jQuery('.send-message').offset().top - jQuery('.message-history').offset().top + jQuery('.message-history').scrollTop()
		 });
    }
};

var sendMsg_new2 = function(content, avatar, type, time) {
	if(content) {
        tempContent = '<div class="'+classType[type]+'"><div class="message-time">' + new Date(time).toLocaleTimeString() + '</div>'
                    + '<div class="message-info">'
                    + '<div class="user-info_chat"><img class="user-avatar" src="' + avatar + '"></div>'
                    + '<div class="message-content-box"><div class="arrow"></div><div class="item-pics-box"></div>'
                    + '<div class="message-content">' + content + '</div></div></div></div>';
        jQuery('.message-history').append(tempContent);
		 jQuery('.message-history').animate({
			scrollTop: jQuery('.send-message').offset().top - jQuery('.message-history').offset().top + jQuery('.message-history').scrollTop()
		 });
    }
};


var initSendMsg = function() {
    // normal submit
    jQuery('.send-msg-btn').on('click', function() {
        sendMsg();
    });
    // Ctrl+Enter submit
    jQuery("[name='content']").on('keydown', function(e){
        e = e ? e : window.event;
        if(e.ctrlKey && 13 == e.keyCode){
            sendMsg();
        }
    });
};
//initSendMsg();

function getUserId() {
	var userId = throwable_base.getIdFromCookie("throwable");
	if (userId) {
		return userId;
	}
	var time = new Date().getTime();
	return time + "" + Math.floor(Math.random() * 1000);
}

$("document").ready(function(){
	var roomId = throwable_util.url.getUrlParam("roomId");
	if (!roomId) {
		throwable_util.url.location("index.html");
	}
	chat.subscribe();
	userId = getUserId();
	throwable_util.cookie.setCookie(chat.cookieName, userId, 5);
	chat.userId = userId;
	socket = io.connect(chat.url + '/chat?uid='+userId+'&fid=1&rid=' + roomId);
	console.log(socket);
	socket.on('msg', function(msg) {
		chat.publish(msg.fc, msg);
	});
	initSendMsg();
});


avalon.scan();