
angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService','CacheService','myConstant',
        function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,CacheService,myConstant) {
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

        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached ={
            COMANY_CODE:{},
            COMMON_VALUE:{},


        };
        (function initCache() {
            CacheService.initCache(vm.cached);

            CacheService.getObj('ALL_CITY', function (key, cacheObj) {
                $log.debug(cacheObj);
                vm.cached['ALL_CITY'] = cacheObj;
            });

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
                modalTitle: '新增',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached:{
                    COMANY_CODE:CacheService.getCache("COMANY_CODE"),
                    COMMON_VALUE:CacheService.getCache("COMMON_VALUE"),
                    ALL_CITY: vm.cached.ALL_CITY
                }
            }, function (item) {

            },'','myModalNoFooter.html');
        }

        function updateItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var pro;
            var city;
            var item = vm.pagination.list.filter(function (item, index, array) {
                if (item.id == CheckboxService.getChecked()[0]) {
                    console.debug(item.areaCode);
                    pro = vm.cached.ALL_CITY.filter(function (it) {
                        if (item.areaCode && it.areaCode == item.areaCode){
                            return true;
                        }
                    })[0];
                    city = pro.citys.filter(function (it) {
                        if (item.cityCode && it.cityCode == item.cityCode){
                            return true;
                        }
                    })[0];
                    return true;
                }

            })[0];
            // item.createTime=null;
            // item.lastUpdTime=null;

            OpenService({
                modalTitle: '修改',
                modalBody: 'toUpdate',
                url: 'update',
                item:item,
                pro:pro,
                city:city,
                cached: {
                    ALL_CITY: vm.cached.ALL_CITY,
                }
            }, function (item) {
            },'updateMailInfoInstanceCtrl','myModalNoFooter.html');
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
    }]);

angular.module('myApp').controller('updateMailInfoInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
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