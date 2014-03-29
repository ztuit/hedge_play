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
	 mymessages : function() {
	 	this.props.router.navigate("privatemessages", {trigger : true})
	 },
	 forum : function () {
	 	this.props.router.navigate("forum", {trigger : true})
	 },
	 myblog : function () {
	 	this.props.router.navigate("myblog", {trigger : true})
	 },
  	render : function() {
    	return <nav className="mainMenu">
    			<a href="#" onClick={this.profile}>my profile</a> | 
    			<a href="#" onClick={this.mymessages}> my messages</a> |
    			<a href="#" onClick={this.myblog}> my blog</a> |
				<a href="#" onClick={this.users}> profiles</a> | 
				<a href="#" onClick={this.portfolio}> portfolio apps</a> |
				<a href="#" onClick={this.forum}> discussion</a>	|
				<a href="#" onClick={this.about}> about</a>
    			
    		</nav>;
  	}
});

