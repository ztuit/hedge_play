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
		"sendMessage/:user" : "sendMessage",
		"about" : "about"
	},
	about : function () {
				document.getElementById("content").innerHTML=""
		React.renderComponent(
			<aboutComponent />,
			document.getElementById("content")
			);
	},
	profile : function() {
		document.getElementById("content").innerHTML=""
		React.renderComponent(
			<userProfile id={userSS.state.name} router={this} />,
			document.getElementById("content")
			);
	},
	users : function() {
		document.getElementById("content").innerHTML=""
		React.renderComponent(
			<userProfileList url="/user/profiles" router={this}/>,
			document.getElementById("content")
			);		
	},
	portfolio : function() {
		document.getElementById("content").innerHTML=""
		React.renderComponent(
			<sandBox/>,
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
			<blogEntries url={urlu} blogger={user} readOnly="true"/>,
			document.getElementById("content")
			);
	}
});

var mainRouter = new MainRouter();
Backbone.history.start();

var userSS = new userSnapshot();

//React.renderComponent(
 // userSS,
 // document.getElementById("user_details")
//);

React.renderComponent(
  <headerItems user={userSS}/>,
  document.getElementById("header_items")
);

React.renderComponent(
  <mainMenu router={mainRouter} />,
  document.getElementById("main_menu")
);

React.renderComponent(
  <footerItems/>,
  document.getElementById("pageFooter")
);

mainRouter.navigate("about", {trigger : true})
