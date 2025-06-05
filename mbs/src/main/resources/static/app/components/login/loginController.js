angular.module('bankingApp')
.controller('LoginController', ['$scope', 'authService', '$window', '$location',
function($scope, authService, $window, $location) {
  $scope.credentials = {
    username: '',
    password: ''
  };
  $scope.error = null;
  $scope.isLoading = false;

  $scope.login = function() {
    if ($scope.loginForm && $scope.loginForm.$invalid) return;
    
    $scope.error = null;
    $scope.isLoading = true;

    authService.login($scope.credentials)
      .then(function(user) {
        if (user && user.role) {
          // Initialize token refresh after successful login
          authService.initializeTokenRefresh();
          $window.location.href = ('/dashboard/' + user.role.toLowerCase());
        } else {
          throw new Error('Invalid user data received');
        }
      })
      .catch(function(error) {
        console.error('Login error:', error);
        $scope.error = error.data?.message || error.message || 'Login failed';
        $scope.credentials.password = '';
      })
      .finally(function() {
        $scope.isLoading = false;
      });
  };

  // If already logged in, redirect to dashboard
  if (authService.isLoggedIn()) {
    const user = authService.getUser();
    if (user && user.role) {
      $location.path('/dashboard/' + user.role.toLowerCase());
    }
  }
}]);