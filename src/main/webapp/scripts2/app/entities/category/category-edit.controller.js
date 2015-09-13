'use strict';

/**
 * @ngdoc function
 * @name yoAngularApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the yoAngularApp
 */
angular.module('talarionApp').controller('CategoryEditController',
    function($scope, $state, $stateParams, Category, growl) {

        $scope.category = {};

        $scope.load = function(id) {
            Category.get({
                id: id
            }, function(result) {
                $scope.category = result;
            });
        };
        $scope.load($stateParams.id);

        $scope.save = function() {

          Category.update($scope.category, function() {
        	  
        	  growl.info("Category successfully edited ", {});
        	  
                $state.go('category')
            });

        };

    });
