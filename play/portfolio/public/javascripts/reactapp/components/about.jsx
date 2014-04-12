/**
 * @jsx React.DOM
 */

 var aboutComponent = React.createClass({
 	render : function () {
 		return (<div className="aboutBox">
 				<header></header>
 				<nav>
 					<ul>
 					</ul>
 				</nav>
 				<section>
 					<header>
 						<h1>What are we?</h1>
 					</header>
 					 <p>
 						Hedge Technology Solutions (HTS) is a software consultancy based in London providing analysis, design and development of technology solutions for investment banks.
 						
 						HTS has a history of working with some of the largest and reputable global financial institutions for front & middle office IT in the FX Options space covering;
 						pricing & distribution, trade infrasctuture, and reporting. 
		
						HTS has a background and experience covering a wide range of languages, frameworks, technologies and common industry platforms.
					</p>
 				</section>
 				<div id="aboutcontent">
 					<div id="aboutarticle">
						<article>
	 						<h4>Who are we?</h4>
	 						The current directors of HTS
	 						<figure>
								<img src="assets/images/chong.jpeg" alt="bob" />
								<figcaption>Peter Chong</figcaption>
							</figure>
							<p>Peter specialises in development of reporting solution using .net</p>
							<figure>
								<img src="assets/images/owens.jpeg" alt="pete" />
								<figcaption>Dr Robert Owens</figcaption>
							</figure>
							<p>Bob Ownes specialises has ten years experience in providing developing trading applications specialising in option pricing.</p>
							<figure>
								<img src="assets/images/thompson.jpeg" alt="stu" />
								<figcaption>Stuart Thompson</figcaption>
							</figure>
							<p>Stuart has worked with front office IT in London for more than seven years with clients including HSBC and JPMorgan in both the FX and FXO space, 
								developing solutions to support trade lifecycle and risk management involving, amogst other technologies; spring integration, spring MVC, hibernate and modern javascript frameworks such as ExtJs</p>
						</article>
						<article>
	 						<h4>Contact Us</h4>
	 						
							<p>If you would like to get in touch to discuss how HTS could help you with one of your project please get in touch: <a href="contact@hedgetechnologysolutions.com">Contact</a></p> 
						</article>
					</div>
					<aside id="aboutaside">
  						<h4>This web site</h4>
  						<p>This website has been built by HTS to host
  							information about the company and to 
  							provide a space to present demos and
  							discuss technology. As well as a sandbox
  							for demos it provides basic social media
  							for registered users allowing them to 
  							create profiles, blog entries, exchange 
  							messages and discuss in forums.
  							It is built using the Scala Play! Framework, 
  							React.js &amp; Backbone.js, and the Riak key/vaue DB.</p>
					</aside>
				</div>
					<footer></footer>
 				</div>)
 	}
 });



