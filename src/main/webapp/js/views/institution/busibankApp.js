/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService', 'CacheService', 'myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService, CacheService, myConstant) {
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
        vm.addItem = addItem;
        vm.queryOrgBankInfo = vm.queryOrgBankInfo;

        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached = {
            BANK_TYPE: {},
            CONN_CHANNEL: {},
            BUSINESS_FUNDING_SOURCE:{}

        };
        CacheService.initCache(vm.cached);

        
        vm.getBusiDesc = function(key, value) {
            return value+"("+key+")";
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
                        if(vm.pagination.list==null || vm.pagination.list.length==0){
                            vm.pagination = {
                                pageSize: 10,
                                pageNum: 1
                            };
                            document.getElementById('busi_bank_tr').style.display = "";
                        }else{
                            document.getElementById('busi_bank_tr').style.display = "none";
                        }
                        //清空选中记录
                        CheckboxService.clearChecked();
                    } else {
                        alert(data.msg);
                        document.getElementById('busi_bank_tr').style.display = "";
                    }
                });

        };

        function resetForm() {
            vm.queryBean = {};
        }

        function addItem() {
            OpenService({
                modalTitle: '业务资金源配置修改或新增',
                modalBody: 'toAdd',
                url: "toAdd",
                cached: {
                    BUSINESS_FUNDING_SOURCE: vm.cached.BUSINESS_FUNDING_SOURCE,
                    BANK_TYPE: CacheService.getCache('BANK_TYPE'),
                    CONN_CHANNEL: CacheService.getCache('CONN_CHANNEL')
                }
            }, function (item) {
                vm.queryDetail();
            }, "busiBankModalInstanceCtrl",'myModalNoFooter.html');
        }
    }]);


angular.module('myApp').controller('busiBankModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
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
    vm.orgBanks = new Array();
    vm.queryBean ={};

    $scope.save = function () {
        vm.url = config.ctx + "/institution/busiBank/saveBusiBank";
        var header = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        $http.post(vm.url, vm.orgBanks, header)
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    $scope.cancel();
                } else {
                    alert(data.msg);
                    $scope.cancel();
                }
            });
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    vm.getBankType =function getBankType(channel) {
        var results = vm.cached.BANK_TYPE;
        var bankTypes = new Array();
        var i = 0;
        for (var j = 0; j < results.length; j++) {
            var key = results[j].key;
            if (key.substr(0, channel.length) == channel) {
                bankTypes[i] = results[j];
                i++;
            }
        }
        vm.bankTypes = bankTypes;
        vm.orgBanks = new Array();
        document.getElementById('orgBanks').style.display = "none";
        document.getElementById('busi_propmt').style.display = "none";

    };

    vm.updateChecked = function updateChecked(event, bankCode) {
        var checkbox = event.target;
        if (checkbox.checked) {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                if (vm.orgBanks[i].bankCode == bankCode) {
                    vm.orgBanks[i].choose = "1";
                }
            }
        } else {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                if (vm.orgBanks[i].bankCode == bankCode) {
                    vm.orgBanks[i].choose = "0";
                }
            }
        }

    };

    vm.chooseAll = function chooseAll(event) {
        var checkbox = event.target;
        if (checkbox.checked) {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                vm.orgBanks[i].choose = "1";
            }
        } else {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                vm.orgBanks[i].choose = "0";
            }
        }
    };

    vm.getBusiBankInfo  = function getBusiBankInfo(bankType) {
        var results;
        vm.url = config.ctx + "/institution/busiBank/getBusiBankInfo";
        if (vm.queryBean.busiType == null || vm.queryBean.busiType == "") {
            document.getElementById('busi_propmt').style.display = "";
            return;
        }
        //组织参数
        vm.postData = {"bankType": bankType, "busiType": vm.queryBean.busiType};
        $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    if (data.object.length == 0) {
                        document.getElementById('orgBanks').style.display = "none";
                    } else {
                        document.getElementById('orgBanks').style.display = "block";
                    }
                    vm.orgBanks = data.object;
                } else {
                    document.getElementById('orgBanks').style.display = "none";
                    alert(data.msg);

                }
            });
    };
    vm.getBusiDesc = function(key, value) {
        return value+"("+key+")";
    };


}]);
