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
	},
	userPools : {
		type : "throwable.server.framework.client.ClientPools",
		args : [ {
			java : "$config.get('user.thrift.ip')"
		}, {
			java : "$config.get('user.thrift.port')"
		} ],
		fields : {
			minIdle : "5",
			maxIdle : "50",
			maxActive : "60"
		}
	},
	tmpFilePool : {
		type : 'org.nutz.filepool.NutFilePool',
		args : ["~/upload/images/tmps", 1000]
	},
	uploadFileContext : {
		type : 'org.nutz.mvc.upload.UploadingContext',
		singleton : false,
		args : [{refer : 'tmpFilePool'}],
		fields : {
			ignoreNull : true,
			maxFileSize : 10485760,
			nameFilter : '^(.+[.])(gif|jpg|jpeg|png)$'
		}
	},
	myUpload : {
		type : 'org.nutz.mvc.upload.UploadAdaptor',
		singleton : false,
		args : [{refer : 'uploadFileContext'}]
	},
	serverCall : {
		type : 'throwable.web.utils.api.ThriftCallTool',
		args : [{refer : 'thriftPools'}]
	},
	userCall : {
		type : 'throwable.web.utils.api.ThriftCallTool',
		args : [{refer : 'userPools'}]
	}
};
