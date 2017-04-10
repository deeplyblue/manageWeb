/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService', 'CacheService', 'dateFilter', 'myConstant',
        function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService, CacheService, dateFilter, myConstant) {
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

        vm.downEnclosure = downEnclosure;
        vm.updateItemEnableFlag = updateItemEnableFlag;
        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached = {
            MERCHANT_CODE: {},
            CONN_CHANNEL: {},
            COMANY_CODE: {},
            AUDIT_STATUS: {}

        };
        CacheService.initCache(vm.cached);

        vm.getCache = function (key) {
            return CacheService.getCache(key);
        };

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            // vm.postData = vm.queryBean;
            var postData = angular.copy(vm.queryBean);
            postData.pageSize = vm.pagination.pageSize;
            postData.pageNum = vm.pagination.pageNum;
            postData.contBgnDate = dateFilter(postData.contBgnDate, 'yyyyMMdd');
            postData.contEndDate = dateFilter(postData.contEndDate, 'yyyyMMdd');
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
                modalTitle: '新增合同信息',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    MERCHANT_CODE: CacheService.getCache("MERCHANT_CODE"),
                    COMANY_CODE: CacheService.getCache('COMANY_CODE')
                }
            }, function (item) {
                queryDetail();
            }, 'AddContractModalInstanceCtrl', 'myModalNoFooter.html');
        }

        function updateItem() {


            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var temp = vm.pagination.list.filter(function (item, index, array) {
                return item.id == CheckboxService.getChecked()[0];
                return true;
            })[0];
            //加载合同文件下载信息
            if (temp.dfsContAttach != null && temp.dfsContAttach != "") {
                var url = config.ctx + "/merchant/contaract/getDfs?id=" + temp.dfsContAttach;
                $http.get(url).success(function (response) {
                    temp.dfsInfo = response.object;
                    // alert(bean.dfsFullFilename);
                    // $log.info($scope.bean.object);
                    // $log.info(response.object);
                });

            }
            //加载开户行许可证文件下载信息
            if (temp.dfsOpenBankCert != null && temp.dfsOpenBankCert != "") {
                var url = config.ctx + "/merchant/contaract/getDfs?id=" + temp.dfsOpenBankCert;
                $http.get(url).success(function (response) {
                    temp.dfsInfo1 = response.object;
                });
            }
            //加载一般纳税人证书下载信息
            if (temp.dfsRatePayerCert != null && temp.dfsRatePayerCert != "") {
                var url = config.ctx + "/merchant/contaract/getDfs?id=" + temp.dfsRatePayerCert;
                $http.get(url).success(function (response) {
                    temp.dfsInfo2 = response.object;
                });
            }
            //加载税务登记证下载信息
            if (temp.dfsTaxRegisterCert != null && temp.dfsTaxRegisterCert != "") {
                var url = config.ctx + "/merchant/contaract/getDfs?id=" + temp.dfsTaxRegisterCert;
                $http.get(url).success(function (response) {
                    temp.dfsInfo3 = response.object;
                });
            }
            //加载营业执照下载信息
            if (temp.dfsBizLicenseCert != null && temp.dfsBizLicenseCert != "") {
                var url = config.ctx + "/merchant/contaract/getDfs?id=" + temp.dfsBizLicenseCert;
                $http.get(url).success(function (response) {
                    temp.dfsInfo4 = response.object;
                });
            }
            //加载组织机构代码证下载信息
            if (temp.dfsOrganizationCodeCert != null && temp.dfsOrganizationCodeCert != "") {
                var url = config.ctx + "/merchant/contaract/getDfs?id=" + temp.dfsOrganizationCodeCert;
                $http.get(url).success(function (response) {
                    temp.dfsInfo5 = response.object;
                });
            }
            //加载银行基本信息表下载信息
            if (temp.dfsBankFile != null && temp.dfsBankFile != "") {
                var url = config.ctx + "/merchant/contaract/getDfs?id=" + temp.dfsBankFile;
                $http.get(url).success(function (response) {
                    temp.dfsInfo6 = response.object;
                });
            }

            OpenService({
                modalTitle: '修改合同信息',
                modalBody: 'toUpdate',
                url: 'update',
                item: temp,
            }, function (item) {
                $log.debug(item);
                queryDetail();
            }, 'updateContractModalInstanceCtrl','myModalNoFooter.html');

        }

        function downEnclosure(item) {
            location.href = config.ctx + '/merchant/merchantContaract/downEnclosure?id=' + item.id;
        }

        function updateItemEnableFlag(item, status) {
            var postData = angular.copy(item);
            postData.auditStatus = status;
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post(config.ctx + '/merchant/contaract/updateItemEnableFlag', postData, config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        item.auditStatus = status;
                        alert("操作成功");
                    } else {
                        alert("操作失败");
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function deleteItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            if (!confirm("是否确认此操作?")) {
                return;
            }
            $http.post('delete', $httpParamSerializerJQLike({'id': CheckboxService.getChecked()[0]}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        var item = vm.pagination.list.filter(function (item, index, array) {
                            return item.id == CheckboxService.getChecked()[0];
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
<!-- 日期转换-->
angular.module('myApp').controller('AddContractModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'FileUploader', 'dateFilter', '$log', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                             $httpParamSerializerJQLike, FileUploader, dateFilter, $log, myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
    vm.fileItems = [
        {'field': 'dfsContAttach'},
        {'field': 'dfsOpenBankCert'},
        {'field': 'dfsRatePayerCert'},
        {'field': 'dfsTaxRegisterCert'},
        {'field': 'dfsBizLicenseCert'},
        {'field': 'dfsOrganizationCodeCert'},
        {'field': 'dfsBankFile'}
    ]
    //创建对象，文件上传
    vm.fileUploader = new FileUploader(
        {
            //公用的文件上传路径
            url: config.ctx + '/system/fileUploader/upload',
            //跟随文件一起提交的数据
            formData: [
                {
                    busiType: 'HT',
                    remark: '支付机构合同'
                }
            ],
            removeAfterUpload: false,
            filters: [{
                name: 'fileTypeFilter',
                // A user-defined filter
                fn: function (item) {
                    $log.debug(item);
                    var fileType = item.name.substring(item.name.lastIndexOf('.') + 1);
                    $log.debug(fileType);
                    if (fileType && (fileType.indexOf('pdf') != -1 || fileType.indexOf('rar') != -1 || fileType.indexOf('zip') != -1 || fileType.indexOf('jpg') != -1)) {
                        $log.debug("文件类型符合类型：jpg/pdf/rar/zip");
                    } else {
                        alert("文件类型不符：不是jpg/pdf/rar/zip类型！");
                        return false;
                    }
                    var fileSize = item.size;
                    if (fileSize && fileSize <= 1024 * 1024 * 10) {
                        $log.debug("文件大小符合：小于10M");
                    } else {
                        alert("文件大小不符：不允许超过10M的文件！")
                        return false;
                    }
                    return true;
                }
            }]
            
        }
    );

    vm.fileUploader.onAfterAddingFile = function (fileItem, response) {
        vm.fileUploader.queue.forEach(function (it) {
            if (it['field'] == fileItem['field'] && it != fileItem) {
                vm.fileUploader.removeFromQueue(it);
            }
        })
    }

    vm.fileUploader.onSuccessItem = function (fileItem, response, status, headers) {
        //上传成功后，将原文件名、dfs文件名保存到file  确认动作时使用
        if (response.object) {
            fileItem.originFileName = fileItem.file.name
            fileItem.dfsFullFilename = response.object.dfsFullFilename
            fileItem.dfsGroupName = response.object.dfsGroupName
        } else {
            alert(response.msg);
            vm.fileUploader.removeFromQueue(fileItem);
        }
    };

    $scope.save = function () {
        var postData = angular.copy(vm.item);
        postData.contEndDate = dateFilter(postData.contEndDate, 'yyyyMMdd');
        postData.contBgnDate = dateFilter(postData.contBgnDate, 'yyyyMMdd');
        postData.contSignedDate = dateFilter(postData.contSignedDate, 'yyyyMMdd');
        postData.payerCertEndDate = dateFilter(postData.payerCertEndDate, 'yyyyMMdd');
        postData.taxRegisterEndDate = dateFilter(postData.taxRegisterEndDate, 'yyyyMMdd');
        postData.bizLicenseEndDate = dateFilter(postData.bizLicenseEndDate, 'yyyyMMdd');


        vm.fileUploader.queue.forEach(function (it) {
            $log.debug(it)
            if (it.isSuccess) {
                //此处只保存了dfs文件路径
                //@TODO 其他需要保存的字段，请自己添加(dfs三要素，均在上面方法中保留了值)
                postData[it['field']] = it.dfsFullFilename;
            }
        })

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

angular.module('myApp').controller('updateContractModalInstanceCtrl', ['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike','FileUploader', '$log', 'dateFilter', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                $httpParamSerializerJQLike, FileUploader, $log, dateFilter, myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;

    vm.fileItems = [
        {'field': 'dfsContAttach'},
        {'field': 'dfsOpenBankCert'},
        {'field': 'dfsRatePayerCert'},
        {'field': 'dfsTaxRegisterCert'},
        {'field': 'dfsBizLicenseCert'},
        {'field': 'dfsOrganizationCodeCert'},
        {'field': 'dfsBankFile'}
    ]
    //创建对象，文件上传
    vm.fileUploader = new FileUploader(
        {
            //公用的文件上传路径
            url: config.ctx + '/system/fileUploader/upload',
            //跟随文件一起提交的数据
            formData: [
                {
                    busiType: 'HT',
                    remark: '支付机构合同'
                }
            ],
            removeAfterUpload: false,
            filters: [{
                name: 'fileTypeFilter',
                // A user-defined filter
                fn: function (item) {
                    $log.debug(item);
                    var fileType = item.name.substring(item.name.lastIndexOf('.') + 1);
                    $log.debug(fileType);
                    if (fileType && (fileType.indexOf('pdf') != -1 || fileType.indexOf('rar') != -1 || fileType.indexOf('zip') != -1 || fileType.indexOf('jpg') != -1)) {
                        $log.debug("文件类型符合类型：jpg/pdf/rar/zip");
                    } else {
                        alert("文件类型不符：不是jpg/pdf/rar/zip类型！");
                        return false;
                    }
                    var fileSize = item.size;
                    if (fileSize && fileSize <= 1024 * 1024 * 10) {
                        $log.debug("文件大小符合：小于10M");
                    } else {
                        alert("文件大小不符：不允许超过10M的文件！")
                        return false;
                    }
                    return true;
                }
            }]
        }
    );

    vm.fileUploader.onAfterAddingFile = function (fileItem, response) {
        vm.fileUploader.queue.forEach(function (it) {
            if (it['field'] == fileItem['field'] && it != fileItem) {
                vm.fileUploader.removeFromQueue(it);
            }
        })
    }

    vm.fileUploader.onSuccessItem = function (fileItem, response, status, headers) {
        //上传成功后，将原文件名、dfs文件名保存到file  确认动作时使用
        if (response.object) {
            fileItem.originFileName = fileItem.file.name
            fileItem.dfsFullFilename = response.object.dfsFullFilename
            fileItem.dfsGroupName = response.object.dfsGroupName
        } else {
            alert(response.msg);
            vm.fileUploader.removeFromQueue(fileItem);
        }

    };

    $scope.save = function () {
        var postData = angular.copy(vm.item);
        // vm.postData.merchantOnlineDate = dateFilter(vm.postData.merchantOnlineDate,'yyyyMMdd');
        postData.createTime = null;
        postData.lastUpdTime = null;
        postData.contEndDate = dateFilter(postData.contEndDate, 'yyyyMMdd');
        postData.contBgnDate = dateFilter(postData.contBgnDate, 'yyyyMMdd');
        postData.contSignedDate = dateFilter(postData.contSignedDate, 'yyyyMMdd');
        postData.payerCertEndDate = dateFilter(postData.payerCertEndDate, 'yyyyMMdd');
        postData.taxRegisterEndDate = dateFilter(postData.taxRegisterEndDate, 'yyyyMMdd');
        postData.bizLicenseEndDate = dateFilter(postData.bizLicenseEndDate, 'yyyyMMdd');

        $log.debug(vm.postData);

        vm.fileUploader.queue.forEach(function (it) {
            $log.debug(it)
            if (it.isSuccess) {
                //此处只保存了dfs文件路径
                //@TODO 其他需要保存的字段，请自己添加(dfs三要素，均在上面方法中保留了值)
                postData[it['field']] = it.dfsFullFilename;
            }
        })

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