<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/inc/import.jsp"%>
<head>
	<%@include file="/inc/meta.jsp"%>  
	<style>
		@IMPORT url("${ctx}/resources/scripts/dojo/dojox/image/resources/Lightbox.css");
		@IMPORT url("${ctx}/resources/scripts/dojo/medialib/themes/MultiUploadImagePanel.css");
	</style>
	<script type="text/javascript"> 
	require(
	// 模块标识集合
	[ "dojo", "dojo/parser", "dijit/Tree", "dojox/data/JsonRestStore","medialib/ThumbnailPicList","dojo/store/JsonRest",
	  "dojox/image/Lightbox","dijit/form/DateTextBox"],
	// 回调函数，调用依赖性求值结果
	function(dojo) {
		dojo.ready(function() {
			init();
		});
	});
	</script>
</head> 

<body class="lotusui lotusSpritesOn claro">
	<input data-dojo-id="myFromDate" type="text" name="fromDate" data-dojo-type="dijit/form/DateTextBox" required="true" />
</body>
</html>