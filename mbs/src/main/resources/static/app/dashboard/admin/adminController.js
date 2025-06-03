angular.module('bankingApp')
.controller('AdminController', function ($scope, $location, authService, bankService) {
  $scope.view = 'accounts';         // current view ('accounts' or 'transactions')
  $scope.accounts = [];             // list of all accounts
  $scope.transactions = [];         // list of transactions for selected account
  $scope.selectedAccount = null;    // the account currently selected (for transactions view)
  $scope.searchAccountInput = '';   // input box for searching transactions by account number or user id
  $scope.searched = false;          // flag to indicate a search was performed
  
  // Load all accounts when controller initializes
  bankService.getAllAccounts()
    .then(function(accounts) {
      $scope.accounts = accounts;
    })
    .catch(function(err) {
      alert('Failed to load accounts.');
      console.error(err);
    });

  // Switch view between 'accounts' and 'transactions'
  $scope.setView = function(view) {
    $scope.view = view;
    $scope.transactions = [];
    $scope.selectedAccount = null;
    $scope.searchAccountInput = '';
    $scope.searched = false;
  };

  // Load transactions for the entered account number or user ID
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

        // Find the selected account details from accounts list
        $scope.selectedAccount = $scope.accounts.find(acc =>
          acc.accountId === query || acc.userId === query
        ) || null;

        // Switch to transactions view if not already there
        if ($scope.view !== 'transactions') {
          $scope.view = 'transactions';
        }
      })
      .catch(function(err) {
        alert('Failed to load transactions. Please check the account or user ID.');
        $scope.transactions = [];
        $scope.selectedAccount = null;
        $scope.searched = true;
        console.error(err);
      });
  };

  // Allow pressing Enter to trigger transactions search
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
