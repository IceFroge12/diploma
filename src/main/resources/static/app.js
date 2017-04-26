angular.module('clientApp', [
    'ngRoute',
    'clientApp.band',
    'clientApp.song',
    'clientApp.album'
])
    .config(function ($routeProvider, $locationProvider) {


        $routeProvider
            .when('/', {
                templateUrl: 'songPanel.html',
                controller: 'songController',

            })
            .when('/song', {
                templateUrl: '/songModule/songPanel.html',
                controller: 'songController',

            })
            .when('/band', {
                templateUrl: '/bandModule/bandPanel.html',
                controller: 'bandController',

            })
            .when('/album',{
                templateUrl: '/albumModule/albumPanel.html',
                controller: 'albumController',

            })
            .otherwise(
                {redirectTo: '/'}
            );

        $locationProvider.hashPrefix('!');
    })
    .controller('indexController', function ($scope, $location) {
        $scope.isActive = function(route) {
            return route === $location.path();
        }
    });

