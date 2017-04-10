/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService','CacheService','myConstant',
        function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,CacheService,myConstant) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        //变量初始化
        //分页数据
        vm.pagination =  {
            pageSize: 10,
            pageNum: 1
        };
        //查询条 件
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
            BANK_CARD_TYPE:{},
            ENABLE_FLAG:{},
            BANK_INFO_TYPE:{},
            BANK_INFO:{},
            T_BANK_CODE:{}

        };
        CacheService.initCache(vm.cached);

        /*将前面缓存的数据格式化
         [{key:value},{key:value}]*/
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
                    BANK_CARD_TYPE:CacheService.getCache('BANK_CARD_TYPE'),
                    ENABLE_FLAG:CacheService.getCache('ENABLE_FLAG'),
                    BANK_INFO_TYPE:CacheService.getCache('BANK_INFO_TYPE'),
                    T_BANK_CODE:CacheService.getCache('T_BANK_CODE'),
                }
            }, function (item) {
                vm.pagination.list.push(item);
            },'','myModalNoFooter.html');
        }

        function updateItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var temp=vm.pagination.list.filter(function (item, index, array) {
                return item.id == CheckboxService.getChecked()[0];
            })[0];
            temp.createTime=null;
            temp.lastUpdTime=null;
            temp.auditDate=null;

            OpenService({
                modalTitle: '修改',
                modalBody: 'toUpdate',
                url: 'update',
                item:temp,
                cached:{
                    BANK_CARD_TYPE:CacheService.getCache('BANK_CARD_TYPE'),
                    ENABLE_FLAG:CacheService.getCache('ENABLE_FLAG')
                }
            }
                , function (item) {
                    queryDetail();
                },'','myModalNoFooter.html');

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
