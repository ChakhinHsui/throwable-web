package throwable.web.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.web.utils.api.ApiDirUtil;
import throwable.web.utils.api.ThriftCallTool;

/**
 * 聊天的房间相关
 * @author WaterHsu
 *
 */
@IocBean
public class RoomService {
	
	/**
	 * 远程调用接口
	 */
	@Inject
	private ThriftCallTool serverCall;
	@Inject
	private ApiDirUtil apiDirUtil;
	
	/**
	 * 查询所有房间
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map queryAllRooms() {
		Map<String, Object> params = new HashMap<>();
		List<Map> list = serverCall.call2ListObj("/chat/queryAllRooms", params, Map.class);
		if (null == list) {
			list = new ArrayList<>();
		}
		Map map = new HashMap();
		map.put("msgCode", 1);
		map.put("rooms", list);
		return map;
	}
}
