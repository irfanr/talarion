'use strict';

angular.module('talarionApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('image', {
                parent: 'entity',
                url: '/image',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/image/image-list.view.html',
                        controller: 'ImageController'
                    }
                }
            }).state('image-create', {
                parent: 'entity',
                url: '/image/create',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/image/image-create.view.html',
                        controller: 'ImageCreateController'
                    }
                }
            });

    });
