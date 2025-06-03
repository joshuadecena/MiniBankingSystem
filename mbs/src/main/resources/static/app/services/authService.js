angular.module('bankingApp', [])
  .factory('authService', ['$window', function($window) {
    var storage = $window.localStorage;
    var currentUserKey = 'currentUser';

    return {
      // Save user info (token, role, etc.) after login
      setUser: function(user) {
        storage.setItem(currentUserKey, JSON.stringify(user));
      },

      // Get current user info (returns object or null)
      getUser: function() {
        var userJson = storage.getItem(currentUserKey);
        return userJson ? JSON.parse(userJson) : null;
      },

      // Check if user is logged in
      isLoggedIn: function() {
        return !!this.getUser();
      },

      // Get user role (e.g. 'ADMIN' or 'CUSTOMER')
      getUserRole: function() {
        var user = this.getUser();
        return user ? user.role : null;
      },

      // Clear user info on logout
      logout: function() {
        storage.removeItem(currentUserKey);
      }
    };
  }]);
