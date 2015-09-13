'use strict';

/**
 * @ngdoc function
 * @name yoAngularApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the yoAngularApp
 */
angular.module('talarionApp').controller('CategoryCreateController',
    function($scope, $state, $stateParams, Category, growl) {

        $scope.category = {};

        $scope.create = function() {

            Category.save($scope.category, function() {
            	
            	growl.info("Category successfully added ", {});
            	
                $state.go('category')
            });

        };

    });
