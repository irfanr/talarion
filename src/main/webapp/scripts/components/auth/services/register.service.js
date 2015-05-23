'use strict';

angular.module('punicApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


