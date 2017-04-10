/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'myConstant', 'growl', 'BoxService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, myConstant, growl, BoxService) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;


        vm.reFlashCache = function () {
            $http.get(config.ctx + '/base/cache/reFlashCache')
                .then(function (response) {
                    if (response.data.success) {
                        BoxService.alert("缓存刷新成功!");
                    } else {
                        BoxService.alert("缓存刷新失败!");
                    }
                }, function () {
                    BoxService.alert("缓存刷新失败!");
                })
        }


    }]);


