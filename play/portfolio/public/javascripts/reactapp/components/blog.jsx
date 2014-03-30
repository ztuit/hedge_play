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
		
		if(this.props.readOnly=="false") {
		    var blogNodes = this.state.data.map(function (blogEnt) {
		    	
		      return <blogEditor entry={blogEnt} />;
		    });
		    return (<div className="blogList">
		    			<div>
		    				<label>New Blog Entry</label>
		    				<blogEditor entry={{key:"", content:"", created:"", edited:""}}/>
		    				<br/><br/>
		    			</div>
				   		<div>
				   			{blogNodes}
				      	</div>
				    </div>
		    );
		} else {
			 var blogNodes = this.state.data.map(function (blogEnt) {
		    	
		      return <blogViewer entry={blogEnt} />;
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
 	 getInitialState: function() {
    	return {content: this.props.entry.content, 
    			info:""};
  	},
 	deleteEntry : function() {

 	},
 	save : function () {
 		var self = this;
 		var blog = new BlogModel();
 		if(this.state.key)
			blog.set("key",this.state.key)

		blog.set("content", this.state.content)
		blog.set("created", this.props.entry.created)
		blog.set("edited", this.props.entry.edited)	
		
		blog.save(null, {
			success: function (model, response) {
        		self.setState({info:"blog entry saved"});
        	},
    		error: function (model, response) {
    			self.setState({info:"blog entry save failed"});
    	} });
 	},
 	render: function() {
 		return (
 				
 				<div className="blogEditor">
    				<label>Created: </label><label>{this.props.entry.created}</label><br/>
    				<label>Last Edited: </label><label>{this.props.entry.edited}</label><br/>
    				<textarea valueLink={this.linkState('content')} ></textarea><br/>    				
    				<input type="button" value="Save/Update" onMouseUp={this.save}/><input type="button" value="Delete" onMouseUp={this.deleteEntry}/>
    				<label>{this.linkState('info')}</label>
    			</div>);
	  }
 });


var blogViewer = React.createClass({
 	 mixins: [React.addons.LinkedStateMixin],
 	 getInitialState: function() {
    	return {content: this.props.entry.content, 
    			info:""};
  	},
 	
 	render: function() {
 		return (
 				
 				<div className="blogEntry">
    				<label>Created: </label><label>{this.props.entry.created}</label><br/>
    				<label>Last Edited: </label><label>{this.props.entry.edited}</label><br/>
    				<textarea valueLink={this.linkState('content')} ></textarea><br/><br/>
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