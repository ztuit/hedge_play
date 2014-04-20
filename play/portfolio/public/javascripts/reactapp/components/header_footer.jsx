/**
 * @jsx React.DOM
 */


var headerItems = React.createClass({
	render : function() {
			return <div id="mainheader" className="navbar navbar-default navbar-fixed-top" role="header">
			      <div className="container-fluid">
			        <div className="navbar-header">
			          <div className="navbar-form navbar-left" >
			          <a className="navbar-brand" href="#"><img id="hedge_logo" src="assets/images/hedge_logo.jpeg" alt="HTS"/></a>
			          </div>
			          <div className="navbar-form navbar-right" >
			          		{this.props.user}<span/><img id="riak_logo" src="assets/images/riakpowered.jpeg" alt="riak"/><br/><br/>
			        	</div>
			        </div>
			    </div>
			  </div>;
		//return (<div><img id="hedge_logo" src="assets/images/hedge_logo.jpeg" alt="HTS"/>{this.props.user}<img id="riak_logo" src="assets/images/riakpowered.jpeg" alt="riak"/></div>)
	}
});


var footerItems = React.createClass({
	render : function() {
		return (<div><img id="hedge_thumb" src="assets/images/hedge_thumb.jpeg" alt="HTS"/>Hedge Technology Solutions</div>)
	}
});