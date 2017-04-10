/**
 * Created by wuxg on 2016/5/31.
 */

angular.module('myApp')

    .controller('queryCtrl', [ '$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'myConstant',function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, myConstant) {
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
            BANK_INFO: {},
            BANK_CARD_TYPE: {},
            MCHNT_TRANS_CODE: {},
            MERCHANT_CODE: {},
            CONN_CHANNEL: {}
        };

       
        (function initCache() {
            CacheService.initCache(vm.cached);
            initTemp();
        }());

        function initTemp() {
            $http.post(config.ctx + '/merchant/feeCfg/initTemp', $httpParamSerializerJQLike({companyType: '03'}))
                .then(function (response) {
                    if (response.data.success) {
                        vm.cached.TEMP_lIST = response.data.object;
                        //$log.info('获取手续费模版数据c成功,%s',JSON.stringify(vm.cached.TEMP_lIST));
                        format();
                    } else {
                        $log.error('获取手续费模版数据失败,%s', JSON.stringify(response));
                    }
                }, function (response) {
                    $log.error('获取手续费模版数据失败');
                })
        }

        function format() {
            vm.cached.TEMP_CACHE = [];
            for (var index in vm.cached.TEMP_lIST) {
                var temp = vm.cached.TEMP_lIST[index];
                var item = {'id': temp['id'], 'templateName': temp['templateName']};
                vm.cached.TEMP_CACHE.push(item);
            }
        }

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

        vm.updateItemEnableFlag = updateItemEnableFlag;

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

        function updateItemEnableFlag(item, status) {
            var postData = angular.copy(item);
            delete postData.feeCfgKey;
            postData.enableFlag = status;
            $http.post(config.ctx+'/merchant/feeCfg/updateItemEnableFlag', postData,config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        item.enableFlag = status;
                        alert("操作成功！");
                    } else {
                        alert(data.msg);
                    }
                });

        }

        function resetForm() {
            vm.queryBean = {};
            vm.queryBean.companyType = '03';
        }

        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }

        function addItem() {
            OpenService({
                modalTitle: '增加手续费信息',
                modalBody: 'toAdd',
                url: 'addFeeCfg',
                item: {},
                tempSize: new Array(1),
                cached: {
                    BANK_INFO: vm.getCache('BANK_INFO'),
                    BANK_CARD_TYPE: vm.getCache('BANK_CARD_TYPE'),
                    MCHNT_TRANS_CODE: vm.getCache('MCHNT_TRANS_CODE'),
                    MERCHANT_CODE: vm.getCache('MERCHANT_CODE'),
                    CONN_CHANNEL: vm.getCache('CONN_CHANNEL'),
                    TEMP_lIST: vm.cached.TEMP_lIST,
                    TEMP_CACHE: vm.cached.TEMP_CACHE
                }
            }, function (item) {
                vm.queryDetail();
            }, "privateAddMerchantFeeCfgInfoCtrl",'myModalNoFooter.html');
        }

        function updateItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }

            var temp = CheckboxService.getCheckedNew(vm.pagination.list)[0];
            OpenService({
                modalTitle: '修改手续费模板信息',
                modalBody: 'toUpdate',
                url: 'updateFeeCfg',
                item: temp,
                tempSize: new Array(1),
                cached: {
                    BANK_INFO: vm.getCache('BANK_INFO'),
                    BANK_CARD_TYPE: vm.getCache('BANK_CARD_TYPE'),
                    MCHNT_TRANS_CODE: vm.getCache('MCHNT_TRANS_CODE'),
                    MERCHANT_CODE: vm.cached.MERCHANT_CODE,
                    CONN_CHANNEL: vm.getCache('CONN_CHANNEL'),
                    TEMP_lIST: vm.cached.TEMP_lIST,
                    TEMP_CACHE: vm.cached.TEMP_CACHE
                }
            }, function (item) {
                vm.queryDetail();
            }, "privateUpdateMerchantFeeCfgInfoCtrl",'myModalNoFooter.html');
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
                            return item.feeCfgNo == CheckboxService.getChecked()[0];
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

