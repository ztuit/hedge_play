/**
 * @jsx React.DOM
 */
var registerRouter = Backbone.Router.extend({
	routes : {
		"error?reason=:x" : "error",
		"success" : "success"
	},
	error : function() {
		React.renderComponent(
			<validationMessage reason={arguments}/>,
			document.getElementById("message")
			);
	},
	success : function() {
		React.renderComponent(
			<successMessage/>,
			document.getElementById("message")
			);		
	}
});

var pageRouter = new registerRouter();

Backbone.history.start();

React.renderComponent(
  <createUserForm router={pageRouter}/>,
  document.getElementById("main")
);