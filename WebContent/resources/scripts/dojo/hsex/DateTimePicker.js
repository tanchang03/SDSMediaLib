define("hsex/DateTimePicker", [ "dojo", "dijit", "dijit/_Widget","dojo/_base/lang",
		"dijit/_TemplatedMixin", "dijit/_WidgetsInTemplateMixin",
		"dojo/text!hsex/templates/DateTimePicker.html","hsex/ext/My97DatePicker/WdatePicker" ], function(dojo, dijit,
		_Widget,lang, _TemplatedMixin, _WidgetsInTemplateMixin) {
	return dojo.declare("hsex.DateTimePicker", [ dijit._Widget,
			dijit._TemplatedMixin, dijit._WidgetsInTemplateMixin ], {
		// Path to the template
		templateString : dojo.cache("hsex", "templates/DateTimePicker.html"),
		

		// Override this method to perform custom behavior during dijit construction.
		// Common operations for constructor:
		// 1) Initialize non-primitive types (i.e. objects and arrays)
		// 2) Add additional properties needed by succeeding lifecycle methods
		constructor : function() {
			this.dtValue = null;
			this.dtStrValue = null;
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
			var dateFmt = (this.dateFormat?this.dateFormat:"yyyy-MM-dd");
			
			//alert(this.inputobj.id);
			//WdatePicker({el:this.inputobj.id});
			var _this = this;
			dojo.connect(this.inputobj,"onfocus",function(){
				WdatePicker({isShowClear:false,readOnly:false,dateFmt:dateFmt,onpicked:lang.hitch(_this, _this._onPicked)});
			});
		},
		_onPicked : function(){
			//alert(this.id);
			this.dtValue = $dp.cal.date;
			this.dtStrValue = this.inputobj.value;
		},
		getValue : function(){
			return this.dtStrValue;
		},
		getDate : function(){
			return this.dtValue;
		},
		setValue : function(value){
			this.dtStrValue = value;
			this.inputobj.value = value;
		}
	});
});