'use strict';

angular.module('talarionApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('health', {
                parent: 'admin',
                url: '/health',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'health.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/admin/health/health.html',
                        controller: 'HealthController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('health');
                        return $translate.refresh();
                    }]
                }
            });
    });
