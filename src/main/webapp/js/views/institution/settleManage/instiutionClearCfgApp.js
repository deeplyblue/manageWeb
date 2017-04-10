/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService','CacheService','myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,CacheService,myConstant) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        //变量初始化
        //分页数据
        vm.pagination = {
            pageSize: 10,
            pageNum: 1
        };
        //查询条 件
        vm.queryBean = {};

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;

        /*------------------以上配置通用---------------------*/


        vm.updateChecked = updateChecked;
        vm.addItem = addItem;
        vm.updateItem = updateItem;
        vm.deleteItem = deleteItem;
        vm.updateItemEnableFlag=updateItemEnableFlag;

        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached ={
            COMANY_CODE:{},
            CLR_CYCLE:{},
            REPORT_TYPE:{},
            CLR_TYPE:{},
            FEE_CLEAR_TYPE:{},
            ENABLE_FLAG:{},
            CLR_RANGE:{},
            CLEAR_VALUE_POINT:{},
            CLEAR_VALUE_POINT_WEEK:{}

        };
        CacheService.initCache(vm.cached);

        vm.getCache = function (key) {
            return CacheService.getCache(key);
        };

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            var postData = vm.queryBean;
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
            vm.queryBean.companyType='02';
        }

        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }


        function addItem() {
            OpenService({
                modalTitle: '新增机构结算信息',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {
                    COMANY_CODE: vm.getCache('COMANY_CODE'),
                    CLR_CYCLE: vm.cached.CLR_CYCLE,
                    REPORT_TYPE: vm.cached.REPORT_TYPE,
                    CLR_TYPE: vm.cached.CLR_TYPE,
                    FEE_CLEAR_TYPE:vm.cached.FEE_CLEAR_TYPE,
                    CLR_RANGE: vm.cached.CLR_RANGE,
                    CLEAR_VALUE_POINT: vm.cached.CLEAR_VALUE_POINT,
                    CLEAR_VALUE_POINT_WEEK: vm.cached.CLEAR_VALUE_POINT_WEEK
                }
            }, function (item) {
            },'addInstiutionClearCfgApp','myModalNoFooter.html');
        }

        function updateItemEnableFlag(item,auditStatus,enableFlag){
            var postData = {id:item.id};
            if(auditStatus){
                postData.auditStatus=auditStatus;
            }
            if (enableFlag){
                postData.enableFlag = enableFlag;
            }
            $http.post(config.ctx+'/merchant/clearCfg/updateItemEnableFlag',postData,config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        if(auditStatus){
                            item.auditStatus=auditStatus;
                        }
                        if (enableFlag){
                            item.enableFlag = enableFlag;
                        }
                        alert("操作成功");
                        vm.queryDetail();
                    } else {
                        alert("操作失败");
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function updateItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var temp=vm.pagination.list.filter(function (item, index, array) {
                return item.id == CheckboxService.getChecked()[0];
            })[0];
            temp.createTime=null;
            temp.lastUpdTime=null;
            temp.auditDate=null;

            OpenService({
                modalTitle: '修改机构结算信息',
                modalBody: 'toUpdate',
                url: 'update',
                item:temp,
                cached: {
                    COMANY_CODE: vm.getCache('COMANY_CODE'),
                    CLR_CYCLE: vm.cached.CLR_CYCLE,
                    REPORT_TYPE: vm.cached.REPORT_TYPE,
                    CLR_TYPE: vm.cached.CLR_TYPE,
                    FEE_CLEAR_TYPE:vm.cached.FEE_CLEAR_TYPE,
                    CLR_RANGE: vm.cached.CLR_RANGE,
                    CLEAR_VALUE_POINT: vm.cached.CLEAR_VALUE_POINT,
                    CLEAR_VALUE_POINT_WEEK: vm.cached.CLEAR_VALUE_POINT_WEEK
                }
            }, function (response) {
            },'updateInstiutionClearCfgApp','myModalNoFooter.html');

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

angular.module('myApp').controller('addInstiutionClearCfgApp', function ($scope, $uibModalInstance, modalItem, $http,
                                                                       $httpParamSerializerJQLike, $log, myConstant) {
    console.log("test");
    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;


    $scope.save = function () {
        var postData = vm.item;
        $log.debug("postData---------", postData, "--------postData")
        $http.post(vm.url, postData, config.jsonHeader)
            .then(function (response) {
                if (response.data.success) {
                    alert("操作成功");
                    $uibModalInstance.close(vm.item);
                } else {
                    if(response.data.msg != ""){
                        alert(response.data.msg);
                    }else{
                        alert("新增失败");
                    }
                }
            }, function (response) {
                alert("操作失败");
                alert(response.status);
            })
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

angular.module('myApp').controller('updateInstiutionClearCfgApp', function ($scope, $uibModalInstance, modalItem, $http,
                                                                          $httpParamSerializerJQLike, $log, myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    };
    vm = $scope.vm = modalItem;
    vm.constant = myConstant;

    $scope.save = function () {
        var postData = vm.item;
        delete postData.clearKey;
        delete postData.$$hashKey;
        $log.debug("postDate:", postData);
        $http.post(vm.url, postData, config.jsonHeader)
            .then(function (response) {
                if (response.data.success) {
                    alert("操作成功");
                    $uibModalInstance.close(vm.item);
                } else {
                    if(response.data.msg != ""){
                        alert(response.data.msg);
                    }else{
                        alert("新增失败");
                    }
                }
            }, function (response) {
                alert("操作失败");
                alert(response.status);
            })
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
