function ControleAcessoAlteracaoController($scope, $http, $routeParams,
		$location, AlteracaoAcesso) {

	$http(
			{
				method : "GET",
				url : '/AcessoRestrito/rest/user/ObterRolesUsuario?id='
						+ $routeParams.id + '',
			}).success(function(data) {

		var arrayRoles = String(data.roles).split(',');

		for (var i = 0; i < arrayRoles.length; i++) {
			$("#" + arrayRoles[i] + "").prop('checked', true);

		}

	});

	$scope.ListaPermissoes = {};

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/user/ObterRoles',
		cache : false
	}).success(function(data) {

		$scope.ListaPermissoes = data;
	});

	$scope.obterUsers = AlteracaoAcesso.get({
		id : $routeParams.id
	});

	$scope.Alterar = function() {

		var ListaRolesTrueEnviadaController = [];

		var arrayRolesTrue = String($scope.ListaPermissoes).split(',');

		for (var i = 0; i < arrayRolesTrue.length; i++) {

			if ($("#" + arrayRolesTrue[i] + "").is(":checked") == true) {

				ListaRolesTrueEnviadaController.push(arrayRolesTrue[i]);
			}
		}

		var Usuario = {
			id : $routeParams.id,
			name : $scope.obterUsers.name,
			password : $scope.obterUsers.password
		}

		$http(
				{
					method : "POST",
					url : '/AcessoRestrito/rest/user/AddRoles?lista='
							+ ListaRolesTrueEnviadaController + '',
					data : Usuario,
					cache : false
				}).success(
				function(data) {

					$('#showToastSucesso').click();
					$location.path("/ControleAcessoConsulta");

					var setFunctionInTime = setTimeout(
							FuncaoExecutaDepoisDoTempo, 1000);
					function FuncaoExecutaDepoisDoTempo() {
						location.reload();
					}

				});

	};

}

function AcessoConsultaController($scope, AlteracaoAcesso) {

	$scope.ObterUsuarios = AlteracaoAcesso.query();

};

services.factory('AlteracaoAcesso', function($resource) {

	return $resource('/AcessoRestrito/rest/user/list/:id', {
		id : '@id'
	});
});
