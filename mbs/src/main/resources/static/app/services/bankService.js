angular.module('bankingApp')
.service('bankService', function($http) {
	
	/***********	  ACCOUNTS		***********/
	this.getAllAccounts = function(page, size) {
		return $http.get(`/api/accounts/all?page=${page}&size=${size}`, {
			headers: {}
		});
	};
	
	this.getAccountByUserId = function(userId) {
		return $http.get(`/api/accounts/user/${userId}`, {
			headers: {}
		});
	};
	
	/***********	TRANSACTIONS	***********/
	
	this.getAllTransactions = function(page, size) {
		return $http.get(`/api/transactions/all?page=${page}&size=${size}&sort=timestamp,desc`, {
			headers: {}
		});
	};
	
	this.getAccountTransactions = function(accountId, page, size) {
		return $http.get(`/api/transactions/${accountId}?page=${page}&size=${size}&sort=timestamp,desc`, {
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
