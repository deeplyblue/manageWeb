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
        //变量初始化
        //分页数据
        vm.pagination = {
            pageSize: 10,
            pageNum: 1
        };
        //查询条件
        vm.queryBean = {
            beginDate: DateCalculateService.getYesterday(),
            endDate: DateCalculateService.getToday()
        };

        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            ORDER_STATUS: {}
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


        // vm.updateChecked = updateChecked;
        // vm.updateItem = updateItem;
        // vm.deleteItem = deleteItem;
        // vm.roleAllocate = roleAllocate;
        vm.upload = upload;
        vm.confirmUpload = confirmUpload;
        vm.queryPaymentDetail = queryPaymentDetail;
        vm.cancel = cancel;

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
            vm.url = config.ctx + '/payment/searchPaymentBatchList';

            //组织参数：查询条件 + 分页数据
            vm.postData = angular.copy(vm.queryBean);
            vm.postData.pageSize = vm.pagination.pageSize;
            vm.postData.pageNum = vm.pagination.pageNum;
            vm.postData.beginDate = dateFilter(vm.postData.beginDate, 'yyyyMMdd');
            vm.postData.endDate = dateFilter(vm.postData.endDate, 'yyyyMMdd');
            vm.postData.operateBeginDate = dateFilter(vm.postData.operateBeginDate, 'yyyyMMdd');
            vm.postData.operateEndDate = dateFilter(vm.postData.operateEndDate, 'yyyyMMdd');
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

        }

        function resetForm() {
            vm.queryBean = {};
        }

        function upload() {
            OpenService({
                modalTitle: '文件上传',
                modalBody: 'toUpload',
                url: 'upload',
                item: {},
                cached: {}
            }, function (iteaddItemm) {
                queryDetail();
            }, 'uploadModalInstanceCtrl', 'myModalNoSave.html');
        }

        function confirmUpload() {
            OpenService({
                modalTitle: '确认文件上传',
                modalBody: 'toConfirmUpload',
                url: 'confirmUpload',
                item: {},
                cached: {}
            }, function (iteaddItemm) {
                queryDetail();
            }, 'confirmUploadModalInstanceCtrl', 'myModalNoSave.html');
        }

        function queryPaymentDetail(batchNo) {
            var results;
            $http.post(config.ctx + '/payment/queryPaymentDetail', $httpParamSerializerJQLike({batchNo: batchNo}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        results = data.object;
                        OpenService({
                            modalTitle: '付款明细查询',
                            modalBody: 'toQueryDetail',
                            url: 'queryDetail',
                            item: {results: results, batchNo: batchNo},
                            cached: {ORDER_STATUS: vm.cached.ORDER_STATUS}
                        }, function (iteaddItemm) {
                            queryDetail();
                        }, 'QueryDetailModalInstanceCtrl', 'myModalNoSave.html');
                    } else {
                        alert(data.msg);
                    }
                });
        }

        function cancel(batchNo) {
            if (!confirm("确认取消文件？")) {
                return false;
            }
            $http.post(config.ctx + '/payment/check', $httpParamSerializerJQLike({
                    auditState: '03',
                    batchNo: batchNo,
                    verifyCode: "",
                    smsVerifyCode: ""
                }))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert("处理成功");
                        queryDetail();
                    } else {
                        alert(data.msg);
                    }
                });
        }

    }]);

