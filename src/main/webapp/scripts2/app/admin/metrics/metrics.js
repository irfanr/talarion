'use strict';

angular.module('talarionApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('metrics', {
                parent: 'admin',
                url: '/metrics',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'metrics.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/admin/metrics/metrics.html',
                        controller: 'MetricsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('metrics');
                        return $translate.refresh();
                    }]
                }
            });
    });
