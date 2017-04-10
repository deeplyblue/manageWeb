/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter','dateFilter','myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter,dateFilter,myConstant) {
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
            RESERVE_LIST:{},
            RESERVE_INFO:{}
        };

        vm.cachedTemp = {
            COMANY_CODE:{},
            MERCHANT_CODE:{}
        };

            (function(){
                $http.post(config.ctx + "/reserve/bank/info/init/bank",{}, config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            vm.cached.BANK_INFO = data.object;
                            var arr = [];
                            vm.cached.RESERVE_LIST = [];
                            for (var index in data.object){
                                var item = data.object[index];
                                var bankInfo ={'accountNo':item.accountNo,'desc':item.openBankName};
                                arr.push(bankInfo);
                                vm.cached.RESERVE_LIST.push({'accountNo':item.accountNo,'bankCode':item.remarkDesc});
                            }
                            vm.cached.BANK_INFO_LIST = arr;
                        } else {
                            alert(data.msg);
                        }
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
                modalTitle: '新增备付金账户机构',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    RESERVE_LIST:vm.cached.RESERVE_LIST
                }
            }, function (item) {
                vm.pagination.list.push(item);
            });
        }

        function updateItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var item = CheckboxService.getCheckedNew(vm.pagination.list)[0];
            OpenService({
                modalTitle: '修改备付金账户机构',
                modalBody: 'toUpdate',
                url: 'update',
                item:item,
                cached: {
                }
            },function(item){
              $log.debug(item);
            },'updateAccountOrgCodeController','myModalContent.html');
        }

        function deleteItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            if (confirm("是否确认删除！")){
                var item = CheckboxService.getCheckedNew(vm.pagination.list)[0];
                $http.post('delete', $httpParamSerializerJQLike(item))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
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
        }
    }]);
angular.module('myApp').controller('updateAccountOrgCodeController', ['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'FileUploader', 'dateFilter', '$log', 'CheckboxService', 'myConstant', 'DateCalculateService', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                                                                           $httpParamSerializerJQLike, FileUploader, dateFilter, $log, CheckboxService, myConstant, DateCalculateService) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {}
        };

        vm = $scope.vm = modalItem;
        vm.constant = myConstant;

        vm.queryBean = {};

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.save = function () {
            $http.post(config.ctx+'/reserve/account/orgCode/update', vm.item ,config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert("操作成功");
                        $uibModalInstance.dismiss('cancel');
                    } else {
                        alert("操作失败");
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        };
    }]);