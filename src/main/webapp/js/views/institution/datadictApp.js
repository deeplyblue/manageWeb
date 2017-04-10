/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService','CacheService','myConstant','dateFilter',
        function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,CacheService,myConstant,dateFilter) {
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
            TRANS_CODE_ALL:{}

        };
        CacheService.initCache(vm.cached);

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
            vm.queryBean = {
                itemType: '02',
            };
        }

        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }


        function addItem() {
            OpenService({
                modalTitle: '新增支付机构交易代码',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    TRANS_CODE_ALL: CacheService.getCache("TRANS_CODE_ALL"),
                }
            }, function (item) {
                vm.pagination.list.push(item);
            });
        }

        function updateItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var temp= vm.pagination.list.filter(function (item, index, array) {
                return item.id == CheckboxService.getChecked()[0];
            })[0];
            temp.createTime=dateFilter(temp.createTime,'yyyyMMdd');
            temp.lastUpdTime=dateFilter(temp.lastUpdTime,'yyyyMMdd');

            OpenService({
                modalTitle: '修改机构信息',
                modalBody: 'toUpdate',
                url: 'update',
                item: temp,
                cached: {
                    TRANS_CODE_ALL: CacheService.getCache("TRANS_CODE_ALL"),
                }
            },function (item){
                queryDetail();
            });

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
                            return item.userId == CheckboxService.getChecked()[0];
                        })[0];
                        vm.pagination.list.splice(vm.pagination.list.indexOf(item), 1);
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
    }]);



