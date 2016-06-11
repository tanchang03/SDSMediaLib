Date.prototype.format = function(formatStr) {
	var date = this;
	/*
	 * 函数：填充0字符 参数：value-需要填充的字符串, length-总长度 返回：填充后的字符串
	 */
	var zeroize = function(value, length) {
		if (!length) {
			length = 2;
		}
		value = new String(value);
		for ( var i = 0, zeros = ''; i < (length - value.length); i++) {
			zeros += '0';
		}
		return zeros + value;
	};
	return formatStr
			.replace(
					/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g,
					function($0) {
						switch ($0) {
						case 'd':
							return date.getDate();
						case 'dd':
							return zeroize(date.getDate());
						case 'ddd':
							return [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri',
									'Sat' ][date.getDay()];
						case 'dddd':
							return [ 'Sunday', 'Monday', 'Tuesday',
									'Wednesday', 'Thursday', 'Friday',
									'Saturday' ][date.getDay()];
						case 'M':
							return date.getMonth() + 1;
						case 'MM':
							return zeroize(date.getMonth() + 1);
						case 'MMM':
							return [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
									'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ][date
									.getMonth()];
						case 'MMMM':
							return [ 'January', 'February', 'March', 'April',
									'May', 'June', 'July', 'August',
									'September', 'October', 'November',
									'December' ][date.getMonth()];
						case 'yy':
							return new String(date.getFullYear()).substr(2);
						case 'yyyy':
							return date.getFullYear();
						case 'h':
							return date.getHours() % 12 || 12;
						case 'hh':
							return zeroize(date.getHours() % 12 || 12);
						case 'H':
							return date.getHours();
						case 'HH':
							return zeroize(date.getHours());
						case 'm':
							return date.getMinutes();
						case 'mm':
							return zeroize(date.getMinutes());
						case 's':
							return date.getSeconds();
						case 'ss':
							return zeroize(date.getSeconds());
						case 'l':
							return date.getMilliseconds();
						case 'll':
							return zeroize(date.getMilliseconds());
						case 'tt':
							return date.getHours() < 12 ? '上午' : '下午';
						case 'TT':
							return date.getHours() < 12 ? 'AM' : 'PM';
						}
					});
};









//===================================================================
//Author: Matt Kruse <matt@mattkruse.com>
//WWW: http://www.mattkruse.com/
//
//NOTICE: You may use this code for any purpose, commercial or
//private, without any further permission from the author. You may
//remove this notice from your final code if you wish, however it is
//appreciated by the author if at least my web site address is kept.
//
//You may *NOT* re-distribute this code in any way except through its
//use. That means, you can include it in your product, or your web
//site, or any other form where the code is actually being used. You
//may not put the plain javascript up on your site for download or
//include it in your javascript libraries for download. 
//If you wish to share this code with others, please just point them
//to the URL instead.
//Please DO NOT link directly to my .js files from your site. Copy
//the files to your server and use them there. Thank you.
//===================================================================

//HISTORY
//------------------------------------------------------------------
//May 17, 2003: Fixed bug in parseDate() for dates <1970
//March 11, 2003: Added parseDate() function
//March 11, 2003: Added "NNN" formatting option. Doesn't match up
//              perfectly with SimpleDateFormat formats, but 
//              backwards-compatability was required.

//------------------------------------------------------------------
//These functions use the same 'format' strings as the 
//java.text.SimpleDateFormat class, with minor exceptions.
//The format string consists of the following abbreviations:
//
//Field        | Full Form          | Short Form
//-------------+--------------------+-----------------------
//Year         | yyyy (4 digits)    | yy (2 digits), y (2 or 4 digits)
//Month        | MMM (name or abbr.)| MM (2 digits), M (1 or 2 digits)
//           | NNN (abbr.)        |
//Day of Month | dd (2 digits)      | d (1 or 2 digits)
//Day of Week  | EE (name)          | E (abbr)
//Hour (1-12)  | hh (2 digits)      | h (1 or 2 digits)
//Hour (0-23)  | HH (2 digits)      | H (1 or 2 digits)
//Hour (0-11)  | KK (2 digits)      | K (1 or 2 digits)
//Hour (1-24)  | kk (2 digits)      | k (1 or 2 digits)
//Minute       | mm (2 digits)      | m (1 or 2 digits)
//Second       | ss (2 digits)      | s (1 or 2 digits)
//AM/PM        | a                  |
//
//NOTE THE DIFFERENCE BETWEEN MM and mm! Month=MM, not mm!
//Examples:
//"MMM d, y" matches: January 01, 2000
//                   Dec 1, 1900
//                   Nov 20, 00
//"M/d/yy"   matches: 01/20/00
//                   9/2/00
//"MMM dd, yyyy hh:mm:ssa" matches: "January 01, 2000 12:30:45AM"
//------------------------------------------------------------------

