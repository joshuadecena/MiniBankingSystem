angular.module('bankingApp')
.service('bankService', function($http, authService) {

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

  // Get transactions for a specific account ID
  this.getTransactions = function(accountId) {
    return $http.get(`${API_BASE}/accounts/${accountId}/transactions`, {
      headers: getAuthHeaders()
    }).then(response => response.data);
  };

  // Optional: Add more service methods here for future features
});
