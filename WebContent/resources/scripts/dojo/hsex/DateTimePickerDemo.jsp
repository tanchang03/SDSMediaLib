<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../dijit/themes/dijit.css">
<link rel="stylesheet" type="text/css" href="../dijit/themes/claro/claro.css">
<title>test02</title>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<script type="text/javascript"
	data-dojo-config="isDebug: false, async: true, parseOnLoad: true"
	src="../dojo/dojo.js"></script>
<script type="text/javascript">
require(
[ "dojo","dijit/registry", "dojo/parser", "dijit/form/DateTextBox", "dijit/form/TimeTextBox", "dijit/form/ToggleButton","hsex/DateTimePicker", "dijit/form/Button" ],

function(dojo) {
	dojo.ready(function() {

	});
});

function onButtonClick(thisObj, thisEvent) {
//使用“thisObj”代替关键字“this”以直接引用此组件
//使用“thisEvent”代替关键字“event”以引用生成的事件
	var datePicker = dijit.registry.byId("datePicker");
	
	alert(datePicker.getValue());
	alert(datePicker.getDate());

}
function onButton02Click(thisObj, thisEvent) {
//使用“thisObj”代替关键字“this”以直接引用此组件
//使用“thisEvent”代替关键字“event”以引用生成的事件
	var datePicker = dijit.registry.byId("datePicker");
	datePicker.setValue("2012-09-28");
}</script>
<style>



</style>
</head>
<body class="claro">
	<div id='datePicker' data-dojo-id="datePicker" data-dojo-type="hsex.DateTimePicker" data-dojo-props="dateFormat:'yyyy-MM-dd HH:mm'">
		
	</div>
	
	<div data-dojo-type="dijit.form.Button"
			data-dojo-props="label:'Button'" onclick="return onButtonClick(this, event);"></div>

	<div data-dojo-type="dijit.form.Button"
		data-dojo-props="label:'Button'" onclick="return onButton02Click(this, event);"></div>
</body>
</html>