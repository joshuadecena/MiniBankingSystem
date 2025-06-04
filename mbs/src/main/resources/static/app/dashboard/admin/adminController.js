angular.module('bankingApp')
.controller('AdminController', function ($scope, $location, authService, bankService) {
  $scope.view = 'accounts';          // current view ('accounts' or 'transactions')
  $scope.accounts = [];              // list of all accounts
  $scope.transactions = [];          // list of transactions for selected account
  $scope.selectedAccount = null;     // currently selected account for transactions view
  $scope.searchAccountInput = '';    // input box for searching transactions by account number or user id
  $scope.searched = false;           // flag indicating if a search was performed

  // Pagination setup
  $scope.currentPage = 0;
  $scope.pageSize = 5;

  $scope.paginatedAccounts = function () {
    const start = $scope.currentPage * $scope.pageSize;
    return $scope.accounts.slice(start, start + $scope.pageSize);
  };

  $scope.numPages = function () {
    return Math.ceil($scope.accounts.length / $scope.pageSize);
  };

  $scope.prevPage = function () {
    if ($scope.currentPage > 0) {
      $scope.currentPage--;
    }
  };

  $scope.nextPage = function () {
    if ($scope.currentPage < $scope.numPages() - 1) {
      $scope.currentPage++;
    }
  };

  // Load all accounts on controller init
  bankService.getAllAccounts()
    .then(function(accounts) {
      $scope.accounts = accounts;
    })
    .catch(function(err) {
      alert('Failed to load accounts.');
      console.error(err);
    });

  // Switch between 'accounts' and 'transactions' views
  $scope.setView = function(view) {
    $scope.view = view;
    $scope.transactions = [];
    $scope.selectedAccount = null;
    $scope.searchAccountInput = '';
    $scope.searched = false;
    $scope.currentPage = 0; // Reset pagination when switching views
  };

  // Load transactions by account number or user ID
  $scope.loadTransactions = function() {
    const query = $scope.searchAccountInput.trim();
    if (!query) {
      alert('Please enter an Account Number or User ID.');
      return;
    }

    bankService.getTransactions(query)
      .then(function(transactions) {
        $scope.transactions = transactions || [];
        $scope.searched = true;

        // Find selected account from the accounts list
        $scope.selectedAccount = $scope.accounts.find(acc =>
          acc.accountId === query || acc.userId === query
        ) || null;

        // Switch to transactions view if not already there
        if ($scope.view !== 'transactions') {
          $scope.view = 'transactions';
        }
      })
      .catch(function(err) {
        alert('Failed to load transactions. Please check the Account Number or User ID.');
        $scope.transactions = [];
        $scope.selectedAccount = null;
        $scope.searched = true;
        console.error(err);
      });
  };

  // Trigger search on Enter key press
  $scope.checkEnter = function(event) {
    if (event.keyCode === 13) {
      $scope.loadTransactions();
    }
  };

  // Logout and redirect to login page
  $scope.logout = function() {
    authService.logout();
    $location.path('/login');
  };
});
