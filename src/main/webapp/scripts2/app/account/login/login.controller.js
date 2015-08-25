'use strict';

angular.module('talarionApp')
    .controller('LoginController', function($rootScope, $scope, $state, $timeout, Auth, Principal) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $timeout(function() {
            angular.element('[ng-model="username"]').focus();
        });
        $scope.login = function(event) {
            event.preventDefault();
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function() {
                $scope.authenticationError = false;

                Principal.identity().then(function(account) {
                  // Redefine $rootScope.account after logged in
                    $rootScope.account = account;
                });

                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else {
                    $rootScope.back();
                }
            }).catch(function() {
                $scope.authenticationError = true;
            });
        };
    });
