'use strict';

/**
 * @ngdoc function
 * @name yoAngularApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the yoAngularApp
 */
angular.module('talarionApp').controller('SalesHeadEditController',
    function($scope, $state, $stateParams, SalesHead) {

        $scope.salesHead = {};

        // BEGIN Datepicker
        $scope.open = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.opened = true;
        };

        $scope.dateOptions = {
            formatYear: 'yy',
            startingDay: 1
        };

        $scope.format = 'yyyy-MM-dd';
        // END  Datepicker

        $scope.load = function(id) {
          SalesHead.get({
                id: id
            }, function(result) {
                $scope.salesHead = result;
            });
        };
        $scope.load($stateParams.id);

        $scope.save = function() {

          SalesHead.update($scope.salesHead, function() {
                $state.go('sales-head')
            });

        };

    });
