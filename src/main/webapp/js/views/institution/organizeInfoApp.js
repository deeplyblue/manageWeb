/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService','myConstant','dateFilter', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService,myConstant,dateFilter) {
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

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;

        /*------------------以上配置通用---------------------*/


        vm.updateChecked = updateChecked;
        vm.addItem = addItem;
        vm.updateItem = updateItem;
        vm.deleteItem = deleteItem;
        vm.updateItemEnableFlag = updateItemEnableFlag;
        /*------------------以上方法名可选择性通用---------------------*/

        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached ={
            ORG_CATEGORY:{},
            ORG_TYPR:{},
            ORG_STATUS:{},
            COMANY_CODE:{},
            PAY_BANK_CODE:{},
            ORG_CHANNEL_TYPE:{},


        };
        (function initCache() {

            CacheService.getObj('ALL_CITY',function(key,cacheObj){
                $log.debug(cacheObj);
                vm.cached['ALL_CITY'] = cacheObj;
            })
            CacheService.initCaches(vm.cached);
        }());


        vm.getCache = function (key) {
            return CacheService.getCache(key);
        };
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
                modalTitle: '新增机构信息',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    ORG_TYPR: CacheService.getCache('ORG_TYPR'),
                    ALL_CITY: vm.cached.ALL_CITY,
                    PAY_BANK_CODE:CacheService.getCache('PAY_BANK_CODE'),
                    ORG_CHANNEL_TYPE:CacheService.getCache('ORG_CHANNEL_TYPE')
                }
            }, function (item) {
                queryDetail();
            },'updateOrganizeInstanceCtrl','myModalNoFooter.html');
        }

        function updateItemEnableFlag(item,bean) {
            var postData = angular.copy(item);
            postData.createTime = null;
            postData.lastUpdTime=null;
            postData.orgConnDate=null;
            postData.orgStatus = item.orgStatus;

            $http.post(config.ctx+"/institution/payment/update", $httpParamSerializerJQLike(postData))
                .then(function (response) {
                    if (response.data.success) {
                        item.orgStatus = postData.orgStatus;
                        alert("操作成功");
                    } else {
                        alert(response.data.msg);
                    }
                }, function (response) {
                    alert("操作失败");
                    alert(response.status);
                })

        }



        function updateItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var pro;
            var city;

            var item = vm.pagination.list.filter(function (item, index, array) {
                if (item.orgCode == CheckboxService.getChecked()[0]) {
                    console.debug(item.orgArea);
                    // alert(item.orgArea);
                    pro = vm.cached.ALL_CITY.filter(function (it) {
                        if (item.orgArea.substring(0,2) && it.areaCode == item.orgArea.substring(0,2)){
                            return true;
                        }
                    })[0];
                    city = pro.citys.filter(function (it) {
                        if (item.orgArea.substring(2,4) && it.cityCode == item.orgArea.substring(2,4)){
                            return true;
                        }
                    })[0];
                    return true;
                }

            })[0];
            // item.createTime = null;
            // item.lastUpdTime=null;
            // item.orgConnDate=dateFilter(item.orgConnDate,'yyyyMMdd');
            OpenService({
                modalTitle: '修改机构信息',
                modalBody: 'toUpdate',
                url: 'update',
                item: item,
                pro:pro,
                city:city,
                cached: {
                    ALL_CITY: vm.cached.ALL_CITY,
                    ORG_TYPR: CacheService.getCache('ORG_TYPR'),
                    PAY_BANK_CODE:CacheService.getCache('PAY_BANK_CODE'),
                    ORG_CHANNEL_TYPE:CacheService.getCache('ORG_CHANNEL_TYPE')
                }
            },function(item){
                $log.debug(item);
            }  ,'updateOrganizeInstanceCtrl','myModalNoFooter.html');

        }

        function deleteItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            $http.post('delete', $httpParamSerializerJQLike({'orgCode': CheckboxService.getChecked()[0]}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        var item = vm.pagination.list.filter(function (item, index, array) {
                            return item.orgCode == CheckboxService.getChecked()[0];
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
    }]);

angular.module('myApp').controller('updateOrganizeInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike','$log','dateFilter','myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                    $httpParamSerializerJQLike,$log,dateFilter,myConstant) {

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
        vm.postData.orgConnDate = dateFilter(vm.postData.orgConnDate,'yyyyMMdd');
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
