/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'dateFilter', 'myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, dateFilter, myConstant) {
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
            MERCHANT_CODE: {},
            MERCHANT_ABBR_NAME: {},
            MCHNT_STATUS: {},
            REPRT_TYPE: {},
            CHK_DEAL_FLAG: {},
            CHK_METHOD: {},
            CA_APPLY_STATE: {},
            MCHNT_TYPE: {},
            COMPANY_TYPE: {}
        };

        (function initCache() {

            CacheService.getObj('ALL_CITY', function (key, cacheObj) {
                vm.cached['ALL_CITY'] = cacheObj;
            });
            CacheService.getObj('MCC_CODE', function (key, cacheObj) {
                vm.cached['MCC_CODE'] = cacheObj;
            });

            CacheService.initCaches(vm.cached);
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
            var postData = angular.copy(vm.queryBean);
            postData.pageSize = vm.pagination.pageSize;
            postData.pageNum = vm.pagination.pageNum;
            postData.beginTime = dateFilter(postData.beginTime, 'yyyy-MM-dd');
            postData.endTime = dateFilter(postData.endTime, 'yyyy-MM-dd');
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
                modalTitle: '新增商户',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                    ALL_CITY: vm.cached.ALL_CITY,
                    REPRT_TYPE: CacheService.getCache('REPRT_TYPE'),
                    CHK_DEAL_FLAG: CacheService.getCache('CHK_DEAL_FLAG'),
                    CHK_METHOD: CacheService.getCache('CHK_METHOD'),
                    CA_APPLY_STATE: CacheService.getCache('CA_APPLY_STATE'),
                    MCHNT_TYPE: CacheService.getCache('MCHNT_TYPE'),
                    COMPANY_TYPE: CacheService.getCache('COMPANY_TYPE'),
                    MCC_CODE: vm.cached.MCC_CODE
                }
            }, function (item) {

            }, 'AddMerchantInfoModalInstanceCtrl','myModalNoFooter.html');
        }

        function updateItemEnableFlag(item, temp) {

            var postData = angular.copy(item);
            postData.createTime = null;
            postData.lastUpdTime = null;
            postData.merchantOnlineDate = dateFilter(postData.merchantOnlineDate, 'yyyyMMdd');
            postData.openDate = dateFilter(postData.openDate, 'yyyyMMdd');
            postData.auditDate = dateFilter(postData.auditDate, 'yyyyMMdd');
            postData.merchantStatus = temp;

            $http.post(config.ctx + "/merchant/info/updateByStatus", $httpParamSerializerJQLike(postData))
                .then(function (response) {
                    if (response.data.success) {
                        item.merchantStatus = postData.merchantStatus;
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

            var pro;
            var city;
            //var mccOne='00';
            var mccOne;
            var mccTwo;
            var mccThree;


            var item = vm.pagination.list.filter(function (item, index, array) {
                if (item.merchantCode == CheckboxService.getChecked()[0]) {
                    console.debug(item.merchantArea);

                    $log.debug(vm.cached);
                    if (item.merchantArea){

                        pro = vm.cached.ALL_CITY.filter(function (it) {
                            if (item.merchantArea && it.areaCode == item.merchantArea) {
                                console.debug(it);
                                return true;
                            }
                        })[0];
                    }

                    if (pro && pro.citys && item.cityCode){

                        city = pro.citys.filter(function (it) {
                            if (item.cityCode && it.cityCode == item.cityCode) {
                                return true;
                            }
                        })[0];
                    }

                    if (item.mccCode){

                        mccOne = vm.cached.MCC_CODE.filter(function (it) {
                            //截取一级字符串
                            if (it.oValue == item.mccCode.substring(0, 2)) {
                                return true;
                            }
                        })[0];
                    }

                    if (mccOne && mccOne.tList) {
                        mccTwo = mccOne.tList.filter(function (it) {
                            //截取二级字符串
                            if (it.tValue == item.mccCode.substring(2, 4)) {
                                return true;
                            }
                        })[0];
                    }

                    console.log(mccTwo);

                    if (mccTwo && mccTwo.trList) {
                        mccThree = mccTwo.trList.filter(function (it) {
                            //截取三级字符串
                            if (it.trValue == item.mccCode.substring(4, 6)) {
                                return true;
                            }
                        })[0];
                    }

                    return true;
                }


            })[0];

            console.log('ready to open second.');
            OpenService({
                modalTitle: '修改用户',
                modalBody: 'toUpdate',
                url: 'update',
                item: item,
                pro: pro,
                city: city,
                mccOne: mccOne,
                mccTwo: mccTwo,
                mccThree: mccThree,
                cached: {
                    MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                    ALL_CITY: vm.cached.ALL_CITY,
                    REPRT_TYPE: CacheService.getCache('REPRT_TYPE'),
                    CHK_DEAL_FLAG: CacheService.getCache('CHK_DEAL_FLAG'),
                    CHK_METHOD: CacheService.getCache('CHK_METHOD'),
                    CA_APPLY_STATE: CacheService.getCache('CA_APPLY_STATE'),
                    MCHNT_TYPE: CacheService.getCache('MCHNT_TYPE'),
                    COMPANY_TYPE: CacheService.getCache('COMPANY_TYPE'),
                    MCC_CODE: vm.cached.MCC_CODE
                }
            }, function (item) {
                vm.queryDetail();
            },'updateMerchantInfoModalInstanceCtrl','myModalNoFooter.html');
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
    }]);


angular.module('myApp').controller('AddMerchantInfoModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'dateFilter', '$log', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                 $httpParamSerializerJQLike, dateFilter, $log, myConstant) {

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
        postData.merchantOnlineDate = dateFilter(postData.merchantOnlineDate, 'yyyyMMdd');
        $http.post(vm.url, $httpParamSerializerJQLike(postData))
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


angular.module('myApp').controller('updateMerchantInfoModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', 'dateFilter', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                    $httpParamSerializerJQLike, $log, dateFilter, myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }
    $scope.vm.item.mccOne = '10';


    console.log(modalItem)
    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
    //vm.mccOne = '166';
    //vm.mccTwo = '00';
    //vm.mccThree = '00';
    $scope.save = function () {
        var postData = angular.copy(vm.item);
        postData.merchantOnlineDate = dateFilter(postData.merchantOnlineDate, 'yyyyMMdd');
        postData.createTime = null;
        postData.lastUpdTime = null;
        postData.openDate = null;
        postData.auditDate = null;
        $log.debug(vm.postData);
        $http.post(vm.url, $httpParamSerializerJQLike(postData))
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