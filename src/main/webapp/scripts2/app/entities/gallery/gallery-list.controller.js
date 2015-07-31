'use strict';

angular.module('talarionApp')
    .controller('GalleryController', function($scope, $state, Image, ParseLinks) {

      $('#gallery').mixItUp({
          animation: {
              animateResizeContainer: false,
              duration: 400,
              effects: 'fade translateZ(-360px) stagger(34ms)',
              easing: 'cubic-bezier(0.175, 0.885, 0.32, 1.275)'
          }
      });
      setTimeout(function () {
          $('#gallery').mixItUp('setOptions', {
              animation: {
                  easing: 'ease'
              }
          });
      }, 1000);

        $scope.imageList = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Image.query({
                page: $scope.page,
                per_page: 20
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.imageList = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        // $scope.loadAll();

        $scope.showUpdate = function(id) {
            Image.get({
                id: id
            }, function(result) {
                $scope.image = result;
                $('#saveImageModal').modal('show');
            });
        };

        $scope.save = function() {
            if ($scope.author.id != null) {
                Image.update($scope.image,
                    function() {
                        $scope.refresh();
                    });
            } else {
                Image.save($scope.image,
                    function() {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function(id) {
            Image.get({
                id: id
            }, function(result) {
                $scope.image = result;
                $('#deleteImageConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function(id) {
            Image.delete({
                    id: id
                },
                function() {
                    $scope.loadAll();
                    $('#deleteImageConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function() {
            $scope.loadAll();
            $('#saveImageModal').modal('hide');
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
            var categoryNameSearchCrit = '';

            if (tableState.search.predicateObject != undefined) {
                nameSearchCrit = tableState.search.predicateObject.name || '';
                categoryNameSearchCrit = tableState.search.predicateObject.categoryName || '';
            }

            Image.query({
                page: page,
                per_page: 10,
                name: nameSearchCrit,
                categoryName: categoryNameSearchCrit
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.imageList = result;
                tableState.pagination.numberOfPages = 1 * headers('X-Total-Pages');
                tableState.pagination.number = 1 * headers('X-Size');
                tableState.pagination.start = tableState.pagination.number * (page - 1);
                $scope.isLoading = false;

                console.log('tableState.pagination.start: ' + tableState.pagination.start);
                console.log('tableState.pagination.numberOfPages: ' + tableState.pagination.numberOfPages);
                console.log('tableState.pagination.number: ' + tableState.pagination.number);

                $state.go('image');
            });

        };
    });
