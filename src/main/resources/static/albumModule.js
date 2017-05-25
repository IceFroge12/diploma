/**
 * Created by IO on 24.04.2017.
 */

'use strict';

var app = angular.module('clientApp.album', ['ngResource', 'ngToast']);

app.factory("Album", function ($resource) {
    return $resource("/api/album/:id", {id: '@id', title: '@title'}, {
        query: {method: 'GET', isArray: true},
        save: {method: 'POST'},
        update: {method: 'PUT', hasBody : true}
    });
});

app.config(['ngToastProvider', function(ngToast) {
    ngToast.configure({
        // verticalPosition: 'bottom',
        // horizontalPosition: 'center'
        animation: 'fade'
    });
}]);

app.controller('albumController', function ($scope, $http, Album, ngToast) {
    $scope.isActive = function(route) {
        return route === $location.path();
    }

    $scope.album = new Album();
    let listForDelete = [];

    let fetchData = function () {
        let query = Album.query();
        query.$promise.then(function (data) {
            $scope.albums = data;
        }, function (err) {
            console.log(err);
        });
    };

    $scope.getBands = function (val) {
        return $http.get('/api/band/seek', {
            params: {
                regex: val
            }
        }).then(function (response) {
            return response.data.map(function (item) {
                return item;
            });
        });
    };

    $scope.addAlbum = function () {
        $scope.album.band = $scope.asyncSelectedBand;
        $scope.album.$save(function (data) {
            $scope.albums.push(data);
            ngToast.create("Album \"" + data.title + "\" has been added")
        }, function (err) {
            console.log(err);
        });
        clearModal();
    };

    $scope.delete = function () {

        let req = {
            method: 'POST',
            url: '/api/album/deleteArray',
            data: listForDelete,
            contentType: 'application/json'

        };
        $http(req).then(function () {
            listForDelete.forEach(function (obj) {
                let index = $scope.albums.indexOf(obj);
                $scope.albums.splice(index, 1);
            })
        },function (err) {
            console.log(err);
        });
    };

    $scope.markRecord = function (record) {
        let position = listForDelete.indexOf(record);
        if (position === -1) {
            listForDelete.push(record);
        } else {
            listForDelete.splice(position, 1);
        }
    };

    $scope.markEdit = function (record) {
        $scope.edited = record;
    };

    $scope.editAlbum = function () {
        let req = {
            method: 'PUT',
            url: '/api/album',
            data: $scope.edited, // added =
            contentType: 'application/json' // specify content type

        };
        $http(req).then(function () {

        },function (err) {
            console.log(err);
        });
        clearModal();
    };

    function clearModal() {
        $scope.album = new Album();
        $scope.newBandForm.$setPristine();
        $scope.currentRecord = {};
    }

    fetchData();
});
