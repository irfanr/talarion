'use strict';

angular.module('talarionApp')
    .factory('Image', function($resource) {
        return $resource('api/image/:id', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
