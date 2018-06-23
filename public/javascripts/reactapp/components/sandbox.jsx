/**
 * @jsx React.DOM
 */

 var sandBox = React.createClass({
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
 	findApp : function(appname) {
 		document.getElementById("sandboxDesc").innerHTML=""
 		
 		React.renderComponent(
			<fruitMachineContainer/>,
			document.getElementById("sandboxContainer")
			);
 	},
 	render : function () {
var self = this;
 			var sandCastles = this.state.data.map(function (sandcastle) {
		    	
		      return <article>
 						<h2>{sandcastle.shortdesc}</h2>
 						<figure>
							<img src={sandcastle.img} alt={sandcastle.appname} />
							<figcaption>{sandcastle.caption}</figcaption>
						</figure>
						<p>{sandcastle.longdesc}<a href="#" onClick={function(){self.findApp(sandcastle.appname)}}>View Here</a></p>
					</article>;
		    });

 		return (<div className="sandBox panel panel-primary body-padding" >
 				<div id="sandboxContainer"></div>
 				<div id="sandboxDesc">
 				<header></header>
 				<nav>
 					<ul>
 						<li>three.js fruit machine</li>
 						<li>Delauncy Triangulation</li>
 						<li>This website</li>
 					</ul>
 				</nav>
 				<section>
 					<header>
 						<h1>Experiements</h1>
 					</header>
 					{sandCastles}
					
					<article>
 						<h2>Delauay Triangulation</h2>
 						<figure>
							<img src="assets/images/delaunay.jpeg" alt="fruit" />
							<figcaption>delaunay</figcaption>
						</figure>
						<p>Generating a mesh from a point 2d cloud. </p>
					</article>
					<footer></footer>
 				</section>
 				</div>
 				</div>)
 	}
 });



var sandCastle = new Backbone.Model.extend({
	idAttribute : 'appname',
	defaults :{
		appname : "",
		shortdesc : "",
		img: "",
		longdesc : "",
		caption : ""
	},
	urlRoot : "/sandbox"
});