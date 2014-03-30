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
		"myblog" : "myblog",
		"viewBlog/:user" : "viewBlog",
		"sendMessage/:user" : "sendMessage"
	},
	profile : function() {
		React.renderComponent(
			<userProfile id={userSS.state.name} />,
			document.getElementById("content")
			);
	},
	users : function() {
		React.renderComponent(
			<userProfileList url="/user/profiles" router={this}/>,
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
			<blogEntries url="/blog" readOnly="false"/>,
			document.getElementById("content")
			);
	},
	sendMessage : function(user) {
		document.getElementById("content").innerHTML=""
		React.renderComponent(
  			<messageSender recipient={user} previous="" url="/messages/private"/>,
  			document.getElementById("content")
		);
	},
	viewBlog : function(user) {
				
		document.getElementById("content").innerHTML=""
		var urlu = "/blog/" + user
		React.renderComponent(
			<blogEntries url={urlu} readOnly="true"/>,
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