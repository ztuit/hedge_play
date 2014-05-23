/**
 * @jsx React.DOM
 */



var validationMessage = React.createClass({
	reasonCodes : ["","Not all fields Filled out", "User name already taken", "DB Error"],
	render : function() {
    	return <label>Error in creating user: {this.reasonCodes[this.props.reason[0]]}</label>;
	}
});

var successMessage = React.createClass({
	render : function() {
    	return <div><label>User Succesfully Created</label></div>;
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
        		self.props.router.navigate("success", {trigger : true}) 
        	},
    		error: function (model, response) {
    			self.props.router.navigate("error?reason=" + response.responseText, {trigger : true})  
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
				        	<div className="col-sm-1 col-sm-offset-5 col-md-1 col-md-offset-6">
					    		 <form className="navbar-form navbar-right" role="form">
			            			<div className="form-group">
			              				<input type="text" placeholder="username" className="form-control" valueLink={this.linkState('name')}/>
			            			</div><br/>
			            			<div  className="form-group">
			              				<input type="password" placeholder="Password" className="form-control" valueLink={this.linkState('password')}/>
			            			</div><br/>
			            			<div  className="form-group">
			              				<input type="email" placeholder="email address" className="form-control" valueLink={this.linkState('email')}/>
			            			</div><br/>			          
			            			<button  className="btn btn-success" onMouseUp={this.handleSubmit}>Create User {this.linkState('name')}</button>
			            			<br/>
									<label>Already have an account?</label> <a href="/login">Login Here</a>
			          			</form>
					    	</div>
					    </div>

					</div>
			    </div>;

  		// return <div className="navbar navbar-default navbar-fixed-top" role="navigation">
			 //      <div className="container">
			 //        <div className="navbar-header">
			 //          <button type="button" className="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			 //            <span className="sr-only">Toggle navigation</span>
			 //            <span className="icon-bar"></span>
			 //            <span className="icon-bar"></span>
			 //            <span className="icon-bar"></span>
			 //          </button>
			 //          <a className="navbar-brand" href="#"><img id="hedge_logo" src="assets/images/hedge_logo.jpeg" alt="HTS"/></a>
			 //        </div>
			 //        <div className="navbar-collapse collapse">
			 //          <form className="navbar-form navbar-right" role="form">
			 //            <div className="form-group">
			 //              <input type="text" placeholder="username" className="form-control" valueLink={this.linkState('name')}/>
			 //            </div><br/>
			 //            <div  className="form-group">
			 //              <input type="password" placeholder="Password" className="form-control" valueLink={this.linkState('password')}/>
			 //            </div><br/>
			 //            <div  className="form-group">
			 //              <input type="email" placeholder="email address" className="form-control" valueLink={this.linkState('email')}/>
			 //            </div><br/>			          
			 //            <button  className="btn btn-success" onMouseUp={this.handleSubmit}>Create User {this.linkState('name')}</button>
			 //            <br/>
				// 		<label>Already have an account?</label> <a href="/login">Login Here</a>
			 //          </form>
			 //        </div>
			 //    </div>
			 //  </div>;
    	
  	}
});




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

