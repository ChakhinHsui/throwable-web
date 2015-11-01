var room_model = avalon.define("rooms", function(vm){
	vm.room_list = [];
});
var room = {
	getAllRooms: function() {
		$.post("../room/queryAllRooms", {}, function(result){
			console.log(result);
			if(1 == result.msgCode) {
				room_model.room_list = result.rooms;
			}
		}, "json");
	}
};

$(document).ready(function(){
	room.getAllRooms();
});


avalon.scan();