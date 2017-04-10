/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'dateFilter', 'myConstant', 'DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, dateFilter, myConstant, DateCalculateService) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        vm.downCfg = {
            contentType: 'jqLike',
            date: {
                'clrDate': 'yyyyMMdd',
                'settleDateBegin': 'yyyyMMdd',
                'settleDateEnd': 'yyyyMMdd'
            }
        }
        //变量初始化
        //分页数据
        vm.pagination = {
            pageSize: 10,
            pageNum: 1
        };
        vm.sumObject = {};
        //查询条件
        vm.queryBean = {};
        vm.queryBean.clrDateBegin = DateCalculateService.getToday();
        vm.queryBean.clrDateEnd = DateCalculateService.getToday();
        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            CLR_FLAG: {},
            COMANY_CODE: {},
            REPORT_TYPE: {},
            CLR_TYPE: {},
            CLR_ADJUST_FLAG: {}
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

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;

        /*------------------以上配置通用---------------------*/
        vm.toClearDetail = toClearDetail;

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            var postData = angular.copy(vm.queryBean);
            postData.pageSize = vm.pagination.pageSize;
            postData.pageNum = vm.pagination.pageNum;
            postData.beginDate = dateFilter(postData.clrDateBegin, 'yyyyMMdd');
            postData.endDate = dateFilter(postData.clrDateEnd, 'yyyyMMdd');
            postData.clrDateBegin = null;
            postData.clrDateEnd = null;
            $http.post(vm.url, $httpParamSerializerJQLike(postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination = data.object;
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert("操作失败");
                });

        };

        function resetForm() {
            vm.queryBean = {};
        }

        function toClearDetail(bean) {
            var pagination;
            $http.post(config.ctx + '/settlement/unityFunding/orgClearDetail/queryClearDetail',
                $httpParamSerializerJQLike({
                    clrBatchNo: bean.clrBatchNo,
                    clrType: bean.clrType,
                    clrFlag: bean.clrFlag,
                    beginDate: dateFilter(vm.queryBean.clrDateBegin, 'yyyyMMdd'),
                    endDate: dateFilter(vm.queryBean.clrDateEnd, 'yyyyMMdd')
                }))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        pagination = data.object;
                        OpenService({
                            modalTitle: '结算明细【' + bean.clrBatchNo + '】',
                            modalBody: 'toClearDetail',
                            url: '#',
                            item: pagination,
                            clrFlag: bean.clrFlag,
                            cached: {
                                CLR_FLAG: vm.cached.CLR_FLAG,
                                COMANY_CODE: vm.cached.COMANY_CODE,
                                REPORT_TYPE: vm.cached.REPORT_TYPE,
                                CLR_TYPE: vm.cached.CLR_TYPE,
                                CLR_ADJUST_FLAG: vm.cached.CLR_ADJUST_FLAG
                            }
                        }, function (item) {
                            $log.debug(item);
                            vm.queryDetail();
                        }, 'OrgClearHandleDetailCtrl', 'myModalNoFooter.html');
                    } else {
                        alert('未找到相关数据');
                        return false;
                    }
                });
        }
    }]);

