var ioc = {
	// 配置文件信息读取
	config : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields : {
			paths : "conf/"
		}
	},
	//thrift连接池
	thriftPools : {
		type : "throwable.server.framework.client.ClientPools",
		args : [ {
			java : "$config.get('server.thrift.ip')"
		}, {
			java : "$config.get('server.thrift.port')"
		} ],
		fields : {
			minIdle : "5",
			maxIdle : "50",
			maxActive : "60"
		}
	}
};
