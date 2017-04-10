/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService', 'CacheService', 'myConstant',
        function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService, CacheService, myConstant) {
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
        vm.queryOrgBankInfo = vm.queryOrgBankInfo;
        vm.updateItemEnableFlag=updateItemEnableFlag;

        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached = {
            COMANY_CODE: {},
            BANK_TYPE: {},
            CONN_CHANNEL: {},
            ENABLE_FLAG:{}

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


        function addItem() {
            OpenService({
                modalTitle: '新增机构银行信息',
                modalBody: 'toUpdate',
                url: "update",
                orgBankInfo: vm.cached.COMANY_CODE,
                cached: {
                    CONN_CHANNEL: CacheService.getCache('CONN_CHANNEL'),
                    BANK_TYPE: CacheService.getCache('BANK_TYPE'),
                }
            }, function (item) {
                vm.queryDetail();
            }, "OrgBankModalInstanceCtrl");
        }

        function updateItemEnableFlag(item) {
            var enableTypeList = {'1':'0','0':'1'};
            var postData = angular.copy(item);
            postData.createTime = null;
            postData.lastUpdTime=null;
            // postData.orgConnDate=null;
            postData.enableFlag = enableTypeList[item.enableFlag];
            $http.post(config.ctx+"/institution/orgbank/updateEnableFlag", $httpParamSerializerJQLike(postData))
                .then(function (response) {
                    if (response.data.success) {
                        item.enableFlag = postData.enableFlag;
                        alert("操作成功");
                    } else {
                        alert(response.data.msg);
                    }
                }, function (response) {
                    alert("操作失败");
                    alert(response.status);
                })

        }

        function updateItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            vm.url = config.ctx + "/institution/orgbank/queryOrgBankInfo";
            vm.postData = {"orgCode": CheckboxService.getChecked()[0]};
            $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        var orgItem = {};
                        orgItem[data.object.orgCode] = vm.cached.COMANY_CODE[data.object.orgCode];
                        OpenService({
                            modalTitle: '修改机构银行信息',
                            modalBody: 'toUpdate',
                            item: data.object,
                            orgBankInfo: orgItem,
                            url: "update",
                            cached: {
                                CONN_CHANNEL: CacheService.getCache('CONN_CHANNEL'),
                                BANK_TYPE: CacheService.getCache('BANK_TYPE'),
                            }
                        }, function (item) {
                            vm.queryDetail();
                        }, "OrgBankModalInstanceCtrl");

                    } else {
                        alert(data.msg);
                    }
                });
        }


        function deleteItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            $http.post('delete', $httpParamSerializerJQLike({'userId': CheckboxService.getChecked()[0]}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        var item = vm.pagination.list.filter(function (item, index, array) {
                            return item.userId == CheckboxService.getChecked()[0];
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

        function queryOrgBankInfo() {
            vm.url = config.ctx + "/institution/orgbank/queryOrgBankInfo";
            $http.post(vm.url, $httpParamSerializerJQLike(CheckboxService.getChecked()[0]))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.cached.orgBankInfo = data.object;
                    } else {
                        alert(data.msg);
                    }
                });
        }
    }]);


angular.module('myApp').controller('OrgBankModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
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
    vm.getOrgBankInfo = getOrgBankInfo;
    vm.chooseAll = chooseAll;
    vm.getBankType = getBankType;
    vm.orgBanks = new Array();
    vm.updateChecked = updateChecked;
    vm.setPayLvlBox = setPayLvlBox;

    vm.getBusiDesc = function(key, value) {
        return value+"("+key+")";
    };

    $scope.save = function () {
        vm.url = config.ctx + "/institution/orgbank/saveOrgBank";
        var header = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        if (vm.item.refundDeadline == null || vm.item.refundDeadline == '') {
            alert("请填写退款期限");
            return;
        }
        for (var i = 0; i < vm.orgBanks.length; i++) {
            vm.orgBanks[i].refundDeadline = vm.item.refundDeadline;
            vm.orgBanks[i].orgRefundLimit = vm.item.orgRefundLimit;
            if (vm.orgBanks[i].choose == '1' && (vm.orgBanks[i].orgBankCode == '' || vm.orgBanks[i].orgBankCode == null ||
                vm.orgBanks[i].orgBankCodeDesc == '' || vm.orgBanks[i].orgBankCodeDesc == null )) {
                alert("请填写外系统银行代码");
                return;
            }
        }
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

    function getBankType(channel) {
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

    }

    function updateChecked(event, bankCode) {
        var checkbox = event.target;
        if (checkbox.checked) {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                if (vm.orgBanks[i].bankCode == bankCode) {
                    vm.orgBanks[i].choose = "1";
                    fillContent(i,1);
                }
            }
        } else {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                if (vm.orgBanks[i].bankCode == bankCode) {
                    vm.orgBanks[i].choose = "0";
                    fillContent(i,0);
                }
            }
        }

    }

    function chooseAll(event) {
        var checkbox = event.target;
        if (checkbox.checked) {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                vm.orgBanks[i].choose = "1";
                fillContent(i,1);
            }
        } else {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                vm.orgBanks[i].choose = "0";
                fillContent(i,0);
            }
        }
    }

    function fillContent(i,fillType){
        if(fillType ==1){
            vm.orgBanks[i].orgBankCode =vm.orgBanks[i].bankCode;
            vm.orgBanks[i].orgBankCodeDesc =vm.orgBanks[i].bankName;
        }else{
            vm.orgBanks[i].orgBankCode ='';
            vm.orgBanks[i].orgBankCodeDesc ='';
        }
    }


    function getOrgBankInfo(bankType) {
        var results;
        vm.url = config.ctx + "/institution/orgbank/getOrgBankInfo";
        if (vm.item.orgCode == null || vm.item.orgCode == "") {
            return;
        }
        //组织参数
        vm.postData = {"bankType": bankType, "companyType": "02", "companyCode": vm.item.orgCode};
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
    }

    function setPayLvlBox(event, bankCode) {
        var checkbox = event.target;
        if (checkbox.checked) {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                if (vm.orgBanks[i].bankCode == bankCode) {
                    vm.orgBanks[i].payLvl = "1";
                }
            }
        } else {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                if (vm.orgBanks[i].bankCode == bankCode) {
                    vm.orgBanks[i].payLvl = "0";
                }
            }
        }

    }


}]);
