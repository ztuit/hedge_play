/**
 * @jsx React.DOM
 */


var fruitMachineContainer =  React.createClass({
	rollers :  null,
	message : null,
	camera : null,
	scene : null,
	renderer : null,
	PIOVER6 : (Math.PI/3),
	hex : null,
	spun : false,
	componentDidMount : function () {
		this.rollers = new Array();
		var container = document.getElementById('fruityCanvas')
		this.renderer = new THREE.WebGLRenderer({ antialias: true, canvas: container });
		var wwidth = 700;
		var wheight = 300;
      	this.renderer.setSize(wwidth, wheight);
      	
      // camera
      this.camera = new THREE.PerspectiveCamera(90, wwidth/wheight, 1, 1000);
      this.camera.position.z = 475;
      this.camera.position.x = 0;
      this.camera.position.y = 10;
 	  //this.camera.lookAt(0,0,0);
      // scene
      this.scene = new THREE.Scene();
                
      // cubes (rollers)
    var textureMesh = this.newMaterial();
    this.rollers[1] = this.buildOcto(-200,0,0,200, textureMesh )
    this.scene.add(this.rollers[1]);
      
     this.rollers[0] = this.rollers[1].clone();//this.buildOcto(0,0,0,  textureMesh.clone())
     this.rollers[0].position.set(200,0,0)
     this.scene.add(this.rollers[0]);

     this.rollers[2] = this.rollers[1].clone();
     this.rollers[2].position.set(0,0,0)
     this.scene.add(this.rollers[2]);

      // add subtle ambient lighting
      var ambientLight = new THREE.AmbientLight(0x000044);
      this.scene.add(ambientLight);
      
      // directional lighting
      var directionalLight = new THREE.DirectionalLight(0xffffff);
      directionalLight.position.set(0, 0, 1).normalize();
      this.scene.add(directionalLight);

      
 	 

		this.renderer.render(this.scene, this.camera);
		this.myrender();
	},
	newMaterial : function () {
			  //texture
	    var material1 = new THREE.MeshLambertMaterial({ side: THREE.DoubleSide,
	        map: THREE.ImageUtils.loadTexture('assets/images/fruitmachine.jpeg'), 
	      }).clone();
	    var material2 = new THREE.MeshLambertMaterial({ side: THREE.DoubleSide,
	        map: THREE.ImageUtils.loadTexture('assets/images/riak.jpeg'), 
	      }).clone();
	    var material3 = new THREE.MeshLambertMaterial({ side: THREE.DoubleSide,
	        map: THREE.ImageUtils.loadTexture('assets/images/book_covers.png'), 
	      }).clone();
	    var material4 = new THREE.MeshLambertMaterial({ side: THREE.DoubleSide,
	        map: THREE.ImageUtils.loadTexture('assets/images/chong.jpeg'), 
	      }).clone();
	    var material5 = new THREE.MeshLambertMaterial({ side: THREE.DoubleSide,
	        map: THREE.ImageUtils.loadTexture('assets/images/favicon.png'), 
	      }).clone();
	    var material6 = new THREE.MeshLambertMaterial({ side: THREE.DoubleSide,
	        map: THREE.ImageUtils.loadTexture('assets/images/delaunay.jpeg'), 
	      }).clone();
		var materials = [material5, material5, material6,material6,material3,material3, material4, material4, material1, material1, material2, material2];
 		return new THREE.MeshFaceMaterial(materials);
	},
	myrender : function () {
		requestAnimationFrame(this.myrender);
			
				
				if(this.message)
					this.message.rotation.y += 	(Math.PI/40);

				for( i=0;i<3;i++) {
					if(this.rollers[i].cube1Dampener>-5 && this.rollers[i].cube1Dampener<1)  {
						this.rollers[i].rotation.x += this.rollers[i].cube1Spin;
						
						this.rollers[i].cube1Spin = (Math.PI/10)*Math.pow(2,this.rollers[i].cube1Dampener)
						this.rollers[i].cube1Dampener -= this.rollers[i].spinRate;
					} else if(this.rollers[i].cube1Dampener<=-5) {
						//Need to rotate the cube to face forwards

						var  remainder = this.rollers[i].rotation.x % this.PIOVER6;
						var minRemainder = this.PIOVER6-remainder
						if(minRemainder<0.2) { 
							//console.log("Cube " + i)
							//console.log(this.rollers[i].rotation.x)
							//console.log(remainder)
							this.rollers[i].rotation.x += minRemainder;
							this.rollers[i].cube1Dampener=1;
							this.hasJackpot();
						} else {
							//this.rollers[i].cube1Dampener=1;
							//nudge fwd
							this.rollers[i].cube1Dampener += this.rollers[i].spinRate*4;
						}
					}
				}

				this.renderer.render(this.scene, this.camera);
	},
	hasJackpot : function () {
		var x2 = (parseFloat(this.rollers[1].rotation.x.toFixed(5))  % (Math.PI*2)).toFixed(4) ;
		var x1 = (parseFloat(this.rollers[0].rotation.x.toFixed(5))  % (Math.PI*2)).toFixed(4) ; 
		var x3 = (parseFloat(this.rollers[2].rotation.x.toFixed(5))  % (Math.PI*2)).toFixed(4) ;
		
		console.log(x1 + " " + x2 + " " + x3)
		if( (x1 == x3 ) && (x2 == x3 ) && this.spun==true) {
			console.log("jp")
			this.scene.remove(this.message);
			this.message = this.createMessage("Jackpot!");
			this.scene.add(this.message);
		} else if ( ((x1 == x3 ) || (x2 == x3 ) || (x2 == x1 )) && this.spun==true ){
			this.scene.remove(this.message);
			this.message = this.createMessage("Half Jackpot!")
			console.log("hapf jp")
			this.scene.add(this.message);
		}
	},
	buildCube : function(x, y, z, size, material ) {
	  var c = new THREE.Mesh(new THREE.CubeGeometry(size, size, size), material);
      c.overdraw = true;
      c.position.x = x
      c.position.y = y
      c.position.z = z
      return c;
	},
	buildOcto : function(x, y, z, size, materialsz) {

		//The hexon consists of six sides all seperated by 45 degrees, so we build six identical sides
		//Then rotate by 45 degrees
		var geometry = new THREE.Geometry();
		var sides = 6;
		var vertices=4
		var totalVertices = sides*vertices;
		var i = 0;
		var j = 0;
		for(i=0;i<totalVertices;i+=vertices, j++) {
			geometry.vertices.push( new THREE.Vector3( -100, -100, 173 ) ); 
			geometry.vertices.push( new THREE.Vector3( 100, -100, 173 ) ); 
			geometry.vertices.push( new THREE.Vector3( -100, 100, 173 ) ); 
			geometry.vertices.push( new THREE.Vector3( 100, 100, 173 ) ); 
			geometry.faces.push( new THREE.Face3( i, i+1, i+2 ) ); 
			geometry.faces.push( new THREE.Face3( i+1, i+3, i+2 ) ); 
			
			if(i<(totalVertices-vertices)) {
				var rotation = new THREE.Matrix4().makeRotationX(Math.PI/3);
				geometry.applyMatrix(rotation);
			}
			geometry.faceVertexUvs[ 0 ].push( [ new THREE.Vector2( 0, 0 ), new THREE.Vector2( 1, 0 ), new THREE.Vector2( 0, 1 )]);
			geometry.faceVertexUvs[ 0 ].push( [ new THREE.Vector2( 1, 0 ), new THREE.Vector2( 1, 1 ), new THREE.Vector2( 0, 1 )]);
		}


			for(i=0;i<12;i++) {	
				geometry.faces[i].materialIndex = i;
			}
		
		geometry.computeCentroids();
		geometry.computeFaceNormals();
		geometry.computeVertexNormals();

		object = new THREE.Mesh( geometry, materialsz); 
		object.overdraw = true;
		
		object.position.set( x,y,z);
		
		return object;
	},
	spinRollers : function () {
		this.rollers[0].cube1Spin = 2* Math.PI * (Math.random()*3.0)
		this.rollers[0].rotation.x =  this.rollers[0].cube1Spin
		this.rollers[0].spinRate = 1/(Math.floor(Math.random()*10.0) + 15);
		this.rollers[0].cube1Dampener = 0.0;
		this.rollers[1].cube1Spin = 2* Math.PI * (Math.random()*3.0)
		this.rollers[1].rotation.x = this.rollers[1].cube1Spin
		this.rollers[1].spinRate = 1/(Math.floor(Math.random()*10.0) + 15);
		this.rollers[1].cube1Dampener = 0.0;
		this.rollers[2].cube1Spin = 2* Math.PI * (Math.random()*3.0)
		this.rollers[2].rotation.x = this.rollers[2].cube1Spin
		this.rollers[2].spinRate = 1/(Math.floor(Math.random()*10.0) + 15);
		this.rollers[2].cube1Dampener = 0.0;
		this.scene.remove(this.message);
		this.spun = true;
	},
	createMessage : function (s) {
				var text3d = new THREE.TextGeometry( s, {

					size: 80,
					height: 20,
					curveSegments: 2,
					font: "optimer"

				});
				THREE.GeometryUtils.center( text3d )
				text3d.computeBoundingBox();
				var centerOffset = -0.5 * ( text3d.boundingBox.max.x - text3d.boundingBox.min.x );

				var textMaterial = new THREE.MeshFaceMaterial( [ 
					new THREE.MeshPhongMaterial( { color: 0xffffff, shading: THREE.FlatShading } ), // front
					new THREE.MeshPhongMaterial( { color: 0xffffff, shading: THREE.SmoothShading } ) // side
				] );
				text = new THREE.Mesh( text3d, textMaterial );
				
				text.position.x = 0;
				text.position.y = 200;
				text.position.z = 200;

				text.rotation.x =  Math.PI * 0.2;
				text.rotation.y = Math.PI * 2;

				group = new THREE.Object3D();

		return text;
	},
	render : function () {
		return <div>
			<label>Current score:</label><br/>
			<canvas id="fruityCanvas">Machine</canvas><br/>
			<input type="button" onMouseUp={this.spinRollers}/>
			<label>Leader Board</label><br/>
			<contextCommentViewer contextBucket="Sandcastle" contextKey="fruity" />   
		</div>;

	}
});