/**
 * Created by hz on 2016/12/21.
 */
angular.module('myApp')

    .controller('queryCtrl', ['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService', 'CacheService', 'dateFilter', 'myConstant',
        function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService, CacheService, myConstant, dateFilter) {
            //视图层变量viewModel
            var vm = $scope.vm = {};
            vm.constant = myConstant;
            //变量初始化
            //分页数据
            vm.pagination = {
                pageSize: 10,
                pageNum: 1
            };
            //查询条 件
            vm.queryBean = {};

            vm.queryDetail = queryDetail;

            vm.resetForm = resetForm;
            vm.queryCleearCfgDetail = queryCleearCfgDetail;

            /*------------------以上配置通用---------------------*/
            vm.updateChecked = updateChecked;
            vm.addItem = addItem;
            vm.updateItem = updateItem;
            vm.deleteItem = deleteItem;

            /*------------------以上方法名可选择性通用---------------------*/
            //缓存数据初始化(需要缓存的key请自定义)
            vm.cached = {
                CENTER_STATUS_FOR_RESERVE:{},
                MESSAGE_TYPE_FOR_RESERVE: {},
                MAIN_CONN_FOR_RESERVE: {},
                OFF_STATUS_FOR_RESERVE: {},
                CENTER_STATUS_FOR_RESERVE: {}
            };
            CacheService.initCache(vm.cached);

            vm.getCache = function (key) {
                return CacheService.getCache(key);
            };
            function updateChecked($event, id) {
                CheckboxService.updateChecked($event, id);
                $log.debug(id);
                $log.debug(CheckboxService.getChecked());
            }

            function queryDetail() {
                var queryForm = document.getElementById('queryForm');
                vm.url = angular.element(queryForm).prop('action');

                //组织参数：查询条件 + 分页数据
                var postData = vm.queryBean;
                postData.pageSize = vm.pagination.pageSize;
                postData.pageNum = vm.pagination.pageNum;
                $http.post(vm.url, $httpParamSerializerJQLike(postData))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            vm.pagination = data.object;
                            //清空选中记录
                            $log.debug("清空check");
                            CheckboxService.clearChecked();
                        } else {
                            alert(data.msg);
                            CheckboxService.clearChecked();
                        }
                    });

            };

            function resetForm() {
                vm.queryBean = {};
            }


            function queryCleearCfgDetail(bean) {
                var item = angular.copy(bean);

                OpenService({
                    modalTitle: '查看明细',
                    modalBody: 'toDetail',
                    url: '#',
                    item: item,
                    cached: {
                        MESSAGE_TYPE_FOR_RESERVE: vm.cached.MESSAGE_TYPE_FOR_RESERVE,
                        MAIN_CONN_FOR_RESERVE: vm.cached.MAIN_CONN_FOR_RESERVE,
                        OFF_STATUS_FOR_RESERVE: vm.cached.OFF_STATUS_FOR_RESERVE,
                        CENTER_STATUS_FOR_RESERVE: vm.cached.CENTER_STATUS_FOR_RESERVE
                    }
                }, function (item) {
                }, '', 'myModalNoSave.html');
            }

            function addItem() {
                OpenService({
                    modalTitle: '新增支付机构联系人信息',
                    modalBody: 'toAdd',
                    url: 'add',
                    item: {},
                    cached: {
                        MAIN_CONN_FOR_RESERVE: vm.cached.MAIN_CONN_FOR_RESERVE,
                        OFF_STATUS_FOR_RESERVE: vm.cached.OFF_STATUS_FOR_RESERVE
                    }
                }, function (item) {
                    queryDetail();
                }, '', 'myModalNoFooter.html');
            }

            function updateItem() {
                // if (CheckboxService.getChecked().length != 1) {
                //     alert("必须勾选一条记录！");
                //     return;
                // }
                // var temp = vm.pagination.list.filter(function (item, index, array) {
                //     if (item.id == CheckboxService.getChecked()[0]) {
                //         return true;
                //     }
                // })[0];
                if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                    alert("必须勾选一条记录！");
                    return;
                }

                var temp = CheckboxService.getCheckedNew(vm.pagination.list)[0];

                $log.debug("需要修改的数据：", temp);
                OpenService({
                    modalTitle: '修改支付机构联系人信息',
                    modalBody: 'toUpdate',
                    url: 'update',
                    item: temp,
                    cached: {
                        MAIN_CONN_FOR_RESERVE: vm.cached.MAIN_CONN_FOR_RESERVE,
                        OFF_STATUS_FOR_RESERVE: vm.cached.OFF_STATUS_FOR_RESERVE

                    }
                }, function () {
                    vm.queryDetail();
                }, "updateContactInfo", 'myModalNoFooter.html');
            }

            function deleteItem() {
                // if (CheckboxService.getChecked().length != 1) {
                //     alert("必须勾选一条记录！");
                //     return;
                // }
                if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                    alert("必须勾选一条记录！");
                    return;
                }
                if (!confirm("是否确认此操作?")) {
                    return;
                }

                // alert(1);
                $http.post('delete', $httpParamSerializerJQLike({'id': CheckboxService.getCheckedNew(vm.pagination.list)[0].id}))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert(data.msg);
                            vm.queryDetail();
                        } else {
                            alert(data.msg);
                            vm.queryDetail();
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

        }]);

angular.module('myApp').controller('updateContactInfo', ['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', 'dateFilter', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                $httpParamSerializerJQLike, $log, dateFilter, myConstant) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {}
        }

        vm = $scope.vm = modalItem;
        vm.constant = myConstant;
        $scope.save = function () {

            vm.postData = angular.copy(vm.item);
            vm.postData.operateTime = dateFilter(vm.postData.operateTime, 'yyyy-MM-dd');
            vm.postData.updateAt = dateFilter(vm.postData.updateAt, 'yyyy-MM-dd');
            $log.debug("postData", vm.postData);
            $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
                .then(function (response) {
                    if (response.data.success) {
                        alert("操作成功");
                        $uibModalInstance.close(vm.item);
                    } else {
                        alert(response.data.msg);
                    }
                }, function (response) {
                    alert("操作失败");
                    alert(response.status);
                })
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }]);