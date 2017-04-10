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
        //查询条 件
        vm.queryBean = {};

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;
        vm.queryCleearCfgDetail=queryCleearCfgDetail;

        /*------------------以上配置通用---------------------*/


        vm.updateChecked = updateChecked;
        vm.addItem = addItem;
        vm.updateItem = updateItem;
        vm.deleteItem = deleteItem;

        vm.updateItemEnableFlag = updateItemEnableFlag;

        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached = {
            MERCHANT_CODE: {},
            CLR_CYCLE: {},
            REPORT_TYPE: {},
            CLR_TYPE: {},
            FEE_CLEAR_TYPE: {},
            ENABLE_FLAG: {},
            CLR_RANGE: {},
            CLEAR_VALUE_POINT: {},
            CLEAR_VALUE_POINT_WEEK: {},
            CLR_RULES_1: {},
            CLR_RULES_2: {},
            COMANY_CODE: {}
        };
        CacheService.initCache(vm.cached);

        vm.getCache = function (key) {
            return CacheService.getCache(key);
        };

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            var postData = vm.queryBean;
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
            vm.queryBean.companyType = '03';
        }

        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }

        function queryCleearCfgDetail(bean) {
            var item = angular.copy(bean);
            item.clrPointDesc='';
            if(item.clrCycle == '03' || item.clrCycle == '04'){
                var c = vm.getCache('CLEAR_VALUE_POINT');
                c.forEach(function (it) {
                    if(it['value'] == item.clrPoint){
                        item.clrPointDesc = it['key'];
                    }
                });
            }else if (item.clrCycle == '02' || item.clrCycle == '05'){
                var c = vm.getCache('CLEAR_VALUE_POINT_WEEK');
                c.forEach(function (it) {
                    if(it['value'] == item.clrPoint){
                        item.clrPointDesc = it['key'];
                    }
                });
            }
            // $log.debug("item:",item);
            // item.bankClearCfgList.forEach(function (itm) {
            //
            //     itm.clrPointDesc='';
            //     if(itm.clrCycle == '03' || itm.clrCycle == '04'){
            //         var c = vm.getCache('CLEAR_VALUE_POINT');
            //         c.forEach(function (it) {
            //             if(it['value'] == it.clrPoint){
            //                 itm.clrPointDesc = it['key'];
            //             }
            //         });
            //     }else if (itm.clrCycle == '02' || itm.clrCycle == '05'){
            //         var c = vm.getCache('CLEAR_VALUE_POINT_WEEK');
            //         c.forEach(function (it) {
            //             if(it['value'] == itm.clrPoint){
            //                 itm.clrPointDesc = it['key'];
            //             }
            //         });
            //     }
            // });

            OpenService({
                modalTitle: '查看明细',
                modalBody: 'toDetail',
                url: '#',
                item: item,
                cached: {
                    MERCHANT_CODE: vm.cached.MERCHANT_CODE,
                    CLR_CYCLE: vm.cached.CLR_CYCLE,
                    REPORT_TYPE: vm.cached.REPORT_TYPE,
                    CLR_TYPE: vm.cached.CLR_TYPE,
                    FEE_CLEAR_TYPE: vm.cached.FEE_CLEAR_TYPE,
                    CLR_RANGE: vm.cached.CLR_RANGE,
                    CLEAR_VALUE_POINT: vm.cached.CLEAR_VALUE_POINT,
                    CLEAR_VALUE_POINT_WEEK: vm.cached.CLEAR_VALUE_POINT_WEEK,
                    CLR_RULES_1: vm.cached.CLR_RULES_1,
                    CLR_RULES_2: vm.cached.CLR_RULES_2,
                    COMANY_CODE: vm.cached.COMANY_CODE
                }
            }, function (item) {
            }, '', 'myModalNoSave.html');
        }

        function addItem() {
            OpenService({
                modalTitle: '新增商户结算信息',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    MERCHANT_CODE: vm.getCache('MERCHANT_CODE'),
                    CLR_CYCLE: vm.cached.CLR_CYCLE,
                    REPORT_TYPE: vm.cached.REPORT_TYPE,
                    CLR_TYPE: vm.cached.CLR_TYPE,
                    FEE_CLEAR_TYPE: vm.cached.FEE_CLEAR_TYPE,
                    CLR_RANGE: vm.cached.CLR_RANGE,
                    CLEAR_VALUE_POINT: vm.cached.CLEAR_VALUE_POINT,
                    CLEAR_VALUE_POINT_WEEK: vm.cached.CLEAR_VALUE_POINT_WEEK,
                    CLR_RULES_1: vm.cached.CLR_RULES_1,
                    CLR_RULES_2: vm.cached.CLR_RULES_2,
                    COMANY_CODE: vm.getCache('COMANY_CODE')
                }
            }, function (item) {
            }, "addMerchantClearCfgApp", 'myModalNoFooter.html');
        }

        function updateItemEnableFlag(item, auditStatus, enableFlag) {
            var postData = {id: item.id};

            if (auditStatus) {
                postData.auditStatus = auditStatus;
            }
            if (enableFlag) {
                postData.enableFlag = enableFlag;
            }
            postData.companyCode=item.companyCode;
            console.log(postData);
            $http.post(config.ctx + '/merchant/clearCfg/updateItemEnableFlag', postData, config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        if (auditStatus) {
                            item.auditStatus = auditStatus;
                        }
                        if (enableFlag) {
                            item.enableFlag = enableFlag;
                        }
                        alert("操作成功");
                        vm.queryDetail();
                    } else {
                        alert("操作失败");
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function updateItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var temp = CheckboxService.getCheckedNew(vm.pagination.list)[0];
            temp.createTime = null;
            temp.lastUpdTime = null;
            temp.auditDate = null;
            $log.debug("temp:", temp);
            OpenService({
                modalTitle: '修改商户结算信息',
                modalBody: 'toUpdate',
                url: 'update',
                item: temp,
                cached: {
                    MERCHANT_CODE: vm.getCache('MERCHANT_CODE'),
                    CLR_CYCLE: vm.cached.CLR_CYCLE,
                    REPORT_TYPE: vm.cached.REPORT_TYPE,
                    CLR_TYPE: vm.cached.CLR_TYPE,
                    FEE_CLEAR_TYPE: vm.cached.FEE_CLEAR_TYPE,
                    CLR_RANGE: vm.cached.CLR_RANGE,
                    CLEAR_VALUE_POINT: vm.cached.CLEAR_VALUE_POINT,
                    CLEAR_VALUE_POINT_WEEK: vm.cached.CLEAR_VALUE_POINT_WEEK,
                    CLR_RULES_1: vm.cached.CLR_RULES_1,
                    CLR_RULES_2: vm.cached.CLR_RULES_2,
                    COMANY_CODE: vm.getCache('COMANY_CODE')
                }
            }, function () {
                vm.queryDetail();
            }, 'updateMerchantClearCfgApp', 'myModalNoFooter.html');

        }

        function deleteItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            $http.post('delete', $httpParamSerializerJQLike({'id': CheckboxService.getCheckedNew(vm.pagination.list)[0].id}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        var item = vm.pagination.list.filter(function (item, index, array) {
                            return item.id == CheckboxService.getChecked()[0];
                        })[0];
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

angular.module('myApp').controller('addMerchantClearCfgApp', function ($scope, $uibModalInstance, modalItem, $http,
                                                                       $httpParamSerializerJQLike, $log, myConstant) {
    console.log("test");
    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
    //商业银行结算配置
    vm.companyBank = [];
    vm.companyBank[0] = {"companyCode": "", "clrPoint": "", "clrCycle": "", "clrRange": ""};

    vm.addCompanyBank = function addCompanyBank(obj) {
        console.log("addCompanyBank");
        vm.companyBank.push({"companyCode": "", "clrPoint": "", "clrCycle": "", "clrRange": ""});
    };

    vm.deleteCompanyBank = function deleteCompanyBank(index) {
        if (index == 0) {
            alert("第一条信息不能删除!");
        } else {
            vm.companyBank.remove(index);
        }
    };
    //商业分账
    vm.companyRout = [];
    vm.companyRout[0] = {"companyCode": "", "propNum": ""};

    vm.addCompanyRout = function addCompanyRout(obj) {
        console.log("addCompanyRout");
        vm.companyRout.push({"companyCode": "", "propNum": ""});
    };

    vm.deleteCompanyRout = function deleteCompanyRout(index) {
        if (index == 0) {
            alert("第一条信息不能删除!");
        } else {
            vm.companyRout.remove(index);
        }
    };
    //商业分潤
    vm.companyProfit = [];
    vm.companyProfit[0] = {"companyCode": "", "propNum": ""};

    vm.addCompanyProfit = function addCompanyProfit(obj) {
        console.log("addCompanyProfit");
        vm.companyProfit.push({"companyCode": "", "propNum": ""});
    };

    vm.deleteCompanyProfit = function deleteCompanyProfit(index) {
        if (index == 0) {
            alert("第一条信息不能删除!");
        } else {
            vm.companyProfit.remove(index);
        }
    };

    Array.prototype.remove = function (dx) {
        if (isNaN(dx) || dx > this.length) {
            return false;
        }
        for (var i = 0, n = 0; i < this.length; i++) {
            if (this[i] != this[dx]) {
                this[n++] = this[i]
            }
        }
        this.length -= 1
    };

    $scope.save = function () {
        var postData = vm.item;
        $log.debug("vm.item.clrRules---------", vm.item.clrRules);
        if (vm.item.clrRules == 01) {
            postData.bankClearCfgList = vm.companyBank;//商业银行
        }
        else if (vm.item.clrRules == 02) {
            postData.accountClearCfgList = vm.companyRout;//分账
        }
        else if (vm.item.clrRules == 03) {
            postData.propClearCfgList = vm.companyProfit;//分潤
        }
        $log.debug("postData---------", postData, "--------postData")
        $http.post(vm.url, postData, config.jsonHeader)
            .then(function (response) {
                if (response.data.success) {
                    alert("操作成功");
                    $uibModalInstance.close(vm.item);
                } else {
                    if(response.data.msg != ""){
                        alert(response.data.msg);
                    }else{
                        alert("新增失败");
                    }
                }
            }, function (response) {
                alert("操作失败");
                alert(response.status);
            })
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

angular.module('myApp').controller('updateMerchantClearCfgApp', function ($scope, $uibModalInstance, modalItem, $http,
                                                                          $httpParamSerializerJQLike, $log, myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };
    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
//商业
    if(vm.item.bankClearCfgList=="" || vm.item.bankClearCfgList==null){
        vm.item.bankClearCfgList.push({"companyCode": "", "clrPoint": "", "clrCycle": "", "clrRange": ""});
    }

    vm.addCompanyBank = function addCompanyBank(obj) {
        vm.item.bankClearCfgList.push({"companyCode": "", "clrPoint": "", "clrCycle": "", "clrRange": ""});
    };

    vm.deleteCompanyBank = function deleteCompanyBank(index) {
        if (index == 0) {
            alert("第一条信息不能删除!");
        } else {
            vm.item.bankClearCfgList.remove(index);
        }
    };
    //分账
    if(vm.item.accountClearCfgList=="" || vm.item.accountClearCfgList==null){
        vm.item.accountClearCfgList.push({"companyCode": "", "propNum": ""});
    }
    vm.addCompanyRout = function addCompanyRout(obj) {
        vm.item.accountClearCfgList.push({"companyCode": "", "propNum": ""});
    };

    vm.deleteCompanyRout = function deleteCompanyRout(index) {
        if (index == 0) {
            alert("第一条信息不能删除!");
        } else {
            vm.item.accountClearCfgList.remove(index);
        }
    };
    //分潤
    if(vm.item.propClearCfgList == "" || vm.item.propClearCfgList == null){
        vm.item.propClearCfgList.push({"companyCode": "", "propNum": ""});
    }
    vm.addCompanyProfit = function addCompanyProfit(obj) {
        vm.item.propClearCfgList.push({"companyCode": "", "propNum": ""});
    };

    vm.deleteCompanyProfit = function deleteCompanyProfit(index) {
        if (index == 0) {
            alert("第一条信息不能删除!");
        } else {
            vm.item.propClearCfgList.remove(index);
        }
    };
    Array.prototype.remove = function (dx) {
        if (isNaN(dx) || dx > this.length) {
            return false;
        }
        for (var i = 0, n = 0; i < this.length; i++) {
            if (this[i] != this[dx]) {
                this[n++] = this[i]
            }
        }
        this.length -= 1
    };
    $scope.save = function () {
        $log.debug("vm.item.clrRules---------", vm.item.clrRules);
        var postData = vm.item;
        if(postData.clrType == 01){
            postData.clrRules = null;
        }
        if (postData.clrRules == 01) {
            delete postData.accountClearCfgList;
            //delete vm.item.bankClearCfgList;
            delete postData.propClearCfgList;
            delete postData.clearKey;
            delete postData.$$hashKey;
        }
        else if (postData.clrRules == 02) {
            //delete postData.accountClearCfgList;
            delete postData.bankClearCfgList;
            delete postData.propClearCfgList;
            delete postData.clearKey;
            delete postData.$$hashKey;
        }
        else if (postData.clrRules == 03) {
            delete postData.accountClearCfgList;
            delete postData.bankClearCfgList;
            delete postData.clearKey;
            delete postData.$$hashKey;
            //delete postData.propClearCfgList;
        }
        else if (postData.clrRules == null) {
            delete postData.accountClearCfgList;
            delete postData.bankClearCfgList;
            delete postData.propClearCfgList;
            delete postData.clearKey;
            delete postData.$$hashKey;
        }
        $log.debug("postDate:", postData);
        $http.post(vm.url, postData, config.jsonHeader)
            .then(function (response) {
                if (response.data.success) {
                    alert("操作成功");
                    $uibModalInstance.close(vm.item);
                } else {
                    if(response.data.msg != ""){
                        alert(response.data.msg);
                    }else{
                        alert("新增失败");
                    }
                }
            }, function (response) {
                alert("操作失败");
                alert(response.status);
            })
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
