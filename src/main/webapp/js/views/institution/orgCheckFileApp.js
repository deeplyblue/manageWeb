/**
 * Created by wuxg on 2016/7/5.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter','dateFilter','myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter,dateFilter,myConstant) {
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
            ALL_COMPANY_CODE:{},
            UPLOAD_STATUS:{},
            DOCUMENT_TYPE:{},
            ALL_COMPANY_CODE:{}
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


        //vm.updateChecked = updateChecked;
        //vm.addItem = addItem;
        //vm.updateItem = updateItem;
        //vm.deleteItem = deleteItem;
        vm.upload = upload;
        vm.downFile=downFile;
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
            vm.pagination.queryBean = vm.queryBean;
            var postData = vm.pagination;
            $http.post(vm.url, postData, config.jsonHeader)
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
            vm.queryBean = {
                busiType:'B1',
            };
            vm.pagination.queryBean={};
        }

        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }


        function upload() {
            OpenService({
                modalTitle: '上传文件',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    COMANY_CODE:vm.getCache('COMANY_CODE')
                }
            }, function (iteaddItemm) {

            }, 'uploadReserveModalInstanceCtrl', 'myModalNoSave.html');
        }

        function downFile(item){
            location.href = config.ctx + '/base/dfsFileInfo/download?dfsGroupname='+item['dfsGroupname']+'&dfsFullFilename='+item['dfsFullFilename']+'&localFilename='+item['localFilename'];
        }


    }]);


angular.module('myApp').controller('uploadReserveModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'FileUploader', '$log','myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                        $httpParamSerializerJQLike, FileUploader, $log,myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };
    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
    vm.file1 = {};
    //创建对象，文件上传
    vm.fileUploader = new FileUploader(
        {
            //公用的文件上传路径
            url: config.ctx + '/system/fileUploader/upload',
            //跟随文件一起提交的数据
            formData: [
                {
                    busiType: 'TP',
                    remark: '支付机构对账文件'
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
        //上传成功后，将原文件名、dfs文件名保存到file1  确认动作时使用
        vm.file1 = {
            localFilename:response.object.localFilename,
            dfsFullFilename: response.object.dfsFullFilename,
            dfsGroupName: response.object.dfsGroupName,
            id:response.object.id
        }
    };

    vm.fileUploader.onCancelItem = function (fileItem, response, status, headers) {
        vm.file1 = {};
    }

    vm.uploadBean = {};
    vm.uploadFile = uploadFile;

    function uploadFile() {
        if(vm.item.companyCode && vm.item.bankSettleDate){
            vm.file1.companyCode=vm.item.companyCode;
            vm.file1.bankSettleDate=vm.item.bankSettleDate;
        }else {
            alert("请先选择备付金银行");
            return false;
        }
        if(vm.file1.id){
            $http.post(config.ctx + '/orgCheckFile/add', vm.file1,config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert("提交成功");
                        $scope.cancel();
                    } else {
                        alert(data.msg);
                    }
                });
        }else{
            alert("请先上传文件");
            return false;
        }
    }

    $scope.save = function () {

    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

}]);
