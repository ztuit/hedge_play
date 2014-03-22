/**
 * @jsx React.DOM
 */
 var loginRouter = Backbone.Router.extend({
	routes : {
		"validation?reason=:x" : "error",
	},
	error : function() {
		React.renderComponent(
			<validationMessage reason={arguments}/>,
			document.getElementById("messages")
			);
	}
});

var pageRouter = new loginRouter();

Backbone.history.start();

React.renderComponent(
  <loginForm router={pageRouter}/>,
  document.getElementById("loginForm")
);