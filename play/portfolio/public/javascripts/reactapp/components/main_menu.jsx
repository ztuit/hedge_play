/**
 * @jsx React.DOM
 */

/**
 * Main menu component for navigating around the app
 **/



var mainMenu = React.createClass({
	 setNav : function(e) {
	 	$("#mi").find(".active").attr("class", "")
	 	$(e.target).parent().attr("class", "active")
	 },
	 profile : function(e) {
	 	this.setNav(e);
	 	this.props.router.navigate("profile", {trigger : true})
	 },
	 users : function(e) {
	 	this.setNav(e);
	 	
	 	this.props.router.navigate("users", {trigger : true})
	 },
	 portfolio : function(e) {
	 	this.setNav(e);
	 	this.props.router.navigate("portfolio", {trigger : true})
	 },
	 mymessages : function(e) {
	 	this.setNav(e);
	 	this.props.router.navigate("privatemessages", {trigger : true})
	 },
	 forum : function (e) {
	 	this.setNav(e);
	 	this.props.router.navigate("forum", {trigger : true})
	 },
	 myblog : function (e) {
	 	this.setNav(e);
	 	this.props.router.navigate("myblog", {trigger : true})
	 },
	 about : function(e) {
	 	this.setNav(e);
	 	this.props.router.navigate("about", {trigger : true})
	 },
  	render : function() {
    	return <nav className="main_menu navbar navbar-inverse navbar-default navbar-fixed-top" role="navigation">
			    			<div className="container-fluid">
				    			<ul id="mi" className="nav navbar-nav">
					    			<li id="profile" ><a href="#" onClick={this.profile}>my profile</a></li>
					    			<li><a href="#" onClick={this.mymessages}> my messages</a></li>
					    			<li id="blog" ><a href="#" onClick={this.myblog}> my blog</a></li>
									<li id="profiles" ><a href="#" onClick={this.users}> profiles</a></li>
									<li id="toybox" ><a href="#" onClick={this.portfolio}> experiments</a></li>
									<li id="discussion" ><a href="#" onClick={this.forum}> discussion</a></li>
									<li id="about" ><a href="#" onClick={this.about}> about</a></li>
				    			</ul>
				    			<ul className="nav navbar-nav navbar-right">
			        				<li >{this.props.user}</li>
			        			</ul>
			    			</div>
	    		</nav>;
  	}
});

