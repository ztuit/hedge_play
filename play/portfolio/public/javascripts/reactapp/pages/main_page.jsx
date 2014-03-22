/**
 * @jsx React.DOM
 */
var MainRouter = Backbone.Router.extend({
	routes : {
		"profile" : "profile",
		"users" : "users",
		"portfolio" : "portfolio"
	},
	profile : function() {
		React.renderComponent(
			<userProfile/>,
			document.getElementById("content")
			);
	},
	users : function() {
		React.renderComponent(
			<usersView/>,
			document.getElementById("userlist")
			);		
	},
	portfolio : function() {
		React.renderComponent(
			<portfolio/>,
			document.getElementById("portfolio")
			);		
	}
});

var mainRouter = new MainRouter();
Backbone.history.start();

React.renderComponent(
  <userSnapshot />,
  document.getElementById("user_details")
);

React.renderComponent(
  <mainMenu router={mainRouter} />,
  document.getElementById("main_menu")
);