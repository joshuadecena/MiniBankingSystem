angular.module('bankingApp').controller('LoginController', ['$scope', '$http', '$window', 'authService',
	function($scope, $http, $window, authService) {
		$scope.credentials = {
			username: '',
			password: ''
		};
		$scope.error = null;

		$scope.login = function() {
			$scope.error = null;

			$http.post('/api/auth/login', $scope.credentials, {
				headers: { 'Content-Type': 'application/json' },
				withCredentials: true
			}).then(function(response) {
				//const userData = {
				//	token: response.data.token,
				//	username: response.data.username,
				//	role: response.data.role
				//};

				//authService.setUser(userData);
				
				console.log(response);
				$window.localStorage.setItem('accessToken', response.data.accessToken);
				$window.localStorage.setItem('refreshToken', response.data.refreshToken);
				$window.localStorage.setItem('role', response.data.role);
				$window.localStorage.setItem('userId', response.data.userId);
				$window.localStorage.setItem('username', response.data.username);

				if (response.data.role === 'ADMIN') {
					$window.location.href = '/dashboard/admin';
				} else if (response.data.role === 'CUSTOMER') {
					$window.location.href = '/dashboard/customer';
				} else {
					$window.location.href = '/';
				}
			}, function(errorResponse) {
				$scope.error = 'Invalid username or password';
			});
		};
	}
]);
