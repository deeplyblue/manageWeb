/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter','dateFilter','myConstant','DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter,dateFilter,myConstant,DateCalculateService) {
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


        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            RESERVE_LIST:{},
            RESERVE_INFO:{}
        };

        vm.tempParams = {
            transType:{
            '001':'支付结算',
            '002':'退款结算',
            '003':'利息',
            '004':'预付卡',
            '005':'自有资金',
            '006':'手续费',
            '007':'风险保证金',
            '008':'商户结算',
            '009':'头寸调拨',
            '010':'管理费'}
        };

        (function initCache() {
            vm.queryBean = {};
            $http.post(config.ctx + '/reserve/info/init/reserve',{},config.jsonHeader)
                .then(function (response) {
                    if (response.data.success){
                        vm.cached.RESERVE_INFO=response.data.object;
                        format();
                    }else {
                        $log.error('获取数据%s失败',response.data.msg);
                    }
                }, function (response) {
                    $log.error('获取数据%s失败', cacheKey);
                })
        }());

        function format(){
            vm.cached.RESERVE_LIST = [];
            for (var index in vm.cached.RESERVE_INFO){
                vm.cached.RESERVE_LIST.push({'accountNo':index,'desc':vm.cached.RESERVE_INFO[index]['description'],'bankCode':vm.cached.RESERVE_INFO[index]['bankCode']});
            }
        }

        (function init() {
            vm.queryBean = {};
            vm.queryBean.begin =DateCalculateService.dayBefore(1);
            vm.queryBean.end =DateCalculateService.dayBefore(1);
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
            $log.debug("当前日期"+vm.queryBean.begin);
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            //var postData = angular.copy(vm.queryBean);
            vm.queryBean.pageSize = vm.pagination.pageSize;
            vm.queryBean.pageNum = vm.pagination.pageNum;
            vm.pagination.queryBean = vm.queryBean;
            var headersConfig = {headers:{
                'Content-Type': 'application/json'
            }};
            $log.debug("当前日期"+vm.queryBean.begin);
            $log.debug("当前日期"+JSON.stringify(vm.pagination));
            $log.debug("当前日期"+JSON.stringify(vm.queryBean));
            $http.post(vm.url, vm.pagination,headersConfig)
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
            vm.queryBean.begin =DateCalculateService.dayBefore(1);
            vm.queryBean.end =DateCalculateService.dayBefore(1);
        }

        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }

        function addItem() {
            OpenService({
                modalTitle: '新增备付金信息',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {

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
                modalTitle: '修改备付金余额',
                modalBody: 'toUpdate',
                url: 'update',
                item:item,
                cached: {
                    TRANS_TYPE: vm.tempParams.transType
                }
            },function(item){
              $log.debug(item);
            },"updateReserveBalanceModalInstanceCtrl");
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

angular.module('myApp').controller('updateReserveBalanceModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike','FileUploader','dateFilter','$log','myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                             $httpParamSerializerJQLike,FileUploader,dateFilter,$log,myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;

    $scope.save = function () {
        var postData = angular.copy(vm.item);
        $log.debug("进入特殊修改类:{}",postData);
        $http.post(vm.url, postData,config.jsonHeader)
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
    }
    
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);