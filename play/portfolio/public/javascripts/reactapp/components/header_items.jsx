/**
 * @jsx React.DOM
 */


var headerItems = React.createClass({
	render : function() {
		return (<div><img src="assets/images/riakpowered.jpeg" alt="riak powered"/>{this.props.user}</div>)
	}
});