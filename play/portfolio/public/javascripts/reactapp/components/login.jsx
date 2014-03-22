/**
 * @jsx React.DOM
 */



var validationMessage = React.createClass({
	reasonCodes : ["Unknown Username/password","Missing username/password", "System Unavailable", "Login Successful", "Unknown request format"],
	render : function() {
    	return <label>Has occurred this: {this.reasonCodes[this.props.reason[0]]} </label>;
	}
});

var loginForm = React.createClass({
	 mixins: [React.addons.LinkedStateMixin],
	getInitialState : function() {
		return {
			name : "guest",
			password : "guest",
			reminderName : "username"
		};
	},

	handleSubmit : function() {
		var userC = new UserModel();
		userC.set("name", this.state.name)
		userC.set("password", this.state.password)
		var valid = userC.check();
		
		if(valid>=0) {
			this.props.router.navigate("validation?reason=" + valid, {trigger : true})
		} else {
			var self = this;			
			userC.save(null, {
				success: function (model, response) {
        			self.props.router.navigate("validation?reason=3", {trigger : true})
        			 window.location = "/main"
        		},
    			error: function (model, response) {
    				self.props.router.navigate("validation?reason=" + response.responseText, {trigger : true})  
    		} });
		}
	},
  	render : function() {
    	return <div className="userEntryForm">
    			<label>User Name:</label><input type="text" valueLink={this.linkState('name')}/><br/>
    			<label>Password: </label><input type="password" valueLink={this.linkState('password')}/><br/>
    			<input type="button" value="Login as" onMouseUp={this.handleSubmit}/><label>{this.linkState('name')}</label><br/>
    			<input type="button" value="Email Password Reminder For User:" onMouseUp={this.passwordReminder}/><label>{this.linkState('name')}</label><br/>
    			<br/><label>New User?  </label><a href="/register">Register Here</a><br/>
    			<br/>
    		</div>;
  	}
});




UserModel = Backbone.Model.extend({
	defaults :{
		name : null,
		password : null,
		email : ""
	},
	url : "/login",
	check : function() {

		if(this.get("name").length==0 || this.get("password").length ==0) {
			return 1;
		} 
		return -1;
	}
});






