/**
 * 公共参数
 * 		eg: var email = jiaxin.util.getUrlParamVal('email');
 * @author yubing
 */
var jiaxin = {
		getUtil : function(){
			var commonFucInstance = function(){};
			commonFucInstance.prototype = new commonFunction();
		    commonFucInstance.prototype.constructor = commonFucInstance;
		    return new commonFucInstance();
		}
}
jiaxin.util = jiaxin.getUtil();

/**
 * 工具类方法
 */
function commonFunction(){
	/**
	 * 获取url参数map
	 */
	this.getUrlParam = function(){
		var search = location.search;
		if(search.length>2){
			search = search.substring(1);
			var map = {};
			var arr = search.split("&");
			var parr = new Array();
			for(var i in arr){
				parr = arr[i].split("=");
				if(parr.length==2){
					map[parr[0]] = parr[1];
				}
			}
			return map;
		}
		return null;
	};
	/**
	 * 获取url参数值
	 * @param key
	 * @returns
	 */
	this.getUrlParamVal = function(key){
		var map = this.getUrlParam();
		if(map){
			return map[key];
		}
	};
	/**
	 * 根据key获取配置文件对应的值
	 */
	this.getConfigureValue= function(key){
		var result = "";
		$.ajax({
			url:'common/getAppConfigureValue.form',
			data:{'configureKey':key},
			type:'POST',
			async:false,
			cache:false,
			success:function(data){
				data = JSON.parse(data);
				result = data.data; 
			}
		});
		return result;
	}
}