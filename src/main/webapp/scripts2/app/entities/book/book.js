'use strict';

angular.module('talarionApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('book', {
                parent: 'entity',
                url: '/book',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'talarionApp.book.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/book/books.html',
                        controller: 'BookController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('book');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bookDetail', {
                parent: 'entity',
                url: '/book/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'talarionApp.book.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts2/app/entities/book/book-detail.html',
                        controller: 'BookDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('book');
                        return $translate.refresh();
                    }]
                }
            });
    });