angular.module('myApp').controller('QueryDetailModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http', 'CheckboxService',
    '$httpParamSerializerJQLike', function ($scope, $uibModalInstance, modalItem, $http, CheckboxService,
                                                                             $httpParamSerializerJQLike) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };
    vm = $scope.vm = modalItem;
    vm.queryBean = {};
    //分页数据
    vm.pagination = {
        pageSize: 10,
        pageNum: 1,
        rowCount: vm.item.results.rowCount,
        list: vm.item.results.list
    };

    vm.myPopoverTemplate = 'myPopoverTemplate.html';
    vm.popover = {open: false};
    vm.queryDetail = queryDetail;
    vm.check = check;
    vm.sendSmsVerifyCode = sendSmsVerifyCode;
    vm.resetForm = resetForm;
    vm.downloadResultFile = downloadResultFile;
    vm.paymentAgain = paymentAgain;

    $scope.save = function () {

    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    function resetForm() {
        vm.queryBean = {};
    }

    function paymentAgain() {
        var batchNo = vm.item.batchNo;
        if (batchNo == null || batchNo == undefined || batchNo == '') {
            alert("批次号不能为空");
            return false;
        }
        if (!confirm("确认付款已中断？")) {
            return false;
        }
        $http.post(config.ctx + '/payment/paymentAgain', $httpParamSerializerJQLike({batchNo: batchNo}))
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    alert("处理成功");
                } else {
                    alert(data.msg);
                }
            });
    }

    function check(auditState) {
        var batchNo = vm.item.batchNo;
        var verifyCode = vm.queryBean.verifyCode;
        var smsVerifyCode = vm.queryBean.smsVerifyCode;
        if ('04' == auditState) {
            if (verifyCode == undefined || verifyCode == '') {
                alert("密码不能为空");
                return false;
            }
            if (smsVerifyCode == undefined || smsVerifyCode == '') {
                alert("短信验证码不能为空");
                return false;
            }
            vm.popover.open = false;
        }
        if (!confirm('04' == auditState ? "确认发起付款？" : "确认取消文件？")) {
            return false;
        }
        $http.post(config.ctx + '/payment/check', $httpParamSerializerJQLike({
                auditState: auditState,
                batchNo: batchNo,
                verifyCode: verifyCode,
                smsVerifyCode: smsVerifyCode
            }))
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    alert("处理成功");
                    vm.pagination.list[0].auditState = auditState;
                } else {
                    alert(data.msg);
                }
                vm.queryBean.verifyCode = "";
                vm.queryBean.smsVerifyCode = "";
            });
    }

    function sendSmsVerifyCode() {
        var verifyCode = vm.queryBean.verifyCode;
        if (verifyCode == undefined || verifyCode == '') {
            alert("密码不能为空");
            return false;
        }
        $.getJSON(config.ctx + '/payment/smsVerifyCode',
            function (data) {
                if (data.success) {
                    alert("短信发送成功!");
                    var seconds = 60;
                    $('#smsWrap').hide();
                    $('#smsSecond').show();
                    var id = window.setInterval(function () {
                        $('#smsSecond').text(seconds + "秒后重发");
                        seconds--;
                        if (seconds == 0) {
                            $('#smsWrap').show();
                            $('#smsSecond').hide();
                            window.clearInterval(id);
                        }
                    }, 1000);
                } else {
                    alert("短信发送失败!");
                }
            })
    }

    function queryDetail() {
        var queryForm = document.getElementById('queryForm');
        vm.url = config.ctx + '/payment/queryPaymentDetail';
        //组织参数：查询条件 + 分页数据
        vm.postData = angular.copy(vm.queryBean);
        vm.postData.pageSize = vm.pagination.pageSize;
        vm.postData.pageNum = vm.pagination.pageNum;
        vm.postData.batchNo = vm.item.batchNo;
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
    }

    function downloadResultFile() {
        var batchNo = vm.item.batchNo;
        if (batchNo == null || batchNo == undefined || batchNo == '') {
            alert("批次号为空");
            return false;
        }
        $http.post(config.ctx + '/payment/checkDownloadResultFile', $httpParamSerializerJQLike({batchNo: vm.item.batchNo}))
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    location.href = config.ctx + '/payment/downloadResultFile?batchNo=' + vm.item.batchNo;
                } else {
                    alert(data.msg);
                }
            });
    }
}]);

