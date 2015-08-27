'use strict';

angular.module('talarionApp')
    .controller('RegisterController', function($scope, $translate, $timeout, Auth, Group) {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $scope.registerAccount.group = {};
        $scope.groupList = {};
        $timeout(function() {
            angular.element('[ng-model="registerAccount.login"]').focus();
        });

        $scope.loadAllGroup = function() {

            $scope.sipGrup1List = {};
            $scope.sipGrup2List = {};
            $scope.sipGrup3List = {};

            Group.query(function(result, headers) {
                $scope.groupList = result;
            });

        };

        $scope.loadAllGroup();

        $scope.register = function() {
            if ($scope.registerAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.registerAccount.langKey = $translate.use();
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;

                Auth.createAccount($scope.registerAccount).then(function() {
                    $scope.success = 'OK';
                }).catch(function(response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };
    });
