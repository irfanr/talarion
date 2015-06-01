'use strict';

angular.module('talarionApp')
    .factory('Book', function ($resource) {
        return $resource('api/books/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    if (data.publicationDate != null){
                        var publicationDateFrom = data.publicationDate.split("-");
                        data.publicationDate = new Date(new Date(publicationDateFrom[0], publicationDateFrom[1] - 1, publicationDateFrom[2]));
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
