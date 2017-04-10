/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',[ '$scope', '$http', '$httpParamSerializerJQLike', '$log', 'DateCalculateService','OpenService', 'CheckboxService','CacheService','dateFilter'
        ,'myConstant',function ($scope, $http, $httpParamSerializerJQLike, $log, DateCalculateService,OpenService, CheckboxService,CacheService,dateFilter
        ,myConstant) {
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
        vm.queryBean = {createTime:DateCalculateService.getToday(),
            lastUpdTime:DateCalculateService.getTomorrow()};


        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;

        /*------------------以上配置通用---------------------*/


        vm.updateChecked = updateChecked;
        vm.addItem = addItem;
        vm.updateItem = updateItem;
        vm.deleteItem = deleteItem;


        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached = {
            T_USER_DOWNLOAD:{}

        };
        CacheService.initCache(vm.cached);


        vm.getCache = function (key) {
            return CacheService.getCache(key);
        };

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            var postData = angular.copy(vm.queryBean);
            postData.pageSize = vm.pagination.pageSize;
            postData.pageNum = vm.pagination.pageNum;
            postData.createTime=dateFilter(postData.createTime, 'yyyyMMdd');
            postData.lastUpdTime=dateFilter(postData.lastUpdTime, 'yyyyMMdd');
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
                modalTitle: '添加响应码',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached:{
                }
            }, function (item) {
                queryDetail();
            },'','myModalNoFooter.html');
        }


        function updateItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var temp= CheckboxService.getCheckedNew(vm.pagination.list)[0];

            temp.createTime=null;
            temp.lastUpdTime=null;

            OpenService({
                modalTitle: '修改响应码',
                modalBody: 'toUpdate',
                url: 'update',
                item: temp,
                cached:{
                }
            });

        }

        function deleteItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            $http.post('delete', $httpParamSerializerJQLike({'id': CheckboxService.getCheckedNew(vm.pagination.list)[0].id}))
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


    }]);
