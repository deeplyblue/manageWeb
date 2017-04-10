/**
 * tanglu
 * 银行信息录入
 */

angular.module('myApp')
    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter','dateFilter','myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                                                                             CacheService, limitToFilter, filterFilter,dateFilter,myConstant) {
            //视图层变量viewModel
            var vm = $scope.vm = { };
            vm.constant = myConstant;
            //变量初始化
            //分页数据
            vm.pagination = {
                pageSize: 10,
                pageNum: 1
            };
            //查询条件
            vm.queryBean = {};
            vm.checkBankCode = true;
            //缓存数据初始化(需要缓存的key请自定义)
            /*数据格式{
             key1 :value1,
             key2:value2
             }*/
            vm.BANK_CODE = {};
            vm.cached = {
                BANK_STATUS:{},
                BANK_DETAIL_TYPE:{},
                IS_CROSS_TRANS:{},
                ACCT_TYPE:{},
                DELETE_STATUS:{}
            };
            CacheService.initCache(vm.cached);

            /*将前面缓存的数据格式化
             [{key:value},{key:value}]*/
            vm.getCache = function (key) {
                return CacheService.getCache(key);
            };

            (function init() {
                vm.queryBean = {};
                $http.post(config.ctx + '/reserve/bank/info/getPartner',{},config.jsonHeader)
                    .then(function (response) {
                        if (response.data.success){
                            vm.BANK_CODE=response.data.object;
                            format();
                        }else {
                            $log.error('获取数据%s失败',response.data.msg);
                        }
                    }, function (response) {
                        //$log.error('获取数据%s失败', cacheKey);
                    })
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

            function format(){
                vm.cached.BANK_LIST = [];
                for (var index in vm.BANK_CODE){
                    var tmpObj = {'code':index,'name':vm.BANK_CODE[index]['name']};
                    vm.cached.BANK_LIST.push(tmpObj);
                }
            }
            function queryDetail() {
                var queryForm = document.getElementById('queryForm');
                vm.url = angular.element(queryForm).prop('action');
                //组织参数：查询条件 + 分页数据
                var postData = angular.copy(vm.queryBean);
                postData.pageSize = vm.pagination.pageSize;
                postData.pageNum = vm.pagination.pageNum;
                vm.pagination.queryBean = postData;
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

            function updateChecked($event, submitMsgId) {
                CheckboxService.updateChecked($event, submitMsgId);
                $log.debug(submitMsgId);
                $log.debug(CheckboxService.getChecked());
            }

            function addItem() {
                OpenService({
                    modalTitle: '新增银行卡信息',
                    modalBody: 'toAdd',
                    url: 'add',
                    item: {},
                    cached: {
                        MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                        BANK_LIST:vm.cached.BANK_LIST,
                        ACCT_TYPE:vm.cached.ACCT_TYPE,
                        BANK_DETAIL_TYPE:vm.cached.BANK_DETAIL_TYPE,
                        IS_CROSS_TRANS:vm.cached.IS_CROSS_TRANS,
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
                console.info("size======:"+vm.pagination.list.length)
                var temp = vm.pagination.list.filter(function (item, index, array) {
                    if (item.submitMsgId == CheckboxService.getChecked()[0]) {
                        return true;
                    }
                })[0];
                console.info("temp:"+temp);
                OpenService({
                    modalTitle: '修改银行信息',
                    modalBody: 'toUpdate',
                    url: 'update',
                    item:temp,
                    cached: {
                        MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                        ALL_CITY: vm.cached.ALL_CITY,
                        BANK_LIST:vm.cached.BANK_LIST,
                        IS_CROSS_TRANS:vm.cached.IS_CROSS_TRANS,
                        ACCT_TYPE:vm.cached.ACCT_TYPE,
                        BANK_DETAIL_TYPE:vm.cached.BANK_DETAIL_TYPE
                    }
                },function(item){
                    $log.debug("item:" + item);
                },"updateBankCtrl",'myModalNoFooter.html');
            }

            function deleteItem() {
                if (CheckboxService.getChecked().length != 1) {
                    alert("必须勾选一条记录！");
                    return;
                }
                if (confirm("你要删除当前所选账号？")) {
                    $http.post('delete', $httpParamSerializerJQLike({'submitMsgId': CheckboxService.getChecked()[0]}))
                        .then(function (response) {
                            var data = response.data;
                            if (data.success) {
                                var item = vm.pagination.list.filter(function (item, index, array) {
                                    return item.msgId == CheckboxService.getChecked()[0];
                                })[0];
                                vm.pagination.list.splice(vm.pagination.list.indexOf(item), 1);
                            } else {
                                alert(data.msg);
                            }
                        }, function (response) {
                            alert(response.statusText);
                            $log.debug("error");
                        });
                }
            }
        }]);

angular.module('myApp').controller('updateBankCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
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
        console.info("vm.item:"+ vm.item);
        $scope.save = function () {
            var postData = angular.copy(vm.item);
            $log.debug("进入特殊修改类:{}",postData);
            delete postData.currency;
            delete postData.queryEndDate;
            delete postData.queryStartDate;
            delete postData.createBy;
            delete postData.remarkDesc;
            delete postData.deleteFlag;
            delete postData.status;
            delete postData.updateBy;
            delete postData.allAmt;
            delete postData.rowCount;
            delete postData.startRow;
            delete postData.createAt;
            delete postData.updateAt;
            delete postData.clearKey;
            delete postData.$$hashKey;
            $http.post(config.ctx + '/reserve/bank/info/update', $httpParamSerializerJQLike(postData))
                .then(function (response) {
                    if (response.data.success) {
                        alert("操作成功");
                        $uibModalInstance.close(vm.item);
                    } else {
                        alert(response.data.msg);
                    }
                }, function (response) {
                    alert("操作失败");
                    alert(response.data.msg);
                })
        }

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    }]);

