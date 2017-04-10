/**
 * Created by wuxg on 2016/6/15.
 */

angular.module('myApp')
    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter','dateFilter','myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                                                                             CacheService, limitToFilter, filterFilter,dateFilter,myConstant) {
            //视图层变量viewModel
            var vm = $scope.vm = {};
            vm.constant = myConstant;
            //变量初始化
            //分页数据
            vm.pagination = {
                pageSize: 10,
                pageNum: 1
            };
            //查询条件
            vm.queryBean = {};

            //缓存数据初始化(需要缓存的key请自定义)
            /*数据格式{
             key1 :value1,
             key2:value2
             }*/
            vm.cached = {
            };

            (function initCache() {
                for (var cacheKey in vm.cached) {
                    CacheService.initCache(cacheKey, function (cacheKey, cacheObj) {
                        $log.debug(cacheKey, cacheObj);
                        vm.cached[cacheKey] = cacheObj;
                    })
                }
            }());

            /*将前面缓存的数据格式化
             [{key:value},{key:value}]*/
            vm.getCache = function (key) {
                return CacheService.getCache(key);
            };

            //获取对象数据[user,user,user]
            vm.getObj = function (key, input) {
                return $http.post(config.ctx + '/base/cache/getAll',
                    $httpParamSerializerJQLike({cacheKey: key}))
                    .then(function (response) {
                        return limitToFilter(filterFilter(response.data.object, input), 6);
                    }, function (response) {
                        $log.error('获取数据%s失败', cacheKey);
                    })
            };

            vm.queryDetail = queryDetail;
            vm.resetForm = resetForm;

            /*------------------以上配置通用---------------------*/



            /*------------------以上方法名可选择性通用---------------------*/

            /*CacheService.initCache(vm.cached, function (cacheKey, cacheObj) {
             $log.debug(cacheKey, cacheObj);
             vm.cached[cacheKey] = cacheObj;
             });*/

            /*vm.getCache = function (key) {
             CacheService.getCache(key)
             }*/

            //查询机构号
            vm.BANK_CODE={}
            $http.post(config.ctx + '/reserve/dayEnd/excepRestatements/getOrgCache')
                .then(function (response) {
                    vm.BANK_CODE = response.data.object;
                    $log.debug(vm.BANK_CODE);
                }, function (response) {
                    $log.error('获取数据%s失败', '关联机构号');
                })

            //数据格式化格式化
            vm.getBankCode = function () {
                var arr = [];
                for (var temp in vm.BANK_CODE) {
                    arr.push({'key': temp, 'value':temp});
                }
                return arr;
            };
            function queryDetail() {
                var queryForm = document.getElementById('queryForm');
                vm.url = angular.element(queryForm).prop('action');

                //组织参数：查询条件 + 分页数据
                var postData = angular.copy(vm.queryBean);
                postData.pageSize = vm.pagination.pageSize;
                postData.pageNum = vm.pagination.pageNum;
                vm.pagination.queryBean = postData;
                $http.post(vm.url, vm.pagination,config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            vm.pagination = data.object;
                            //清空选中记录
                            CheckboxService.clearChecked();
                        } else {
                            alert(data.msg);
                        }
                    });

            };

            function resetForm() {
                vm.queryBean = {};
            }





        }]);
