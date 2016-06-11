<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/inc/import.jsp"%> 
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/scripts/dojo/dijit/themes/dijit.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/scripts/dojo/dijit/themes/claro/claro.css">
<title>treedemo</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" data-dojo-config="isDebug: false, async: true, parseOnLoad: true" src="${ctx}/resources/scripts/dojo/dojo/dojo.js"></script>
<script type="text/javascript">
require(
// 模块标识集合
[ "dojo", "dojo/parser", "dijit/Tree", "dojox/data/JsonRestStore" ],
// 回调函数，调用依赖性求值结果
function(dojo) {
	dojo.ready(function() {
		
	});
});
</script>
</head>
<body class="claro">
	<div data-dojo-id="store" data-dojo-type="dojox.data.JsonRestStore" data-dojo-props="target:'${pageContext.request.contextPath}/admin/ctrl/dir/dirtree/',labelAttribute:'name'"></div>
	<div data-dojo-id="model" data-dojo-type="dijit.tree.ForestStoreModel" data-dojo-props="store:store,deferItemLoadingUntilExpand:true,rootLabel:'我的目录',rootId:'root',query:'rootNodes',childrenAttrs:['children']"/>
	<div data-dojo-type="dijit.Tree" model="model" showRoot="true" persist="true" ></div>
</body>
</html>