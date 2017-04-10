/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'dateFilter', 'myConstant','DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, dateFilter, myConstant,DateCalculateService) {
        //视图层变量viewModel
        var vm = $scope.vm = {};

        vm.constant = myConstant;
        //变量初始化
        //分页数据
        vm.pagination = {
            pageSize: 10,
            pageNum: 1
        };
        vm.sumObject = {};
        //查询条件
        vm.queryBean = {};
        vm.queryBean.bateBegin = DateCalculateService.getToday();
        vm.queryBean.endDate = DateCalculateService.getToday();
        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            PAY_REQ_FLAG: {},
            MERCHANT_CODE: {},
            CLR_TYPE:{},
            REPORT_TYPE: {},
            CLR_TYPE:{},
            COMANY_CODE:{}
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
        vm.toAuditDetail = toAuditDetail;

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            var postData = angular.copy(vm.queryBean);
            postData.pageSize = vm.pagination.pageSize;
            postData.pageNum = vm.pagination.pageNum;
            postData.beginDate = dateFilter(postData.bateBegin, 'yyyyMMdd');
            postData.endDate = dateFilter(postData.endDate, 'yyyyMMdd');
            postData.clrDateBegin = null;
            postData.clrDateEnd = null;
            $http.post(vm.url, $httpParamSerializerJQLike(postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination = data.object;
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert("操作失败");
                });

        };

        function resetForm() {
            vm.queryBean = {};
        }

        function toAuditDetail(handleBatchNo) {
            var pagination;
            $http.post(config.ctx + '/settlement/unityFunding/mchntClearDetailAudit/queryAuditDetail',
                $httpParamSerializerJQLike({
                    handleBatchNo: handleBatchNo
                }))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        pagination = data.object;
                        OpenService({
                            modalTitle: '审核明细【' + handleBatchNo + '】',
                            modalBody: 'toAuditDetail',
                            url: '#',
                            item: pagination,
                            cached: {
                                PAY_REQ_FLAG: vm.cached.PAY_REQ_FLAG,
                                MERCHANT_CODE: vm.cached.MERCHANT_CODE,
                                CLR_TYPE:vm.cached.CLR_TYPE,
                                REPORT_TYPE: vm.cached.REPORT_TYPE,
                                COMANY_CODE:vm.cached.COMANY_CODE

                            }
                        }, function (item) {
                            $log.debug(item);
                            vm.queryDetail();
                        }, 'MchntClearAuditDetailCtrl', 'myModalNoSave.html');
                    } else {
                        alert('未找到相关数据');
                        return false;
                    }
                });
        }
    }]);

angular.module('myApp').controller('MchntClearAuditDetailCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike','dateFilter', '$log', function ($scope, $uibModalInstance, modalItem, $http,
                                                                          $httpParamSerializerJQLike,dateFilter, $log) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {},
        clrFlag: ''
    }

    vm = $scope.vm = modalItem;

    vm.audit = function (handleBatchNo, auditStatus) {
        if(confirm("确认审核？"))
        {

            $http.post(config.ctx + '/settlement/unityFunding/mchntClearDetailAudit/audit',
                $httpParamSerializerJQLike({
                    handleBatchNo: handleBatchNo,
                    auditStatus: auditStatus
                }))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert('操作成功!');
                        $scope.cancel();
                    } else {
                        alert('操作失败!');
                    }
                }, function () {
                    alert('操作失败!');
                })
        }
    }

    vm.auditFail = function (handleBatchNo,clrDate,clrType) {
        if(confirm("确认审核？"))
        {
            vm.url = config.ctx + '/settlement/unityFunding/mchntClearDetailAudit/auditFail';
            //组织参数：查询条件 + 分页数据
            var postData = {};
            postData.handleBatchNo = handleBatchNo;
            postData.clrDate = clrDate;
            postData.clrType = clrType;
            $http.post(vm.url,postData,config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert('操作成功!');
                        $scope.cancel();
                    } else {
                        alert('操作失败!');
                    }
                }, function () {
                    alert('操作失败!');
                })
        }
    }

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);
