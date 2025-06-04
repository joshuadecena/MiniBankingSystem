angular.module('bankingApp')
.service('bankService', function($http) {
	
	/*
	const API_BASE = '/api'; // Change this if your API base path is different

	// Helper function to get auth headers
	function getAuthHeaders() {
		return {
			Authorization: 'Bearer ' + authService.getToken()
		};
	}

	// Get all customer accounts
	this.getAllAccounts = function() {
		return $http.get(`${API_BASE}/accounts`, {
			headers: getAuthHeaders()
		}).then(response => response.data);
	};
	*/
	
	/***********	  ACCOUNTS		***********/
	this.getAllAccounts = function() {
		return $http.get('/api/accounts/all', {
			headers: {}
		});
	};
	
	this.getAccountByUserId = function(userId) {
		return $http.get(`/api/accounts/user/${userId}`, {
			headers: {}
		});
	};
	
	/***********	TRANSACTIONS	***********/
	
	this.getAllTransactions = function() {
		return $http.get('/api/transactions/all?sort=timestamp,desc', {
			headers: {}
		});
	};
	
	this.getAccountTransactions = function(accountId) {
		return $http.get(`/api/transactions/${accountId}?sort=timestamp,desc`, {
			headers: {}
		});
	}
	
	this.createTransaction = function(sourceAccountId, destinationAccountId, amount) {
		const body = {
			sourceAccountId: sourceAccountId,
			destinationAccountId: destinationAccountId,
			amount: amount
		};
		
		return $http.post('/api/transactions/new', body, {
			headers: {
				'Content-Type': 'application/json'
			}
		});
	}

});
