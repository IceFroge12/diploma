/**
 * Created by IO on 20.04.2017.
 */

'use strict';

var app = angular.module('clientApp.song', ['ngResource', 'ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ngProgress', 'ngToast']);

app.factory("Song", function ($resource) {
    return $resource("/api/song/:id")

});

app.controller('songController', function ($scope, $http, Song, ngProgressFactory, ngToast) {
    $scope.isActive = function(route) {
        return route === $location.path();
    }
    let listForDelete = [];

    $scope.songs = Song.query();
    $scope.song = new Song();
    $scope.selected = undefined;
    $scope.progressbar = ngProgressFactory.createInstance();
    $scope.progressbar.setColor('blue');
    $scope.progressbar.setHeight('5px');


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

    $scope.getAlbums = function (val) {
        return $http.get('/api/album/seek', {
            params: {
                regex: val
            }
        }).then(function (response) {
            return response.data.map(function (item) {
                return item;
            });
        });
    };

    $scope.uploadedFile = function (element) {
        $scope.$apply(function ($scope) {
            $scope.files = element.files;
        });
    };

    $scope.addSong = function () {
        let fd = new FormData();

        angular.forEach($scope.files, function (file) {
            fd.append('file', file);
        });

        $scope.song.band = $scope.asyncSelectedBand;
        $scope.song.album = $scope.asyncSelectedAlbum;


        console.log($scope.song);

        fd.append('data', new Blob([angular.toJson($scope.song)], {
            type: "application/json"
        }));

        $http.post('/api/song/addSong', fd, {
            withCredentials: false,
            headers: {
                'Content-Type': undefined
            },
            // transformRequest: angular.identity
        }).then(function (response) {
            $scope.progressbar.complete();
            ngToast.create("Song " + response.data.title + " has just been added")
            $scope.songs.push(response.data)
        }, function (err) {
            console.log(err);
        });
        $scope.progressbar.start();
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

    $scope.delete = function () {
        let req = {
            method: 'POST',
            url: '/api/song/deleteArray',
            data: listForDelete,
            contentType: 'application/json'
        };
        $http(req).then(function () {
            listForDelete.forEach(function (obj) {
                let index = $scope.songs.indexOf(obj);
                $scope.songs.splice(index, 1);
            })
        },function (err) {
            console.log(err);
        });
    };

    $scope.editSong = function () {
        let req = {
            method: 'PUT',
            url: '/api/song',
            data: $scope.edited,
            contentType: 'application/json'

        };
        $http(req).then(function () {

        },function (err) {
            console.log(err);
        });
    };
    //
    // function clearModal() {
    //     $scope.band = new Band();
    //     $scope.newBandForm.$setPristine();
    //     $scope.currentRecord = {};
    // }

});