var app = angular.module("app", [ 'ngRoute', 'ngCookies' ]);
app.config([ '$routeProvider', function($routeProvider) {

	

	$routeProvider.when('/login', {
		templateUrl : "pages/login.html",
		controller : "navigation"
	});
	$routeProvider.when('/', {
		templateUrl : "pages/listCFDI.html",
		controller : "cfdiController"
	});
	$routeProvider.when('/validxml', {
		templateUrl : "pages/validar.html",
		controller : "validarController"
	});

	$routeProvider.when('/altaperfiles', {
		templateUrl : "pages/altaperfil.html",
		controller : "perfilController"
	})

	$routeProvider.when('/modificarperfiles', {
		templateUrl : "pages/modificaperfiles.html",
		controller : "controladorListaPerfiles"
	})

	$routeProvider.when('/altausuarios', {
		templateUrl : "pages/altausuario.html",
		controller : "usuarioController"
	})

	$routeProvider.when('/modificarusuarios', {
		templateUrl : "pages/modificausuarios.html",
		controller : "controladorListaUsuarios"
	})

	$routeProvider.when('/resetPass', {
		templateUrl : "pages/resetpass.html",
		controller : "controladorReset"
	})

	

	$routeProvider.when('/archivo', {
		templateUrl : "pages/archivo.html",
		controller : "archivoController"
	});
	

	
	//altaImagen#/listComplementos
	
	$routeProvider.when('/altaImagen', {
		templateUrl : "pages/altaImagen.html",
		controller : "imagenController"
	});
	
	$routeProvider.otherwise({
		redirectTo : '/'
	});

	
} ]);

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
				$http.get('/sesionp/user', {
					headers : headers
				}).success(function(data) {
					$cookieStore.put('usernameP', data.usuario);
					if (data.usuario) {
						$rootScope.authenticated = true;
						$rootScope.variable = true;
						$rootScope.cargarEmpresasHeader();
						$location.path("/empresas/list");
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
				$http.get("currentSession").success(function(data) {
					$rootScope.authenticated = true;
					d.resolve(data);
				}).error(function(data) {
					$location.path("/login");
				});
				return d.promise;
			}
		} ]);

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