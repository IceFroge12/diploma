/**
 * Created by IO on 20.04.2017.
 */

'us strict'

var app = angular.module('clientApp.band', ['ngResource', 'ngToast']);

app.factory("Band", function ($resource) {
    return $resource("/api/band/:id", {id: '@id', title: '@title'}, {
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

app.controller('bandController', function ($scope, $http, Band, ngToast) {
    $scope.isActive = function(route) {
        return route === $location.path();
    }

    $scope.band = new Band();
    let listForDelete = [];

    let fetchData = function () {
        let query = Band.query();
        query.$promise.then(function (data) {
            $scope.bands = data;
        }, function (err) {
            console.log(err);
        });
    };

    $scope.addBand = function () {
        $scope.band.$save(function (data) {
            $scope.bands.push(data);
            ngToast.create("Band " + data.title + "has been added")
        }, function (err) {
            console.log(err);
        });
        clearModal();
    };

    $scope.delete = function () {

        let req = {
            method: 'POST',
            url: '/api/band/deleteArray',
            data: listForDelete,
            contentType: 'application/json'

        };
        $http(req).then(function () {
                listForDelete.forEach(function (obj) {
                    let index = $scope.bands.indexOf(obj);
                    $scope.bands.splice(index, 1);
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

    $scope.editBand = function () {
        let req = {
            method: 'PUT',
            url: '/api/band',
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
        $scope.band = new Band();
        $scope.newBandForm.$setPristine();
        $scope.currentRecord = {};
    }

    fetchData();
});

