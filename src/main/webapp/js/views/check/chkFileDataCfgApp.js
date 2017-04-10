/**
 * Created by 蒯越 on 2016/5/30.
 */
angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, myConstant) {
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
            FILE_TEMPLATE_NO: {},
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

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;

        /*------------------以上配置通用---------------------*/


        vm.updateChecked = updateChecked;
        vm.addItem = addItem;
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
        }

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
                modalTitle: '新增对账文件模板',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {}
            }, function (item) {
                vm.pagination.list.push(item);
            }, "chkFileDataCfgModalInstanceCtrl",'myModalNoFooter.html');
        }

        function updateItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }

            OpenService({
                modalTitle: '修改对账文件模板',
                modalBody: 'toUpdate',
                url: 'update',
                item: CheckboxService.getCheckedNew(vm.pagination.list)[0],
                cached: {}
            }, function () {

            }, "updateChkFileDataCfgModalInstanceCtrl");
        }
    }]);

angular.module('myApp').controller('chkFileDataCfgModalInstanceCtrl',[ '$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log','myConstant',function ($scope, $uibModalInstance, modalItem, $http,
                                                                                $httpParamSerializerJQLike, $log,myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;

    vm.chkFileDataField = [];
    vm.chkFileDataField[0] = {"field": "", "seqNo": ""};
    vm.chkFileDataField[1] = {"field": "", "seqNo": ""};

    vm.addChkFileDataField = function addChkFileDataField(obj) {
        vm.chkFileDataField.push({"field": "", "seqNo": ""});
    };

    vm.deleteChkFileDataField = function deleteChkFileDataField(index) {
        if (index == 0) {
            alert("第一条信息不能删除!");
        } else {
            vm.chkFileDataField.remove(index);
        }
    };
    Array.prototype.remove = function (dx) {
        if (isNaN(dx) || dx > this.length) {
            return false;
        }
        for (var i = 0, n = 0; i < this.length; i++) {
            if (this[i] != this[dx]) {
                this[n++] = this[i]
            }
        }
        this.length -= 1
    };

    $scope.save = function () {
        var postData = angular.copy(vm.item);
        postData.fieldDTOList = vm.chkFileDataField;
        $http.post(vm.url, postData, config.jsonHeader)
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

angular.module('myApp').controller('updateChkFileDataCfgModalInstanceCtrl',[ '$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log','myConstant',function ($scope, $uibModalInstance, modalItem, $http,
                                                                                      $httpParamSerializerJQLike, $log,myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;

    vm.addChkFileDataField = function addChkFileDataField(obj) {
        vm.item.fieldDTOList.push({"field": "", "seqNo": ""});
    };

    vm.deleteChkFileDataField = function deleteChkFileDataField(index) {
        if (index == 0) {
            alert("第一条信息不能删除!");
        } else {
            vm.item.fieldDTOList.remove(index);
        }
    };
    Array.prototype.remove = function (dx) {
        if (isNaN(dx) || dx > this.length) {
            return false;
        }
        for (var i = 0, n = 0; i < this.length; i++) {
            if (this[i] != this[dx]) {
                this[n++] = this[i]
            }
        }
        this.length -= 1
    };

    $scope.save = function () {
        var postData = angular.copy(vm.item);
        $http.post(vm.url, postData, config.jsonHeader)
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