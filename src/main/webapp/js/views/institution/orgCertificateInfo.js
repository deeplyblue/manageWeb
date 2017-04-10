/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService','CacheService','myConstant','dateFilter',
        function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,CacheService,myConstant,dateFilter) {
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
        vm.updateItemEnableFlag = updateItemEnableFlag;
        vm.deleteItem = deleteItem;

        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached ={
            TRANS_CODE_ALL:{},
            COMANY_CODE:{}

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
                modalTitle: '新增机构证书',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    COMANY_CODE:CacheService.getCache('COMANY_CODE'),
                }
            }, function (item) {
                queryDetail();
            },'addOrgCertificateInfo','myModalNoFooter.html');
        }

        function updateItemEnableFlag(item,status) {
            if (!confirm("是否确认此操作?")) {
                return;
            }
            var postData = angular.copy(item);
            postData.effectData = dateFilter(postData.effectData,'yyyy-MM-dd');
            postData.expiryData=dateFilter(postData.expiryData,'yyyy-MM-dd');
            postData.lastUpdTime=dateFilter(postData.lastUpdTime,'yyyy-MM-dd');
            postData.createTime=dateFilter(postData.createTime,'yyyy-MM-dd');
            postData.operateStatus=status;
            if(postData.operateStatus=='02'){
                postData.status='0';
            }
            if(status=='04'){
                postData.status='1';
                postData.operateStatus='00';

            }
            if(postData.operateStatus=='05'){
                postData.status='2';
            }
            $http.post(config.ctx+"/institution/orgCertificate/update", $httpParamSerializerJQLike(postData))
                .then(function (response) {
                    if (response.data.success) {
                        alert("操作成功");
                        queryDetail();
                    } else {
                        alert(response.data.msg);
                    }
                }, function (response) {
                    alert("操作失败");
                    alert(response.status);
                })

        }

        function deleteItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            $http.post('delete', $httpParamSerializerJQLike({'id': CheckboxService.getChecked()[0]}))
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


angular.module('myApp').controller('addOrgCertificateInfo',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike','FileUploader' , '$log', '$timeout','dateFilter','myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                           $httpParamSerializerJQLike,FileUploader , $log, $timeout,dateFilter,myConstant) {
    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
    vm.constant = myConstant;
    vm.constant = myConstant;
    vm.fileItems = [
        {'field': 'dfsFullFilename'}

    ]


    //创建对象，文件上传
    vm.fileUploader = new FileUploader(

        {
            //公用的文件上传路径
            url: config.ctx + '/system/fileUploader/upload',
            //跟随文件一起提交的数据
            formData: [
                {
                    busiType: 'OC',
                    remark: '机构证书'
                }
            ],
            removeAfterUpload: false
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
        fileItem.originFileName = fileItem.file.name
        fileItem.dfsFullFilename = response.object.dfsFullFilename
        fileItem.dfsGroupName = response.object.dfsGroupName

    };

    $scope.save = function () {
        // vm.postData = angular.copy(vm.item);
        var postData = angular.copy(vm.item);
        postData.effectData = dateFilter(postData.effectData,'yyyy-MM-dd');
        postData.expiryData = dateFilter(postData.expiryData,'yyyy-MM-dd');
        vm.fileUploader.queue.forEach(function (it) {
            postData.localFilename=it.originFileName.substring(0,it.originFileName.lastIndexOf("."));
            postData.certificateType = it.originFileName.substring(it.originFileName.lastIndexOf(".")+1);
            if (it.isSuccess) {
                //此处只保存了dfs文件路径
                //@TODO 其他需要保存的字段，请自己添加(dfs三要素，均在上面方法中保留了值)
                postData[it['field']] = it.dfsFullFilename;
            }
        })
        if(postData.localFilename==null){
            alert("请上传机构证书！")
            return false;
        }
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



