'use strict';

/**
 * @ngdoc function
 * @name talarionApp.controller:ProductCreateController
 * @description # ProductCreateController Controller of the talarionApp
 */
angular.module('talarionApp').controller('ProductCreateController',
    function($scope, $state, $stateParams, Product, Category, ParseLinks) {

        $scope.product = {};

        $scope.create = function() {

            Product.save($scope.product, function() {
                $state.go('product')
            });

        };

        $scope.showLookup = function() {

            $('#lookupDialog').modal('show');
        };

        $scope.selectLookup = function(category) {

            $scope.product.category = category;

            $('#lookupDialog').modal('hide');
        };

        $scope.callServer = function callServer(tableState) {

            $scope.isLoading = true;

            var pagination = tableState.pagination;

            var start = pagination.start || 0; // This is NOT the page number, but the index of item in the list that you want to use to display the table.
            var number = pagination.number || 10; // Number of entries showed per page.
            var numberOfPages = pagination.numberOfPages || 1;
            var page = start / number + 1;

            var nameSearchCrit = '';

            if (tableState.search.predicateObject != undefined) {
              nameSearchCrit = tableState.search.predicateObject.name || '';
            }

            Category.query({
                page: page,
                per_page: 10,
                name: nameSearchCrit
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.categoryList = result;
                tableState.pagination.numberOfPages = 1 * headers('X-Total-Pages');
                tableState.pagination.number = 1 * headers('X-Size');
                tableState.pagination.start = tableState.pagination.number * (page - 1);
                $scope.isLoading = false;

                // console.log('tableState.pagination.start: ' + tableState.pagination.start);
                // console.log('tableState.pagination.numberOfPages: ' + tableState.pagination.numberOfPages);
                // console.log('tableState.pagination.number: ' + tableState.pagination.number);

                // $state.go('setting-produk');
            });

        };

    });
