/**
 * Created by 蒯越 on 2016/5/10.
 */
angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'dateFilter', 'myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, dateFilter, myConstant) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        //变量初始化
        //查询条件
        vm.queryBean = {};

        vm.resetForm = resetForm;

        vm.datepicker = {
            dateOptions: {
                formatYear: 'yy',
                startingDay: 1,
                formatDayTitle: 'yyyy MMMM'
            }
        };

        /*------------------以上配置通用---------------------*/


        vm.resetForm = resetForm;
        vm.rollBackDownloadAndChkData = rollBackDownloadAndChkData;
        vm.rollBackChkData = rollBackChkData;
        vm.startDownload = startDownload;
        vm.startCheck = startCheck;
        vm.startForceCheck = startForceCheck;
        vm.startInnerCheck = startInnerCheck;
        vm.startChkFileCreate = startChkFileCreate;
        vm.countBankChkComplete = countBankChkComplete;

        vm.cached = {
            ALL_COMPANY_CODE: {}
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

        /*------------------以上方法名可选择性通用---------------------*/

        function resetForm() {
            vm.queryBean = {};
        }

        function rollBackDownloadAndChkData() {
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post('rollBackDownloadAndChkData', $httpParamSerializerJQLike(
                {'orgCode': vm.queryBean.orgCode, 'settleDate': dateFilter(vm.queryBean.settleDate, 'yyyyMMdd')}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert(data.object);
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function rollBackChkData() {
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post('rollBackChkData', $httpParamSerializerJQLike(
                {'orgCode': vm.queryBean.orgCode, 'settleDate': dateFilter(vm.queryBean.settleDate, 'yyyyMMdd')}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert(data.object);
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function startDownload() {
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post('startDownload', $httpParamSerializerJQLike(
                {'orgCode': vm.queryBean.orgCode, 'settleDate': dateFilter(vm.queryBean.settleDate, 'yyyyMMdd')}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert(data.object);
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function startCheck() {
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post('startCheck', $httpParamSerializerJQLike(
                {'orgCode': vm.queryBean.orgCode, 'settleDate': dateFilter(vm.queryBean.settleDate, 'yyyyMMdd')}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert(data.object);
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function startForceCheck() {
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post('startForceCheck', $httpParamSerializerJQLike(
                {'orgCode': vm.queryBean.orgCode, 'settleDate': dateFilter(vm.queryBean.settleDate, 'yyyyMMdd')}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert(data.object);
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function startInnerCheck() {
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post('startInnerCheck', $httpParamSerializerJQLike(
                {'orgCode': vm.queryBean.orgCode, 'settleDate': dateFilter(vm.queryBean.settleDate, 'yyyyMMdd')}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert(data.object);
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function startChkFileCreate() {
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post('startChkFileCreate', $httpParamSerializerJQLike(
                {'orgCode': vm.queryBean.orgCode, 'settleDate': dateFilter(vm.queryBean.settleDate, 'yyyyMMdd')}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert(data.object);
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function countBankChkComplete() {
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post('countBankChkComplete', $httpParamSerializerJQLike(
                {'settleDate': dateFilter(vm.queryBean.settleDate, 'yyyyMMdd')}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert(data.object);
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }
    }]);
