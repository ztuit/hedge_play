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
		usnps.fetch({ success : function(user){self.setState({name: user.get("user"), time : user.get("time"), role : user.get("role")});}});
		
		return {
			name : usnps.get("user"),
			time : usnps.get("time"),
			role : usnps.get("role")
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
    	return <ol className="breadcrumb">
    			<li>{this.linkState('name')}</li>
    			<li><img className="img-thumbnail" src="/user/photothumb" alt="*"/></li>
    			<li> Since: {this.linkState('time')} </li>
    			<li><a href="#" onClick={this.logout}>logout</a></li>
    			</ol>;
  	}
});

UserSnapshotModel = Backbone.Model.extend({
	defaults :{
		id : null,
		user : null,
		time : null,
		role : null
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
	componentDidMount : function() {

 	 	 	 		 	 		tinyMCE.init({
        			mode : "exact",
        			elements : "profiledesc",
        			 theme : "modern"

                       
				});

 	 	 	 		 	 		
 	},
 	componentWillMount: function() {
    	var self = this;

	    $.ajax({
	      url: "/user/photo",
	      dataType: 'text',
	      success: function(data) {
	       
	      }.bind(this),
	      error: function(xhr, status, err) {
	        console.error(this.props.url, status, err.toString());
	      }.bind(this)
	    });
  	}, 	
	getInitialState : function() {

		var usnps = new UserProfileModel();
		var self = this;
		usnps.set("username",this.props.id)
		usnps.fetch({ success : function(user){
			self.setState({username: user.get("username"), 
																fullname : user.get("fullname"), 
																description : user.get("description"),
																img : user.get("img"),
																created : user.get("created"),
																role : user.get("role")});

		}});
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
		usnps.set("description", tinyMCE.get("profiledesc").getContent())
		usnps.set("created", this.state.created)
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
	uploadFile : function() {
		var formData = new FormData();
		formData.append("userfile", document.getElementById("fileUplad").files[0])
		var request = new XMLHttpRequest();
		request.open("POST", "/user/photo");
		request.send(formData);
		this.props.router.navigate("profile", {trigger : true}) 
	},
  	render : function() {
    	return <div className="userProfile">
    				<div className="col-sm-20 col-md-20 col-lg-20">
    					<div className="panel panel-primary body-padding">
    						<div className="panel-heading header-padding">
    							<h3 className="panel-title">{this.linkState('username')} edit profile</h3>
  							</div>
	  						<div className="panel-body">
			    				<span className="label label-info">Registered on: {this.linkState('created')}</span><br/>
			    				<span className="label label-info">Role: {this.linkState('role')}</span><br/>
			    				<form role="form">
				    				<div className="row">
				    					<label className="col-xs-1" for="inputName">Name: </label>
				    					<div className="col-md-4">
				    						<input id="inputName" type="text" className="form-control" valueLink={this.linkState('fullname')}/><br/>
				    					</div>
				    				</div>
			    				</form>
			    				<form role="form">
				    				<div className="row">
				    					<label className="col-xs-1" for="inputEmail">Email: </label>
				    					<div className="col-md-4">
				    						<input id="inputEmail" type="text" className="form-control" valueLink={this.linkState('email')}/><br/>
				    					</div>
				    				</div>
			    				</form>
			    				<img className="thumbnail" src="/user/photo" alt="pic"/>
			    				<div className="btn-group">
 									<input type="file" id="fileUplad" className="btn btn-success"/>
  									<button type="button" onMouseUp={this.uploadFile} className="btn btn-success">Upload Picture</button>
								</div>
			    				<br/><br/>
			    				<label>About {this.linkState('username')} :</label><br/><textarea id="profiledesc" valueLink={this.linkState('description')}/><br/>
			    				<input className="btn btn-success" type="button" value="Update" onMouseUp={this.update}/><label>{this.linkState('updatemessage')}</label><br/><br/>
    						</div>
    					</div>
    				</div>
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
		var self = this;
	    var profileNodes = this.state.data.map(function (uspm) {
	      return <userProfileView profile={uspm} router={self.props.router}/>;
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

		this.props.router.navigate("sendMessage/" + this.props.profile.username, {trigger : true}) 
	},
	viewBlog : function() {
			this.props.router.navigate("viewBlog/" + this.props.profile.username, {trigger : true}) 
	},
 	render: function() {
 		var imgsrc = "/user/photo/" + this.props.profile.username
 		return <div className="userProfile">
    				<div >
    					<div className="panel panel-primary body-padding">
    						<div className="panel-heading header-padding">
    							<h3 className="panel-title">{this.props.profile.username}  profile</h3>
    							<p/>
    							<input type="button" className="btn btn-success" value="Send Message" onMouseUp={this.requestMessageEntry}/>
    							<input type="button" className="btn btn-success" value="View blog" onMouseUp={this.viewBlog}/>
  							</div>
	  						<div className="panel-body">
			    				<span className="label label-info">Name: {this.props.profile.username}</span><br/>
			    				<span className="label label-info">Role: {this.props.profile.role}</span><br/>
			    				
			    				<img className="thumbnail" src={imgsrc} alt="pic"/>
			    				<label>About: </label><br/><div dangerouslySetInnerHTML={{__html: this.props.profile.description}}/>
			    				
    						</div>
    					</div>
    				</div>
    			</div>;

 		
	  }
 });