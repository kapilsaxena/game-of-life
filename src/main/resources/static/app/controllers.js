(function (angular) {
    var AppController = function ($scope, $http) {

        $scope.newGame = function () {
            $scope.board = init($scope.height, $scope.width);
        };

        $scope.next = function () {
            $http({
                method: 'POST',
                url: '/next',
                data: {
                    board: $scope.board
                }
            }).then(function successCallback(response) {
                $scope.board = response.data.board;
            });
        };


        $scope.toggle = function (row, cell) {
            $scope.board[row][cell] = !$scope.board[row][cell];
        };

        $scope.height = 10;
        $scope.width = 20;
        $scope.newGame();

        function init(height, width) {
            var board = [];
            for (var h = 0; h < height; h++) {
                var row = [];
                for (var w = 0; w < width; w++) {
                    row.push(false);
                }
                board.push(row);
            }
            return board;
        }
    };

    angular.module("myApp.controllers").controller("AppController", AppController);
}(angular));