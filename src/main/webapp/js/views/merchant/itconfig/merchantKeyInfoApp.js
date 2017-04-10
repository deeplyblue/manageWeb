/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',[ '$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'dateFilter','myConstant',function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, dateFilter,myConstant) {
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
            ENCTYPE: {}
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


        vm.updateChecked = updateChecked;
        vm.addItem = addItem;
        vm.updateItem = updateItem;
        vm.deleteItem = deleteItem;
        vm.queryDataKey = queryDataKey;
        vm.queryRSAKey=queryRSAKey;

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
            var tempDate = new Date();
            tempDate.setFullYear(new Date().getFullYear()+1);
            OpenService({
                modalTitle: '增加商户密钥配置 ',
                modalBody: 'toAdd',
                url: 'add',
                item: {
                    dataKeyValidDate : tempDate,
                    rsaKeyValidDate : tempDate
                },
                cached: {
                    MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                    ENCTYPE: CacheService.getCache('ENCTYPE')
                }
            }, function () {
                queryDetail();
            },"privateMerchantKeyInfoCtrl",'myModalNoFooter.html');
        }
        

        function queryDataKey(id) {
            var results;
            $http.post(config.ctx + '/merchant/itCfg/key/searchDataKey', $httpParamSerializerJQLike({id:id}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        results = data.object;
                        OpenService({
                            modalTitle: '数据加密KEY查看',
                            modalBody: 'toDataKey',
                            url: '#',
                            item: results,
                            cached: vm.cached
                        }, function () {

                        }, '', 'myModalNoSave.html');
                    } else {
                        alert(data.msg);
                    }
                });
        }
        function queryRSAKey(id) {
            var results;
            $http.post(config.ctx + '/merchant/itCfg/key/searchRsaKey', $httpParamSerializerJQLike({id:id}))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        results = data.object;
                        OpenService({
                            modalTitle: 'RSA公钥查看',
                            modalBody: 'toRsaKey',
                            url: '#',
                            item: results,
                            cached: vm.cached
                        }, function () {

                        }, '', 'myModalNoSave.html');
                    } else {
                        alert(data.msg);
                    }
                });
        }
        

        function updateItem() {
            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }

            var temp = CheckboxService.getCheckedNew(vm.pagination.list)[0];
            OpenService({
                modalTitle: '修改商户密钥配置 ',
                modalBody: 'toUpdate',
                url: 'update',
                item: temp,
                cached: {
                    MERCHANT_CODE: vm.cached.MERCHANT_CODE,
                    ENCTYPE: CacheService.getCache('ENCTYPE')
                }
            },function(){
                queryDetail();
            },"privateMerchantKeyInfoCtrl",'myModalNoFooter.html')
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

angular.module('myApp').controller('privateMerchantKeyInfoCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
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
        {'field': 'rsaRemoteAddr'}

    ]
    

    //创建对象，文件上传
    vm.fileUploader = new FileUploader(

        {
            //公用的文件上传路径
            url: config.ctx + '/system/fileUploader/upload',
            //跟随文件一起提交的数据
            formData: [
                {
                    busiType: 'PK',
                    remark: '公钥文件'
                }
            ],
            filters :[{
                'field': 'rsaRemoteAddr',
                fn:function (item) {
                     var extend = item.name.substring(item.name.lastIndexOf(".")+1);
                        if(extend=="cer"){
                            return true
                        }else{
                            alert("请上传后缀名为cer的文件!");
                        }
                }
            }],
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
        postData.dataKeyValidDate = dateFilter(postData.dataKeyValidDate,'yyyy-MM-dd');
        postData.rsaKeyValidDate = dateFilter(postData.rsaKeyValidDate,'yyyy-MM-dd');
        vm.fileUploader.queue.forEach(function (it) {
            $log.debug(it)
            if (it.isSuccess) {
                //此处只保存了dfs文件路径
                //@TODO 其他需要保存的字段，请自己添加(dfs三要素，均在上面方法中保留了值)
                postData[it['field']] = it.dfsFullFilename;
            }
        })
        if(postData.dataKey=='*******'){
            postData.dataKey=null;
        }
        $log.debug(postData);
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