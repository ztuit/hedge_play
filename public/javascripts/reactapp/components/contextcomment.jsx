/**
 * @jsx React.DOM
 */


var contextCommentViewer = React.createClass({
 	getInitialState: function() {
    	return {data: []};
  	},
   	componentWillMount: function() {
	    $.ajax({
	      url: "/contextcomment/" + this.props.contextBucket + "/" + this.props.contextKey,
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

	    var commentNodes = this.state.data.map(function (comment) {
	    	
	      return <contextComment entry={comment} />;
	    });
	    return (<div className="contextCommentViewer">
	    			<div>
	    				<contextCommentEditor contextBucket={this.props.contextBucket} contextKey={this.props.contextKey} view={this}/>
	    				<br/><br/>
	    			</div>
			   		<div>
			   			{commentNodes}
			      	</div>
			    </div>
	    );
	}
 });


var contextCommentEditor = React.createClass({ 
 mixins: [React.addons.LinkedStateMixin],
 	 getInitialState: function() {
    	return {content: "comment here", 
    			info : ""};
  	},
 	save : function () {
 		var self = this;
 		var blog = new ContextCommentModel();
 		if(this.state.key)
			blog.set("key",this.state.key)

		blog.set("content", this.state.content)
		blog.set("contextKey", this.props.contextKey)
		blog.set("contextBucket", this.props.contextBucket)	
		
		blog.save(null, {
			success: function (model, response) {
        		self.setState({info:"comment entry saved"});
        		self.props.view.componentWillMount();
        	},
    		error: function (model, response) {
    			self.setState({info:"comment entry save failed"});
    	} });
 	},
 	render: function() {
 		return (
 				
 				<div className="contextCommentEditor">
 					<span className="label label-primary" >Comment on this:</span><br/>
    				<textarea className="form-control" valueLink={this.linkState('content')}></textarea><br/>    				
  					<button className="btn btn-success" onMouseUp={this.save}>Post Comment</button><label>{this.linkState('info')}</label>
    			</div>);
	  }
});


 var contextComment = React.createClass({
 	render: function() {
 		var commenterThumbUrl = "/user/photothumb/" + this.props.entry.author
 		return (
 				
 				<div >
    				<label>{this.props.entry.author}</label><img src={commenterThumbUrl} alt="*"/>
    				<div className="bubble me">{this.props.entry.content}</div><label>{this.props.entry.created}</label><br/>    				  			
    			</div>);
	  }
 });

 ContextCommentModel = Backbone.Model.extend({
 	idAttribute : "key",
	defaults :{
		
		content : "",
		author : "",
		contextKey : "",
		contextBucket : ""
	},
	urlRoot : "/contextcomment"
});