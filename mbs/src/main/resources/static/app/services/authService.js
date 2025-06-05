angular.module('bankingApp')
.factory('authService', ['$window', '$http', '$q', '$timeout', '$rootScope', 
function($window, $http, $q, $timeout, $rootScope) {
  let storage = $window.localStorage;
  let currentUserKey = 'currentUser';
  let accessTokenKey = 'accessToken';
  let tokenRefreshInProgress = false;
  let refreshPromise = null;

  return {
    login: function(credentials) {
      return $http.post('/api/auth/login', credentials, {
        withCredentials: true
      }).then(function(response) {
        if (!response.data || !response.data.accessToken) {
          return $q.reject('Invalid response from server');
        }
		
        storage.setItem(accessTokenKey, response.data.accessToken);
        
        let user = {
          username: credentials.username,
          role: response.data.role,
		  userId: response.data.userId,
          token: response.data.accessToken
        };
        storage.setItem(currentUserKey, JSON.stringify(user));
        
		this.startTokenRefreshTimer();
		
        return user;
      }.bind(this));
    },

    logout: function() {
      return $http.post('/api/auth/logout', {}, {
        withCredentials: true
      }).finally(function() {
        storage.removeItem(currentUserKey);
        storage.removeItem(accessTokenKey);
      });
    },

	refreshToken: function() {
		if (!this.isLoggedIn()) {
		    return $q.reject('User not logged in');
		  }	
	
	  if (tokenRefreshInProgress) {
	    return refreshPromise;
	  }

	  tokenRefreshInProgress = true;
	  
	  refreshPromise = $http.post('/api/auth/refresh-token', {}, {
	    withCredentials: true
	  }).then(response => {
	    if (!response.data || !response.data.accessToken) {
	      throw new Error('Invalid token refresh response');
	    }

	    storage.setItem(accessTokenKey, response.data.accessToken);

	    let user = this.getUser();
	    if (user) {
	      user.token = response.data.accessToken;
	      storage.setItem(currentUserKey, JSON.stringify(user));
	    }

	    $rootScope.$emit('tokenRefreshed');
	    
	    return response.data.accessToken;
	  }).finally(() => {
	    tokenRefreshInProgress = false;
	    refreshPromise = null;
	  });

	  return refreshPromise;
	},

    getAccessToken: function() {
      return storage.getItem(accessTokenKey);
    },

    setUser: function(user) {
      storage.setItem(currentUserKey, JSON.stringify(user));
    },

    getUser: function() {
      let userJson = storage.getItem(currentUserKey);
      return userJson ? JSON.parse(userJson) : null;
    },

    isLoggedIn: function() {
      return !!this.getAccessToken();
    },

    getUserRole: function() {
      let user = this.getUser();
      return user ? user.role : null;
    },

	isTokenExpired: function(token) {
	  if (!token) return true;
	  
	  try {
	    const payload = JSON.parse(atob(token.split('.')[1]));
	    const expiration = payload.exp * 1000;
	    const now = Date.now();
	    
	    return now > expiration || (expiration - now) < 60000;
	  } catch (e) {
	    console.error('Error parsing token:', e);
	    return true;
	  }
	},
	
	getTokenExpiration: function(token) {
	  if (!token) return null;
	  
	  try {
	    const payload = JSON.parse(atob(token.split('.')[1]));
	    return payload.exp * 1000;
	  } catch (e) {
	    console.error('Error parsing token expiration:', e);
	    return null;
	  }
	},
	
	startTokenRefreshTimer: function() {
	  if (!this.isLoggedIn()) return;
	  const token = this.getAccessToken();
	  if (!token) return;

	  const expiration = this.getTokenExpiration(token);
	  if (!expiration) return;

	  const now = Date.now();
	  const timeUntilExpiration = expiration - now;

	  if (timeUntilExpiration < 5000) {
	    this.refreshToken();
	    return;
	  }

	  const refreshTime = timeUntilExpiration - 60000;
	  
	  $timeout(() => {
	    this.refreshToken();
	  }, refreshTime);
	},

	initializeTokenRefresh: function() {
	  if (this.isLoggedIn()) {
	    this.startTokenRefreshTimer();
	    return $q.resolve();
	  } else {
	    return $q.reject('Not authenticated');
	  }
	}
  };
}]);