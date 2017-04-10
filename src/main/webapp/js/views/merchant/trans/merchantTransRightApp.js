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

        vm.reverseFlag = {'1': '正交易', '0': '反交易'};
        vm.splitRightFlag = {'0': '分账交易', '1': '非分账交易'};

        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            MERCHANT_CODE: {},
            TRANS_CODE_ALL: {},
            CONN_CHANNEL: {},
            REFUND_FLAG: {}
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
        vm.deleteItem=deleteItem;


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
            var postData = vm.queryBean;
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
            var temp = vm.queryBean.companyType;
            vm.queryBean = {};
            vm.queryBean.companyType=temp;
        }

        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }

        function addItem() {
            $http.post(config.ctx + '/merchant/trans/right/initResource', $httpParamSerializerJQLike({}))
                .then(function (response) {
                    OpenService({
                        modalTitle: '增加交易权限',
                        modalBody: 'toAdd',
                        url: 'add',
                        item: {
                            treeData: response.data.object
                        },
                        cached: {
                            MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE')
                        }
                    }, function (item) {

                    },"privateMerchantCtrl",'myModalNoFooter.html');
                }, function (response) {
                    alert('访问失败');
                })
        }


        function updateItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }

            var temp = CheckboxService.getCheckedNew(vm.pagination.list)[0].companyCode;
            $log.info("----%s", temp);
            $http.post(config.ctx + '/merchant/trans/right/initResource', $httpParamSerializerJQLike({merchantCode: temp,companyType:vm.queryBean.companyType}))
                .then(function (response) {
                    OpenService({
                        modalTitle: '交易权限权限分配',
                        modalBody: 'toUpdate',
                        url: 'update',
                        item: {
                            merchantCode: temp,
                            treeData: response.data.object,
                            MERCHANT_CODE:vm.cached.MERCHANT_CODE
                        }
                    }, function (item) {

                    }, 'privateMerchantCtrl','myModalNoFooter.html');
                }, function (response) {
                    alert('访问失败');
                })
        }
        function deleteItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post('delete', $httpParamSerializerJQLike({'companyCode': CheckboxService.getCheckedNew(vm.pagination.list)[0].companyCode}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination.list.splice(vm.pagination.list.indexOf(CheckboxService.getCheckedNew(vm.pagination.list)[0]), 1);
                        vm.queryDetail();
                        alert("操作成功");
                    } else {
                        alert("操作失败");
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

    }]);


angular.module('myApp').controller('privateMerchantCtrl',[ '$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', '$timeout',function ($scope, $uibModalInstance, modalItem, $http,
                                                                    $httpParamSerializerJQLike, $log, $timeout) {
    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {
            id: '',
            treeData: {}
        }
    };

    vm = $scope.vm = modalItem;

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
        });

        function initNodes(data, nodes) {
            if (data.nodes && data.nodes.length > 0) {
                data.nodes.forEach(function (item) {
                    initNodes(item, nodes);
                });
            }
            nodes.push({
                id: data.id,
                pId: data.pid,
                name: data.name,
                checked: data.checked
            });
        }

        $.fn.zTree.init(angular.element('.ztree'), setting, zNodes);
    }, 1000);


    $scope.save = function () {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = zTree.getCheckedNodes();

        var param = [];
        if(nodes.length==0){
            alert("操作失败,渠道/订单类型不能为空");
            return false;
        }

        for (var index in nodes){
            var i = {acqOrgCode:vm.item.acqOrgCode,companyType:vm.item.companyType,companyCode:vm.item.merchantCode,transCode:nodes[index].id,connChannel:nodes[index].pId};
            param.push(i);
        }
        $log.debug("交易权限:"+JSON.stringify(param)+"----"+vm.item.companyType);

        $http.post(vm.url, $httpParamSerializerJQLike({
                tempParams:JSON.stringify(param)
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


}]);