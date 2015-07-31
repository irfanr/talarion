'use strict';

angular.module('talarionApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('gallery', {
                parent: 'entity',
                url: '/gallery',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/gallery/gallery-list.view.html',
                        controller: 'GalleryController'
                    }
                }
            });

    });
