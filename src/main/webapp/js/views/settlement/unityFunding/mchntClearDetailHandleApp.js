/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'dateFilter', 'myConstant', 'growl', 'DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                                                                                                                CacheService, limitToFilter, filterFilter, dateFilter, myConstant, growl, DateCalculateService) {
            //视图层变量viewModel
            var vm = $scope.vm = {};
            vm.constant = myConstant;
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
            vm.validateOptions = {
                blurTrig: true,
                showError: function (elem, errorMessages) {
                    growl.addErrorMessage(errorMessages);
                    // angular.element(elem).after('<span>' + errorMessages + '</span>');
                },
                removeError: true

            };

            //缓存数据初始化(需要缓存的key请自定义)
            /*数据格式{
             key1 :value1,
             key2:value2
             }*/
            vm.cached = {
                CLR_FLAG: {},
                MERCHANT_CODE: {},
                REPORT_TYPE: {},
                COMANY_CODE:{},
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
            vm.toPayHandle = toPayHandle;
            vm.resetForm = resetForm;
            vm.toClearOrFeeHandle =toClearOrFeeHandle;

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
                            vm.pagination.list.forEach(function(it){
                                if(it.mchntPayOrgClearDetailModelList !=null) {
                                    it.mchntPayOrgClearDetailModelList.forEach(function (bean) {

                                        var adjustModel = {}
                                        bean.adjustModel = adjustModel;

                                    })
                                }
                                if(it.mchntClearDetailSplitModelList !=null) {
                                    it.mchntClearDetailSplitModelList.forEach(function (bean1) {
                                        var adjustModel = {}
                                        bean1.adjustModel = adjustModel;
                                    })

                                }


                            });
                            $log.debug(vm.pagination);
                            vm.sumObject = data.sumObject;
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

            //按支付机构明细经办
            function toPayHandle(bean,flag){
                var title ='按支付机构明细经办';
                var pagination;
                if (bean.mchntPayOrgClearDetailModelList !=null) {
                    pagination = bean
                    if(flag == '003'){
                        title='手续费';
                    }
                    //$log.debug(bean.mchntPayOrgClearDetailModelList);
                    OpenService({
                        modalTitle: title,
                        modalBody: 'toClearDetail',
                        url: '#',
                        item: pagination,
                        flag:flag,
                        clrFlag: bean.clrFlag,
                        cached: {
                            CLR_FLAG: vm.cached.CLR_FLAG,
                            MERCHANT_CODE: vm.cached.MERCHANT_CODE,
                            COMANY_CODE:vm.cached.COMANY_CODE,
                            REPORT_TYPE: vm.cached.REPORT_TYPE,
                            CLR_TYPE: vm.cached.CLR_TYPE,
                            CLR_ADJUST_FLAG: vm.cached.CLR_ADJUST_FLAG
                        }
                    }, function (item) {
                        $log.debug(item);
                        vm.queryDetail();
                    }, 'MchntClearHandleDetailCtrl', 'myModalNoFooter.html');
                } else {
                    alert('未找到相关数据');
                    return false;
                }


            }

            function toClearDetail(bean) {
                var pagination;
                $http.post(config.ctx + '/settlement/unityFunding/mchntClearDetail/queryClearDetail',
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
                                    MERCHANT_CODE: vm.cached.MERCHANT_CODE,
                                    COMANY_CODE:vm.cached.COMANY_CODE,
                                    REPORT_TYPE: vm.cached.REPORT_TYPE,
                                    CLR_TYPE: vm.cached.CLR_TYPE,
                                    CLR_ADJUST_FLAG: vm.cached.CLR_ADJUST_FLAG
                                }
                            }, function (item) {
                                $log.debug(item);
                                vm.queryDetail();
                            }, 'MchntClearHandleDetailCtrl', 'myModalNoFooter.html');
                        } else {
                            alert('未找到相关数据');
                            return false;
                        }
                    });
            }


            //按分账结算明细经办或手续费
            function toClearOrFeeHandle(bean,flag){
                $log.debug(bean.mchntClearDetailSplitModelList)
                var pagination;
                if (bean.mchntClearDetailSplitModelList !=null) {
                    var title;
                    if(flag == '002'){
                        title ='按分账结算明细经办'
                    }
                    if(flag == '003'){
                        title = '手续费经办'
                    }
                    pagination = bean
                    //$log.debug(bean.mchntPayOrgClearDetailModelList);
                    OpenService({
                        modalTitle: title,
                        modalBody: 'toFeeDetail',
                        url: '#',
                        item: pagination,
                        flag:flag,
                        clrFlag: bean.clrFlag,
                        cached: {
                            CLR_FLAG: vm.cached.CLR_FLAG,
                            MERCHANT_CODE: vm.cached.MERCHANT_CODE,
                            COMANY_CODE:vm.cached.COMANY_CODE,
                            REPORT_TYPE: vm.cached.REPORT_TYPE,
                            CLR_TYPE: vm.cached.CLR_TYPE,
                            CLR_ADJUST_FLAG: vm.cached.CLR_ADJUST_FLAG
                        }
                    }, function (item) {
                        $log.debug(item);
                        vm.queryDetail();
                    }, 'MchntFeeHandleDetailCtrl', 'myModalNoFooter.html');
                } else {
                    alert('未找到相关数据');
                    return false;
                }




            }



        }]);



