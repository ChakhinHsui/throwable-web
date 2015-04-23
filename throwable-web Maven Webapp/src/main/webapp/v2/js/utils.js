var events = (function(){
	var topics = {};
	return {
		subscribe: function(topic, listener){
			if(!topics[topic]) {
				topics[topic] = {queue: []};
			}
			var index = topics[topic].queue.push(listener) - 1;
			console.log(index);
			return (function(topic, index){
				return {
					remove: function() {
						delete topics[topic].queue[index];
					}
				};
			})();
		},
		publish: function(topic, info) {
			if(!topics[topic] || !topics[topic].queue.length) return;
			var items = topics[topic].queue;
			items.forEach(function(item){
				item(info || {});
			});
		}
	};
})();

var throwable_util = {
	/**
	 * 检查值的合法性  判断是否为空 是否为null 是否为undefined 是不是数字 是不是email
	 */
	checkValue : {
		/**
		 * 根据id  去获取页面元素<input> <textarea> <select>的值 然后去判断是否为空
		 */
		isEmptyById : function(input_id) {
			if(!$("#" + input_id).val()) {
				return false;
			}
			return true;
		},
		/**
		 * 判断值是不是为空'' 为null  为undefined
		 */
		isEmpty : function(value) {
			if(!value) {
				return false;
			}
			return true;
		},
		/**
		 * 判断邮件格式是否正确
		 * @param email
		 * @returns {Boolean}
		 */
		isEmail : function(email) {
			console.log("ff");
			if(!throwable_util.regx.email_regx.test(email)) {
				return false;
			}
			console.log("mm");
			return true;
		}
	},
	/**
	 * 存放匹配的正则表达式
	 */
	regx:{
		//邮箱的正则表达式
		email_regx:  /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/
	},
	/**
	 * 与url相关的基础工具
	 */
	url : {
		/**
		 * js 获取网址后面的参数
		 * @param name
		 * @returns
		 */
		getUrlParam : function(name) {
			var reg = new RegExp("(^|&)" + name +"=([^&]*)(&|$)");
    		var r = window.location.search.substr(1).match(reg);
    		if (r!=null) return unescape(r[2]); 
    		return null;
		},
		location : function(url) {
			  window.location.href = url;
		},
		refresh : function() {
			window.location.reload();
		},
		goPre : function() {
			 window.location.href = document.referrer; //使用document.referrer
		}
	},
	cookie : {
		//存储cookie  time为小时
		setCookie : function(name, value, time) {
			var exp = new Date();
			exp.setTime(exp.getTime() + time * 60 * 60 * 1000);
			document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
		},
		//取cookie
		getCookie : function(name) {
			var arr, reg = new RegExp("(^|)"+name+"=([^;]*)(;|$)");
			if(arr = document.cookie.match(reg)) {
				return unescape(arr[2]);
			} else {
				return null;
			}
		},
		//删除cookie
		deleteCookie : function(name) {
			var exp = new Date();
			exp.setTime(exp.getTime() - 1);
			var cval = throwable_util.cookie.getCookie(name);
			if(null != cval) {
				document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
			}
		}
	},
	subPubTool : events
};


