/**
 * Created by zhangxinhai on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'dateFilter','myConstant','DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, dateFilter,myConstant,DateCalculateService) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        //变量初始化
        //操作条件
        vm.queryBean = {};
        vm.queryBean.clrDate = DateCalculateService.getToday();
        vm.queryBean.clrType = '02';
        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            MERCHANT_CODE: {}
        };

        CacheService.initCache(vm.cached);

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

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            if(!confirm("是否确认操作？")){
                return;
            }
            vm.postData = angular.copy(vm.queryBean);
            if(vm.postData.orgCode == null || vm.postData.orgCode == ''){
                alert("商户不能为空！");
                return;
            }
            $http.post(vm.url, vm.postData,config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination = data.object;
                        alert(data.msg);
                    } else {
                        alert(data.msg);
                    }
                });

        };

        function resetForm() {
            vm.queryBean = {};
            vm.queryBean.clrDate = DateCalculateService.getToday();
            vm.queryBean.clrType = '02';
        }

    }]);