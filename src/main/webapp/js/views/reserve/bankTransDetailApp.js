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


        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            RESERVE_LIST:{},
            RESERVE_INFO:{},
            COMANY_CODE:{}
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

        (function init() {
            vm.queryBean = {};
            var temp = new Date();
            temp.setDate(temp.getDate()-1);
            var now= new Date(temp.getFullYear(),temp.getMonth(),temp.getDate());
            vm.queryBean.queryStartDate =now;
            vm.queryBean.queryEndDate =now;
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

        vm.checkBusiness = checkBusiness;

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
            //var postData = angular.copy(vm.queryBean);
            vm.queryBean.pageSize = vm.pagination.pageSize;
            vm.queryBean.pageNum = vm.pagination.pageNum;
            vm.pagination.queryBean = vm.queryBean;
            var headersConfig = {headers:{
                'Content-Type': 'application/json'
            }};
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

        function checkBusiness(checkItem){
            var checkList={};
            $http.post(config.ctx+"/bank/trans/detail/queryPeriodCheck", checkItem,config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        checkList = data.object.list;
                    } else {
                        alert(data.msg);
                    }
                    OpenService({
                        modalTitle: '核对业务/银行实际交易流水',
                        modalBody: 'toCheck',
                        url: 'check',
                        item:checkItem,
                        cached: {
                            CHECK_LIST:checkList,
                            COMANY_CODE:vm.cached['COMANY_CODE']
                        }
                    },function(item){
                        $log.debug(item);
                    },"checkTransDetailModalInstanceCtrl","myModalNoSave.html");
                });
        }

        function updateItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var item = CheckboxService.getCheckedNew(vm.pagination.list)[0];
            OpenService({
                modalTitle: '修改银行实际交易流水',
                modalBody: 'toUpdate',
                url: 'update',
                item:item,
                cached: {}
            },function(item){
              $log.debug(item);
            },"updateTransDetailModalInstanceCtrl");
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
angular.module('myApp').controller('updateTransDetailModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
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

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

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
}]);


angular.module('myApp').controller('checkTransDetailModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike','FileUploader','dateFilter','$log','CheckboxService','myConstant','DateCalculateService', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                   $httpParamSerializerJQLike,FileUploader,dateFilter,$log,CheckboxService,myConstant,DateCalculateService) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;

    vm.queryBean = {};

    vm.queryDetail = queryDetail;
    vm.checkTrans = checkTrans;
    vm.checkAccount = checkAccount;
    vm.clearCheck = clearCheck;

    vm.queryBean.accountNo = vm.item.accountNo;
    vm.queryBean.queryStartDate = new Date(vm.item.transDate);
    vm.queryBean.queryEndDate=new Date(vm.item.transDate);

    $log.debug("vm信息:{}",vm);

    function queryDetail(){
        $log.debug("查询核对业务数据:{}",vm.queryBean);
        $http.post(config.ctx+"/bank/trans/detail/queryPeriodCheck", vm.queryBean,config.jsonHeader)
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    vm.cached.CHECK_LIST = data.object.list;
                } else {
                    alert(data.msg);
                }
            });
    }

    function checkAccount(){
        $log.debug("查询核对业务数据:{}",vm.queryBean);
        var postData = angular.copy(vm.queryBean);
        delete postData.accountNo;
        $http.post(config.ctx+"/bank/trans/detail/queryPeriodCheck", postData,config.jsonHeader)
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    vm.cached.CHECK_LIST = data.object.list;
                } else {
                    alert(data.msg);
                }
            });
    }

    function clearCheck(item){
        if (confirm("你确定要清除核对！")){
            $http.post(config.ctx+"/bank/trans/detail/clearPeriodCheck", item,config.jsonHeader)
                .then(function (response) {
                    if (response.data.success) {
                        alert("操作成功");
                        $uibModalInstance.close();
                    } else {
                        alert(response.data.msg);
                    }
                }, function (response) {
                    alert("操作失败");
                    alert(response.status);
                })
        }
    }

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    function checkTrans(){
        if (CheckboxService.getCheckedNew(vm.cached.CHECK_LIST).length < 1) {
            alert("必须勾选一条记录！");
            return;
        }
        var postData = CheckboxService.getCheckedNew(vm.cached.CHECK_LIST);
        for (var i in postData){
            postData[i].bankTransDate=vm.item.settleDate;
            postData[i].bankTransNo=vm.item.id;
        }
        $http.post(config.ctx+"/bank/trans/detail/updatePeriodCheck", postData,config.jsonHeader)
            .then(function (response) {
                if (response.data.success) {
                    alert("操作成功");
                    $uibModalInstance.close();
                } else {
                    alert(response.data.msg);
                }
            }, function (response) {
                alert("操作失败");
                alert(response.status);
            })
    }

}]);