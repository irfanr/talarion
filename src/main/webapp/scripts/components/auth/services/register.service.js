'use strict';

angular.module('talarionApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


