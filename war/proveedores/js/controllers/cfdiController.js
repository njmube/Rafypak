app.service('cfdiService',['$http', '$q', function($http, $q){
   
	this.getCFDI=function(rfc,pag){
		var d = $q.defer();
		$http.get("/xml/getFacByProv/"+rfc+"/"+pag).then(
				function(response) {
					//console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	} 
	this.getDetallesFact=function(uuid){
		var d = $q.defer();
		$http.get("/xml/consultFac/" + uuid).then(
				function(response) {
					//console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	} 
	this.DelFact=function(uuid){
		var d = $q.defer();
		$http.get("/xml/deleteFac/" + uuid).then(
				function(response) {
					//console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	} 
	this.getPaginas=function(rfc){
		var d = $q.defer();
		$http.get("/xml/paginasRfc/"+rfc).then(
				function(response) {
					//console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	} 
	 
}]);



app.controller('cfdiController',['$scope','$rootScope','cfdiService','validarService','$cookieStore','$http',function($scope,$rootScope,cfdiService,validarService, $cookieStore,$http){
	$scope.usuario = $cookieStore.get('usernameP');
	$scope.usrRFC = $cookieStore.get('rfcP')
//	$scope.permisos = $cookieStore.get('permisos');
	$scope.usuarioTipo = $cookieStore.get('tipoP')
	$scope.ordenCompra = "";
	$scope.disOrdenC = false;
	if($scope.usuarioTipo == "Servicios"){
		$scope.disOrdenC = true;
		$scope.ordenCompra = "Servicios";
	}
//	$scope.permisoXML = $scope.permisos[17];
	$scope.viewFact=function(data){
		cfdiService.getDetallesFact(data).then(function(data) {
			$scope.fact= data;
			console.log("Detalles Fact ", $scope.fact);
			$http.get("/xml/consultConceptos/"+ data.uuid).then(function(response){
				$scope.factConcep = response.data;
				console.log("Detalles Fact ", $scope.factConcep);
			})
			
			$('#mdlView').modal() 
//			$scope.llenarPags();
	});
	}
	$scope.delFact=function(data){
		cfdiService.DelFact(data).then(function(data) {
			var r = confirm("Click en aceptar para eliminar la Factura");
			if (r == true) {
				location.reload();
			} 
	});
	}
	
	$scope.load=function(data){
		cfdiService.getCFDI($scope.usrRFC,data).then(function(data) {
			
			$scope.listCFDI = data;
			$scope.llenarPags();
	});
	}
	$scope.load(1);

	$scope.paginaActual=1;
	$scope.llenarPags=function(){
		var inicio=0;
		if($scope.paginaActual>5){
			inicio=$scope.paginaActual-5;
		}
		var fin = inicio+9;
		if(fin>$scope.maxPage){
			fin=$scope.maxPage;
		}
		$scope.paginas=[];
		for(var i = inicio; i< fin; i++){
			$scope.paginas.push(i+1);
		}
		for(var i = inicio; i<= fin; i++){
			$('#pagA'+i).removeClass("active");
			$('#pagB'+i).removeClass("active");
		}
		$('#pagA'+$scope.paginaActual).addClass("active");
		$('#pagB'+$scope.paginaActual).addClass("active");
	}
	
		$scope.cargarPagina=function(pag){
		if($scope.paginaActual!=pag){
			$scope.paginaActual=pag;
			$scope.load(pag);
		}
	}
	
		cfdiService.getPaginas($scope.usrRFC).then(function(data){
		$scope.maxPage=data;
		$scope.llenarPags();
		
	});
	
		document.querySelector('input').addEventListener('change', function() {

			  for(var i= 0; i<this.files.length; i++){
				  var reader = new FileReader();
				  reader.onload = function() {
				    var arrayBuffer = this.result;
				    var cadena=$('#cadena').text();
				    cadena= cadena+arrayBuffer+"CADENADEESCAPE";
				   
				    $('#cadena').text(cadena);
				  }
				  $('#cadena').text("");
				  reader.readAsText(this.files[i]);
			  }
			}, false);
		$scope.ok=function(){
			var c= $('#cadena').text();
			validarService.sendfile(c,$scope.usuario,$scope.ordenCompra).then(function(data){
				alert("Las Facturas se han subido correctamente");
				location.reload();
//				$scope.conceptos=data;
			});
		}
		$scope.valid = function(compras,cxp,rafy){
			if(compras && cxp && rafy){
				return "Verificado"
			}
			return "Proceso"
		}
}]);