/**
 * @jsx React.DOM
 */


var headerItems = React.createClass({
	render : function() {
			return <div id="mainheader" className="mainheader" role="header">
					<div className="container-fluid">
						<div className="row">
							<div className="col-sm-4">
								<a  href="#"><img id="hedge_logo" className="headerImg" src="assets/images/hedge_logo.jpeg" alt="HTS"/></a>
				        	</div>
				        	<div className="col-sm-1 col-sm-offset-6">
					    		<img id="riak_logo" src="assets/images/riakpowered.jpeg" alt="riak"/>
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