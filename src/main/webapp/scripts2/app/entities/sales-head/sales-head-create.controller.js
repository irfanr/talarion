'use strict';

/**
 * @ngdoc function
 * @name yoAngularApp.controller:AboutCtrl
 * @description # AboutCtrl Controller of the yoAngularApp
 */
angular.module('talarionApp').controller('SalesHeadCreateController',
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

        $scope.today = function() {
            $scope.salesHead.salesDate = new Date();
        };
        $scope.today();
        // END  Datepicker

        $scope.clear = function() {
            $scope.rBisnisTree.tglSistem = null;
        };

        $scope.create = function() {

            SalesHead.save($scope.salesHead, function() {
                $state.go('sales-head')
            });

        };

    });
