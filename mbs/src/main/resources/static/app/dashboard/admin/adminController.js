angular.module('bankingApp')
.controller('AdminController', function ($scope, $location, authService, bankService) {
  const userId = localStorage.getItem('userId'); // Get user ID from local storage

  // Initial view (set only once!)
  $scope.view = 'accounts'; // or 'transactions' if you want that to be the default

  $scope.accounts = [];              // list of all accounts
  $scope.transactions = [];          // list of transactions
  $scope.selectedAccount = null;     // currently selected account (if filtering)
  $scope.searchAccountInput = '';    // input for searching
  $scope.searched = false;           // flag for whether a search was done

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
	$scope.currentPage--;
      getPaginatedAccountTransactions($scope.account.accountId);
    }
  };

  $scope.nextPage = function () {
      $scope.currentPage++
	  getPaginatedAccountTransactions($scope.account.accountId);

    }
  };
  
  // for paginating transactions
  $scope.paginatedTransactions = function () {
    const start = $scope.currentPage * $scope.pageSize;
    return $scope.transactions.slice(start, start + $scope.pageSize);
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

  $scope.formatAccountId = function(accountId) {
    return String(accountId).padStart(9, '0');
  };
  
  $scope.formatDestinationAccountId = function(destinationAccountId) {
    return String(destinationAccountId).padStart(9, '0');
  };
  
  $scope.formatSourceAccountId = function(sourceAccountId) {
    return String(sourceAccountId).padStart(9, '0');
  };

  // Load all accounts on init
  bankService.getAllAccounts($scope.currentPage, $scope.pageSize)
    .then(function(response) {
      console.log("Accounts loaded:", response.data.content);
      $scope.accounts = response.data.content;
    })
    .catch(function(err) {
      alert('Failed to load accounts.');
      console.error(err);
    });

  // Load all transactions on init
  bankService.getAllTransactions($scope.currentPage, $scope.pageSize)
    .then(function(response) {
      console.log("Transactions loaded:", response.data.content);
      $scope.transactions = response.data.content;
    })
    .catch(function(err) {
      alert('Failed to load transactions.');
      console.error(err);
    });
	
	function getPaginatedAccountTransactions(accountId) {
		bankService.getAllTransactions($scope.currentPage, $scope.pageSize)
            .then(function(response) {
                $scope.transactions = response.data.content.map(function(transaction) {
					                    return {
                        ...transaction,
						transactionId: transaction.transactionId.toString().padStart(9, '0'),
                        sourceAccountId: transaction.sourceAccountId.toString().padStart(9, '0'),
                        destinationAccountId:transaction.destinationAccountId.toString().padStart(9, '0'),
                    };
                });
				$scope.totalPages = response.data.content;

            }).catch(function(err) {
                console.error(err);
            });
	}

  // Switch views and reset fields
  $scope.setView = function(viewName) {
    $scope.view = viewName;
    $scope.transactions = [];
    $scope.selectedAccount = null;
    $scope.searchAccountInput = '';
    $scope.searched = false;
    $scope.currentPage = 0;
    
    // Optional: reload transactions if switching to 'transactions'
    if (viewName === 'transactions') {
      bankService.getAllTransactions()
        .then(function(response) {
          console.log("Transactions loaded (from setView):", response.data.content);
          $scope.transactions = response.data.content;
        })
        .catch(function(err) {
          alert('Failed to load transactions.');
          console.error(err);
        });
    }
  };

  // Logout
  $scope.logout = function() {
    authService.logout();
    $location.path('/login');
  };
});
