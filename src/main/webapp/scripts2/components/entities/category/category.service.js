'use strict';

angular.module('talarionApp')
    .factory('Category', function($resource) {
        return $resource('api/category/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
