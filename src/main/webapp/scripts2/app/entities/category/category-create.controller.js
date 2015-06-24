'use strict';

/**
 * @ngdoc function
 * @name yoAngularApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the yoAngularApp
 */
angular.module('talarionApp').controller('CategoryCreateController',
    function($scope, $state, $stateParams, Category) {

        $scope.category = {};

        $scope.create = function() {

            Category.save($scope.category, function() {
                $state.go('category')
            });

        };

    });
