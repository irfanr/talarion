'use strict';

angular.module('talarionApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('category', {
                parent: 'entity',
                url: '/category',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/category/category-list.view.html',
                        controller: 'CategoryController'
                    }
                }
            }).state('category-create', {
                parent: 'entity',
                url: '/category/create',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/category/category-create.view.html',
                        controller: 'CategoryCreateController'
                    }
                }
            }).state('category-edit', {
                parent: 'entity',
                url: '/category/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/category/category-edit.view.html',
                        controller: 'CategoryEditController'
                    }
                }
            });
    });
