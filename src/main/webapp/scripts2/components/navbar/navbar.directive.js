'use strict';

angular.module('talarionApp').directive('activeMenu',
        function($translate, $locale, tmhDynamicLocale) {
            return {
                restrict: 'A',
                link: function(scope, element, attrs) {
                    var language = attrs.activeMenu;

                    scope.$watch(function() {
                        return $translate.use();
                    }, function(selectedLanguage) {
                        if (language === selectedLanguage) {
                            tmhDynamicLocale.set(language);
                            element.addClass('active');
                        } else {
                            element.removeClass('active');
                        }
                    });
                }
            };
        }).directive('activeLink', function(location) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                var clazz = attrs.activeLink;
                var path = attrs.href;
                path = path.substring(1); // hack because path does bot return
                // including hashbang
                scope.location = location;
                scope.$watch('location.path()', function(newPath) {
                    if (path === newPath) {
                        element.addClass(clazz);
                    } else {
                        element.removeClass(clazz);
                    }
                });
            }
        };
    })
    .controller('UserProfileController', function($scope, $rootScope, Principal) {

        // Principal.identity().then(function(account) {
				// 	console.log('refresh');
        //     $scope.account = account;
        // });

        $rootScope.customer = {
            name: 'Naomi',
            address: '1600 Amphitheatre'
        };
    })
    .directive('userProfileFirstName', function($rootScope, Principal) {
        return {
            restrict: 'A',
            template: $rootScope.account.firstName
                // link: function(rootScope, scope, element, attrs) {
                //
								// 	console.log('refresh1');
                //
                //     Principal.identity().then(function(account) {
                //
                //
								// 			console.log('refresh2');
								// 				scope.account = account;
                //     });
                //
                // }
        };
    });
