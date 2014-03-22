/**
 * @jsx React.DOM
 */


/**
 * A component to displat the currently logged in user
 **/
var userSnapshot = React.createClass({
	 mixins: [React.addons.LinkedStateMixin],
	getInitialState : function() {
		var usnps = new UserSnapshotModel({id: 'abcd1234'});
		var self = this;
		usnps.fetch({ success : function(user){self.setState({name: user.get("user"), time : user.get("time")});}});
		
		return {
			name : usnps.get("user"),
			time : usnps.get("time")
		};
	},
	logout : function() {
		if(this.state.name) {
			var lo  = new LogoutModel()
			lo.set("id", this.state.name)
			lo.fetch({ success : function(user){}});
		} 
			window.location = "/login"
		
		
	},
  	render : function() {
    	return <div className="userSnapshot">
    			<label>Logged in as: </label><label>{this.linkState('name')}</label><label> Since: </label><label>{this.linkState('time')} </label><a href="#" onClick={this.logout}>logout</a>
    		</div>;
  	}
});

UserSnapshotModel = Backbone.Model.extend({
	defaults :{
		id : null,
		user : null,
		time : null
	},
	urlRoot : "/connected"
});


LogoutModel = Backbone.Model.extend({
	defaults :{
		id : null,
		name : null
	},
	urlRoot : "/logout"
});

/**
 * A component to display and edit the user profile.
 */
var userProfile = React.createClass({
	mixins: [React.addons.LinkedStateMixin],
	getInitialState : function() {
		return {
			username : null,
			fullname : null,
			description : null,
			createDate: null
		};
	},
  	render : function() {
    	return <div className="userProfile">
    				<label>Username: </label><label>{this.linkState('username')}</label><br/>
    				<label>Name: </label><input type="text" valueLink={this.linkState('fullname')}/><br/>
    				<label>Description: </label><textarea rows="4" cols="50">{this.linkState('description')}</textarea><br/>
    				<label>Created: </label><input type="text" valueLink={this.linkState('createDate')}/><br/>
    				<label>Friends: </label><ul></ul>
    				<label>Messages:</label><ul></ul>
    			</div>;
  	}
});