angular.module('bankingApp')
.controller('customerController', function($scope, $http, $location, bankService, authService) {
	const userId = localStorage.getItem('userId');
	
	$scope.page = 'dashboard';
	$scope.account = {};
	$scope.transactions = [];
	
	$scope.transferData = {
		destinationAccountId: '',
		amount: null
	};
	
	// Function to switch between different pages
	$scope.setPage = function(page) {
		$scope.page = page;
		
		if (page === 'dashboard') {
			loadAccountDetails(localStorage.getItem('userId'));
		} else if (page === 'transfer') {
			$scope.transferData.destinationAccountId = '';
			$scope.transferData.amount = null;
			$scope.transactions = [];
		}
	}
    
    $scope.logout = function() {
        authService.logout();
        $location.path('/login');
    };
	
	function loadAccountDetails(userId) {
		bankService.getAccountByUserId(userId).then(function(response) {
			$scope.account = response.data;
		    return bankService.getAccountTransactions($scope.account.accountId);
		}).then(function(response) {
			$scope.transactions = response.data.content;
		}).catch(function(error) {
			console.error('Error:', error);
		});
	}
	
	if (userId) {
		loadAccountDetails(userId);
	}
});