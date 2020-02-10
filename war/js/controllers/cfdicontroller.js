app.service('cfdiService',['$http', '$q', function($http, $q){
	this.getCFDI=function(pag){
		var d = $q.defer();
		$http.get("/xml/getAllFac/"+pag).then(
				function(response) {
					//console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	} 
	this.updateFecha=function(uuid,user,date){
		var d = $q.defer();
		$http.get("/xml/updateFecha/"+uuid+"/"+user+"/"+date).then(
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
	this.getPaginas=function(pag){
	var d = $q.defer();
	$http.get("/xml/paginas").then(
			function(response) {
				//console.log(response);
				d.resolve(response.data);
			},
			function(response) {
			});
	return d.promise;
} 
}]);
app.controller("cfdiController",['$scope','$rootScope','$http','cfdiService','$cookieStore',function($scope,$rootScope,$http,cfdiService, $cookieStore){
	$scope.nombre= "Memo";
	$scope.usuario = $cookieStore.get('username');
	$scope.permisos = $cookieStore.get('permisos');
	$scope.permisoCompras = $scope.permisos[15];
	$scope.permisoCxP = $scope.permisos[16];
	$scope.genPDF=function(){
		html2canvas(document.body,{
			onrendered: function (canvas){
				var img=canvas.toDataURL("imagen/jpg");
				var doc = new jsPDF();
				doc.addImage(img, 'JPEG', .1, .1);
				doc.save('cfdi.pdf');
			}
		})
	}	
	
	$scope.viewFact=function(data){
		cfdiService.getDetallesFact(data).then(function(data) {
			$scope.fact= data;
		$http.get("/xml/consultConceptos/"+ data.uuid).then(function(response){
			$scope.factConcep = response.data;
		})
			$('#mdlView').modal() 
//			$scope.llenarPags();
	});
	}
	$scope.delFact=function(data){
		var r = confirm("Click en aceptar para eliminar la Factura");
		
		if (r == true) {
		cfdiService.DelFact(data).then(function(data) {
			
				location.reload();
			
	});
		}
	}
	
	$scope.load=function(data){
		cfdiService.getCFDI(data).then(function(data) {
			$scope.listCFDI = data;
			$scope.llenarPags();
	});
	}
	$scope.load(1);
	
	$scope.validCompra = function(uuid){
		var send = {
				Validacion: [null,null,true]
		}
		$http.post("/xml/validarCompras/"+ uuid,send).then(function(response){
			alert("Se ha validado la factura por el area de compras");
			
			location.reload();
		})
	}
	$scope.validCxP = function(uuid){
		var send = {
				Validacion: [null,null,true]
		}
		$http.post("/xml/validarCxP/"+ uuid,send).then(function(response){
			alert("Se ha validado la factura por cuentas   por pagar");
			
			location.reload();
		})
	}
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
	
		cfdiService.getPaginas().then(function(data){
		$scope.maxPage=data;
		$scope.llenarPags();
		
	});
		$('.datepicker').datepicker({format: 'mm-dd-yyyy '});
		
		$('.input-daterange').datepicker({
		    format: "mm-dd-yyyy"
		});
		
		$('.input-daterange input').each(function() {
		    $(this).datepicker("format","mm-dd-yyyy");
		});
		$scope.optionChange=function(){
		if($scope.tipo!="periodo"){
			$scope.datoDis=false;
			$scope.dato="";
			}else{
				$scope.datoDis=true;
				$scope.dato="ninguno";
			
		}
		}
		$scope.updateFecha = function(){
			if($scope.newDate){
			cfdiService.updateFecha($scope.dFecha.uuid,$scope.usuario,$scope.newDate).then(function(data) {
				alert("Se ha registrado la nueva fecha correctamente")
				location.reload();
			});
			}
			
		}
		$scope.changeF=function(ind){
			$scope.dFecha = $scope.listCFDI[ind];
			$('#mdlNewFecha').modal();
		}
		$scope.btnReporte=function(){
			$scope.dato="ninguno";
			$scope.fechaInicio="";
			$scope.fechaFin="";
			$scope.tipo="periodo";
			$scope.datoDis=true;
			
		}
	
}]);