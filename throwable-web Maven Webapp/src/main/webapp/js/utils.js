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
		 * 判断值是不是为空 为null  为undefined
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
			if(!throwalbe_util.checkValue.isEmpty(email)) {
				return false;
			}
			if(!email.match(throwalbe_util.regx.email_regx)) {
				return false;
			}
			return true;
		}
	},
	/**
	 * 存放匹配的正则表达式
	 */
	regx:{
		//邮箱的正则表达式
		email_regx: /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
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
    		if (r!=null) return unescape(r[2]); return null;
		},
		location : function(url) {
			  window.location.href = url;
		}
	}
};