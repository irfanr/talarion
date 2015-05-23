'use strict';

angular.module('punicApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