var MONTH_NAMES=new Array('January','February','March','April','May','June','July','August','September','October','November','December','Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
var DAY_NAMES=new Array('Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sun','Mon','Tue','Wed','Thu','Fri','Sat');
function LZ(x) {return(x<0||x>9?"":"0")+x;}

//------------------------------------------------------------------
//isDate ( date_string, format_string )
//Returns true if date string matches format of format string and
//is a valid date. Else returns false.
//It is recommended that you trim whitespace around the value before
//passing it to this function, as whitespace is NOT ignored!
//------------------------------------------------------------------
function isDate(val,format) {
	
	var date=getDateFromFormat(val,format);
	if (date==0) { return false; }
	return true;
	}

//-------------------------------------------------------------------
//compareDates(date1,date1format,date2,date2format)
//Compare two date strings to see which is greater.
//Returns:
//1 if date1 is greater than date2
//0 if date2 is greater than date1 of if they are the same
//-1 if either of the dates is in an invalid format
//-------------------------------------------------------------------
function compareDates(date1,dateformat1,date2,dateformat2) {
	var d1=getDateFromFormat(date1,dateformat1);
	var d2=getDateFromFormat(date2,dateformat2);
	if (d1==0 || d2==0) {
		return -1;
		}
	else if (d1 > d2) {
		return 1;
		}
	return 0;
	}

//------------------------------------------------------------------
//formatDate (date_object, format)
//Returns a date in the output format specified.
//The format string uses the same abbreviations as in getDateFromFormat()
//------------------------------------------------------------------
function formatDate(date,format) {
	format=format+"";
	var result="";
	var i_format=0;
	var c="";
	var token="";
	var y=date.getYear()+"";
	var M=date.getMonth()+1;
	var d=date.getDate();
	var E=date.getDay();
	var H=date.getHours();
	var m=date.getMinutes();
	var s=date.getSeconds();
	var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;
	// Convert real date parts into formatted versions
	var value=new Object();
	if (y.length < 4) {y=""+(y-0+1900);}
	value["y"]=""+y;
	value["yyyy"]=y;
	value["yy"]=y.substring(2,4);
	value["M"]=M;
	value["MM"]=LZ(M);
	value["MMM"]=MONTH_NAMES[M-1];
	value["NNN"]=MONTH_NAMES[M+11];
	value["d"]=d;
	value["dd"]=LZ(d);
	value["E"]=DAY_NAMES[E+7];
	value["EE"]=DAY_NAMES[E];
	value["H"]=H;
	value["HH"]=LZ(H);
	//if (H==0){value["h"]=12;}
	//else if (H>12){value["h"]=H-12;}
	//else {value["h"]=H;}
	value["h"]=H;
	value["hh"]=LZ(value["h"]);
	if (H>11){value["K"]=H-12;} else {value["K"]=H;}
	value["k"]=H+1;
	value["KK"]=LZ(value["K"]);
	value["kk"]=LZ(value["k"]);
	if (H > 11) { value["a"]="PM"; }
	else { value["a"]="AM"; }
	value["m"]=m;
	value["mm"]=LZ(m);
	value["s"]=s;
	value["ss"]=LZ(s);
	while (i_format < format.length) {
		c=format.charAt(i_format);
		token="";
		while ((format.charAt(i_format)==c) && (i_format < format.length)) {
			token += format.charAt(i_format++);
			}
		if (value[token] != null) { result=result + value[token]; }
		else { result=result + token; }
		}
	return result;
	}
	
//------------------------------------------------------------------
//Utility functions for parsing in getDateFromFormat()
//------------------------------------------------------------------
function _isInteger(val) {
	var digits="1234567890";
	for (var i=0; i < val.length; i++) {
		if (digits.indexOf(val.charAt(i))==-1) { return false; }
		}
	return true;
	}
function _getInt(str,i,minlength,maxlength) {
	for (var x=maxlength; x>=minlength; x--) {
		var token=str.substring(i,i+x);
		if (token.length < minlength) { return null; }
		if (_isInteger(token)) { return token; }
		}
	return null;
	}
	
//------------------------------------------------------------------
//getDateFromFormat( date_string , format_string )
//
//This function takes a date string and a format string. It matches
//If the date string matches the format string, it returns the 
//getTime() of the date. If it does not match, it returns 0.
//------------------------------------------------------------------
function getDateFromFormat(val,format) {
	val=val+"";
	format=format+"";
	var i_val=0;
	var i_format=0;
	var c="";
	var token="";
	var token2="";
	var x,y;
	var now=new Date();
	var year=now.getYear();
	var month=now.getMonth()+1;
	var date=1;
	var hh=now.getHours();
	var mm=now.getMinutes();
	var ss=now.getSeconds();
	var ampm="";
	
	while (i_format < format.length) {
		// Get next token from format string
		c=format.charAt(i_format);
		token="";
		while ((format.charAt(i_format)==c) && (i_format < format.length)) {
			token += format.charAt(i_format++);
			}
		// Extract contents of value based on format token
		if (token=="yyyy" || token=="yy" || token=="y") {
			if (token=="yyyy") { x=4;y=4; }
			if (token=="yy")   { x=2;y=2; }
			if (token=="y")    { x=2;y=4; }
			year=_getInt(val,i_val,x,y);
			if (year==null) { return 0; }
			i_val += year.length;
			if (year.length==2) {
				if (year > 70) { year=1900+(year-0); }
				else { year=2000+(year-0); }
				}
			}
		else if (token=="MMM"||token=="NNN"){
			month=0;
			for (var i=0; i<MONTH_NAMES.length; i++) {
				var month_name=MONTH_NAMES[i];
				if (val.substring(i_val,i_val+month_name.length).toLowerCase()==month_name.toLowerCase()) {
					if (token=="MMM"||(token=="NNN"&&i>11)) {
						month=i+1;
						if (month>12) { month -= 12; }
						i_val += month_name.length;
						break;
						}
					}
				}
			if ((month < 1)||(month>12)){return 0;}
			}
		else if (token=="EE"||token=="E"){
			for (var i=0; i<DAY_NAMES.length; i++) {
				var day_name=DAY_NAMES[i];
				if (val.substring(i_val,i_val+day_name.length).toLowerCase()==day_name.toLowerCase()) {
					i_val += day_name.length;
					break;
					}
				}
			}
		else if (token=="MM"||token=="M") {
			month=_getInt(val,i_val,token.length,2);
			if(month==null||(month<1)||(month>12)){return 0;}
			i_val+=month.length;}
		else if (token=="dd"||token=="d") {
			date=_getInt(val,i_val,token.length,2);
			if(date==null||(date<1)||(date>31)){return 0;}
			i_val+=date.length;}
		else if (token=="hh"||token=="h") {
			hh=_getInt(val,i_val,token.length,2);
			if(hh==null||(hh<1)||(hh>12)){return 0;}
			i_val+=hh.length;}
		else if (token=="HH"||token=="H") {
			hh=_getInt(val,i_val,token.length,2);
			if(hh==null||(hh<0)||(hh>23)){return 0;}
			i_val+=hh.length;}
		else if (token=="KK"||token=="K") {
			hh=_getInt(val,i_val,token.length,2);
			if(hh==null||(hh<0)||(hh>11)){return 0;}
			i_val+=hh.length;}
		else if (token=="kk"||token=="k") {
			hh=_getInt(val,i_val,token.length,2);
			if(hh==null||(hh<1)||(hh>24)){return 0;}
			i_val+=hh.length;hh--;}
		else if (token=="mm"||token=="m") {
			mm=_getInt(val,i_val,token.length,2);
			if(mm==null||(mm<0)||(mm>59)){return 0;}
			i_val+=mm.length;}
		else if (token=="ss"||token=="s") {
			ss=_getInt(val,i_val,token.length,2);
			if(ss==null||(ss<0)||(ss>59)){return 0;}
			i_val+=ss.length;}
		else if (token=="a") {
			if (val.substring(i_val,i_val+2).toLowerCase()=="am") {ampm="AM";}
			else if (val.substring(i_val,i_val+2).toLowerCase()=="pm") {ampm="PM";}
			else {return 0;}
			i_val+=2;}
		else {
			if (val.substring(i_val,i_val+token.length)!=token) {return 0;}
			else {i_val+=token.length;}
			}
		}
	// If there are any trailing characters left in the value, it doesn't match
	if (i_val != val.length) { return 0; }
	// Is date valid for month?
	if (month==2) {
		// Check for leap year
		if ( ( (year%4==0)&&(year%100 != 0) ) || (year%400==0) ) { // leap year
			if (date > 29){ return 0; }
			}
		else { if (date > 28) { return 0; } }
		}
	if ((month==4)||(month==6)||(month==9)||(month==11)) {
		if (date > 30) { return 0; }
		}
	// Correct hours value
	if (hh<12 && ampm=="PM") { hh=hh-0+12; }
	else if (hh>11 && ampm=="AM") { hh-=12; }
	var newdate=new Date(year,month-1,date,hh,mm,ss);
	return newdate.getTime();
	}

//------------------------------------------------------------------
//parseDate( date_string [, prefer_euro_format] )
//
//This function takes a date string and tries to match it to a
//number of possible date formats to get the value. It will try to
//match against the following international formats, in this order:
//y-M-d   MMM d, y   MMM d,y   y-MMM-d   d-MMM-y  MMM d
//M/d/y   M-d-y      M.d.y     MMM-d     M/d      M-d
//d/M/y   d-M-y      d.M.y     d-MMM     d/M      d-M
//A second argument may be passed to instruct the method to search
//for formats like d/M/y (european format) before M/d/y (American).
//Returns a Date object or null if no patterns match.
//------------------------------------------------------------------
function parseDate(val) {
	var preferEuro=(arguments.length==2)?arguments[1]:false;
	generalFormats=new Array('y-M-d','MMM d, y','MMM d,y','y-MMM-d','d-MMM-y','MMM d');
	monthFirst=new Array('M/d/y','M-d-y','M.d.y','MMM-d','M/d','M-d');
	dateFirst =new Array('d/M/y','d-M-y','d.M.y','d-MMM','d/M','d-M');
	var checkList=new Array('generalFormats',preferEuro?'dateFirst':'monthFirst',preferEuro?'monthFirst':'dateFirst');
	var d=null;
	for (var i=0; i<checkList.length; i++) {
		var l=window[checkList[i]];
		for (var j=0; j<l.length; j++) {
			d=getDateFromFormat(val,l[j]);
			if (d!=0) { return new Date(d); }
			}
		}
	return null;
	}

/**
* 解析日期
*/
parseDateCHS = function(dateString) {
	// /<summary>
	// /解析常用的中文日期并返回日期对象。
	// /</summary>
	// /<param name="dateString" type="string">
	// /日期字符串。包含的格式有："xxxx(xx)-xx-xx xx:xx:xx","xxxx(xx).xx.xx xx:xx:xx",
	// /"xxxx(xx)年xx月xx日 xx时xx分xx秒"
	// /</param>
	var regExp1 = /^\d{4}-\d{1,2}-\d{1,2}( \d{1,2}:\d{1,2}:\d{1,2})?$/;
	var regExp2 = /^\d{4}\.\d{1,2}\.\d{1,2}( \d{1,2}:\d{1,2}:\d{1,2})?$/;
	var regExp3 = /^\d{4}年\d{1,2}月\d{1,2}日( \d{1,2}时\d{1,2}分\d{1,2}秒)?$/;
	if (regExp1.test(dateString)) {
	} else if (regExp2.test(dateString)) {
		dateString = dateString.replace(/\./g, "-");
	} else if (regExp3.test(dateString)) {
		dateString = dateString.replace("年", "-").replace("月", "-").replace(
				"日", "").replace("时", ":").replace("分", ":").replace("秒", "");
	} else {
		throw "传给Date.parseCHS的参数值的格式不正确。请传递一个有效的日期格式字符串作为参数。";
	}
	var date_time = dateString.split(" ");
	var date_part = date_time[0].split("-");
	var time_part = (date_time.length > 1 ? date_time[1].split(":") : "");
	if (time_part == "") {
		return new Date(date_part[0], date_part[1] - 1, date_part[2]);
	} else {
		return new Date(date_part[0], date_part[1] - 1, date_part[2],
				time_part[0], time_part[1], time_part[2]);
	}
};

/**
 * 获取事件目标对象
 * 为了兼容IE FF CHROME的写法
 */
function getEventTargetElement(){
	var evt=arguments[0] || window.event;
	var ele=evt.srcElement || evt.target;
	return ele;
}
