'use strict';

angular.module('talarionApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('sales-head', {
                parent: 'entity',
                url: '/sales-head',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/sales-head/sales-head-list.view.html',
                        controller: 'SalesHeadController'
                    }
                }
            }).state('sales-head-create', {
                parent: 'entity',
                url: '/sales-head/create',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/sales-head/sales-head-create.view.html',
                        controller: 'SalesHeadCreateController'
                    }
                }
            }).state('sales-head-edit', {
                parent: 'entity',
                url: '/sales-head/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/sales-head/sales-head-edit.view.html',
                        controller: 'SalesHeadEditController'
                    }
                }
            });
    });
