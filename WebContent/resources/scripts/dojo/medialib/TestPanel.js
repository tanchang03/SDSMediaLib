define("medialib/TestPanel", [ "dojo", "dijit", "dijit/_Widget",
		"dijit/_TemplatedMixin", "dijit/_WidgetsInTemplateMixin",
		"dojo/text!medialib/templates/TestPanel.html","dojox/form/Uploader" ], function(dojo, dijit,
		_Widget, _TemplatedMixin, _WidgetsInTemplateMixin) {
	return dojo.declare("medialib.TestPanel", [ dijit._Widget,
			dijit._TemplatedMixin, dijit._WidgetsInTemplateMixin ], {
		// Path to the template
		templateString : dojo.cache("medialib", "templates/TestPanel.html"),

		// Override this method to perform custom behavior during dijit construction.
		// Common operations for constructor:
		// 1) Initialize non-primitive types (i.e. objects and arrays)
		// 2) Add additional properties needed by succeeding lifecycle methods
		constructor : function() {
			this.ctx = ctx;
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
		}
	});
});