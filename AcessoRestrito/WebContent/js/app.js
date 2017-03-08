angular
		.module(
				'exampleApp',
				[ 'ngRoute', 'ngCookies', 'exampleApp.services', 'oc.lazyLoad' ])
		.config(
				[
						'$routeProvider',
						'$locationProvider',
						'$httpProvider',

						function($routeProvider, $locationProvider,
								$httpProvider) {

							$routeProvider.when('/UsuarioConsulta', {
								templateUrl : 'partials/Usuario/Consulta.html',
								controller : UsuarioConsultaController,

							});
							$routeProvider
									.when(
											'/UsuarioAlteracao/:id',
											{
												templateUrl : 'partials/Usuario/Alteracao.html',
												controller : UsuarioAlteracaoController,

											});

							$routeProvider.when('/UsuarioCadastro', {
								templateUrl : 'partials/Usuario/Cadastro.html',
								controller : UsuarioCadastroController,

							});
							$routeProvider
									.when(
											'/ControleAcessoConsulta',
											{
												templateUrl : 'partials/ControleAcesso/Consulta.html',
												controller : AcessoConsultaController,

											});
							$routeProvider
									.when(
											'/ControleAcessoAlteracao/:id',
											{
												templateUrl : 'partials/ControleAcesso/Alteracao.html',
												controller : ControleAcessoAlteracaoController,

											});

							$routeProvider.when('/login', {
								templateUrl : 'partials/login/formLogin.html',
								controller : LoginController
							});

							$routeProvider
									.when('/Dashboard', {
												template : '<div> <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top"><input type="hidden" name="cmd" value="_s-xclick"><input type="hidden" name="encrypted" value="-----BEGIN PKCS7-----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-----END PKCS7-----"><input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!"><img alt="" border="0" src="https://www.paypalobjects.com/pt_BR/i/scr/pixel.gif" width="1" height="1"></form>'
														+ '</div> <div> <h1>EXAMPLE AUTHENTICATIONS AND AUTHORITIES</h1><br> <h3>AUTHENTICATIONS AND AUTHORITIES USING ANGULARJS + SPRING SECURITY+ RESTFUL + ACCESSTOKEN + COOKIES +  BOOTSTRAP + JAVA + JPA + POSTGRESQL...<h3><br><h3>IF YOU LIKE! CLICK IN DONATE.</h3></div>',
												controller : DashboardController
											});

							$routeProvider.when('/DashboardIndex', {
								template : '<div></div>',
								controller : DashboardIndexController
							});

							$routeProvider.otherwise({
								template : '<div></div>',
								controller : IndexController
							});

							$locationProvider.hashPrefix('!');

							$httpProvider.interceptors
									.push(function($q, $rootScope, $location) {
										return {
											'responseError' : function(
													rejection) {
												var status = rejection.status;
												var config = rejection.config;
												var method = config.method;
												var url = config.url;

												if (status == 401) {

													window.location.href = '/AcessoRestrito/login.html#!/login'

												} else {
													$rootScope.error = method
															+ " on "
															+ url
															+ " failed with status "
															+ status;

												}
												return $q.reject(rejection);
											}
										};
									});

							$httpProvider.interceptors
									.push(function($q, $rootScope, $location) {
										return {
											'request' : function(config) {
												var isRestCall = config.url
														.indexOf('/AcessoRestrito/rest') == 0;
												if (isRestCall
														&& angular
																.isDefined($rootScope.accessToken)) {
													var accessToken = $rootScope.accessToken;
													if (exampleAppConfig.useAccessTokenHeader) {
														config.headers['X-Access-Token'] = accessToken;
													} else {
														config.url = config.url
																+ "?token="
																+ accessToken;
													}
												}
												return config
														|| $q.when(config);
											}
										};
									});

						} ]

		).run(function($rootScope, $location, $cookieStore, UserService) {

			$rootScope.$on('$viewContentLoaded', function() {
				delete $rootScope.error;
			});

			$rootScope.hasRole = function(role) {

				if ($rootScope.user === undefined) {
					return false;
				}

				if ($rootScope.user.roles[role] === undefined) {
					return false;
				}

				return $rootScope.user.roles[role];
			};

			$rootScope.logout = function() {
				delete $rootScope.user;
				delete $rootScope.accessToken;
				$cookieStore.remove('accessToken');
				window.location.href = '/AcessoRestrito/login.html#!/login'

			};

			var originalPath = $location.path();

			var accessToken = $cookieStore.get('accessToken');

			if (accessToken !== undefined) {
				$rootScope.accessToken = accessToken;
				UserService.get(function(user) {
					$rootScope.user = user;
					$location.path(originalPath);
				});
			} else {
				window.location.href = '/AcessoRestrito/login.html#!/login'

			}

			$rootScope.initialized = true;
		});

function DashboardIndexController($scope, $cookieStore) {
	window.location.href = '/AcessoRestrito/index.html#!/Dashboard'
}

function DashboardController($scope) {

}

function IndexController($scope, $cookieStore) {

}