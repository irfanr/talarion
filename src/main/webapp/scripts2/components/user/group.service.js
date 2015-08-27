'use strict';

angular.module('talarionApp')
    .factory('Group', function($resource) {
        return $resource('api/group/:login', {}, {
            'update': {
                method: 'PUT'
            }
        });
    });
