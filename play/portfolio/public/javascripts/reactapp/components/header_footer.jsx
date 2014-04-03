/**
 * @jsx React.DOM
 */


var headerItems = React.createClass({
	render : function() {
		return (<div><img id="hedge_logo" src="assets/images/hedge_logo.jpeg" alt="HTS"/>{this.props.user}<img id="riak_logo" src="assets/images/riakpowered.jpeg" alt="riak"/></div>)
	}
});


var footerItems = React.createClass({
	render : function() {
		return (<div><img id="hedge_thumb" src="assets/images/hedge_thumb.jpeg" alt="HTS"/>Hedge Technology Solutions</div>)
	}
});