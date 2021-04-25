function isIpStr(strIP) {
	if (strIP == 'undefined' || strIP == null) {
		return false;
	}
	//匹配IP地址的正则表达式
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g; 
	if(re.test(strIP)) {
		if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) 
		return true;
	}
	return false;
}

String.prototype.startWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length) {
		return false;
	}
	if (this.substr(0, s.length) == s) {
		return true;
	} else {
		return false;
	}
	return true;
}

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.ltrim=function(){
    return this.replace(/(^\s*)/g,"");
}
 
String.prototype.rtrim=function(){
    return this.replace(/(\s*$)/g, "");
}