angular.module('myApp').controller('privateAddMerchantFeeCfgInfoCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', '$timeout', 'dateFilter', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                 $httpParamSerializerJQLike, $log, $timeout, dateFilter, myConstant) {
    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
    vm.tempParams = {};
    vm.validateOptions = {
        blurTrig: true,
        showError: function (elem, errorMessages) {
            // growl.addErrorMessage(errorMessages);
            // angular.element(elem).after('<span>' + errorMessages + '</span>');
        },
        removeError: true
    };

    (function () {
        vm.item.feeSegRateModelList = [];
        for (var i = 0; i < 1; i++) {
            vm.item.feeSegRateModelList.push({'segLowLmt': '', orderSeq: i + 1});
        }
        vm.item.feeSegRateModelList[0].segLowLmt = '0';
        vm.item.feeSegRateModelList[0].segUppLmt = '9999999999999';
    }());
    vm.appendSizeFun = function () {
        if (vm.tempParams.feeSegCount) {
            vm.item.feeSegRateModelList = [];
            for (var i = 0; i < vm.tempParams.feeSegCount; i++) {
                vm.item.feeSegRateModelList.push({'segLowLmt': '', orderSeq: i + 1});
            }
            vm.item.feeSegRateModelList[0].segLowLmt = '0';
            vm.item.feeSegRateModelList[Number(vm.tempParams.feeSegCount) - 1].segUppLmt = '99999999999999999';
        }
    };
    //vm.item.feeSegRateModelList=[];
    $scope.save = function () {
        vm.postData = angular.copy(vm.item);
        var flag=vm.postData.reFeeFlag;
        var temp = vm.cached.TEMP_lIST[vm.tempParams.tempId];
        for (var index in temp) {
            if (!('id' == index || 'templateName' == index || 'creator' == index || 'modifief' == index
                || 'createTime' == index || 'lastUpdTime' == index)) {
                vm.postData[index] = temp[index];
            }
        }
        for (var i in vm.postData.feeSegRateModelList) {
            if(vm.postData.feeUnit == '01') {
                vm.postData.feeSegRateModelList[i]['segLowLmt'] = vm.postData.feeSegRateModelList[i]['segLowLmt'] * 100;
                vm.postData.feeSegRateModelList[i]['segUppLmt'] = vm.postData.feeSegRateModelList[i]['segUppLmt'] * 100;
            }
            if (vm.postData.feeSegRateModelList[i]['feeSegMethod'] == '01') {
                vm.postData.feeSegRateModelList[i]['feeLowLmt'] = '';
                vm.postData.feeSegRateModelList[i]['feeUppLmt'] = '';
            } else if (vm.postData.feeSegRateModelList[i]['feeSegMethod'] == '02') {
                vm.postData.feeSegRateModelList[i]['feeLowLmt'] = vm.postData.feeSegRateModelList[i]['feeLowLmt'] * 100;
                vm.postData.feeSegRateModelList[i]['feeUppLmt'] = vm.postData.feeSegRateModelList[i]['feeUppLmt'] * 100;
            }
        }
        $log.info("------------" + JSON.stringify(vm.postData));
        var config = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        if(!angular.isUndefined(flag)){
            vm.postData.reFeeFlag=flag;
        }
        $http.post(vm.url, vm.postData, config)
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
        vm.checkFeeUppLmt = function (index) {
            if (!vm.item.feeSegRateModelList[index].feeUppLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)) {
                vm.item.feeSegRateModelList[index].feeUppLmt = null;
            }
            if (!vm.item.feeSegRateModelList[index].feeLowLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)) {
                vm.item.feeSegRateModelList[index].feeLowLmt = null;
            }
            var segUppLmt = parseFloat(vm.item.feeSegRateModelList[index].feeUppLmt);
            var segLowLmt = parseFloat(vm.item.feeSegRateModelList[index].feeLowLmt);
            if (segUppLmt > segLowLmt) {
                true;
            } else {
                vm.item.feeSegRateModelList[index].feeUppLmt = null;
            }
        }
        vm.checkFeeLowLmt = function (index) {
            if (!vm.item.feeSegRateModelList[index].feeLowLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)) {
                vm.item.feeSegRateModelList[index].feeLowLmt = null;
            }
            var segUppLmt = parseFloat(vm.item.feeSegRateModelList[index].feeUppLmt);
            var segLowLmt = parseFloat(vm.item.feeSegRateModelList[index].feeLowLmt);
            if (segUppLmt > segLowLmt) {
                true;
            } else {
                vm.item.feeSegRateModelList[index].feeUppLmt = null;
            }

        }

    vm.checkUppLmtAmt = function (index) {
        if (!vm.item.feeSegRateModelList[index].segUppLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)) {
            vm.item.feeSegRateModelList[index].segUppLmt = null;
        }
        var segUppLmt = parseFloat(vm.item.feeSegRateModelList[index].segUppLmt);
        var segLowLmt = parseFloat(vm.item.feeSegRateModelList[index].segLowLmt);
        if (segUppLmt > segLowLmt) {
            vm.item.feeSegRateModelList[index + 1].segLowLmt = vm.item.feeSegRateModelList[index].segUppLmt;
        } else {
            vm.item.feeSegRateModelList[index].segUppLmt = null;
        }
    }
}]);


