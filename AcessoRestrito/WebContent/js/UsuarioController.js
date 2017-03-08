function UsuarioAlteracaoController($scope, $http, $routeParams, $location,
		AlteracaoUsuario) {

	$scope.obterUsers = AlteracaoUsuario.get({
		id : $routeParams.id
	});

	$scope.Alterar = function() {

		var Usuario = {
			id : $routeParams.id,
			name : $scope.obterUsers.name,
			password : $scope.obterUsers.password
		}

		var emailValidation = isEmail(Usuario.name);

		if (emailValidation == true && Usuario.password != "") {

			$http({
				method : "POST",
				url : '/AcessoRestrito/rest/user/Alteracao',
				data : Usuario,
				cache : false
			}).success(function(data) {
				$location.path("/UsuarioConsulta");
				$('#showToastSucesso').click();
			});

		}

	};

}

function UsuarioCadastroController($scope, $location, CadastrarUsuario) {

	$scope.Cadastrar = function() {

		var emailValidation = isEmail($("#email").val());

		if (emailValidation == true && $("#password").val() != "") {
			CadastrarUsuario.Cadastrar($.param({
				username : $scope.username,
				password : $scope.password
			}));

			$location.path("/UsuarioConsulta");

			$('#showToastSucesso').click();
		}
	};

};

function isEmail(email) {
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return regex.test(email);
}

function UsuarioConsultaController($scope, AlteracaoUsuario) {

	$scope.ObterUsuarios = AlteracaoUsuario.query();

	$scope.deleteUsuario = function(obterUsers) {
		obterUsers.$remove(function() {
			$scope.ObterUsuarios = AlteracaoUsuario.query();
			$('#showToastSucesso').click();

		});
	};

};

services.factory('CadastrarUsuario', function($resource) {

	return $resource('/AcessoRestrito/rest/user/:action', {}, {
		Cadastrar : {
			method : 'POST',
			params : {
				'action' : 'Cadastrar'
			},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}

		}
	});

});

services.factory('AlteracaoUsuario', function($resource) {

	return $resource('/AcessoRestrito/rest/user/list/:id', {
		id : '@id'
	});
});
