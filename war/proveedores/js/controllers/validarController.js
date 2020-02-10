app.service('validarService',['$http','$q','$rootScope', function ($http,$q,$rootScope) {
	this.sendfile=function(cadena,user,orden){
		var d = $q.defer();
		$http.post("/xml/leer/"+orden+"/"+user,cadena).then(
				function(response) {
					//console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	}
}])

app.controller('validarController',['$scope','validarService','fileService', '$cookieStore', '$http','$interval','$window', function($scope,validarService,fileService,$cookieStore, $http, $interval, $window){
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
		validarService.sendfile(c).then(function(data){
			$scope.conceptos=data;
		});
	}
	$scope.valid = function(){
		validarService.sendfile
	}
	
}]);