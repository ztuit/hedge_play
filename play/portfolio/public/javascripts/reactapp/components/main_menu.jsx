/**
 * @jsx React.DOM
 */

/**
 * Main menu component for navigating around the app
 **/



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
				<a href="#" onClick={this.portfolio}> portfolio apps</a> |
				<a href="#" onClick={this.forums}> comments</a>	|
				<a href="#" onClick={this.about}> about</a>
    			
    		</nav>;
  	}
});