angular.module('myApp').controller('uploadModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'FileUploader', '$log', function ($scope, $uibModalInstance, modalItem, $http,
                                                                        $httpParamSerializerJQLike, FileUploader, $log) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };
    vm = $scope.vm = modalItem;
    //创建对象，文件上传
    vm.fileUploader = new FileUploader(
        {
            //公用的文件上传路径
            url: config.ctx + '/system/fileUploader/upload',
            //跟随文件一起提交的数据
            formData: [
                {
                    busiType: 'DF',
                    remark: '代付管理'
                }
            ],
            filters: [{
                name: 'limitItem',
                fn: function (item) {
                    if (vm.fileUploader.queue.length >= 1) {
                        alert("请先取消原先文件,再选择新文件!");
                        return false;
                    } else {
                        return true;
                    }
                }
            }]
        }
    );

    vm.fileUploader.onSuccessItem = function (fileItem, response, status, headers) {
        //上传成功后，将原文件名、dfs文件名保存到  确认动作时使用
        fileItem.originFileName = fileItem.file.name;
        fileItem.dfsFullFilename = response.object.dfsFullFilename;
        fileItem.dfsGroupName = response.object.dfsGroupName;
    };

    vm.fileUploader.onCancelItem = function (fileItem, response, status, headers) {
    };

    vm.uploadBean = {};
    vm.downLoadTemplate = downLoadTemplate;
    vm.uploadFile = uploadFile;

    $scope.save = function () {

    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    function downLoadTemplate() {
        location.href = config.ctx + '/payment/downloadTemplate';
    }

    function uploadFile() {
        if (vm.fileUploader.queue.length == 1) {
            if (vm.fileUploader.queue[0].isSuccess == true) {
                var postData = {};
                postData.originFileName = vm.fileUploader.queue[0].originFileName;
                postData.dfsFullFilename = vm.fileUploader.queue[0].dfsFullFilename;
                postData.dfsGroupName = vm.fileUploader.queue[0].dfsGroupName;
                $http.post(config.ctx + '/payment/uploadFile', $httpParamSerializerJQLike(postData))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("提交成功");
                            $scope.cancel();
                        } else {
                            alert(data.msg);
                        }
                    });
            } else {
                alert("请先上传文件");
                return false;
            }
        } else {
            alert("只能选择一个文件");
            return false;
        }
    }
}]);


angular.module('myApp').controller('confirmUploadModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'FileUploader', '$log', function ($scope, $uibModalInstance, modalItem, $http,
                                                                               $httpParamSerializerJQLike, FileUploader, $log) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };
    vm = $scope.vm = modalItem;
    //创建对象，文件上传
    vm.fileUploader = new FileUploader(
        {
            //公用的文件上传路径
            url: config.ctx + '/system/fileUploader/upload',
            //跟随文件一起提交的数据
            formData: [
                {
                    busiType: 'DF',
                    remark: '代付管理'
                }
            ],
            filters: [{
                name: 'limitItem',
                fn: function (item) {
                    if (vm.fileUploader.queue.length >= 1) {
                        alert("请先取消原先文件,再选择新文件!");
                        return false;
                    } else {
                        return true;
                    }
                }
            }]
        }
    );

    vm.fileUploader.onSuccessItem = function (fileItem, response, status, headers) {
        //上传成功后，将原文件名、dfs文件名保存 确认动作时使用
        fileItem.originFileName = fileItem.file.name;
        fileItem.dfsFullFilename = response.object.dfsFullFilename;
        fileItem.dfsGroupName = response.object.dfsGroupName;
    };

    vm.fileUploader.onCancelItem = function (fileItem, response, status, headers) {
    };

    vm.uploadBean = {};
    vm.confirmUploadFile = confirmUploadFile;

    $scope.save = function () {

    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    function confirmUploadFile() {
        if (vm.fileUploader.queue.length == 1) {
            if (vm.fileUploader.queue[0].isSuccess == true) {
                var postData = {};
                postData.originFileName = vm.fileUploader.queue[0].originFileName;
                postData.dfsFullFilename = vm.fileUploader.queue[0].dfsFullFilename;
                postData.dfsGroupName = vm.fileUploader.queue[0].dfsGroupName;
                $http.post(config.ctx + '/payment/confirmUploadFile', $httpParamSerializerJQLike(postData))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("提交成功");
                            $scope.cancel();
                        } else {
                            alert(data.msg);
                        }
                    });
            } else {
                alert("请先上传文件");
                return false;
            }
        } else {
            alert("只能选择一个文件");
            return false;
        }
    }
}]);
