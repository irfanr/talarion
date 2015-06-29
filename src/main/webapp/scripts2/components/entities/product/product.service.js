'use strict';

angular.module('talarionApp')
    .factory('Product', function($resource) {
        return $resource('api/product/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
