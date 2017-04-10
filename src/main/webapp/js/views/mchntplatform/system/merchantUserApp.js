/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl', ['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'myConstant', 'growl', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                                                                          CacheService, limitToFilter, filterFilter, myConstant, growl) {
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

            vm.validateOptions = {
                blurTrig: true,
                showError: function (elem, errorMessages) {
                    growl.addErrorMessage(errorMessages);
                    // angular.element(elem).after('<span>' + errorMessages + '</span>');
                },
                removeError: true
            };

            //缓存数据初始化(需要缓存的key请自定义)
            /*数据格式{
             key1 :value1,
             key2:value2
             }*/
            vm.cached = {
                USER_TYPE: {},
                ROLE_INFO: {},
                USER_STATUS: {},
                DPET_NAME: {},
                MERCHANT_CODE: {},
                ROLE_INFO_02: {}
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

            vm.addItem = addItem;
            vm.updateItem = updateItem;
            vm.deleteItem = deleteItem;
            vm.auditItem = auditItem;
            vm.roleAllocate = roleAllocate;
            vm.resetPwd = resetPwd;

            /*------------------以上方法名可选择性通用---------------------*/

            /*CacheService.initCache(vm.cached, function (cacheKey, cacheObj) {
             $log.debug(cacheKey, cacheObj);
             vm.cached[cacheKey] = cacheObj;
             });*/

            /*vm.getCache = function (key) {
             CacheService.getCache(key)
             }*/

            function queryDetail() {
                // vm.url = config.ctx + 'mchntplatform/system/merchantUser';
                var queryForm = document.getElementById('queryForm');
                vm.url = angular.element(queryForm).prop('action');
                //组织参数：查询条件 + 分页数据
                vm.postData = vm.queryBean;
                vm.postData.pageSize = vm.pagination.pageSize;
                vm.postData.pageNum = vm.pagination.pageNum;
                $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
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

            function addItem() {
                OpenService({
                    modalTitle: '新增用户',
                    modalBody: 'toAdd',
                    url: 'add',
                    item: {},
                    cached: {
                        USER_TYPE: CacheService.getCache('USER_TYPE'),
                        DPET_NAME: CacheService.getCache('DPET_NAME'),
                        MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE')
                    }
                }, function (item) {
                    if (vm.pagination && vm.pagination.list) {
                        queryDetail();
                    }
                });
            }

            function updateItem() {
                if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                    alert("必须勾选一条记录！");
                    return;
                }

                var item = CheckboxService.getCheckedNew(vm.pagination.list)[0];
                item.userRoles = null;
                item.auditDate = null;
                item.createTime = null;
                item.lastLoginTime = null;
                item.lastUpdTime = null;
                item.pwdLastUpd = null;
                item.statusLastUpd = null;


                OpenService({
                    modalTitle: '修改用户',
                    modalBody: 'toUpdate',
                    url: 'update',
                    item: item,
                    cached: {
                        USER_TYPE: CacheService.getCache('USER_TYPE'),
                        DPET_NAME: CacheService.getCache('DPET_NAME'),
                        MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE')
                    }
                });
            }

            function deleteItem() {
                if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                    alert("必须勾选一条记录！");
                    return;
                }
                $http.post('delete', $httpParamSerializerJQLike({'userId': CheckboxService.getCheckedNew(vm.pagination.list)[0].userId}))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            vm.pagination.list.splice(vm.pagination.list.indexOf(CheckboxService.getCheckedNew(vm.pagination.list)[0]), 1);
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function auditItem(userStatus) {
                if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                    alert("必须勾选一条记录！");
                    return;
                }
                $http.post('audit', $httpParamSerializerJQLike({
                    'userId': CheckboxService.getCheckedNew(vm.pagination.list)[0].userId,
                    'userStatus': userStatus
                }))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("操作成功");
                            queryDetail();
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }

            function roleAllocate(user) {
                OpenService({
                    modalTitle: '角色分配',
                    modalBody: 'toAllocate',
                    url: 'allocate',
                    item: {
                        user: user,
                        roles: user.userRoles.map(function (item) {
                            return item.roleId;
                        })
                    },
                    cached: {
                        ROLE_INFO_02: CacheService.getCache('ROLE_INFO_02')
                    }
                }, function (item) {
                    vm.queryDetail();
                }, 'RoleAllocateModalInstanceCtrl');
            }

            function resetPwd(user) {
                OpenService({
                    modalTitle: '密码重置',
                    modalBody: 'toResetPwd',
                    url: 'resetPwd',
                    item: {
                        userName: user.userName,
                        userId: user.userId
                    }
                }, function () {
                    vm.queryDetail();
                }, 'ResetPwdModalInstanceCtrl', 'myModalNoFooter.html')
            }
        }]);

angular.module('myApp').controller('RoleAllocateModalInstanceCtrl', ['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', function ($scope, $uibModalInstance, modalItem, $http,
                                            $httpParamSerializerJQLike) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {}
        }

        vm.item.roles = [];
        vm = $scope.vm = modalItem;

        vm.toggleCheck = function (event, key) {
            if (event.target.checked) {
                vm.item.roles.push(key);
            } else {
                vm.item.roles.splice(vm.item.roles.indexOf(key), 1);
            }
        }

        $scope.save = function () {
            $http.post(vm.url, $httpParamSerializerJQLike(
                {
                    userId: vm.item.user.userId,
                    roles: vm.item.roles
                }))
                .then(function (response) {
                    if (response.data.success) {
                        alert("操作成功");
                        $scope.cancel();
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

angular.module('myApp').controller('ResetPwdModalInstanceCtrl', ['$scope', '$uibModalInstance', 'modalItem', '$http'
    , 'myConstant', function ($scope, $uibModalInstance, modalItem, $http, myConstant) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {}
        }

        vm = $scope.vm = modalItem;
        vm.constant = myConstant;

        $scope.save = function () {
            $http.post(vm.url, {
                userId: vm.item.userId,
                userPwd: vm.item.userPwd
            }, config.jsonHeader)
                .then(function (response) {
                    if (response.data.success) {
                        alert("操作成功");
                        $scope.cancel();
                    } else {
                        alert("操作失败");
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
