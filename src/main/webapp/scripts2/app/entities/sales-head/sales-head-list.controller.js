'use strict';

angular.module('talarionApp')
    .controller('SalesHeadController', function($scope, $state, SalesHead, ParseLinks) {
        $scope.salesHeadList = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            SalesHead.query({
                page: $scope.page,
                per_page: 20
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.salesHeadList = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        // $scope.loadAll();

        $scope.showUpdate = function(id) {
            SalesHead.get({
                id: id
            }, function(result) {
                $scope.sales = result;
                $('#saveSalesHeadModal').modal('show');
            });
        };

        $scope.delete = function(id) {
            SalesHead.get({
                id: id
            }, function(result) {
                $scope.salesHead = result;
                $('#deleteSalesHeadConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function(id) {
            SalesHead.delete({
                    id: id
                },
                function() {
                    $scope.loadAll();
                    $('#deleteSalesHeadConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function() {
            $scope.loadAll();
            $('#saveSalesHeadModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function() {

        };

        $scope.callServer = function callServer(tableState) {

            $scope.isLoading = true;

            var pagination = tableState.pagination;

            var start = pagination.start || 0; // This is NOT the page number, but the index of item in the list that you want to use to display the table.
            var number = pagination.number || 10; // Number of entries showed per page.
            var numberOfPages = pagination.numberOfPages || 1;
            var page = start / number + 1;

            var nameSearchCrit = '';

            if(tableState.search.predicateObject != undefined){
              nameSearchCrit = tableState.search.predicateObject.name || '';
            }

            SalesHead.query({
                page: page,
                per_page: 10,
                name: nameSearchCrit
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.salesHeadList = result;
                tableState.pagination.numberOfPages = 1 * headers('X-Total-Pages');
                tableState.pagination.number = 1 * headers('X-Size');
                tableState.pagination.start = tableState.pagination.number * (page - 1);
                $scope.isLoading = false;

                console.log('tableState.pagination.start: ' + tableState.pagination.start);
                console.log('tableState.pagination.numberOfPages: ' + tableState.pagination.numberOfPages);
                console.log('tableState.pagination.number: ' + tableState.pagination.number);

                $state.go('sales-head');
            });

        };
    });
