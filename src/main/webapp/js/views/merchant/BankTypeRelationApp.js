/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',[ '$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService','CacheService','myConstant',
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
            COMANY_CODE:{},
            TRANS_CODE:{},
            BANK_TYPE: {}

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
            vm.queryBean = {};
        }

        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }

        vm.getAllBankCode = getAllBankCode;
        
        function getAllBankCode() {
            var results;
            
           
        }

        function addItem() {
            vm.orgBankType = new Array();
            vm.url = config.ctx + "/merchant/bankTypeRelation/getAllBankCode";
            //组织参数
            $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.orgBankType = data.object;
                        OpenService({
                            modalTitle: '新增',
                            modalBody: 'toAdd',
                            url: 'add',
                            item: {bankCodes:vm.orgBankType},
                            cached:{
                                COMANY_CODE:CacheService.getCache('COMANY_CODE'),
                                TRANS_CODE:CacheService.getCache('TRANS_CODE'),
                                BANK_TYPE:CacheService.getCache('BANK_TYPE')
                            }
                        }, function (item) {
                            queryDetail();
                        },'BankTypeModalInstanceCtrl','myModalNoFooter.html');
                    } else {
                        alert(data.msg);
                    }
                });
        }

        function updateItem() {
            vm.orgBankType = new Array();
            vm.url = config.ctx + "/merchant/bankTypeRelation/getAllBankCode";
            $log.debug(CheckboxService.getCheckedNew(vm.pagination.list));
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var temp = CheckboxService.getCheckedNew(vm.pagination.list)[0].bankTypeCode;
            var templist = vm.pagination.list.filter(function (it) {
                if (it.bankTypeCode == temp){
                    return true;
                }
            });
            $log.info(templist);
            var tempChecked = templist.map(function (it) {
                return it.bankCode;
            })

            //组织参数
            $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.orgBankType = data.object.map(function (it) {
                            $log.debug(it.bankCode);
                            if (tempChecked.indexOf(it.bankCode) != -1){
                                it.checked = true;
                            }
                            return it;
                        });
                OpenService({
                modalTitle: '修改',
                modalBody: 'toUpdate',
                url: 'update',
                item:{
                    bankTypeCode:temp,
                    bankCodes:vm.orgBankType,
                    BANK_TYPE:vm.cached.BANK_TYPE
                },
                cached:{
                    COMANY_CODE:CacheService.getCache('COMANY_CODE'),
                    TRANS_CODE:CacheService.getCache('TRANS_CODE'),
                    BANK_TYPE:CacheService.getCache('BANK_TYPE')
                }
            },function (item) {
                vm.queryDetail();
                 },'updateBankTypeModalInstanceCtrl');
             } else {
                        alert(data.msg);
                    }
                });
        }

        function deleteItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            $http.post('delete', $httpParamSerializerJQLike({'bankTypeCode': CheckboxService.getCheckedNew(vm.pagination.list)[0].bankTypeCode}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination.list.splice(vm.pagination.list.indexOf(CheckboxService.getCheckedNew(vm.pagination.list)[0]), 1);
                        vm.queryDetail();
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

angular.module('myApp').controller('updateBankTypeModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                          $httpParamSerializerJQLike, myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }
    vm = $scope.vm = modalItem;
    vm.constant = myConstant;

    console.log(vm.item);

    $scope.save = function () {
        var postData = angular.copy(vm.item.bankCodes).filter(function (it) {
            it.bankTypeCode = vm.item.bankTypeCode;
            if (it.checked) {
                delete it.checked;
                return true;
            }
            delete it.checked;
        });
        $http.post(vm.url,postData,config.jsonHeader)
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

angular.module('myApp').controller('BankTypeModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                         $httpParamSerializerJQLike, myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }
    vm = $scope.vm = modalItem;
    vm.constant = myConstant;


    $scope.save = function () {
        var postData = angular.copy(vm.item.bankCodes).filter(function (it) {
            it.bankTypeCode = vm.item.bankTypeCode;
            if (it.checked) {
                delete it.checked;
                return true;
            }
            delete it.checked;
        });
      
        console.log(postData);
        $http.post(vm.url,postData,config.jsonHeader)
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