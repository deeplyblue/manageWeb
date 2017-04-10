/**
 * Created by jinxin on 2016/12/20.
 * 支付机构分公司信息
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'myConstant', 'dateFilter', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, myConstant, dateFilter) {
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
            MERCHANT_CODE: {},
            CTT_TYPE: {}
        };

        (function initCache() {

            CacheService.initCaches(vm.cached, function () {
                vm.cached.STATUS = {'0': '1', '1': '0'};
            });

        }());

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
        vm.queryBranchInfoDetail=queryBranchInfoDetail;
        vm.updateChecked = updateChecked;
        vm.addItem = addItem;
        vm.updateItem = updateItem;
        vm.deleteItem = deleteItem;

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

        function addItem() {
            OpenService({
                modalTitle: '新增支付机构分公司信息',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                    CTT_TYPE: CacheService.getCache('CTT_TYPE')
                }
            }, function () {

            },'','myModalNoFooter.html');
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
            OpenService({
                modalTitle: '修改支付机构董高监信息',
                modalBody: 'toUpdate',
                url: 'update',
                item: temp,
                cached: {
                    MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                    CTT_TYPE: CacheService.getCache('CTT_TYPE')
                }
            },function(){},"updateInfoManagementModalInstanceCtrl",'myModalNoFooter.html');
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
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

            function queryBranchInfoDetail(bean) {
                var item = angular.copy(bean);
                // item.clrPointDesc='';
                // if(item.clrCycle == '03' || item.clrCycle == '04'){
                //     var c = vm.getCache('CLEAR_VALUE_POINT');
                //     c.forEach(function (it) {
                //         if(it['value'] == item.clrPoint){
                //             item.clrPointDesc = it['key'];
                //         }
                //     });
                // }else if (item.clrCycle == '02' || item.clrCycle == '05'){
                //     var c = vm.getCache('CLEAR_VALUE_POINT_WEEK');
                //     c.forEach(function (it) {
                //         if(it['value'] == item.clrPoint){
                //             item.clrPointDesc = it['key'];
                //         }
                //     });
                // }

                OpenService({
                    modalTitle: '查看明细',
                    modalBody: 'toDetail',
                    url: '#',
                    item: item,
                    cached: {
                        // MERCHANT_CODE: vm.cached.MERCHANT_CODE,
                        // CLR_CYCLE: vm.cached.CLR_CYCLE,
                        // REPORT_TYPE: vm.cached.REPORT_TYPE,
                        // CLR_TYPE: vm.cached.CLR_TYPE,
                        // FEE_CLEAR_TYPE: vm.cached.FEE_CLEAR_TYPE,
                        // CLR_RANGE: vm.cached.CLR_RANGE,
                        // CLEAR_VALUE_POINT: vm.cached.CLEAR_VALUE_POINT,
                        // CLEAR_VALUE_POINT_WEEK: vm.cached.CLEAR_VALUE_POINT_WEEK,
                        // CLR_RULES_1: vm.cached.CLR_RULES_1,
                        // CLR_RULES_2: vm.cached.CLR_RULES_2,
                        // COMANY_CODE: vm.cached.COMANY_CODE
                    }
                }, function (item) {
                }, '', 'myModalNoSave.html');
            }
    }]);


angular.module('myApp').controller('updateInfoManagementModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
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
        vm.postData.createTime = null;
        vm.postData.lastUpdTime = null;
        $log.debug(vm.postData);
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