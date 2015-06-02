'use strict';

angular.module('talarionApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
