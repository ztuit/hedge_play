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
    	return <div className="messageViewer panel panel-primary body-padding">
            <div style={{display: this.props.allowSend}}><label>New message thread</label><br/>
            <messageSender url={this.props.url} previous="" /><br/></div>
    				<label>Current Message Threads</label><br/><br/>
    				{mThread}<br/>
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
		
      var subject = "";
      var recipient = ""
	    var messageEntries = this.props.entries.map(function (messageEnt) {
        subject = messageEnt.subject;
        recipient = messageEnt.recipient;
	      return <messageEntry entry={messageEnt} />;
	    });

    	return <div className="messagethread">
                <label id="subject">To {recipient} : {subject}</label>
    				    {messageEntries}
    	    		 <button  className="btn btn-success"  onMouseUp={this.reply} >Reply</button>
               <br/><br/>
    			</div>;

	}
});

var messageEntry = React.createClass({

	render : function() {

      var receiveurl = "/user/photothumb/" + this.props.entry.recipient;
      var senderurl = "/user/photothumb/" + this.props.entry.sender;
    	return 	<div className="messageEntry">
    				   
    				  <label id="date">On: {this.props.entry.sent}</label><br/>
    				 
    				  <label>{this.props.entry.sender}</label><img src={senderurl} className="img-thumbnail" alt="*"/>
              <div className="bubble me">
                {this.props.entry.content}
              </div>
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
          var msg = "message send failed, reason: " + response.responseText;
    			self.setState({info:msg});
    	} });
  	},
 	render : function() {
 		return <div className="messageSender" >
 				<span className="label label-info">Recipient:</span><label>{this.props.recipient}</label><br/>
 				<span className="label label-info">Subject:</span><input className="form-control" type="text" valueLink={this.linkState('subject')} />
 				<span className="label label-info">Message:</span>
        <textarea className="form-control" valueLink={this.linkState('content')} />
 				<button className="btn btn-success"  onMouseUp={this.sendMessage} >Post</button><br/>
        <label className="alert alert-success">{this.state.info}</label>
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