angular.module('bankingApp')
.controller('AdminController', function ($scope, $location, authService, bankService) {
  const userId = localStorage.getItem('userId');

  // Initial view
  $scope.view = 'accounts';

  $scope.accounts = [];
  $scope.transactions = [];
  $scope.selectedAccount = null;
  $scope.searchAccountInput = '';
  $scope.searched = false;

  // Pagination
  $scope.currentPage = 0;
  $scope.pageSize = 5;
  $scope.totalPages = 0;

  // Format functions
  $scope.formatAccountId = function(accountId) {
    return String(accountId).padStart(9, '0');
  };
  $scope.formatDestinationAccountId = function(destinationAccountId) {
    return String(destinationAccountId).padStart(9, '0');
  };
  $scope.formatSourceAccountId = function(sourceAccountId) {
    return String(sourceAccountId).padStart(9, '0');
  };

  function getPaginatedAccounts() {
    bankService.getAllAccounts($scope.currentPage, $scope.pageSize)
      .then(function(response) {
        $scope.accounts = response.data.content;
        $scope.totalPages = response.data.totalPages;
      }).catch(function(error) {
        console.error('Failed to load accounts: ', error);
        alert('Failed to load accounts.');
      });
  }

  function getPaginatedTransactions() {
    bankService.getAllTransactions($scope.currentPage, $scope.pageSize)
      .then(function(response) {
        $scope.transactions = response.data.content.map(function(transaction) {
          return {
            ...transaction,
            transactionId: transaction.transactionId.toString().padStart(9, '0'),
            sourceAccountId: transaction.sourceAccountId.toString().padStart(9, '0'),
            destinationAccountId: transaction.destinationAccountId.toString().padStart(9, '0')
          };
        });
        $scope.totalPages = response.data.totalPages;
      }).catch(function(error) {
        console.error('Failed to load transactions: ', error);
        alert('Failed to load transactions.');
      });
  }

  $scope.prevPage = function () {
    if ($scope.currentPage > 0) {
      $scope.currentPage--;
      if ($scope.view === 'accounts') {
        getPaginatedAccounts();
      } else if ($scope.view === 'transactions') {
        getPaginatedTransactions();
      }
    }
  };

  $scope.nextPage = function () {
    if ($scope.currentPage < $scope.totalPages - 1) {
      $scope.currentPage++;
      if ($scope.view === 'accounts') {
        getPaginatedAccounts();
      } else if ($scope.view === 'transactions') {
        getPaginatedTransactions();
      }
    }
  };

  $scope.setView = function(viewName) {
    $scope.view = viewName;
    $scope.transactions = [];
    $scope.selectedAccount = null;
    $scope.searchAccountInput = '';
    $scope.searched = false;
    $scope.currentPage = 0;

    if (viewName === 'accounts') {
      getPaginatedAccounts();
    } else if (viewName === 'transactions') {
      getPaginatedTransactions();
    }
  };

  $scope.logout = function() {
      authService.logout();
      $window.location.href = '/login';
  };

  // Initial load
  if ($scope.view === 'accounts') {
    getPaginatedAccounts();
  } else if ($scope.view === 'transactions') {
    getPaginatedTransactions();
  }
});
