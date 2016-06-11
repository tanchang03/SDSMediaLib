define("medialib/MultiUploadImagePanel", [ "dojo", "dijit", "dijit/_Widget","dojo/_base/lang",
		"dijit/_TemplatedMixin", "dijit/_WidgetsInTemplateMixin","dojo/dom-construct","dijit/registry",
		"dojo/text!medialib/templates/MultiUploadImagePanel.html","dojox/form/Uploader","dojox/uuid/generateTimeBasedUuid","dojo/query" ], function(
		dojo, dijit, _Widget, lang, _TemplatedMixin, _WidgetsInTemplateMixin,domConstruct,registry) {
	return dojo.declare("medialib.MultiUploadImagePanel", [ dijit._Widget,
			dijit._TemplatedMixin, dijit._WidgetsInTemplateMixin ], {
		// Path to the template
		templateString : dojo.cache("medialib",
				"templates/MultiUploadImagePanel.html"),

		// Override this method to perform custom behavior during dijit construction.
		// Common operations for constructor:
		// 1) Initialize non-primitive types (i.e. objects and arrays)
		// 2) Add additional properties needed by succeeding lifecycle methods
		constructor : function(id) {
			this.ctx = ctx; 
			this.imageFiles = [];
			this.fileNames = [];
			this.url = ctx + "/admin/ctrl/media/upload";
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
			
		},
		doClose:function(){
			var dialog = registry.byId("uploadDialog");
			dialog.hide();
		},
		/**
		 * 提交表单
		 */
		doSubmit : function(){
			if(this.imageFiles.length < 1){
				alert("请先选择需要上传的图片");
			}
			for(var i=0;i<this.imageFiles.length;i++){
				var file = this.imageFiles[i];
				this.upload(file);
			}
		},
		/**
		 * 上传单个文件
		 */
		upload : function(file) {
			var self = this;
			var xhr = new XMLHttpRequest();
			if (xhr.upload) {
				// 上传中
				xhr.upload.addEventListener("progress", function(e) {
					self.onProgress(file, e.loaded, e.total);
				}, false);
	
				// 文件上传成功或是失败
				xhr.onreadystatechange = function(e) {
					if (xhr.readyState == 4) {
						if (xhr.status == 200) {
							self.onSuccess(file, xhr.responseText);
							self.deleteFile(file);
							if (!self.imageFiles.length) {
								//全部完毕
								self.onComplete();	
							}
						} else {
							self.onFailure(file, xhr.responseText);		
						}
					}
				};
	
				// 开始上传
				xhr.open("POST", self.url, true);
				xhr.setRequestHeader("X_FILENAME", file.name);
				xhr.send(file);
			}	
		},
		/**
		 * 上传过程中更新进度条
		 */
		onProgress : function(file,loaded,total){
			var progressDiv = dojo.byId("progress_"+file.id);
			var img = dojo.byId("img_"+file.id);
			var percent = ((loaded/total)*100)+"%";
			progressDiv.style.width = percent;
		},
		
		/**
		 * 单张文件上传完毕后，删除该文件
		 */
		onSuccess : function(file, responseText){
			var fileId = file.id;
			var imgNode = dojo.byId("img_"+fileId);
			var liNode = imgNode.parentNode.parentNode;
			dojo.fadeOut({node:liNode,duration : 100,onEnd:lang.hitch(liNode, function(){
				domConstruct.destroy(this);
			})}).play();
		},
		/**
		 * 全部上传完毕后
		 */
		onComplete: function(){
			this.messageNode.innerHTML = "所有图片已经文件上传完毕";
			dojo.fadeIn({
				node : this.messageNode,
				duration : 1500,
				onEnd:lang.hitch(this, function(){
					dojo.fadeOut({node:this.messageNode}).play(1000);
				})
			}).play();
			
			
		},
		/**
		 * 判断当前指定的文件是否已经上传成功完成了
		 */
		_isTheImageUploaded:function(fileId){
			var result = true;
			for(var i=0;i<this.imageFiles.length;i++){
				var file = this.imageFiles[i];
				if(file.id = fileId){
					result = false;
					break;
				}
			}
			return result;
		},
		
		onDelete : function(file){
			
		},
		/**
		 * 删除上传文件缓存
		 */
		deleteFile : function(fileDelete){
			var arrFile = [];
			for (var i = 0, file; file = this.imageFiles[i]; i++) {
				if (file != fileDelete) {
					arrFile.push(file);
				} else {
					this.onDelete(fileDelete);	
				}
			}
			this.imageFiles = arrFile;
			return this;
		},
		/**
		 * 文件是否已经被选择过了
		 */
		isTheFileSelected : function(theFile){
			var result = false;
			for(var i=0;i<this.imageFiles.length;i++){
				var file = this.imageFiles[i];
				if(file.name == theFile.name){
					result = true;
					break;
				}
			}
			return result;
		},
		/**
		 * 选择文件时触发
		 */
		_OnSelectFileChange:function(){
			//alert(dojox.uuid.generateTimeBasedUuid());
			//alert(dojox.uuid.generateTimeBasedUuid());
			//alert(dojox.uuid.generateTimeBasedUuid());
			var files = this.uploadFileSelector._files;
			var i = 0;
			var fixedHeight = 100;
			var imagePrePanelNode = this.imagePrePanelNode;
			var self = this;
			var funAppendImage = function() {
				var file = files[i];
				if (file) {
					if (file.type.indexOf("image") == 0) {
						if (file.size >= 500000) {
							alert('您这张"'+ file.name +'"图片大小过大，应小于500K');	
							i++;
							funAppendImage();
						} else if(self.isTheFileSelected(file)){
							alert('您这张"'+ file.name +'"图片已经被选择过了');
							i++;
							funAppendImage();
						} else {
							self.imageFiles.push(file);
							var fileId = dojox.uuid.generateTimeBasedUuid();
							file.id = fileId;
							var reader = new FileReader();
							reader.onloadend = function(e) {
								var liHtml = "<li><div></div><div id='progress_"+fileId+"' style='height:1px;background-color:red;width:1%;'></div></li>";
								var liDom = domConstruct.toDom(liHtml);
								var imgDom = domConstruct.create("img",{id:'img_'+fileId,src:''},liDom.childNodes[0]);
								imgDom.onload = function(){
									var fixedWidth =  (this.width * fixedHeight)/this.height;
									this.width = fixedWidth;
									this.height = fixedHeight;
									domConstruct.place(liDom,imagePrePanelNode);
								}
								imgDom.src = e.target.result;
								i++;
								funAppendImage();
							}
							reader.readAsDataURL(file);
						}			
					} else {
						alert('文件"' + file.name + '"不是图片。');
						i++;
						funAppendImage();
					}
				}
			};
			funAppendImage();
		}
	});
});