/**
 * @jsx React.DOM
 */


var blogEntries = React.createClass({
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
		if(this.props.readOnly=="false") {
		    var blogNodes = this.state.data.map(function (blogEnt) {
		    	
		      return <blogEditor entry={blogEnt} router={self.props.router}/>;
		    });
		    return (<div className="blogList">
		    			<div>
		    				<blogEditor entry={{key:"", content:"", created:"", edited:""}} router={self.props.router}/>
		    				<br/><br/>
		    			</div>
				   		<div>
				   			{blogNodes}
				      	</div>
				    </div>
		    );
		} else {
			var self = this;
			var blogNodes = this.state.data.map(function (blogEnt) {
		    	
		      return <blogViewer entry={blogEnt} blogger={self.props.blogger}/>;
		    });
			return (<div className="blogList">
				   		<div>
				   			{blogNodes}
				      	</div>
				    </div>
		    );
		}
	}
 });

 var blogEditor = React.createClass({
 	 mixins: [React.addons.LinkedStateMixin],
 	 componentDidMount : function() {

 	 	 	 		 	 		tinyMCE.init({
        			mode : "exact",
        			elements : this.state.id,
        			theme : "modern"

                       
				});
				
 	 },
 	 getInitialState: function() {

 	 	var sid = new Date().getTime().toString();
 	 	
 	 	//tinyMCE.get("blogEditorText").setContent(c);
    	return {content: this.props.entry.content, 
    			info:"",
    			id : sid,
    			edited : this.props.entry.edited,
    			show : true};
  	},
 	deleteEntry : function() {
 		var blog = new BlogModel();
 		
 		if(this.props.entry.key) {
 			
			blog.set("key",this.props.entry.key)
		}
		var self = this;
		blog.destroy({
			success: function (model) {

				tinyMCE.get(self.state.id).setContent("this blog entry has been deleted")
        		self.setState({info:"blog entry deleted", edited : model.get("edited"), content : "this blog has been deleted."});
  	
        		
        	},
    		error: function (model, response) {
    			var msg = "blog entry save failed, reason: " + response.responseText
    			self.setState({info:msg});
    	} });
 
    	
 	},
 	save : function () {
 		
 		var self = this;
 		var blog = new BlogModel();
 		if(this.props.entry.key) {
 		
			blog.set("key",this.props.entry.key)
		}

		blog.set("content", tinyMCE.get(this.state.id).getContent())
		blog.set("created", this.props.entry.created)
		blog.set("edited", this.state.edited)	
		
		blog.save(null, {
			success: function (model, response) {

        		self.setState({info:"blog entry saved", edited : model.get("edited")});
        		self.props.router.navigate("myblog", {trigger : true}) 
        	},
    		error: function (model, response) {
    			var msg = "blog entry save failed, reason: " + response.responseText
    			self.setState({info:msg});
    	} });

 	},
 	subrender : function() {
 		if(this.props.entry.edited.length!=0)
 			return (<div><label>Edit Blog Entry</label><br/><label>Created:</label><label>{this.props.entry.created}</label><span>    </span><label>Last Edited:</label><label>{this.linkState('edited')}</label></div>);
 		else
 			return <div><label>Create New Blog Entry</label><br/></div>;
 	},
 	render: function() {

 		var newEntry = this.subrender();

 		return (
 				
 				<div className="blogEditor" style={{display: this.linkState('show')}}>
 					
 					{newEntry}
    				<textarea id={this.state.id} valueLink={this.linkState('content')} ></textarea><br/>    				
    				<input type="button" value="Save/Update" onMouseUp={this.save}/><input type="button" value="Delete" onMouseUp={this.deleteEntry}/>
    				<label>{this.linkState('info')}</label>
    			</div>);
	  }
 });


var blogViewer = React.createClass({
 	 mixins: [React.addons.LinkedStateMixin],
 	 getInitialState: function() {
 	 	var c = unescape(this.props.entry.content)
 	 	
    	return {content: c, 
    			info:""};
  	},
 	
 	render: function() {
 		var bloggerthumburl="/user/photothumb/" + this.props.blogger
 		return (
 				
 				<div className="blogEntry">
 					<label>Blogger:</label><label>{this.props.blogger}</label><img src={bloggerthumburl}/><br/>
    				<label>Created: </label><label>{this.props.entry.created}</label><br/>
    				<label>Last Edited: </label><label>{this.props.entry.edited}</label><br/>
    				<div dangerouslySetInnerHTML={{__html: this.state.content}} ></div><br/><br/>
    				<contextCommentViewer contextBucket="Blog" contextKey={this.props.entry.key} />    				
    			</div>);
	  }
 });

 BlogModel = Backbone.Model.extend({
 	idAttribute : "key",
	defaults :{
		content : "",
		created : "",
		edited : ""
	},
	urlRoot : "/blog"
});