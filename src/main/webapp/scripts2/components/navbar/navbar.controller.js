'use strict';

angular.module('talarionApp')
    .controller('NavbarController', function ($rootScope, $scope, $location, $state, Auth, Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;

        // Define $rootScope.account if html is refreshed
        Principal.identity().then(function(account) {
            $rootScope.account = account;
        });

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
    });
