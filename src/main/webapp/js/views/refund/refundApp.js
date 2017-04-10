/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'dateFilter', 'myConstant', 'DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, dateFilter, myConstant, DateCalculateService) {
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
        vm.queryBean = {refundBeginDate:DateCalculateService.getYesterday(),
                        refundEndDate:DateCalculateService.getToday()};
        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            ORDER_STATUS: {},
            CONN_CHANNEL: {},
            REFUND_TYPE: {},
            REFUND_STATUS: {},
            MERCHANT_CODE: {},
            COMANY_CODE: {}
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


        // vm.updateChecked = updateChecked;
        // vm.updateItem = updateItem;
        // vm.deleteItem = deleteItem;
        // vm.roleAllocate = roleAllocate;
        vm.onlineRefund = onlineRefund;

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
            vm.url = config.ctx + '/refund/queryRefundList';

            //组织参数：查询条件 + 分页数据
            vm.postData = angular.copy(vm.queryBean);
            vm.postData.pageSize = vm.pagination.pageSize;
            vm.postData.pageNum = vm.pagination.pageNum;
            vm.postData.refundBeginDate = dateFilter(vm.postData.refundBeginDate, 'yyyyMMdd');
            vm.postData.refundEndDate = dateFilter(vm.postData.refundEndDate, 'yyyyMMdd');
            vm.postData.auditBeginDate = dateFilter(vm.postData.auditBeginDate, 'yyyyMMdd');
            vm.postData.auditEndDate = dateFilter(vm.postData.auditEndDate, 'yyyyMMdd');
            vm.postData.proBeginDate = dateFilter(vm.postData.proBeginDate, 'yyyyMMdd');
            vm.postData.proEndDate = dateFilter(vm.postData.proEndDate, 'yyyyMMdd');
            $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination = data.object;
                        vm.sumObject = data.sumObject;
                    } else {
                        alert(data.msg);
                    }
                });

        };

        function resetForm() {
            vm.queryBean = {};
        }

        function onlineRefund(id){
            if(confirm("确认发起退款？")){
                $http.post(config.ctx + '/refund/onlineRefund', $httpParamSerializerJQLike({
                        id: id
                    }))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("处理成功");
                            queryDetail();
                        } else {
                            alert(data.msg);
                            vm.queryDetail();
                        }
                    });
            }
        }


    }]);
