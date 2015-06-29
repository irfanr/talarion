'use strict';

/**
 * @ngdoc function
 * @name yoAngularApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the yoAngularApp
 */
angular.module('talarionApp').controller('ProductEditController',
    function($scope, $state, $stateParams, Product) {

        $scope.product = {};

        $scope.load = function(id) {
            Product.get({
                id: id
            }, function(result) {
                $scope.product = result;
            });
        };
        $scope.load($stateParams.id);

        $scope.save = function() {

          Product.update($scope.product, function() {
                $state.go('product')
            });

        };

    });
