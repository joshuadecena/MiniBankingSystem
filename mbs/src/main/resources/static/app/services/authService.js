angular.module('bankingApp', [])
  .factory('authService', ['$window', function($window) {
    var storage = $window.localStorage;
    var currentUserKey = 'currentUser';

    return {
      setUser: function(user) {
        storage.setItem(currentUserKey, JSON.stringify(user));
      },

      getUser: function() {
        var userJson = storage.getItem(currentUserKey);
        return userJson ? JSON.parse(userJson) : null;
      },

      isLoggedIn: function() {
        return !!this.getUser();
      },

      getUserRole: function() {
        var user = this.getUser();
        return user ? user.role : null;
      },

      getToken: function() {
        var user = this.getUser();
        return user ? user.token : null;
      },

      logout: function() {
        storage.removeItem(currentUserKey);
		storage.removeItem('accessToken');
		storage.removeItem('refreshToken');
		storage.removeItem('role');
		storage.removeItem('userId');
		storage.removeItem('username');
      }
    };
  }]);