angular.module('myApp').controller('OrgClearHandleDetailCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', 'dateFilter', 'CheckboxService', '$timeout', function ($scope, $uibModalInstance, modalItem, $http, $httpParamSerializerJQLike, $log, dateFilter, CheckboxService,
                                          $timeout) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {},
        clrFlag: ''
    }

    vm = $scope.vm = modalItem;

    vm.adjustModel = {}

    vm.item.list.forEach(function (it) {
        it.collapse = true;
    })

    vm.checked = [];

    vm.handleSome = function () {
        if (CheckboxService.getCheckedNew(vm.item.list).length == 0) {
            alert("请选择记录！");
            return;
        }
        if (confirm("确认经办？")) {
            $http.post(config.ctx + '/settlement/unityFunding/orgClearDetail/handle',
                $httpParamSerializerJQLike({
                    'checked': CheckboxService.getCheckedNew(vm.item.list).map(function (it) {
                        return it.orgCode + '#' + it.clrType + '#' + dateFilter(it.clrDate, 'yyyyMMdd');
                    }),
                    clrBatchNo: CheckboxService.getCheckedNew(vm.item.list)[0].clrBatchNo
                }))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert('经办成功!');
                        $scope.cancel();
                    } else {
                        alert('经办失败!');
                    }
                }, function () {
                    alert('经办失败!');
                })
        }
    }

    vm.toggleAdjust = function (bean) {
        if (bean.collapse == false) {
            bean.collapse = true;
            return;
        }
        bean.collapse = false;
        vm.adjustModel = {};
    }

    vm.queryAdjust = function (bean) {
        if (bean.collapse == false) {
            bean.collapse = true;
            addBean(bean);
            return;
        }
        vm.adjustModel.payOrgCode = bean.orgCode;
        vm.adjustModel.clrDate = bean.clrDate;
        vm.adjustModel.clrBatchNo = bean.clrBatchNo;
        vm.adjustModel.clrType = bean.clrType;
        $http.post(config.ctx + '/settlement/unityFunding/payClearDetailAdjust/query',
            vm.adjustModel, config.jsonHeader)
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    vm.adjustModel = data.object;
                    subtractBean(bean);
                    bean.collapse = false;
                } else {
                    alert('系统忙,请稍后再试');
                }
            }, function (response) {
                alert(response.data.msg);
            })
    }

    //bean是原对象
    vm.saveAdjust = function (bean, adjustBean) {
        var adjustModel = angular.copy(adjustBean);
        if(isNaN(parseFloat(adjustModel.payableAmt))){
         adjustModel.payableAmt = 0;
         }
         if(isNaN(parseFloat(adjustModel.receivableAmt))){
         adjustModel.receivableAmt = 0;
         }
         if(isNaN(parseFloat(adjustModel.reFeeAmt))){
         adjustModel.reFeeAmt = 0;
         }
         if(isNaN(parseFloat(adjustModel.feeAmt))){
         adjustModel.feeAmt = 0;
         }
        if (isNaN(parseFloat(adjustModel.payableCount))) {
            adjustModel.payableCount = 0;
        }
        if (isNaN(parseFloat(adjustModel.receivableCount))) {
            adjustModel.receivableCount = 0;
        }
        adjustModel.payableAmt = adjustModel.payableAmt * 100;
        adjustModel.receivableAmt = adjustModel.receivableAmt * 100;
        adjustModel.reFeeAmt = adjustModel.reFeeAmt * 100;
        adjustModel.feeAmt = adjustModel.feeAmt * 100;
        adjustModel.clrNetAmt = adjustModel.payableAmt - adjustModel.receivableAmt;
        adjustModel.clrAmt = adjustModel.reFeeAmt - adjustModel.feeAmt;

        adjustModel.payOrgCode = bean.orgCode;
        adjustModel.clrDate = bean.clrDate;
        adjustModel.clrBatchNo = bean.clrBatchNo;
        adjustModel.clrType = bean.clrType;
        $http.post(config.ctx + '/settlement/unityFunding/payClearDetailAdjust/save',
            adjustModel, config.jsonHeader)
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    alert("保存成功！");
                    bean.collapse = true;
                    bean.clrAdjustFlag = '02';
                    addBean(bean);
                } else {
                    alert(data.msg);
                }
            }, function (response) {
                alert("操作失败");
            });
    }

    function addBean(bean) {
        setInit();
        bean.transAmtP = parseFloat(bean.transAmtP) + parseFloat(vm.adjustModel.payableAmt) * 100;
        bean.transCountP = parseFloat(bean.transCountP) + parseFloat(vm.adjustModel.payableCount);
        bean.transAmtR = parseFloat(bean.transAmtR) + parseFloat(vm.adjustModel.receivableAmt) * 100;
        bean.transCountR = parseFloat(bean.transCountR) + parseFloat(vm.adjustModel.receivableCount);
        bean.feeAmtR = parseFloat(bean.feeAmtR) + parseFloat(vm.adjustModel.reFeeAmt) * 100;
        bean.feeAmtP = parseFloat(bean.feeAmtP) + parseFloat(vm.adjustModel.feeAmt) * 100;
    }

    function subtractBean(bean) {
        setInit();
        bean.transAmtP = parseFloat(bean.transAmtP) - parseFloat(vm.adjustModel.payableAmt) * 100;
        bean.transCountP = parseFloat(bean.transCountP) - parseFloat(vm.adjustModel.payableCount);
        bean.transAmtR = parseFloat(bean.transAmtR) - parseFloat(vm.adjustModel.receivableAmt) * 100;
        bean.transCountR = parseFloat(bean.transCountR) - parseFloat(vm.adjustModel.receivableCount);
        bean.feeAmtR = parseFloat(bean.feeAmtR) - parseFloat(vm.adjustModel.reFeeAmt) * 100;
        bean.feeAmtP = parseFloat(bean.feeAmtP) - parseFloat(vm.adjustModel.feeAmt) * 100;
    }

    function setInit() {
        if (!angular.isNumber(vm.adjustModel.payableAmt)) {
            vm.adjustModel.payableAmt = 0;
        }
        if (!angular.isNumber(vm.adjustModel.receivableAmt)) {
            vm.adjustModel.receivableAmt = 0;
        }
        if (!angular.isNumber(vm.adjustModel.reFeeAmt)) {
            vm.adjustModel.reFeeAmt = 0;
        }
        if (!angular.isNumber(vm.adjustModel.feeAmt)) {
            vm.adjustModel.feeAmt = 0;
        }
        if (!angular.isNumber(vm.adjustModel.payableCount)) {
            vm.adjustModel.payableCount = 0;
        }
        if (!angular.isNumber(vm.adjustModel.receivableCount)) {
            vm.adjustModel.receivableCount = 0;
        }
    }

    $scope.save = function () {
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);
