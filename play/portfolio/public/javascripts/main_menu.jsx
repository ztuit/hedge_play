/**
 * @jsx React.DOM
 */

/**
 * Main menu component for navigating around the app
 **/
var MainRouter = Backbone.Router.extend({
	routes : {
		"profile" : "profile",
		"users" : "users",
		"portfolio" : "portfolio"
	},
	profile : function() {
		React.renderComponent(
			<profile reason={arguments}/>,
			document.getElementById("profile")
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


var mainMenu = React.createClass({
	 profile : function() {
	 	this.props.router.navigate("profile", {trigger : true})
	 },
	 users : function() {
	 	this.props.router.navigate("users", {trigger : true})
	 },
	 portfolio : function() {
	 	this.props.router.navigate("portfolio", {trigger : true})
	 },
  	render : function() {
    	return <nav className="mainMenu">
    			<a href="#" onClick={this.profile}>profile</a> | 
				<a href="#" onClick={this.users}> users</a> | 
				<a href="#" onClick={this.portfolio}> porfolio</a> |
				<a href="#" onClick={this.forums}> forums</a>	|
				<a href="#" onClick={this.about}> about</a>
    			
    		</nav>;
  	}
});

var mainRouter = new MainRouter();
Backbone.history.start();


React.renderComponent(
  <mainMenu router={mainRouter} />,
  document.getElementById("main_menu")
);