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
        vm.queryBean = {beginSettleDate:DateCalculateService.getYesterday(),
                        endSettleDate:DateCalculateService.getToday()};

        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            ORDER_STATUS: {},
            CONN_CHANNEL: {},
            REVERSE_FLAG: {},
            MERCHANT_CODE:{},
            MCHNT_TRANS_CODE:{},
            DELETE_STATUS:{},
            COMMON_REFUND_FLAG:{},
            BANK_INFO:{}
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
        vm.queryBizTransDetail = queryBizTransDetail;

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

            //组织参数：查询条件 + 分页数据
            vm.postData = angular.copy(vm.queryBean);
            vm.postData.pageSize = vm.pagination.pageSize;
            vm.postData.pageNum = vm.pagination.pageNum;
            vm.postData.beginOrderDate = dateFilter(vm.postData.beginOrderDate, 'yyyyMMdd');
            vm.postData.endOrderDate = dateFilter(vm.postData.endOrderDate, 'yyyyMMdd');
            vm.postData.beginSettleDate = dateFilter(vm.postData.beginSettleDate, 'yyyyMMdd');
            vm.postData.endSettleDate = dateFilter(vm.postData.endSettleDate, 'yyyyMMdd');
            $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination = data.object;
                        vm.sumObject = data.sumObject;
                        //清空选中记录
                        CheckboxService.clearChecked();
                    } else {
                        alert(data.msg);
                    }
                });

        }

        function resetForm() {
            vm.queryBean = {};
        }

        function queryBizTransDetail(busiNo) {
            var results;
            $http.post(config.ctx + '/mchntplatform/busitransDetail/queryBizTransDetail', $httpParamSerializerJQLike({busiNo: busiNo}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        results = data.object;
                        OpenService({
                            modalTitle: '业务交易查询',
                            modalBody: 'toQueryDetail',
                            url: 'queryDetail',
                            item: {results: results, busiNo: busiNo},
                            cached: vm.cached
                        }, function (iteaddItemm) {

                        }, 'QueryDetailModalInstanceCtrl', 'myModalNoSave.html');
                    } else {
                        alert(data.msg);
                    }
                });
        }

    }]);

angular.module('myApp').controller('QueryDetailModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', function ($scope, $uibModalInstance, modalItem, $http,
                                                                             $httpParamSerializerJQLike) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };
    vm = $scope.vm = modalItem;

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

}]);
