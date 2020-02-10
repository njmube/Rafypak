app.service('sessionService', [
		'$rootScope',
		'$http',
		'$location',
		'$q','$cookieStore',
		function($rootScope, $http, $location, $q,$cookieStore) {
			this.authenticate = function(credentials, callback) {

				var headers = credentials ? {
					authorization : "Basic"
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};
				$http.get('sesionp/user', {
					headers : headers
				}).success(function(data) {
					if (data.usuario) {
						$cookieStore.put('usernameP', data.usuario);
						$cookieStore.put('rfcP', data.rfc);
						$cookieStore.put('tipoP', data.tipo);
						$rootScope.authenticated = true;
						$rootScope.variable = true;
						$location.path("/");
					} else {
						$rootScope.authenticated = false;
					}
				}).error(function(data) {
					alert("Usuario o Contraseña incorrectos");
					$location.path("/login");
				});
			}
			
			this.isAuthenticated = function() {
				var d = $q.defer();
				$http.get("sessionp/currentSession").success(function(data) {
					$rootScope.authenticated = true;
					d.resolve(data);
				}).error(function(data) {
					$location.path("/login");
				});
				return d.promise;
			}
		} ]);

app.service('usuarioService', [
		'$http',
		'$q',
		'$window',
		'$location',
		function($http, $q, $window, $location) {
			

			this.resetPass = function(email) {
				var d = $q.defer();
				console.log(email);
				$http.post("/usuario/reset/", email).then(function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					// tratar error
					alert("No existe el email, intente de nuevo");
					d.reject(response);
					$window.location.reload;
				});
				return d.promise;
			}

		} ]);

app
		.controller(
				'usuarioController',
				[
						'$scope',
						'$location',
						'usuarioService',
						'perfilService',
						'$window',
						function($scope, $location, usuarioService,
								perfilService, $window) {

							perfilService
									.consultarPerfilesTodosU()
									.then(
											function(data) {
												$scope.perfiles = data;
												if ($scope.perfiles.length == 0) {
													console
															.log($scope.perfiles);
													alert("Necesita crear un perfil antes de crear un usuario");
													$location
															.path("/altaperfiles");
												}
											}).then(function(data){
												
											});
							$scope.validate = function() {
									  if ($scope.usuario.email != $scope.usuario.emailconfirm) {
									    $scope.IsMatch=true;
									    return false;
									  }
									  $scope.IsMatch=false;
							}
							
							$scope.validatePass = function() {
								if($scope.usuario.pass != $scope.usuario.passconfirm){
									$scope.IsMatchP=true;
									return false;
								}
								$scope.IsMatchP=false;
							}		
							
							$scope.acceptSubmit = function() {
								if($scope.form.email.$valid && $scope.form.emailconfirm.$valid && !$scope.IsMatch && $scope.form.pass.$valid && $scope.form.passconfirm.$valid && !$scope.IsMatchP){
									return true;
								}
								return false;
								
							}
							$scope.EnviarFormulario = function() {
								//console.log($scope.form.pass.$valid);
								$scope.validate();
								if(($scope.usuario.email != $scope.usuario.emailconfirm) || ($scope.usuario.pass != $scope.usuario.passconfirm)){
									alert("Email o contraseña no coinciden")
									//$window.location.reload();
								}else{
									if($scope.form.pass.$valid){
								usuarioService
										.crearUsuario($scope.usuario)
										.then(
												function(data) {
													alert("Usuario creado correctamente");
													$location
															.path("/modificarusuarios");
												})}else{
													alert("Contraseña no valida, intente de nuevo");
												}
							}}
						} ]);



app.controller('controladorReset', [ '$scope', 'usuarioService', '$location',
		function($scope, usuarioService, $location) {
	
				$scope.Restablecer = function($scope){
		//			console.log($scope);
					usuarioService.resetPass($scope).then(function(data){
						alert("Contraseña Enviada");
						$location.path("/login");
					})
				}
			} 
]);

app.controller('navigation', [ 'sessionService', '$rootScope', '$scope',
	'$http', '$location',
	function(sessionService, $rootScope, $scope, $http, $location) {

		$scope.credentials = {};
		$scope.login = function() {
			sessionService.authenticate($scope.credentials, function() {
				if ($rootScope.authenticated) {
					$scope.error = false;
					$location.path("/");
				} else {
					$location.path("/login");
					$scope.error = true;
				}
			});
		};
	} ]);