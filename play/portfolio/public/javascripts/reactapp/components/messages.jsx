/**
 * @jsx React.DOM
 */

/**
 * Component for viewing message threads
 **/
var messageViewer = React.createClass({
	 getInitialState: function() {
    	return {threads: [[]]};
  	},
    componentWillMount: function() {
    	var self = this;

	    $.ajax({
	      url: self.props.url,
	      dataType: 'json',
	      success: function(data) {
	        self.setState({threads: data});
	        
	      }.bind(this),
	      error: function(xhr, status, err) {
	        console.error(this.props.url, status, err.toString());
	      }.bind(this)
	    });
  	}, 	
	render : function() {
    var self = this;

		var mThread = this.state.threads.map(function (thread) {
			if(thread.length>0) {
	      		return <messageThread entries={thread} url={self.props.url}/>;
	      	} {
	      		return <div></div>;
	      	}
	    });
    	return <div className="messageViewer">
            <div style={{display: this.props.allowSend}}><label>New message thread</label><br/>
            <messageSender url={this.props.url} previous="" /><br/></div>
    				<label>Current Message Threads</label><br/><br/>
    				{mThread}
    			</div>
	}
});

var messageThread = React.createClass({

	reply : function () {
		var sub = "re:" + this.props.entries[this.props.entries.length-1].subject
	
		React.renderComponent(
  			<messageSender url={this.props.url} recipient={this.props.entries[this.props.entries.length-1].sender} subject={sub} previous={this.props.entries[this.props.entries.length-1].key}/>,
  			document.getElementById("content")
		);
	},
	render : function() {
		
	    var messageEntries = this.props.entries.map(function (messageEnt) {
	      return <messageEntry entry={messageEnt} />;
	    });
	   //messageEntries[messageEntries.length-1].props.subject})
    	return <div className="messageThread">
    				{messageEntries}
    	    		<input type="button" value="reply" onMouseUp={this.reply} />
    			</div>;

	}
});

var messageEntry = React.createClass({

	render : function() {
    	return 	<div className="messageEntry">
    	 			<label>To:</label><label>{this.props.entry.recipient}</label><br/>
    				<label>From: {this.props.entry.sender}</label><br/>
    				<label>From: {this.props.entry.sent}</label><br/>
    				<label>Subject:</label><label>{this.props.entry.subject} </label><br/>
    				<textarea value={this.props.entry.content} readOnly/><br/>
    			</div>;
	}
});

/**
 * Component for sending messages to other uses
 **/
 var messageSender = React.createClass({
 	mixins: [React.addons.LinkedStateMixin],
	getInitialState: function() {
    	return {
    			key : "",
    			subject : this.props.subject,
    			recipient : this.props.recipient,
    			sender : "",
    			content : "",
    			sent : "",
    			previous : this.props.previous
    			};
  	},
  	sendMessage : function () {
  		var message = new messageModel();
  		message.set("key", "");
  		message.set("previous", this.state.previous);
  		message.set("recipient", this.props.recipient);
  		message.set("subject", this.state.subject);
    	message.set("content", this.state.content);
      message.url = this.props.url;
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
 		return <div className="messageSender" >
 				<label>Recipient:</label><label>{this.props.recipient}</label><br/>
 				<label>Subject:</label><input type="text" valueLink={this.linkState('subject')} /><br/>
 				<textarea valueLink={this.linkState('content')} /><br/>
 				<input type="button" value="send" onMouseUp={this.sendMessage}/><label>{this.state.info}</label>
 			</div>;
 	}
 });

 messageModel = Backbone.Model.extend({
 	defaults : {
   			subject : "",
    			recipient : "",
    			sender : "",
    			content : "",
    			sent : "",
    			previous : ""
 	},
 	url : "/messages/private"
 });