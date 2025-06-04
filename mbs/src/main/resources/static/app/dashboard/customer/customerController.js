angular.module('bankingApp')
.controller('customerController', function($scope, $http, $location, $window, bankService, authService) {
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
        $window.location.href = '/login';
    };
	
	function loadAccountDetails(userId) {
		bankService.getAccountByUserId(userId).then(function(response) {
			$scope.account = response.data;
			$scope.account.accountId = $scope.account.accountId.toString().padStart(9, '0');
		    return bankService.getAccountTransactions($scope.account.accountId);
		}).then(function(response) {
			console.log(response);
			$scope.transactions = response.data.content.map(function(transaction) {
				return {
					...transaction,
					transactionId: transaction.transactionId.toString().padStart(12, '0'),
					sourceAccountId: transaction.sourceAccountId.toString().padStart(9, '0'),
					destinationAccountId: transaction.destinationAccountId.toString().padStart(9, '0')
				};
			});
		}).catch(function(error) {
			console.error('Error:', error);
		});
	}
	
	$scope.transferFunds = function() {
		const sourceAccountId = $scope.account.accountId;
		const destinationAccountId = $scope.transferData.destinationAccountId;
		const amount = $scope.transferData.amount;
		
		if (!sourceAccountId || !destinationAccountId || !amount) {
			alert("All fields are required.");
			return;
		}
		
		if (amount <= 0) {
			alert("Amount must be greater than 0.");
			return;
		}

		if (destinationAccountId === sourceAccountId) {
			alert("Cannot transfer to the same account.");
			return;
		}
		
		bankService.createTransaction(sourceAccountId, destinationAccountId, amount).then(function(response) {
			alert("Transfer successful.");
			
			$scope.transferData.destinationAccountId = '';
			$scope.transferData.amount = null;
			$scope.setPage('dashboard');
		}).catch(function(error) {
			console.error("Transfer failed:", error);
			alert(error.data);
		});
	}
	
	if (userId) {
		loadAccountDetails(userId);
	}
});