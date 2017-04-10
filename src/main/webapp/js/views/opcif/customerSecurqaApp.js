
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
        vm.queryCustomerDetail=queryCustomerDetail;
        vm.addItem = addItem;
        vm.updateItem = updateItem;

        /*------------------以上方法名可选择性通用---------------------*/

        vm.getCache = function (key) {
            return CacheService.getCache(key);
        };

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            var url = queryForm.getAttribute('action');
            
            //组织参数：查询条件 + 分页数据
            vm.postData = vm.queryBean;
            vm.postData.pageSize = vm.pagination.pageSize;
            vm.postData.pageNum = vm.pagination.pageNum;
            $http.post(url, $httpParamSerializerJQLike(vm.postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination = data.object;

                    } else {
                        alert(data.msg);
                    }
                });

        };

        function resetForm() {
            vm.queryBean = {};
        }

        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached ={
        };
        (function initCache() {
            for (var cacheKey in vm.cached) {
                CacheService.initCache(cacheKey, function (cacheKey, cacheObj) {
                    $log.debug(cacheKey, cacheObj);
                    vm.cached[cacheKey] = cacheObj;
                })
            }
        }());


        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }

        function queryCustomerDetail(bean) {
            var item=bean;
            OpenService({
                modalTitle: '查看明细',
                modalBody: 'toDetail',
                url: '#',
                item: item,
                cached: {
                }
            }, function (item) {
            }, '', 'myModalNoSave.html');
        }

        function addItem() {
            OpenService({
                modalTitle: '新增',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached:{
                }
            }, function (item) {

            });
        }

        function updateItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var item = CheckboxService.getCheckedNew(vm.pagination.list)[0];
            var postData = angular.copy(item);
            postData.createdAt=null;
            postData.updatedAt=null;
            OpenService({
                modalTitle: '修改',
                modalBody: 'toUpdate',
                url: 'update',
                item:postData,
                cached:{
                }
            });

        }
        
    }]);
