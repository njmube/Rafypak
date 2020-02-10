app.service('proveedoresService', [
		'$http',
		'$q',
		function($http, $q) {
			this.registraProveedor = function(proveedor) {
				var d = $q.defer();
				$http.post("/proveedores/usuarioProveedor/registro/", proveedor).then(
						function(response) {
							// console.log(response);
							d.resolve(response.data);
						});
				return d.promise;
			}
			
			this.actualizar = function(proveedor) {
				var d = $q.defer();
				$http.post("/usuarioProveedor/actualiza/", proveedor).then(
						function(response) {
							// console.log(response);
							d.resolve(response.data);
						});
				return d.promise;
			}
			
			//Consultar proveedores
			this.consultar = function(proveedor) {
				var d = $q.defer();
				$http.get("/proveedores/usuarioProveedor/consultarTodos/").then(
						function(response) {
							d.resolve(response.data);
						});
				return d.promise;
			}
}]);

app.controller('proveedoresController', [ 'proveedoresService', '$scope',
		'$window', function(proveedoresService, $scope, $window) {

			$scope.nuevoCliente = function() {
				$('#newProveedor').modal("show");
				$scope.proveedor = {
					rfcEmpresa : sessionStorage.rfcEmpresa
				}
			}

			$scope.validate = function() {
				if ($scope.proveedor.email != $scope.proveedor.emailconfirm) {
					$scope.IsMatch = true;
					return false;
				}
				$scope.IsMatch = false;
			}

			$scope.validatePass = function() {
				if ($scope.proveedor.pass != $scope.proveedor.passconfirm) {
					$scope.IsMatchP = true;
					return false;
				}
				$scope.IsMatchP = false;
			}
		$scope.guardarProveedor=function(){
			proveedoresService.registraProveedor($scope.proveedor).then(function(data){
				alert("Proveedor Registrado");
				$window.location.reload();
			})
		}
		
		$scope.actualizar=function(){
			proveedoresService.actualizar($scope.proveedor).then(function(data){
				alert("Proveedor actualizado");
				$window.location.reload();
			})
		}
		
		proveedoresService.consultar().then(function(data){
			$scope.proveedores= data;
		})
		
		$scope.editar=function(proveedor){
			$('#editProveedor').modal("show");
			$scope.proveedor = proveedor
		}
}]);