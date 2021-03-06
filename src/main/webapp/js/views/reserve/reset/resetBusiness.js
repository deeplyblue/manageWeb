/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl', ['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'dateFilter', 'myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                                                                               CacheService, limitToFilter, filterFilter, dateFilter, myConstant) {
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
            vm.cached = {};


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


            vm.callbackMessage = callbackMessage;
            vm.resetNonSettlement=resetNonSettlement;
            vm.resetPayTrans=resetPayTrans;
            vm.resetReport=resetReport;
            vm.resetReportBase=resetReportBase;
            vm.resetDepositSettlement=resetDepositSettlement;
            vm.resetRemitSettlement=resetRemitSettlement;
            vm.initBankBalance = initBankBalance;

            //业务数据

            vm.resetBusiPayTrans=resetBusiPayTrans;
            vm.createBusiBacthNo=createBusiBacthNo;
            vm.dealReqPayTransDetail=dealReqPayTransDetail;
            vm.dealPayTransDetail=dealPayTransDetail;

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
                var postData = angular.copy(vm.queryBean);
                postData.pageSize = vm.pagination.pageSize;
                postData.pageNum = vm.pagination.pageNum;
                $http.post(vm.url, $httpParamSerializerJQLike(postData))
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

            function updateChecked($event, id) {
                CheckboxService.updateChecked($event, id);
                $log.debug(id);
                $log.debug(CheckboxService.getChecked());
            }

            function resetReportBase(){
                $http.post(config.ctx+'/reserve/reset/month/base',{'baseBusinessDate':vm.query.base.baseBusinessDate},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function resetReport(){
                $http.post(config.ctx+'/reserve/reset/month/report',{'reportBusinessDate':vm.query.base.reportBusinessDate},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function resetPayTrans(){
                $http.post(config.ctx+'/reserve/reset/pay/trans',{'startDate':vm.query.base.startDate,'endDate':vm.query.base.endDate},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function resetNonSettlement(){
                $http.post(config.ctx+'/reserve/reset/non/settlement',{'NonBusinessDate':vm.query.base.NonBusinessDate,'accountNo':vm.query.base.accountNo},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function resetDepositSettlement(){
                $http.post(config.ctx+'/reserve/reset/deposit/settlement',{'depositBusinessDate':vm.query.base.depositBusinessDate},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function resetRemitSettlement(){
                $http.post(config.ctx+'/reserve/reset/remit/settlement',{'remitBusinessDate':vm.query.base.remitBusinessDate},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function callbackMessage(){
                var message = vm.item.call.message;
                $http.post(config.ctx+'/reserve/reset/callback',{'message':vm.item.call.message},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }


            function resetBusiPayTrans(){
                $http.post(config.ctx+'/reserve/reset/pay/resetBusiPayTrans',
                    {'startTime':vm.query.base.startTime,'endTime':vm.query.base.endTime},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function createBusiBacthNo(){
                $http.post(config.ctx+'/reserve/reset/pay/createBusiBacthNo',
                    {},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function dealReqPayTransDetail(){
                $http.post(config.ctx+'/reserve/reset/pay/dealReqPayTransDetail',
                    {},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }
            function dealPayTransDetail(){
                $http.post(config.ctx+'/reserve/reset/pay/dealPayTransDetail',
                    {},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function initBankBalance(){
                $http.post(config.ctx+'/reserve/reset/bank/init/balance',
                    {'accountNo':vm.query.base.accountNo,'businessDate':vm.query.base.businessDate},config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error:{}",response);
                    });
            }

        }]);
