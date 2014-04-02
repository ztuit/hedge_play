/**
 * @jsx React.DOM
 */

 var sandBox = React.createClass({
 	render : function () {
 		return (<div class="sandBox">
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
 					<article>
 						<h2>Three.js fruit machine</h2>
 						<figure>
							<img src="assets/images/fruitmachine.jpeg" alt="fruit" />
							<figcaption>Fruity</figcaption>
						</figure>
						<p>A three.js app simulating a fruit machine.</p>
					</article>
					<article>
 						<h2>Scala Play! Framework using Riak for social mdeia</h2>
 						<figure>
							<img src="assets/images/riak.jpeg" alt="fruit" />
							<figcaption>Riak</figcaption>
						</figure>
						<p>A Scala playframework. </p>
					</article>
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
 				</div>)
 	}
 });



 var sandBoxItem = React.createClass({
 	render : function() {
 		return <div></div>;
 	} });