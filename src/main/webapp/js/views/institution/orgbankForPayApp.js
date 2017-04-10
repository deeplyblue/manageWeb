/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'myConstant', 'growl', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, myConstant, growl) {
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
        vm.editItem = editItem;
        vm.queryOrgBankInfo = vm.queryOrgBankInfo;

        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached = {
            COMANY_CODE: {},
            BANK_TYPE: {},
            CONN_CHANNEL: {},
            TRANS_CODE: {},
            PAY_TRANS_CODE: {},
            ORG_BANK_CODE_DESC: {},
            TRANS_CODE_02: {}

        };
        CacheService.initCache(vm.cached);

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
                modalTitle: '添加支付策略 ',
                modalBody: 'toAddForPay',
                url: "addForPay",
                orgBankInfo: vm.cached.COMANY_CODE,
                cached: {
                    CONN_CHANNEL: CacheService.getCache('CONN_CHANNEL'),
                    BANK_TYPE: CacheService.getCache('BANK_TYPE'),
                    TRANS_CODE: CacheService.getCache('TRANS_CODE'),
                    ORG_BANK_CODE_DESC: CacheService.getCache("ORG_BANK_CODE_DESC"),
                    TRANS_CODE_02: CacheService.getCache("TRANS_CODE_02")
                }
            }, function (item) {
                // vm.queryDetail();
                OpenService({
                    modalTitle: '添加支付策略 ',
                    modalBody: 'toAddForPayTwo',
                    url: "addForPayTwo",
                    orgBankInfo: vm.cached.COMANY_CODE,
                    cached: {
                        CONN_CHANNEL: CacheService.getCache('CONN_CHANNEL'),
                        BANK_TYPE: CacheService.getCache('BANK_TYPE'),
                        TRANS_CODE: CacheService.getCache('TRANS_CODE'),
                        ORG_BANK_CODE_DESC: CacheService.getCache("ORG_BANK_CODE_DESC"),

                    }
                }, function (item) {
                    vm.queryDetail();
                }, "OrgBankModalInstanceCtrl")
            }, "OrgBankModalInstanceCtrl");
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
                            modalTitle: '修改机构信息',
                            modalBody: 'toUpdateForPay',
                            item: data.object,
                            orgBankInfo: orgItem,
                            url: "updateForPay",
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

        function editItem(bean) {
            if (bean) {
                $http.post(config.ctx + '/institution/orgbank/queryOrgBankByBank', bean, config.jsonHeader)
                    .then(function (response) {
                        OpenService({
                            modalTitle: '支付策略配置',
                            modalBody: 'toUpdateForPay',
                            item: angular.copy(bean),
                            list: response.data.object,
                            disable: true,
                            url: config.ctx + '/institution/payChanStrategyCfg/edit',
                            cached: {
                                CONN_CHANNEL: CacheService.getCache('CONN_CHANNEL'),
                                BANK_TYPE: CacheService.getCache('BANK_TYPE'),
                                COMANY_CODE: CacheService.getCache('COMANY_CODE'),
                                PAY_TRANS_CODE: CacheService.getCache('PAY_TRANS_CODE'),
                                ORG_BANK_CODE_DESC: CacheService.getCache('ORG_BANK_CODE_DESC')
                            }
                        }, function (item) {
                            vm.queryDetail();
                        }, "EditForPayModalInstanceCtrl");
                    })
            } else {
                OpenService({
                    modalTitle: '支付策略配置',
                    modalBody: 'toUpdateForPay',
                    item: {},
                    list: {},
                    url: config.ctx + '/institution/payChanStrategyCfg/edit',
                    cached: {
                        CONN_CHANNEL: CacheService.getCache('CONN_CHANNEL'),
                        BANK_TYPE: CacheService.getCache('BANK_TYPE'),
                        COMANY_CODE: CacheService.getCache('COMANY_CODE'),
                        PAY_TRANS_CODE: CacheService.getCache('PAY_TRANS_CODE'),
                        ORG_BANK_CODE_DESC: CacheService.getCache('ORG_BANK_CODE_DESC')
                    }
                }, function (item) {
                    vm.queryDetail();
                }, "EditForPayModalInstanceCtrl");
            }
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

    $scope.save = function () {
        vm.url = config.ctx + "/institution/orgbank/saveOrgBank";
        var header = {
            headers: {
                'Content-Type': 'application/json'
            }
        };

        for (var i = 0; i < vm.orgBanks.length; i++) {
            vm.orgBanks[i].refundDeadline = vm.item.refundDeadline;
            vm.orgBanks[i].orgRefundLimit = vm.item.orgRefundLimit;
            if (vm.orgBanks[i].choose == '1' && (vm.orgBanks[i].orgBankCode == '' || vm.orgBanks[i].orgBankCode == null)) {
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
                }
            }
        } else {
            for (var i = 0; i < vm.orgBanks.length; i++) {
                if (vm.orgBanks[i].bankCode == bankCode) {
                    vm.orgBanks[i].choose = "0";
                }
            }
        }

    }

    function chooseAll(event) {
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

angular.module('myApp').controller('EditForPayModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                            $httpParamSerializerJQLike, $log, myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }

    vm = $scope.vm = modalItem;

    var emptyOrg = {
        orgCode: '',
        stgyPriority: '',
        enableFlag: ''
    };

    if (!vm.list || vm.list.length == 0) {
        vm.item.orgs = [angular.copy(emptyOrg)];
    } else {
        vm.item.orgs = vm.list;
    }

    function initOrgBanks() {
        if (!vm.item.bankCode) {
            return;
        }
        $http.post(config.ctx + '/institution/orgbank/queryOrgBank', {bankCode: vm.item.bankCode}, config.jsonHeader)
            .then(function (response) {
                if (response.data.success) {
                    var arr = response.data.object.map(function (item) {
                        return item.orgCode;
                    })
                    $log.debug(arr);
                    vm.orgBanks = vm.cached.COMANY_CODE.filter(function (item) {
                        if (arr.indexOf(item.key) != -1) {
                            $log.debug(item.key);
                            return true;
                        }
                    });
                } else {
                    alert("获取对应支付机构失败");
                }
            }, function (response) {
                alert("获取对应支付机构失败");
            })
    }

    initOrgBanks();

    vm.constant = myConstant;

    vm.addItem = function () {
        vm.item.orgs.push(angular.copy(emptyOrg));
    }

    vm.deleteItem = function (index) {
        vm.item.orgs.splice(index, 1);
        if (index == 0) {
            vm.item.orgs.push(angular.copy(emptyOrg));
        }
    }

    vm.queryOrgs = function () {
        if (vm.item.bankCode && vm.item.transCode && vm.item.connChannel) {
            var postData = angular.copy(vm.item);
            delete postData.orgs;
            initOrgBanks();
            $http.post(config.ctx + '/institution/orgbank/queryOrgBankByBank', postData, config.jsonHeader)
                .then(function (response) {
                    if (response.data.success) {
                        vm.item.orgs = response.data.object;
                        if (response.data.object.length == 0) {
                            vm.item.orgs.push(angular.copy(emptyOrg));
                        }
                    } else {
                        alert("获取对应支付机构失败");
                    }
                }, function (response) {
                    alert("获取对应支付机构失败");
                })
        } else {
            return;
        }
    }

    $scope.save = function () {
        var postData = vm.item.orgs.map(function (item) {
            var temp = {};
            temp.orgCode = item.orgCode;
            temp.stgyPriority = item.stgyPriority;
            temp.status = item.enableFlag;
            temp.bankCode = vm.item.bankCode;
            temp.transCode = vm.item.transCode;
            temp.connChannel = vm.item.connChannel;

            return temp;
        })
        $http.post(vm.url, postData, config.jsonHeader)
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
