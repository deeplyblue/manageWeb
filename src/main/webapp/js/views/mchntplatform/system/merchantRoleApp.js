/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter','myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter,myConstant) {
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
            ROLE_INFO: {},
            ROLE_TYPE: {}
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
        vm.deleteItem = deleteItem;

        /*------------------以上方法名可选择性通用---------------------*/
        vm.resourceAllocate = resourceAllocate;

        function resourceAllocate(id) {
            $http.post(config.ctx + '/mchntplatform/merchantRole/initResource', $httpParamSerializerJQLike({id: id}))
                .then(function (response) {
                    OpenService({
                        modalTitle: '权限分配',
                        modalBody: 'toAllocate',
                        url: 'allocate',
                        item: {
                            id: id,
                            treeData: response.data.object
                        }
                    }, function (item) {

                    }, 'privateModalInstanceCtrl');
                }, function (response) {
                    alert('访问失败');
                })
        }


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
                modalTitle: '新增角色',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    ROLE_TYPE: CacheService.getCache('ROLE_TYPE')
                }
            }, function (item) {
                queryDetail();
            });
        }

        function updateItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }

            OpenService({
                modalTitle: '修改角色',
                modalBody: 'toUpdate',
                url: 'update',
                item: vm.pagination.list.filter(function (item, index, array) {
                    if (item.roleId == CheckboxService.getChecked()[0]) {
                        item.createTime = null;
                        item.lastUpdTime = null;
                        return true;
                    }
                })[0],
                cached: {
                    ROLE_TYPE: CacheService.getCache('ROLE_TYPE')
                }
            });
        }

        function deleteItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            $http.post('delete', $httpParamSerializerJQLike({'roleId': CheckboxService.getChecked()[0]}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        var item = vm.pagination.list.filter(function (item, index, array) {
                            return item.roleId == CheckboxService.getChecked()[0];
                        })[0];
                        vm.pagination.list.splice(vm.pagination.list.indexOf(item), 1);
                        alert("操作成功");
                    } else {
                        alert("操作失败");
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }
    }])
;


angular.module('myApp').controller('privateModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', '$timeout', function ($scope, $uibModalInstance, modalItem, $http,
                                                                         $httpParamSerializerJQLike, $log, $timeout) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {
            id: '',
            treeData: {}
        }
    }

    vm.expandNode = expandNode;

    vm = $scope.vm = modalItem;

    $scope.save = function () {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = zTree.getCheckedNodes();
        var ids = nodes.map(function (item) {
            return item.id;
        });

        $http.post(vm.url, $httpParamSerializerJQLike({
                id: vm.item.id,
                ids: ids
            }))
            .then(function (response) {
                if (response.data.success) {
                    alert("操作成功");
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


    $timeout(function () {
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        /*var zNodes = [
         {id: 1, pId: 0, name: "随意勾选 1", open: true},
         {id: 11, pId: 1, name: "随意勾选 1-1", open: true},
         {id: 111, pId: 11, name: "随意勾选 1-1-1"},
         {id: 112, pId: 11, name: "随意勾选 1-1-2"},
         {id: 12, pId: 1, name: "随意勾选 1-2", open: true},
         {id: 121, pId: 12, name: "随意勾选 1-2-1"},
         {id: 122, pId: 12, name: "随意勾选 1-2-2"},
         {id: 2, pId: 0, name: "随意勾选 2", checked: true, open: true},
         {id: 21, pId: 2, name: "随意勾选 2-1"},
         {id: 22, pId: 2, name: "随意勾选 2-2", open: true},
         {id: 221, pId: 22, name: "随意勾选 2-2-1", checked: true},
         {id: 222, pId: 22, name: "随意勾选 2-2-2"},
         {id: 23, pId: 2, name: "随意勾选 2-3"}
         ];*/

        var zNodes = [];

        vm.item.treeData.forEach(function (item) {
            initNodes(item, zNodes);
        })

        function initNodes(data, nodes) {
            if (data.nodes && data.nodes.length > 0) {
                data.nodes.forEach(function (item) {
                    initNodes(item, nodes);
                });
            }
            nodes.push({
                id: data.rsrcCode,
                pId: data.parentRsrcCode,
                name: data.text,
                checked: data.state ? data.state.checked : false
            });
        }

        $.fn.zTree.init(angular.element('.ztree'), setting, zNodes);
        $("#expandAllBtn").bind("click", {type: "expandAll"}, expandNode);
        $("#collapseAllBtn").bind("click", {type: "collapseAll"}, expandNode);
    }, 1000);

    function expandNode(e) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            type = e.data.type,
            nodes = zTree.getSelectedNodes();
        if (type.indexOf("All") < 0 && nodes.length == 0) {
            alert("请先选择一个父节点");
        }

        if (type == "expandAll") {
            zTree.expandAll(true);
        } else if (type == "collapseAll") {
            zTree.expandAll(false);
        }
    }

}]);
