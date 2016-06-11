var leftDirTree = null;
var thumbnailPicList = null;
/**
 * 初始化
 */
function init(){
	require(["dijit/registry"],function(registry){
		leftDirTree = registry.byId("leftDirTree");
		dojo.connect(leftDirTree,"onClick",onTreeClick);
		thumbnailPicList = registry.byId("piclist");
	});
}


/**
 * 上传
 */
function openUploadDialog(){
	require(["dijit/Dialog","dojo/text!medialib/templates/temp_dialog.html",
	         "dojo/text!medialib/templates/uploadForm.html","dojo/dom-construct","dojo/parser","medialib/MultiUploadImagePanel"],
	         function(Dialog,template,uploadForm,domConstruct,parser){
		var myDialog = new Dialog({
            title: "上传图片",
            id:"uploadDialog",
            onHide : function() {            	
            	this.uploadPanel.destroy();
            	this.destroy();
            	
	        },
            templateString:template, 
            
            show:function(){
            	this.uploadPanel = new medialib.MultiUploadImagePanel();
            	this.uploadPanel.placeAt(this.containerNode);
            	this.inherited('show', arguments);
            }      
            
        });
		myDialog.show();
	});	
}

/**
 * 刷新列表
 */
function freshList(){
	thumbnailPicList.loadData();
}

/**
 * 点击树节点事件
 * @param item
 * @param node
 * @param evt
 */
function onTreeClick(item,node,evt){
	var dirId = (item.id == 'root'?"":item.id);
	var param = {dirId:dirId};
	thumbnailPicList.setRequestParam(param);
	thumbnailPicList.loadData();
}