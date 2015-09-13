'use strict';

angular.module('talarionApp')
    .factory('SalesHead', function($resource) {
        return $resource('api/sales-head/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
