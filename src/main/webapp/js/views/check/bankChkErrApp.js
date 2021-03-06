/**
 * Created by 蒯越 on 2016/5/11.
 */
angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'dateFilter', 'myConstant', 'DateCalculateService',  function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, dateFilter, myConstant, DateCalculateService) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;

        vm.downCfg = {
            contentType : 'jqLike',
            date : {
                'oDateStart' : 'yyyyMMdd',
                'oDateEnd' : 'yyyyMMdd',
                'oHandleDateStart' : 'yyyyMMddHHmmss',
                'oHandleDateEnd' : 'yyyyMMddHHmmss'
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
            oDateEnd: DateCalculateService.getToday()
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
        vm.handle = handle;
        vm.batchHandle = batchHandle;

        vm.cached = {
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
            vm.postData.sHandleDateStart = dateFilter(vm.queryBean.oHandleDateStart, 'yyyyMMdd');
            vm.postData.sHandleDateEnd = dateFilter(vm.queryBean.oHandleDateEnd, 'yyyyMMdd');
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
                oDateEnd: DateCalculateService.getToday()
            };
        }

        function handle(id, handleType) {

            if (!confirm("确认操作？")) {
                return;
            }

            $http.post('handle', $httpParamSerializerJQLike({'id': id, 'handleType': handleType}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.queryDetail();
                        alert("操作成功");
                    } else {
                        alert("操作失败:" + data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function batchHandle(handleType) {
            var list = CheckboxService.getCheckedNew(vm.pagination.list);
            if (list.length == 0) {
                alert("请选择记录！");
                return;
            }
            var ids = [];
            for (var i = 0; i < list.length; i++) {
                var err = list[i];
                if (err.button && err.button.indexOf(handleType) >= 0) {
                    ids.push(err.id);
                }
            }
            var handleTypeDesc;
            if (handleType == "02") {
                handleTypeDesc = "补业务";
            } else if (handleType == "03") {
                handleTypeDesc = "退银行";
            } else {
                alert("操作方式参数错误!!");
                return;
            }
            if (!confirm("确认批量" + handleTypeDesc + "？已选中【" + list.length + "】笔差异,其中可" + handleTypeDesc + "差异为【" + ids.length + "】笔")) {
                return;
            }
            $http.post(config.ctx + '/check/bankChkErr/batchHandle',
                $httpParamSerializerJQLike({
                    "ids": ids,
                    "handleType": handleType
                })).then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert("批量" + handleTypeDesc + "处理成功!!请在页面查看结果");
                        $scope.cancel();
                    } else {
                        alert("批量" + handleTypeDesc + "处理失败!!");
                    }
                }, function () {
                    alert('批量处理失败!');
                })
        }
    }]);
