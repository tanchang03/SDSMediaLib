define("medialib/ThumbnailPicList", [ "dojo", "dijit","dojo/_base/lang","dijit/_Widget",
		"dijit/_TemplatedMixin", "dijit/_WidgetsInTemplateMixin",
		"dojo/text!medialib/templates/ThumbnailPicList.html","dojo/text!medialib/templates/ThumbnailPicItem.html",
		"dojox/image/Lightbox","dojo/dom-construct"], function(dojo,
		dijit,lang, _Widget, _TemplatedMixin, _WidgetsInTemplateMixin,thumbnailPicListTemplate,thumbnailPicItemTemplate,
		Lightbox,domConstruct) {
	return dojo.declare("medialib.ThumbnailPicList", [ dijit._Widget,
			dijit._TemplatedMixin, dijit._WidgetsInTemplateMixin ], {
		// Path to the template
		templateString : dojo.cache("medialib",
				"templates/ThumbnailPicList.html"),

		// Override this method to perform custom behavior during dijit construction.
		// Common operations for constructor:
		// 1) Initialize non-primitive types (i.e. objects and arrays)
		// 2) Add additional properties needed by succeeding lifecycle methods
		constructor : function() {
			this.store = null;
			this.ctx = ctx;
			this.listBodyHTML = "";
			this.currentItems = null;
			this.lightboxDialog = null;
			this.pageSize = 10;//页面大小默认为10
			this.param = {pageSize:this.pageSize};
			this.pageNo = 1;
			
		},

		// When this method is called, all variables inherited from superclasses are 'mixed in'.
		// Common operations for postMixInProperties
		// 1) Modify or assign values for widget property variables defined in the template HTML file
		postMixInProperties : function() {
			
		},

		// postCreate() is called after buildRendering().  This is useful to override when 
		// you need to access and/or manipulate DOM nodes included with your widget.
		// DOM nodes and widgets with the dojoAttachPoint attribute specified can now be directly
		// accessed as fields on "this". 
		// Common operations for postCreate
		// 1) Access and manipulate DOM nodes created in buildRendering()
		// 2) Add new DOM nodes or widgets 
		postCreate : function() {
			this.domNode.component = this;
			this.loadData();
		},
		/**
		 * 设置请求参数，该参数将会作为url param添加在请求url后面
		 */
		setRequestParam:function(param){
			dojo.mixin(this.param,param);
		},
		
		_getRequestParamString:function(){
			var paramString = "";
			
			var tmp = [];
			for ( var param in this.param) {				
				tmp.push(param + "=" + encodeURI(encodeURI(eval("this.param."+param))))
			}
			paramString = tmp.join("&");			
			return paramString;
		},
		
		
		/**
		 * 加载数据
		 */
		loadData : function(){
			if(this.store != null){
				var queryUrl = this.pageNo+ "?" + this._getRequestParamString();
				this.store.query(queryUrl).then(lang.hitch(this, function(page){
					if(page && page.result){
						this.currentItems=page.result;
						//debugger;
						this.renderThumbnailPicList(page);
					}
				}));
			}
			this.rebuildLightBoxDialog();;
		},
		/**
		 * 渲染一个数据集结果
		 */
		renderThumbnailPicList : function(page) {
			dojo.empty(this.theUL);
			var items = page.result;
			var rows = [];
			if(items.length < 1){
				domConstruct.create("div",{innerHTML:'未查询到任何媒体数据'},this.theUL);
			}else{
				for ( var i = 0; i < items.length; i++) {
					var item = items[i];
					var itemHTML = this.wrapItemHTML(item);
				}
			}
		},
		
		/**
		 * 解析dom对象中的dojo事件描述，并将函数添加到事件对象上
		 */
		parseDojoAttachEvent:function(dom){
			if(dom.getAttribute && dom.getAttribute("dojoattachevent")){
				var attachEvent = dom.getAttribute("dojoattachevent");
				var eventsStrArray = attachEvent.split(",");
				for(var i=0;i<eventsStrArray.length;i++){
					var eventSplitArray = eventsStrArray[i].split(":");
					try{
						eval("var theFunction = this."+eventSplitArray[1]);
						dojo.connect(dom,eventSplitArray[0],theFunction);
					}catch(e){}
					
				}
				
			}
			if(dom.childNodes){
				for(var i=0;i<dom.childNodes.length;i++){
					this.parseDojoAttachEvent(dom.childNodes[i]);
				}
			}
		},
		/**
		 * 封装一个缩略图 li dom 对象
		 */
		wrapItemHTML:function(item){
			this.thumbnail_item_width = '100px';
			this.thumbnail_item_height = "100px";
			this.thumbnail_item_title = item.name;
			this.thumbnail_item_thumbnailurl = item.url;
			this.thumbnail_item_fileurl = item.url;
			this.thumbnail_item_id = item.id;
			
			var itemHtml = this._stringRepl(thumbnailPicItemTemplate);
			var tmpDom = domConstruct.toDom(itemHtml);
			this.parseDojoAttachEvent(tmpDom);
			this.appendLiToUL(tmpDom);
		},
		/**
		 * 添加li dom对象到Ul对象中
		 */
		appendLiToUL:function(tmpDom){
			for(var i=0;i<tmpDom.childNodes.length;i++){
				if(tmpDom.childNodes[i].tagName == 'LI'){
					domConstruct.place(tmpDom.childNodes[i], this.theUL);
				}
			}
		},
		/**
		 * 单击图片时，显示大图
		 */
		onItemClick:function(){
			var evt=arguments[0] || window.event;
			var imgNode=evt.srcElement || evt.target;
			
			var componentId = imgNode.id.split("_")[0];
			var me = dojo.byId(componentId).component;
			var groupName = "imgGroup";
			
			if(me.lightboxDialog == null){
				me.lightboxDialog = new dojox.image.LightboxDialog({});
				var dialog = me.lightboxDialog;
				for ( var i = 0; i < me.currentItems.length; i++) {
					var item = me.currentItems[i];
					dialog.addImage({ title:item.name, href:item.url}, groupName);
				}
				me.lightboxDialog.startup();
			}
			me.lightboxDialog.show({title:imgNode.title,group:groupName, href:imgNode.getAttribute("thumbnailurl")});
		},
		/**
		 * 图片加载完成后调整好宽带和高度，然后显示
		 */
		onImageLoad:function(){
			var fixedHeight = 100;
			var fixedWidth =  (this.width * fixedHeight)/this.height;
			this.width = fixedWidth;
			this.height = fixedHeight;
			this.style.display='block';
		},
		/**
		 * 重建对话框
		 */
		rebuildLightBoxDialog:function(){
			if(this.lightboxDialog != null){
				this.lightboxDialog.destroy();
				this.lightboxDialog = null;
			}
		}
	});
});
