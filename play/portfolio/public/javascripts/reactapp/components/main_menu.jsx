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
	 about : function() {
	 	this.props.router.navigate("about", {trigger : true})
	 },
  	render : function() {
    	return <div className="navbar navbar-inverse">
    			<nav className="navbar-collapse collapse">
	    			<ul className="nav navbar-nav">
		    			<li><a href="#" onClick={this.profile}>my profile</a></li><li><a href="#" onClick={this.mymessages}> my messages</a></li>
		    			<li><a href="#" onClick={this.myblog}> my blog</a></li>
						<li><a href="#" onClick={this.users}> profiles</a></li>
						<li><a href="#" onClick={this.portfolio}> sandbox apps</a></li>
						<li><a href="#" onClick={this.forum}> discussion</a></li>
						<li><a href="#" onClick={this.about}> about</a></li>
	    			</ul>
    			</nav>
    		</div>;
  	}
});

