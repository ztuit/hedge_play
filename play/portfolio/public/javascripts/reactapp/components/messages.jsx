/**
 * @jsx React.DOM
 */

/**
 * Component for viewing message threads
 **/
var messageViewer = React.createClass({
	 getInitialState: function() {
    	return {threads: []};
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
	render : function() {
		var messageThread = this.state.threads.map(function (thread) {
	      return <messageThread entry={thread} />;
	    });
    	return <div class="messageViewer">
    				<label>Current Message Threads</label><br/><br/>
    				{messageThread}
    			</div>
	}
});

var messageThread = React.createClass({
	
	render : function() {
	    var messageEntries = this.state.thread.entries.map(function (messageEntry) {
	      return <messageEntry entry={messageEntry} />;
	    });
    	return 	<div class="messageThread">
    				<label>Contributors: {this.state.thread.contributors}</label>
    				<label>Subject:{this.state.thread.subject}</label>
    				{messageEntries}
    			</div>
	}
});

var messageEntry = React.createClass({
	render : function() {

    	return 	<div class="messageEntry">
    				<label>By: {this.state.entry.contributor}</label>
    				<label>Content:</label>
    				<textarea value={this.state.entry.subject} readOnly/>
    			</div>
	}
});

/**
 * Component for sending messages to other uses
 **/
 var messageSender = React.createClass({
	getInitialState: function() {
    	return {
    			subject : null,
    			recipent : [],
    			from : null,
    			content : null,
    			into : null
    			};
  	},
  	sendMessage : function () {
  		var message = new messageModel();
  		message.set("recipient", this.props.recipient);
  		message.set("subject", this.state.subject);
  		message.set("content", this.state.content)
  		var self = this;
  		message.save(null, {
			success: function (model, response) {
        		self.setState({info:"message sent"});
        	},
    		error: function (model, response) {
    			self.setState({info:"message send failed"});
    	} });
  	},
 	render : function() {
 		return <div class="messageSender">
 				<label>Recipient:</label><label>{this.props.recipient}</label><br/>
 				<label>Subject:</label><input type="text" valueLink={this.state.subject}/><br/>
 				<textarea valueLink={this.state.content} /><br/>
 				<input type="button" value="send" onMouseUp={this.sendMessage}/><label>{this.state.info}</label>
 			</div>;
 	}
 });

 messageModel = Backbone.Model.extend({
 	defaults : {
 		recipient : null,
 		subject : null,
 		content : null
 	},
 	url : "/messages/private"
 });