angular.module('myApp').controller('privateUpdateMerchantFeeCfgInfoCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', '$timeout', 'dateFilter', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                    $httpParamSerializerJQLike, $log, $timeout, dateFilter, myConstant) {
    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
    vm.tempParams = {};
    vm.validateOptions = {
        blurTrig: true,
        showError: function (elem, errorMessages) {
            // growl.addErrorMessage(errorMessages);
            // angular.element(elem).after('<span>' + errorMessages + '</span>');
        },
        removeError: true
    };

    (function () {
        var count = vm.item.feeSegRateModelList.length;
        vm.tempParams.feeSegCount = count;
    }());
    vm.appendSizeFun = function () {
        if (vm.tempParams.feeSegCount) {
            vm.item.feeSegRateModelList = [];
            for (var i = 0; i < vm.tempParams.feeSegCount; i++) {
                vm.item.feeSegRateModelList.push({'segLowLmt': '', orderSeq: i + 1});
            }
            vm.item.feeSegRateModelList[0].segLowLmt = '0';
            vm.item.feeSegRateModelList[Number(vm.tempParams.feeSegCount) - 1].segUppLmt = '99999999999999999';
        }
    };

    $scope.save = function () {
        vm.postData = angular.copy(vm.item);
        delete vm.postData.feeCfgKey;
        $log.info("------------" + JSON.stringify(vm.postData));
        for (var i in vm.postData.feeSegRateModelList) {
            if(vm.postData.feeUnit == '01') {
                vm.postData.feeSegRateModelList[i]['segLowLmt'] = vm.postData.feeSegRateModelList[i]['segLowLmt'] * 100;
                vm.postData.feeSegRateModelList[i]['segUppLmt'] = vm.postData.feeSegRateModelList[i]['segUppLmt'] * 100;
            }
            if (vm.postData.feeSegRateModelList[i]['feeSegMethod'] == '01') {
                vm.postData.feeSegRateModelList[i]['feeLowLmt'] = '';
                vm.postData.feeSegRateModelList[i]['feeUppLmt'] = '';
            } else if (vm.postData.feeSegRateModelList[i]['feeSegMethod'] == '02') {
                vm.postData.feeSegRateModelList[i]['feeLowLmt'] = vm.postData.feeSegRateModelList[i]['feeLowLmt'] * 100;
                vm.postData.feeSegRateModelList[i]['feeUppLmt'] = vm.postData.feeSegRateModelList[i]['feeUppLmt'] * 100;
            }
        }
        var config = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        $http.post(vm.url, vm.postData, config)
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
        vm.checkFeeUppLmt = function (index) {
            if (!vm.item.feeSegRateModelList[index].feeUppLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)) {
                vm.item.feeSegRateModelList[index].feeUppLmt = null;
            }
            if (!vm.item.feeSegRateModelList[index].feeLowLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)) {
                vm.item.feeSegRateModelList[index].feeLowLmt = null;
            }
            var segUppLmt = parseFloat(vm.item.feeSegRateModelList[index].feeUppLmt);
            var segLowLmt = parseFloat(vm.item.feeSegRateModelList[index].feeLowLmt);
            if (segUppLmt > segLowLmt) {
                true;
            } else {
                vm.item.feeSegRateModelList[index].feeUppLmt = null;
            }
        }
        vm.checkFeeLowLmt = function (index) {
            if (!vm.item.feeSegRateModelList[index].feeLowLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)) {
                vm.item.feeSegRateModelList[index].feeLowLmt = null;
            }
            var segUppLmt = parseFloat(vm.item.feeSegRateModelList[index].feeUppLmt);
            var segLowLmt = parseFloat(vm.item.feeSegRateModelList[index].feeLowLmt);
            if (segUppLmt > segLowLmt) {
                true;
            } else {
                vm.item.feeSegRateModelList[index].feeUppLmt = null;
            }

        }

    vm.checkUppLmtAmt = function (index) {
        if (!vm.item.feeSegRateModelList[index].segUppLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)) {
            vm.item.feeSegRateModelList[index].segUppLmt = null;
        }
        var segUppLmt = parseFloat(vm.item.feeSegRateModelList[index].segUppLmt);
        var segLowLmt = parseFloat(vm.item.feeSegRateModelList[index].segLowLmt);
        if (segUppLmt > segLowLmt) {
            vm.item.feeSegRateModelList[index + 1].segLowLmt = vm.item.feeSegRateModelList[index].segUppLmt;
        } else {
            vm.item.feeSegRateModelList[index].segUppLmt = null;
        }
    }

    for (var i in vm.item.feeSegRateModelList) {
        if(vm.item.feeUnit == '01') {
            vm.item.feeSegRateModelList[i]['segLowLmt'] = vm.item.feeSegRateModelList[i]['segLowLmt'] / 100;
            vm.item.feeSegRateModelList[i]['segUppLmt'] = vm.item.feeSegRateModelList[i]['segUppLmt'] / 100;
        }
    }
}]);