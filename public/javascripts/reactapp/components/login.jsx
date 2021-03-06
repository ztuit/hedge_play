/**
 * @jsx React.DOM
 */



var validationMessage = React.createClass({
	reasonCodes : ["Unknown Username/password","Missing username/password", "System Unavailable", "Login Successful", "Unknown request format"],
	render : function() {
    	return <label className="alert alert-success">Has occurred this: {this.reasonCodes[this.props.reason[0]]} </label>;
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
			alert('inavoue in')
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
  		return  <div id="mainheader" className="mainheader" role="header">
					<div className="container-fluid">
						<div className="row">
							<div className="col-sm-4">
								<a  href="#"><img id="hedge_logo" className="headerBrand" src="assets/images/hedge_logo.png" alt="HTS"/></a>
				        	</div>
						    <div className="col-sm-1 col-sm-offset-5 col-md-1 col-md-offset-4">
					    		<a href="http://basho.com/"><img id="riak_logo" src="assets/images/riakpowered.jpeg" alt="riak"/></a>
					    	</div>
					    	<div className="col-sm-1 col-sm-offset-1 col-md-1 col-md-offset-1">
					    		<a href="http://www.playframework.com/"><img id="riak_logo" height="42" width="80" src="assets/images/play.png" alt="riak"/></a>
					    	</div>
				        	<div className="col-sm-5 col-sm-offset-2 col-md-offset-5 col-md-2">
					    		<div className="navbar-form navbar-right" >
						            <div className="form-group">
						              <input type="text" placeholder="username" className="form-control" valueLink={this.linkState('name')}/>
						            </div>
						            <div  className="form-group">
						              <input type="password" placeholder="Password" className="form-control" valueLink={this.linkState('password')}/>
						            </div>
						            <button  className="btn btn-success" onMouseUp={this.handleSubmit}>Sign in as {this.linkState('name')}</button>
			    					<button className="btn btn-info" onMouseUp={this.passwordReminder}>Email Password Reminder For User: {this.linkState('name')}</button>
			    					<br/><label>New User?  </label><a href="/register">Register Here</a><br/><br/>
						        </div>    
					    	</div>

					    </div>

					</div>
			    </div>;

  	}
});




UserModel = Backbone.Model.extend({
	idAttribute : "name",
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






