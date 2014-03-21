/**
 * @jsx React.DOM
 */

var registerRouter = Backbone.Router.extend({
	routes : {
		"error?reason=:x" : "error",
		"success" : "success"
	},
	error : function() {
		React.renderComponent(
			<validationMessage reason={arguments}/>,
			document.getElementById("message")
			);
	},
	success : function() {
		React.renderComponent(
			<successMessage/>,
			document.getElementById("message")
			);		
	}
});

var validationMessage = React.createClass({
	reasonCodes : ["","Not all fields Filled out", "User name already taken", "DB Error"],
	render : function() {
    	return <label>Error in creating user: {this.reasonCodes[this.props.reason[0]]}</label>;
	}
});

var successMessage = React.createClass({
	render : function() {
    	return <div><label>User Succesfully Created</label> <a href="/login">Login</a></div>;
	}
});

var createUserForm = React.createClass({
	 mixins: [React.addons.LinkedStateMixin],
	getInitialState : function() {
		return {
			name : "username",
			email : "username@emailaddress",
			password : "$$$$$$"
		};
	},
	handleSubmit : function() {
		var userC = new UserModel();
		userC.set("name", this.state.name)
		userC.set("password", this.state.password)
		userC.set("email", this.state.email)
		var valid = userC.check();
		
		if(valid>=0) {
			this.props.router.navigate("error?reason=" + valid, {trigger : true})
		} else {
		var self = this;			
		userC.save(null, {
			success: function (model, response) {
        		self.props.router.navigate("success", {trigger : true}) },
    		error: function (model, response) {
    			self.props.router.navigate("error?reason=" + response.responseText, {trigger : true})  
    	} });

		}
	},
  	render : function() {
    	return <div className="userEntryForm">
    			<label>User Name:</label><input type="text" valueLink={this.linkState('name')}/><br/>
    			<label>Password: </label><input type="password" valueLink={this.linkState('password')}/><br/>
    			<label>Email:</label><input type="text" valueLink={this.linkState('email')}/><br/>
    			<input type="button" value="Create User" onMouseUp={this.handleSubmit}/>
    		</div>;
  	}
});

var pageRouter = new registerRouter();
Backbone.history.start();

UserModel = Backbone.Model.extend({
	defaults :{
		name : null,
		password : null,
		email : null
	},
	url : "/users",
	check : function() {

		if(this.get("name").length==0 || this.get("password").length==0 || this.get("email").length ==0) {
			return 1;
		} else if(1==2){
			//Check user name exists
			return 2;
		}
		return -1;
	}
});

React.renderComponent(
  <createUserForm router={pageRouter}/>,
  document.getElementById("main")
);