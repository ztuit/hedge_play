/**
 * @jsx React.DOM
 */
var MainRouter = Backbone.Router.extend({
	routes : {
		"profile" : "profile",
		"users" : "users",
		"portfolio" : "portfolio",
		"privatemessages" : "privatemessages",
		"forum" : "forum",
		"myblog" : "myblog"
	},
	profile : function() {
		React.renderComponent(
			<userProfile id={userSS.state.name}/>,
			document.getElementById("content")
			);
	},
	users : function() {
		React.renderComponent(
			<userProfileList url="/user/profiles"/>,
			document.getElementById("content")
			);		
	},
	portfolio : function() {
		React.renderComponent(
			<portfolio/>,
			document.getElementById("content")
			);	
	},
	privatemessages : function() {
		document.getElementById("content").innerHTML=""
		React.renderComponent(
			<messageViewer url="/messages/private" allowSend="none" />,
			document.getElementById("content")
			);
	},
	forum : function() {
		
		document.getElementById("content").innerHTML=""

		React.renderComponent(
			<messageViewer url="/messages/public" allowSend="true" />,
			document.getElementById("content")
			);
	},
	myblog : function() {
		document.getElementById("content").innerHTML=""

		React.renderComponent(
			<blogEntries url="/blog" />,
			document.getElementById("content")
			);
	}
});

var mainRouter = new MainRouter();
Backbone.history.start();

var userSS = new userSnapshot();

React.renderComponent(
  userSS,
  document.getElementById("user_details")
);

React.renderComponent(
  <mainMenu router={mainRouter} />,
  document.getElementById("main_menu")
);