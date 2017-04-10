/**
* Created by lupf on 2016/4/28.
*/

angular.module('myApp')

.controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
    'CacheService', 'limitToFilter', 'filterFilter', 'myConstant', 'growl', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
CacheService, limitToFilter, filterFilter, myConstant, growl) {
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
    TYPE_OPERATION: {},
    USER_STATUS: {},
    OPERATION_RESULT: {},
    MERCHANT_CODE: {},
    ENABLE_LIMITFLAG: {},
    IP_TYPE: {}
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
    vm.deleteItem=deleteItem;
    vm.addItem=addItem;

/*------------------以上配置通用---------------------*/


/*------------------以上方法名可选择性通用---------------------*/

/*CacheService.initCache(vm.cached, function (cacheKey, cacheObj) {
$log.debug(cacheKey, cacheObj);
vm.cached[cacheKey] = cacheObj;
});*/

/*vm.getCache = function (key) {
CacheService.getCache(key)
}*/

function queryDetail() {
vm.url = config.ctx + '/system/userIp/search';

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
};
    function deleteItem() {
        if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
            alert("必须勾选一条记录！");
            return;
        }
        $http.post('delete', $httpParamSerializerJQLike({'id': CheckboxService.getCheckedNew(vm.pagination.list)[0].id}))
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    vm.pagination.list.splice(vm.pagination.list.indexOf(CheckboxService.getCheckedNew(vm.pagination.list)[0]), 1);
                    alert("操作成功");
                } else {
                    alert("操作失败");
                }
            }, function (response) {
                alert(response.statusText);
                $log.debug("error");
            });
    }
    function addItem() {
        OpenService({
            modalTitle: '新增用户',
            modalBody: 'toAdd',
            url: 'add',
            item: {},
            cached: {
                IP_TYPE: CacheService.getCache('IP_TYPE'),
                ENABLE_LIMITFLAG: CacheService.getCache('ENABLE_LIMITFLAG')
            }
        }, function (item) {
            queryDetail();
        },'updateOrganizeInstanceCtrl','myModalNoFooter.html');
    }
}]);


angular.module('myApp').controller('updateOrganizeInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike','$log','dateFilter','myConstant', function ($scope, $uibModalInstance, modalItem, $http,
                                                                           $httpParamSerializerJQLike,$log,dateFilter,myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
    $scope.save = function () {
        vm.postData = angular.copy(vm.item);
        vm.postData.ipBeginTime = dateFilter(vm.postData.ipBeginTime,'yyyyMMdd');
        vm.postData.ipEndTime = dateFilter(vm.postData.ipEndTime,'yyyyMMdd');
        $log.debug(vm.postData);
        $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
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
