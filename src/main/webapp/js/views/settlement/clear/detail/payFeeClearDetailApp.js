/**
 * Created by zhangxinhai on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'dateFilter','myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, dateFilter,myConstant) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        vm.downCfg = {
            contentType : 'jqLike',
            date : {
                'beginDate' : 'yyyyMMdd',
                'endDate' : 'yyyyMMdd'
            }
        }
        //变量初始化
        //分页数据
        vm.pagination = {
            pageSize: 10,
            pageNum: 1
        };
        vm.sumObject = {};
        //查询条件
        vm.queryBean = {};

        vm.queryBean.beginDate = new Date();
        vm.queryBean.endDate = new Date();

        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            BANK_INFO:{},
            BANK_CARD_TYPE:{},
            CONN_CHANNEL:{},
            TRANS_CODE_ALL:{},
            CLR_FLAG: {},
            CLR_TYPE: {},
            FEE_CLEAR_TYPE:{},
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
        vm.redirectPayClearDay = redirectPayClearDay;

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
            vm.queryBean.clrType = "03";
            //组织参数：查询条件 + 分页数据
            vm.postData = angular.copy(vm.queryBean);
            vm.postData.pageSize = vm.pagination.pageSize;
            vm.postData.pageNum = vm.pagination.pageNum;
            vm.postData.beginDate = dateFilter(vm.postData.beginDate, 'yyyyMMdd');
            vm.postData.endDate = dateFilter(vm.postData.endDate, 'yyyyMMdd');
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

        };

        function resetForm() {
            vm.queryBean = {};
        }

        function redirectPayClearDay(bean) {

            var results;
            vm.url = config.ctx + "/settlement/clear/detail/payClearDetail/getPayClearDay";

            //组织参数
            vm.postData = {};
            vm.postData.orgCode = bean.orgCode;
            vm.postData.settleDateBegin = dateFilter(bean.settleDateBegin, 'yyyyMMdd');
            vm.postData.settleDateEnd = dateFilter(bean.settleDateEnd, 'yyyyMMdd');
            $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        results = data.object.list;
                        OpenService({
                            modalTitle: '支付手续费结算查询-日结',
                            modalBody: 'redirectPayFeeClearDay',
                            url: 'allocate',
                            item: {
                                results: results
                            },
                            cached:vm.cached
                        }, function (item) {
                            $log.debug(item);
                        }, 'PayFeeClearDayModalInstanceCtrl');
                    } else {
                        alert(data.msg);
                    }
                });
        }
    }]);


angular.module('myApp').controller('PayFeeClearDayModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    'dateFilter','$httpParamSerializerJQLike', function ($scope, $uibModalInstance, modalItem, $http,
                                                                             dateFilter,$httpParamSerializerJQLike) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }

    vm = $scope.vm = modalItem;
    //分页数据
    vm.pagination = {
        pageSize: 10,
        pageNum: 1
    };
    vm.sumObject = {};

    $scope.save = function () {
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    vm.redirectPaySettleDetail = redirectPaySettleDetail;
    vm.queryDetail = queryDetail;

    function redirectPaySettleDetail(bean,bool) {
        if(bool == true && vm.detailFlag == true){
            vm.detailFlag = false;
            return;
        }
        //显示隐藏div
        vm.detailFlag = true;
        vm.url = config.ctx + "/settlement/clear/detail/payClearDetail/redirectPaySettleDetail";
        //组织参数
        vm.postData = {};
        vm.postData.orgCode = bean.orgCode;
        vm.postData.settleDate = dateFilter(bean.settleDate, 'yyyyMMdd');
        vm.postData.pageSize = vm.pagination.pageSize;
        vm.postData.pageNum = vm.pagination.pageNum;
        $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    vm.pagination = data.object;
                    vm.sumObject = data.sumObject;
                    vm.queryBean = bean;
                } else {
                    alert(data.msg);
                }
            });

    };

    function queryDetail() {
        redirectPaySettleDetail(vm.queryBean,false);
    }
}]);

