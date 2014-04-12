/**
 * @jsx React.DOM
 */


var fruitMachineContainer =  React.createClass({
	rollers :  null,
	camera : null,
	scene : null,
	renderer : null,
	PIOVER2 : (Math.PI/2),
	hex : null,
	componentDidMount : function () {
		this.rollers = new Array();
		var container = document.getElementById('fruityCanvas')
		this.renderer = new THREE.WebGLRenderer({ antialias: true, canvas: container });
      	this.renderer.setSize(700, 300);
      	
      // camera
      this.camera = new THREE.PerspectiveCamera(90, 2.5, 1, 1000);
      this.camera.position.z = 350;
      this.camera.position.x = 0;
      this.camera.position.y = 0;
 	  //this.camera.lookAt(0,0,0);
      // scene
      this.scene = new THREE.Scene();
                
      //texture
    var material1 = new THREE.MeshLambertMaterial({
        map: THREE.ImageUtils.loadTexture('assets/images/fruitmachine.jpeg'), 
      });
    var material2 = new THREE.MeshLambertMaterial({
        map: THREE.ImageUtils.loadTexture('assets/images/riak.jpeg'), 
      });
    var material3 = new THREE.MeshLambertMaterial({
        map: THREE.ImageUtils.loadTexture('assets/images/book_covers.png'), 
      });
    var material4 = new THREE.MeshLambertMaterial({
        map: THREE.ImageUtils.loadTexture('assets/images/chong.jpeg'), 
      });
    var material5 = new THREE.MeshLambertMaterial({
        map: THREE.ImageUtils.loadTexture('assets/images/favicon.png'), 
      });
    var material6 = new THREE.MeshLambertMaterial({
        map: THREE.ImageUtils.loadTexture('assets/images/favicon.png'), 
      });
	var materials = [material5, material6,material3, material4,material1, material2];
 
    var meshFaceMaterial = new THREE.MeshFaceMaterial( materials );
      // cubes (rollers)
      this.rollers[0] = this.buildCube(0,0,0, 200, meshFaceMaterial)
    //  this.scene.add(this.rollers[0]);
      
      this.rollers[1] = this.buildCube(-200,0,0, 200, meshFaceMaterial)
     // this.scene.add(this.rollers[1]);

      this.rollers[2] = this.buildCube(200,0,0, 200, meshFaceMaterial)
      //this.scene.add(this.rollers[2]);

      //Add in a octohydron as an experiement
      this.hex = this.buildOcto(0,0,0,200, meshFaceMaterial)
      this.scene.add(this.hex)


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
	myrender : function () {
		requestAnimationFrame(this.myrender);
			this.hex.rotation.x += Math.PI/60;
				
				for( i=0;i<3;i++) {
					if(this.rollers[i].cube1Dampener>-5 && this.rollers[i].cube1Dampener<1)  {
						this.rollers[i].rotation.x += this.rollers[i].cube1Spin;
						
						this.rollers[i].cube1Spin = (Math.PI/10)*Math.pow(2,this.rollers[i].cube1Dampener)
						this.rollers[i].cube1Dampener -= this.rollers[i].spinRate;
					} else if(this.rollers[i].cube1Dampener<=-5) {
						//Need to rotate the cube to face forwards

						var  remainder = this.rollers[i].rotation.x % this.PIOVER2;
						var minRemainder = this.PIOVER2-remainder
						if(minRemainder<0.2) { 
							//console.log("Cube " + i)
							//console.log(this.rollers[i].rotation.x)
							//console.log(remainder)
							this.rollers[i].rotation.x += minRemainder;
							this.rollers[i].cube1Dampener=1;
						} else {
							//this.rollers[i].cube1Dampener=1;
							//nudge fwd
							this.rollers[i].cube1Dampener += this.rollers[i].spinRate*4;
						}
					}
				}

				this.renderer.render(this.scene, this.camera);
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
materials = [

					new THREE.MeshLambertMaterial( { side: THREE.DoubleSide,  map: THREE.ImageUtils.loadTexture('assets/images/fruitmachine.jpeg') } ),
					new THREE.MeshLambertMaterial( { side: THREE.DoubleSide,  map: THREE.ImageUtils.loadTexture('assets/images/chong.jpeg') } ),
					new THREE.MeshLambertMaterial( { side: THREE.DoubleSide,  map: THREE.ImageUtils.loadTexture('assets/images/favicon.png') } ),
					new THREE.MeshLambertMaterial( { side: THREE.DoubleSide,  map: THREE.ImageUtils.loadTexture('assets/images/fruitmachine.jpeg') } ),
					new THREE.MeshLambertMaterial( { side: THREE.DoubleSide,  map: THREE.ImageUtils.loadTexture('assets/images/chong.jpeg') } ),
					new THREE.MeshLambertMaterial( { side: THREE.DoubleSide,  map: THREE.ImageUtils.loadTexture('assets/images/favicon.png') } )				
					//,
					//new THREE.MeshBasicMaterial( { color: 0xffffff, wireframe: true, transparent: true, opacity: 0.1 } )
				];
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
			geometry.vertices.push( new THREE.Vector3( 100, 100, 173 ) ); 
			geometry.vertices.push( new THREE.Vector3( -100, 100, 173 ) ); 
			geometry.faces.push( new THREE.Face3( i, i+1, i+2 ) ); 
			geometry.faces.push( new THREE.Face3( i+2, i, i+3 ) ); 
			
			if(i<(totalVertices-vertices)) {
				var rotation = new THREE.Matrix4().makeRotationX(Math.PI/3);
				geometry.applyMatrix(rotation);
			}
			geometry.faceVertexUvs[ 0 ].push( [ new THREE.Vector2( 0, 0 ), new THREE.Vector2( 1, 0 ), new THREE.Vector2( 1, 1 )]);
			geometry.faceVertexUvs[ 0 ].push( [ new THREE.Vector2( 1, 1 ), new THREE.Vector2( 0, 0 ), new THREE.Vector2( 0, 1 )]);
		}
			/*
			geometry.vertices.push( new THREE.Vector3( -100, -100, 173 ) ); 
			geometry.vertices.push( new THREE.Vector3( 100, -100, 173 ) ); 
			geometry.vertices.push( new THREE.Vector3( 100, 100, 173 ) ); 
			geometry.vertices.push( new THREE.Vector3( -100, 100, 173 ) ); 
			geometry.faces.push( new THREE.Face3( 4, 5, 6 ) ); 
			geometry.faces.push( new THREE.Face3( 6, 4, 7 ) ); 
		*/
			//var rotation = new THREE.Matrix4().makeRotationX(-Math.PI/4);
			//geometry.applyMatrix(rotation);
			geometry.faceVertexUvs[ 0 ].push( [ new THREE.Vector2( 0, 0 ), new THREE.Vector2( 1, 0 ), new THREE.Vector2( 1, 1 )]);
			geometry.faceVertexUvs[ 0 ].push( [ new THREE.Vector2( 1, 1 ), new THREE.Vector2( 0, 0 ), new THREE.Vector2( 0, 1 )]);

			for(i=0;i<12;i++) {	
				geometry.faces[i].materialIndex = i % 6;
			}
		//geometry.faceVertexUvs[ 1 ].push( [ new THREE.Vector2( 0, 1 ), new THREE.Vector2( 1, 1 ), new THREE.Vector2( 0, 0 ) ] );
		geometry.computeCentroids();
geometry.computeFaceNormals();
geometry.computeVertexNormals();

		object = new THREE.Mesh( geometry, new THREE.MeshFaceMaterial( materials ) );
		object.overdraw = true;
		//object.rotation.x = -Math.PI/4;
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