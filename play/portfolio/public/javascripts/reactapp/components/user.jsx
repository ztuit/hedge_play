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
			lo.fetch({ success : function(user){window.location = "/login"}});
		} 
			
		
		
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

		var usnps = new UserProfileModel();
		var self = this;
		usnps.set("username",this.props.id)
		usnps.fetch({ success : function(user){self.setState({username: user.get("username"), 
																fullname : user.get("fullname"), 
																description : user.get("description"),
																img : user.get("img"),
																created : user.get("created"),
																role : user.get("role")});}});
		return {
			username : null,
			role: null,
			fullname : null,
			description : null,
			created: null,
			img: null,
			updatemessage: null
		};
	},
	update : function() {
		
		var usnps = new UserProfileModel();
		usnps.set("username", this.state.username)
		usnps.set("role", this.state.role)
		usnps.set("fullname", this.state.fullname)
		usnps.set("description", this.state.description)
		usnps.set("created", this.state.createDate)
		usnps.set("img", this.state.img)

		var self = this;			
		usnps.save(null, {
			success: function (model, response) {
        		self.setState({"updatemessage":"update success"});
        	},
    		error: function (model, response) {
    		
    			self.setState({"updatemessage":"update failed"});
    	} });
	},
  	render : function() {
    	return <div className="userProfile">
    				<label>Created: </label><label>{this.linkState('created')}</label><br/>
    				<label>Username: </label><label>{this.linkState('username')}</label><br/>
    				<label>Role: </label><label>{this.linkState('role')}</label><br/>
    				<label>Name: </label><input type="text" valueLink={this.linkState('fullname')}/><br/>
    				<label>Pic:</label><img src="" alt="pic"/><br/>
    				<label>Description: </label><textarea rows="4" cols="50" valueLink={this.linkState('description')}/><br/>
    				<input type="button" value="Update" onMouseUp={this.update}/><label>{this.linkState('updatemessage')}</label><br/><br/>
    			</div>;
  	}
});

UserProfileModel = Backbone.Model.extend({
	idAttribute : 'username',
	defaults :{
		username : "",
		fullname : "",
		img: "",
		created : "",
		role : "",
		description : ""
	},
	urlRoot : "/user/profile"
});

/**
 * Components to display lists of user profiles
 **/
 var userProfileList = React.createClass({
 	getInitialState: function() {
    	return {data: []};
  	},
   	componentWillMount: function() {
	    $.ajax({
	      url: this.props.url,
	      dataType: 'json',
	      success: function(data) {

	        this.setState({data: data});
	        
	      }.bind(this),
	      error: function(xhr, status, err) {
	        console.error(this.props.url, status, err.toString());
	      }.bind(this)
	    });
  	}, 	
	render: function() {

	    var profileNodes = this.state.data.map(function (uspm) {
	      return <userProfileView profile={uspm} />;
	    });
	    return (
	      <div className="profileList">
	        {profileNodes}
	      </div>
	    );
	}
 });

 var userProfileView = React.createClass({
	requestMessageEntry : function() {
		React.renderComponent(
  			<messageSender recipient={this.props.profile.username} previous=""/>,
  			document.getElementById("content")
		);
	},
 	render: function() {
 		return (<div><br/><br/>
 				
 				<div className="userProfileView">
    				<label>Created: </label><label>{this.props.profile.created}</label><br/>
    				<label>Username: </label><label>{this.props.profile.username}</label><span/><input type="button" value="Send Message" onMouseUp={this.requestMessageEntry}/><br/>
    				<label>Name: </label><label>{this.props.profile.fullname}</label><br/>
    				<label>Role: </label><label>{this.props.profile.role}</label><br/>
    				<label>Pic:</label><img src={this.props.profile.img} alt="pic"/><br/>
    				<label>Description: </label><textarea readOnly rows="4" cols="50" value={this.props.profile.description}/><br/>
    			</div></div>);
	  }
 });