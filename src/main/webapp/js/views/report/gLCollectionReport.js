/**
 * Created by 蒯越 on 2016/5/11.
 */
angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'dateFilter', 'myConstant', 'DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, dateFilter, myConstant, DateCalculateService) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        vm.downCfg = {
            contentType: 'jqLike',
            date: {
                'oDateStart': 'yyyyMMdd',
                'oDateEnd': 'yyyyMMdd'
            }
        };
        //变量初始化
        //分页数据
        vm.pagination = {
            pageSize: 10,
            pageNum: 1
        };
        //查询条件
        vm.queryBean = {
            oDateStart: DateCalculateService.getYesterday(),
            oDateEnd: DateCalculateService.getYesterday()
        };

        vm.validateOptions = {
            blurTrig: true,
            showError: function (elem, errorMessages) {
                // growl.addErrorMessage(errorMessages);
                // angular.element(elem).after('<span>' + errorMessages + '</span>');
            },
            removeError: true
        };

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;
        vm.queryErrDetail = queryErrDetail;

        vm.cached = {
            BANK_INFO: {},
            COMANY_CODE: {},
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

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            vm.postData = vm.queryBean;
            vm.postData.pageSize = vm.pagination.pageSize;
            vm.postData.pageNum = vm.pagination.pageNum;
            vm.postData.sDateStart = dateFilter(vm.queryBean.oDateStart, 'yyyyMMdd');
            vm.postData.sDateEnd = dateFilter(vm.queryBean.oDateEnd, 'yyyyMMdd');
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
            vm.queryBean = {
                oDateStart: DateCalculateService.getYesterday(),
                oDateEnd: DateCalculateService.getYesterday()
            };
        }

        function queryErrDetail(payOrgCode, bankSettleDate) {
            $http.post(config.ctx + '/report/gLCollectionReport/searchErrDetail',
                $httpParamSerializerJQLike({payOrgCode: payOrgCode, sSettleDate: dateFilter(bankSettleDate, 'yyyyMMdd')}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        var results = data.object;
                        OpenService({
                            modalTitle: '差异明细查询',
                            modalBody: config.ctx + '/report/gLCollectionReport/toSearchErrDetail',
                            url: 'searchErrDetail',
                            item: {results: results, payOrgCode: payOrgCode, sSettleDate: dateFilter(bankSettleDate, 'yyyyMMdd')},
                            cached: vm.cached
                        }, function (iteaddItemm) {

                        }, 'QueryErrDetailModalInstanceCtrl', 'myModalNoSave.html');
                    } else {
                        alert(data.msg);
                    }
                });
        }
    }]);

angular.module('myApp').controller('QueryErrDetailModalInstanceCtrl', function ($scope, $uibModalInstance, modalItem, $http,
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
});
