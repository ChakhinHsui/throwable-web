package throwable.web.room;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * 房间/聊天相关
 * @author WaterHsu
 *
 */
@At("room")
@IocBean
public class RoomController {

	@Inject
	private RoomService roomService;
	
	/**
	 * 查询所有的房间
	 * @return
	 */
	@At("/queryAllRooms")
	@Ok("json")
	@SuppressWarnings("rawtypes")
	public Map queryAllRooms() {
		return roomService.queryAllRooms();
	}
}
