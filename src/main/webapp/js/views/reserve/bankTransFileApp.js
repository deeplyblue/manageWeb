/**
 * Created by lupf on 2016/4/28.
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
        //vm.queryBean = {};

        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            RESERVE_LIST:{},
            RESERVE_INFO:{}
        };

        //(function init() {
        //    vm.queryBean = {};
        //    $http.post(config.ctx + '/reserve/info/init/reserve',{},config.jsonHeader)
        //    .then(function (response) {
        //            if (response.data.success){
        //                vm.cached.RESERVE_INFO=response.data.object;
        //                format();
        //            }else {
        //                $log.error('获取数据%s失败',response.data.msg);
        //            }
        //        }, function (response) {
        //            $log.error('获取数据%s失败', cacheKey);
        //        })
        //}());

            (function(){
                $http.post(config.ctx + "/reserve/bank/info/init/bank",{}, config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            vm.cached.BANK_INFO = data.object;
                            var arr = [];
                            vm.cached.RESERVE_LIST = [];
                            for (var index in data.object){
                                var item = data.object[index];
                                var bankInfo ={'accountNo':item.accountNo,'desc':item.openBankName};
                                arr.push(bankInfo);
                                vm.cached.RESERVE_LIST.push({'accountNo':item.accountNo,'bankCode':item.remarkDesc});
                            }
                            vm.cached.BANK_INFO_LIST = arr;
                        } else {
                            alert(data.msg);
                        }
                    });
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


        //vm.updateChecked = updateChecked;
        //vm.addItem = addItem;
        //vm.updateItem = updateItem;
        //vm.deleteItem = deleteItem;
        vm.upload = upload;

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
            postData.pageNum = vm.pagination.pageNum;
            postData.pageSize = vm.pagination.pageSize;
            $http.post(vm.url, postData,config.jsonHeader)
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
                modalTitle: '新增备付金信息',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                    ALL_CITY: vm.cached.ALL_CITY
                }
            }, function (item) {
                vm.pagination.list.push(item);
            });
        }

        function upload() {
            OpenService({
                modalTitle: '上传文件',
                modalBody: 'toUpload',
                url: 'upload',
                item: {},
                cached: {
                    RESERVE_LIST:vm.cached.RESERVE_LIST,
                    BANK_INFO:vm.cached.BANK_INFO
                }
            }, function (iteaddItemm) {

            }, 'uploadReserveModalInstanceCtrl', 'myModalNoSave.html');
        }

        function updateItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }

            var temp = vm.pagination.list.filter(function (item, index, array) {
                if (item.id == CheckboxService.getChecked()[0]) {
                    return true;
                }
            })[0];
            OpenService({
                modalTitle: '修改备付金信息',
                modalBody: 'toUpdate',
                url: 'update',
                item:temp,
                cached: {
                    MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                    ALL_CITY: vm.cached.ALL_CITY
                }
            },function(item){
              $log.debug(item);
            });
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


angular.module('myApp').controller('uploadReserveModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'FileUploader', '$log', function ($scope, $uibModalInstance, modalItem, $http,
                                                                        $httpParamSerializerJQLike, FileUploader, $log) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };
    vm = $scope.vm = modalItem;
    vm.file1 = {};
    //创建对象，文件上传
    vm.fileUploader = new FileUploader(
        {
            //公用的文件上传路径
            url: config.ctx + '/system/fileUploader/upload',
            //跟随文件一起提交的数据
            formData: [
                {
                    busiType: 'BF',
                    remark: '备付金文件上传'
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
            originFileName: fileItem.file.name,
            dfsFilePath: response.object.dfsFullFilename,
            groupName: response.object.dfsGroupName,
            localFilePath: response.object.localFilename
        }
    };

    vm.fileUploader.onCancelItem = function (fileItem, response, status, headers) {
        vm.file1 = {};
    }

    vm.uploadBean = {};
    vm.uploadFile = uploadFile;

    $scope.save = function () {

    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    function uploadFile() {
        if(vm.item.accountNo){
            var reserveInfo = vm.cached.BANK_INFO[vm.item.accountNo];
            vm.file1.accountNo = reserveInfo['accountNo'];
            vm.file1.bankCode = reserveInfo['bankCode'];
            vm.file1.accountName = reserveInfo['accountName'];
        }else {
            alert("请先选择备付金银行");
            return false;
        }
        if(vm.file1.originFileName){
            $http.post(config.ctx + '/bank/trans/file/upload', $httpParamSerializerJQLike(vm.file1))
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
}]);
