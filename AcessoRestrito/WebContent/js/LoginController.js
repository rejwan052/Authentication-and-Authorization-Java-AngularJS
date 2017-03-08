function LoginController($scope, $rootScope, $location, $cookieStore,
		UserService) {

	$scope.rememberMe = true;

	$scope.login = function() {

		if ($scope.username == undefined && $scope.password == undefined
				|| $scope.username == "" && $scope.password == "") {

			$scope.senha_show = false;
			$scope.email_show = false;
			$scope.email_senha_show = true;

		} else if ($scope.username != undefined && $scope.password == undefined
				|| $scope.username != "" && $scope.password == "") {

			$scope.senha_show = true;
			$scope.email_show = false;
			$scope.email_senha_show = false;

		} else if ($scope.username == undefined && $scope.password != undefined
				|| $scope.username == "" && $scope.password != "") {

			$scope.senha_show = false;
			$scope.email_show = true;
			$scope.email_senha_show = false;

		} else {

			$scope.senha_show = false;
			$scope.email_show = false;
			$scope.email_senha_show = false;

			UserService.authenticate($.param({
				username : $scope.username,
				password : $scope.password
			}), function(authenticationResult) {

				var accessToken = authenticationResult.token;
				$rootScope.accessToken = accessToken;
				if ($scope.rememberMe) {
					$cookieStore.put('accessToken', accessToken);
				}

				UserService.get(function(user) {

					$rootScope.user = user;

					$location.path("/DashboardIndex");

				});

			}, function errorCallback(response) {

				document.getElementById("showtoast").click();

			});

		}

	};
};
var services = angular.module('exampleApp.services', [ 'ngResource' ]);

services.factory('UserService', function($resource) {

	return $resource('/AcessoRestrito/rest/user/:action', {}, {
		authenticate : {
			method : 'POST',
			params : {
				'action' : 'authenticate'
			},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}

		}
	});

});
services.factory('BlogPostService', function($resource) {

	return $resource('/AcessoRestrito/rest/blogposts/:id', {
		id : '@id'
	});
});