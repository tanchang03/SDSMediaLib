<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
    
<%
	Date today = new Date();
	long todayTime = today.getTime();
	String sToday = DateUtils.formatDate(today, "yyyy-MM-dd HH:mm:ss");
	request.setAttribute("today", sToday);
	String userAgent = request.getHeader("User-Agent");
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link id="themeStyles" rel="stylesheet" href="${ctx}/resources/scripts/dojo/dojo/resources/dojo.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/scripts/dojo/dijit/themes/dijit.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/scripts/dojo/dijit/themes/claro/claro.css">



<script type="text/javascript" src="${ctx}/resources/scripts/common.js"></script>
<%
if(userAgent.toLowerCase().indexOf("firefox")>=0){
	%>
	<%@include file="/inc/firefox.jsp" %>
	<%
}
%>


<script language="Javascript"  type="text/javascript">
	//主题
	var theme = "blue";
	var rtl = "";
	document.write("<link rel='stylesheet' href='${ctx}/resources/css/base/core"+rtl+".css' type='text/css' />");
	document.write("<link rel='stylesheet' href='${ctx}/resources/css/"+theme+"Theme/"+theme+"Theme"+rtl+".css' type='text/css' />");
	document.write("<link rel='stylesheet' href='${ctx}/resources/css/medialib/medialib_home.css' type='text/css' />"); 
	
	//服务器端时间
	var todayDt = new Date(<%=todayTime%>);
	var ctx = "${ctx}";
	
 	//dojo configuration
 	var dojoConfig ={
			async: true,
			isDebug: true,
			packages: [{
				name: "custom",
				location: "${ctx}/resources/scripts/dojo/custom"
			}]
		};
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" data-dojo-config="isDebug: false, async: true, parseOnLoad: true" src="${ctx}/resources/scripts/dojo/dojo/dojo.js"></script>
<!--[if IE 6]>
<script type="text/javascript">
document.getElementsByTagName("html")[0].className+=" lotusui_ie lotusui_ie6";
</script>
<![endif]--> 
<!--[if IE 7]>
<script type="text/javascript">
document.getElementsByTagName("html")[0].className+=" lotusui_ie lotusui_ie7";
</script>
<![endif]--> 
<!-- END exampleHead.htm -->

<title>湖南工烟图片管理系统</title>