angular.module('myApp').controller('MchntClearHandleDetailCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', 'dateFilter', 'CheckboxService', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                     $httpParamSerializerJQLike, $log, dateFilter, CheckboxService) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {},
            flag:'',
            clrFlag: ''

        }

        vm = $scope.vm = modalItem;
        $log.debug(vm)
        vm.adjustModel = {}

        vm.item.mchntPayOrgClearDetailModelList.forEach(function (it) {
            it.collapse = true;
        })

        vm.checked = [];

        vm.handleSome = function () {
            var postdata;
            if(vm.flag != '003'){
                if (CheckboxService.getCheckedNew(vm.item.mchntPayOrgClearDetailModelList).length == 0) {
                    alert("请选择记录！");
                    return;
                }

                postdata = angular.copy(vm.item);
                postdata.mchntPayOrgClearDetailModelList = [];

                CheckboxService.getCheckedNew(vm.item.mchntPayOrgClearDetailModelList).forEach(function (it) {
                    //return it.mchntCode + '#' + it.clrType + '#' + dateFilter(it.clrDate, 'yyyyMMdd');
                    var detail = {
                        'mchntCode':it.mchntCode,
                        'clrType':it.clrType,
                        'clrDate':it.clrDate,
                        'payOrgCode':it.payOrgCode,
                        'clrBatchNo':it.clrBatchNo,
                        'paySeq':it.paySeq
                    }
                    postdata.mchntPayOrgClearDetailModelList.push(detail);


                })

            }

            if(vm.flag == '003'){

                postdata = angular.copy(vm.item);
                postdata.mchntPayOrgClearDetailModelList = [];
                vm.item.mchntPayOrgClearDetailModelList.forEach(function(it){
                    var detail = {
                        'mchntCode':it.mchntCode,
                        'clrType':it.clrType,
                        'clrDate':it.clrDate,
                        'payOrgCode':it.payOrgCode,
                        'clrBatchNo':it.clrBatchNo,
                        'paySeq':it.paySeq
                    }
                    postdata.mchntPayOrgClearDetailModelList.push(detail);

                })
                //vm.item.mchntPayOrgClearDetailModelList.forEach(function (it) {
                //    //return it.mchntCode + '#' + it.clrType + '#' + dateFilter(it.clrDate, 'yyyyMMdd');
                //    var detail = {
                //        'mchntCode':it.mchntCode,
                //        'clrType':it.clrType,
                //        'clrDate':it.clrDate,
                //        'payOrgCode':it.payOrgCode
                //    }
                //    postdata.mchntPayOrgClearDetailModelList.push(detail);
                //
                //
                //})
            }




            if (confirm("确认经办？")) {
                $http.post(config.ctx + '/settlement/unityFunding/mchntClearDetail/handle',postdata,config.jsonHeader)
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

        vm.checkA = function(bean){
            vm.item.mchntPayOrgClearDetailModelList.forEach(function(it){
                if(bean.clrRules != null && it.clrRules == bean.clrRules){
                    it._checked = bean._checked;
                }
            });
        }

        vm.toggleAdjust = function (bean) {
            if (bean.collapse == false) {
                bean.collapse = true;
                return;
            }
            bean.collapse = false;
            bean.adjustModel = {};
        }

        vm.queryAdjust = function (bean) {
            if (bean.collapse == false) {
                bean.collapse = true;
                addBean(bean);
                return;
            }
            bean.adjustModel.mchntCode = bean.mchntCode;
            bean.adjustModel.clrDate = bean.clrDate;
            bean.adjustModel.payOrgCode = bean.payOrgCode;
            bean.adjustModel.clrType = bean.clrType;
            $http.post(config.ctx + '/settlement/unityFunding/mchntClearDetailAdjust/query',
                bean.adjustModel, config.jsonHeader)
                .then(function (response) {
                    bean.adjustModel={};
                    var data = response.data;
                    if (data.success) {
                        bean.adjustModel = data.object;
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
            if (isNaN(parseFloat(adjustModel.payableAmt))) {
                adjustModel.payableAmt = 0;
            }
            if (isNaN(parseFloat(adjustModel.receivableAmt))) {
                adjustModel.receivableAmt = 0;
            }
            if (isNaN(parseFloat(adjustModel.reFeeAmt))) {
                adjustModel.reFeeAmt = 0;
            }
            if (isNaN(parseFloat(adjustModel.feeAmt))) {
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

            adjustModel.mchntCode = bean.mchntCode;
            adjustModel.payOrgCode = bean.payOrgCode;
            adjustModel.clrDate = bean.clrDate;
            //adjustModel.clrBatchNo = bean.clrBatchNo;
            adjustModel.clrType = bean.clrType;
            $http.post(config.ctx + '/settlement/unityFunding/mchntClearDetailAdjust/save',
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
            setInit(bean);
            bean.transAmtP = parseFloat(bean.transAmtP) + parseFloat(bean.adjustModel.payableAmt) * 100;
            bean.transCountP = parseFloat(bean.transCountP) + parseFloat(bean.adjustModel.payableCount);
            bean.transAmtR = parseFloat(bean.transAmtR) + parseFloat(bean.adjustModel.receivableAmt) * 100;
            bean.transCountR = parseFloat(bean.transCountR) + parseFloat(bean.adjustModel.receivableCount);
            bean.feeAmtR = parseFloat(bean.feeAmtR) + parseFloat(bean.adjustModel.reFeeAmt) * 100;
            bean.feeAmtP = parseFloat(bean.feeAmtP) + parseFloat(bean.adjustModel.feeAmt) * 100;
        }

        function subtractBean(bean) {
            setInit(bean);
            bean.transAmtP = parseFloat(bean.transAmtP) - parseFloat(bean.adjustModel.payableAmt) * 100;
            bean.transCountP = parseFloat(bean.transCountP) - parseFloat(bean.adjustModel.payableCount);
            bean.transAmtR = parseFloat(bean.transAmtR) - parseFloat(bean.adjustModel.receivableAmt) * 100;
            bean.transCountR = parseFloat(bean.transCountR) - parseFloat(bean.adjustModel.receivableCount);
            bean.feeAmtR = parseFloat(bean.feeAmtR) - parseFloat(bean.adjustModel.reFeeAmt) * 100;
            bean.feeAmtP = parseFloat(bean.feeAmtP) - parseFloat(bean.adjustModel.feeAmt) * 100;
        }

        function setInit(bean) {
            if (!angular.isNumber(bean.adjustModel.payableAmt)) {
                bean.adjustModel.payableAmt = 0;
            }
            if (!angular.isNumber(bean.adjustModel.receivableAmt)) {
                bean.adjustModel.receivableAmt = 0;
            }
            if (!angular.isNumber(bean.adjustModel.reFeeAmt)) {
                bean.adjustModel.reFeeAmt = 0;
            }
            if (!angular.isNumber(bean.adjustModel.feeAmt)) {
                bean.adjustModel.feeAmt = 0;
            }
            if (!angular.isNumber(bean.adjustModel.payableCount)) {
                bean.adjustModel.payableCount = 0;
            }
            if (!angular.isNumber(bean.adjustModel.receivableCount)) {
                bean.adjustModel.receivableCount = 0;
            }
        }

        $scope.save = function () {
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }]);

//按分账结算明细经办或手续费
angular.module('myApp').controller('MchntFeeHandleDetailCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', 'dateFilter', 'CheckboxService', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                     $httpParamSerializerJQLike, $log, dateFilter, CheckboxService) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {},
            flag:'',
            clrFlag: ''
        }

        vm = $scope.vm = modalItem;

        vm.adjustModel = {}

        vm.item.mchntClearDetailSplitModelList.forEach(function (it) {
            it.collapse = true;
        })

        //vm.checked = [];


        vm.handleSomeFee = function () {

            //分账
            var postdata = angular.copy(vm.item);
            postdata.mchntClearDetailSplitModelList = [];
            postdata.mchntPayOrgClearDetailModelList=[];
            vm.item.mchntClearDetailSplitModelList.forEach(function(it){
                var detail = {
                    'mchntCode':it.mchntCode,
                    'clrType':it.clrType,
                    'clrDate':it.clrDate,
                    'mchntCodeSplit':it.mchntCodeSplit
                }
                postdata.mchntClearDetailSplitModelList.push(detail);

            })
            vm.item.mchntPayOrgClearDetailModelList.forEach(function(it){
                var detail = {
                    'mchntCode':it.mchntCode,
                    'clrType':it.clrType,
                    'clrDate':it.clrDate,
                    'payOrgCode':it.payOrgCode,
                    'clrBatchNo':it.clrBatchNo,
                    'paySeq':it.paySeq
                }
                postdata.mchntPayOrgClearDetailModelList.push(detail);
            })

            //if(vm.flag == '003'){
            //    //手续费经办
            //    if (CheckboxService.getCheckedNew(vm.item.mchntClearDetailSplitModelList).length == 0) {
            //        alert("请选择记录！");
            //        return;
            //    }
            //
            //     postdata = angular.copy(vm.item);
            //    postdata.mchntClearDetailSplitModelList = [];
            //
            //    CheckboxService.getCheckedNew(vm.item.mchntClearDetailSplitModelList).forEach(function (it) {
            //        //return it.mchntCode + '#' + it.clrType + '#' + dateFilter(it.clrDate, 'yyyyMMdd');
            //        var detail = {
            //            'mchntCode':it.mchntCode,
            //            'clrType':it.clrType,
            //            'clrDate':it.clrDate,
            //            'mchntCodeSplit':it.mchntCodeSplit
            //        }
            //        postdata.mchntClearDetailSplitModelList.push(detail);
            //        $log.debug("postdata:",postdata);
            //
            //    })
            //
            //}


            if (confirm("确认经办？")) {
                $http.post(config.ctx + '/settlement/unityFunding/mchntClearDetail/feeHandle',postdata
                    ,config.jsonHeader)
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
        vm.toggleAdjustFee = function (bean) {

            if (bean.collapse == false) {
                bean.collapse = true;
                return;
            }
            bean.collapse = false;
            bean.adjustModel = {};
        }

        vm.queryAdjustFee = function (bean) {
            if (bean.collapse == false) {
                bean.collapse = true;
                addBean(bean);
                return;
            }
            bean.adjustModel.mchntCode = bean.mchntCode;
            bean.adjustModel.clrDate = bean.clrDate;
            bean.adjustModel.clrType = bean.clrType;
            bean.adjustModel.mchntCodeSplit = bean.mchntCodeSplit;
            $http.post(config.ctx + '/settlement/unityFunding/mchntClearDetailAdjust/queryFee',
                bean.adjustModel, config.jsonHeader)
                .then(function (response) {
                    bean.adjustModel={};
                    var data = response.data;
                    if (data.success) {
                        bean.adjustModel = data.object;
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
        vm.saveFeeAdjust = function (bean, adjustBean) {
            var adjustModel = angular.copy(adjustBean);
            if (isNaN(parseFloat(adjustModel.payableAmt))) {
                adjustModel.payableAmt = 0;
            }
            if (isNaN(parseFloat(adjustModel.receivableAmt))) {
                adjustModel.receivableAmt = 0;
            }
            if (isNaN(parseFloat(adjustModel.reFeeAmt))) {
                adjustModel.reFeeAmt = 0;
            }
            if (isNaN(parseFloat(adjustModel.feeAmt))) {
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

            adjustModel.mchntCode = bean.mchntCode;
            adjustModel.clrDate = bean.clrDate;
            adjustModel.clrType = bean.clrType;
            adjustModel.mchntCodeSplit = bean.mchntCodeSplit;
            $http.post(config.ctx + '/settlement/unityFunding/mchntClearDetailAdjust/saveFee',
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
            setInit(bean);
            bean.payableAmt = parseFloat(bean.payableAmt) + parseFloat(bean.adjustModel.payableAmt) * 100;
            bean.payableCount = parseFloat(bean.payableCount) + parseFloat(bean.adjustModel.payableCount);
            bean.receivableAmt = parseFloat(bean.receivableAmt) + parseFloat(bean.adjustModel.receivableAmt) * 100;
            bean.receivableCount = parseFloat(bean.receivableCount) + parseFloat(bean.adjustModel.receivableCount);
            bean.feeAmt = parseFloat(bean.feeAmt) + parseFloat(bean.adjustModel.reFeeAmt) * 100;
            bean.reFeeAmt = parseFloat(bean.reFeeAmt) + parseFloat(bean.adjustModel.feeAmt) * 100;
        }

        function subtractBean(bean) {
            setInit(bean);
            bean.payableAmt = parseFloat(bean.payableAmt) - parseFloat(bean.adjustModel.payableAmt) * 100;
            bean.payableCount = parseFloat(bean.payableCount) - parseFloat(bean.adjustModel.payableCount);
            bean.receivableAmt = parseFloat(bean.receivableAmt) - parseFloat(bean.adjustModel.receivableAmt) * 100;
            bean.receivableCount = parseFloat(bean.receivableCount) - parseFloat(bean.adjustModel.receivableCount);
            bean.feeAmt = parseFloat(bean.feeAmt) - parseFloat(bean.adjustModel.reFeeAmt) * 100;
            bean.reFeeAmt = parseFloat(bean.reFeeAmt) - parseFloat(bean.adjustModel.feeAmt) * 100;
        }

        function setInit(bean) {
            if (!angular.isNumber(bean.adjustModel.payableAmt)) {
                bean.adjustModel.payableAmt = 0;
            }
            if (!angular.isNumber(bean.adjustModel.receivableAmt)) {
                bean.adjustModel.receivableAmt = 0;
            }
            if (!angular.isNumber(bean.adjustModel.reFeeAmt)) {
                bean.adjustModel.reFeeAmt = 0;
            }
            if (!angular.isNumber(bean.adjustModel.feeAmt)) {
                bean.adjustModel.feeAmt = 0;
            }
            if (!angular.isNumber(bean.adjustModel.payableCount)) {
                bean.adjustModel.payableCount = 0;
            }
            if (!angular.isNumber(bean.adjustModel.receivableCount)) {
                bean.adjustModel.receivableCount = 0;
            }
        }

        $scope.save = function () {
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }]);
