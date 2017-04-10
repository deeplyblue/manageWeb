/**
 * Created by yutao on 2016/12/21.
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
                HANDLE_STATUS_FOR_RESERVE:{},
                DAY_END_REPORT_STATUS:{},
                DELETE_STATUS:{}
            };

            (function initCache() {
                for (var cacheKey in vm.cached) {
                    CacheService.initCache(cacheKey, function (cacheKey, cacheObj) {
                        $log.debug(cacheKey, cacheObj);
                        vm.cached[cacheKey] = cacheObj;
                    })
                }
            }());

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



            vm.queryDetail = queryDetail;
            vm.resetForm = resetForm;

            /*------------------以上配置通用---------------------*/


            vm.updateChecked = updateChecked;
            vm.addItem = addItem;
            vm.deleteItem = deleteItem;
            vm.updateItem = updateItem;

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

            function updateChecked($event, id) {
                CheckboxService.updateChecked($event, id);
                $log.debug(id);
                $log.debug(CheckboxService.getChecked());
            }

            function addItem() {
                OpenService({
                    modalTitle: '新增日终异常重报申请',
                    modalBody: 'toAdd',
                    url: 'add',
                    item: {},
                    BANK_CODE: vm.BANK_CODE
                }, function (item) {
                    //vm.pagination.list.push(item);
                    vm.queryDetail();
                },'excepRestatementsModalInstanceCtrl','myModalNoFooter.html');
            }


            function updateItem() {


                if (CheckboxService.getChecked().length != 1) {
                    alert("必须勾选一条记录！");
                    return;
                }
                var temp = vm.pagination.list.filter(function (item, index, array) {
                    if (item.id == CheckboxService.getChecked()[0]) {
                        return true;
                    }
                })[0];
                if(temp.replyStatus == null) {
                    OpenService({
                        modalTitle: '修改日终重报申请信息',
                        modalBody: 'toUpdate',
                        url: 'update',
                        item: temp,
                        BANK_CODE: vm.BANK_CODE
                    }, function (item) {
                        $log.debug(item);
                        vm.queryDetail();
                    }, 'excepRestatementsModalInstanceCtrl', 'myModalNoFooter.html');
                }else{
                    alert("人行已处理成功不允许修改，可重新申请");
                }
            }



            function deleteItem() {
                if (CheckboxService.getChecked().length != 1) {
                    alert("必须勾选一条记录！");
                    return;
                }
                $http.post('delete', $httpParamSerializerJQLike({'id': CheckboxService.getChecked()[0]}))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            var item = vm.pagination.list.filter(function (item, index, array) {
                                return item.id == CheckboxService.getChecked()[0];
                            })[0];
                            vm.pagination.list.splice(vm.pagination.list.indexOf(item), 1);
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        //alert(response.statusText);
                        $log.debug("error");
                    });
            }
        }]);


angular.module('myApp').controller('excepRestatementsModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                  $httpParamSerializerJQLike, $log, myConstant) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {}
        }

        vm = $scope.vm = modalItem;

        vm.constant = myConstant;
        $log.debug(vm.BANK_CODE);

        //数据格式化格式化
        vm.getBankCode = function () {
            var arr = [];
            for (var temp in vm.BANK_CODE) {
                arr.push({'key': temp, 'value':temp});
            }
            return arr;
        };
        $scope.save = function () {
            $http.post(vm.url, vm.item,config.jsonHeader)
                .then(function (response) {
                    if (response.data.success) {
                        alert(response.data.msg);
                        $uibModalInstance.close(vm.item);
                    } else {
                        alert(response.data.msg);
                    }
                }, function (response) {
                    alert("操作失败");
                })
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
        //字符长度限制500
        $scope.checkText = function () {
            if ($scope.vm.item.applyInfo.length > 500) {
                alert("字符不能超过500");
                $scope.vm.item.applyInfo = $scope.vm.item.applyInfo.substr(0, 500);
            }
        };
    }]);