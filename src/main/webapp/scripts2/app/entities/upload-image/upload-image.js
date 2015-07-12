'use strict';

angular.module('talarionApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('upload-image', {
                parent: 'entity',
                url: '/upload-image',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/upload-image/upload-image.view.html',
                        controller: 'UploadImageController'
                    }
                }
            });

    });
