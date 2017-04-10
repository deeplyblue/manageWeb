/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService','CacheService','dateFilter'
        ,'myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,CacheService,dateFilter
                                              ,myConstant) {
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


        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached = {
            TRANS_CODE_ALL:{}

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
                modalTitle: '新增岗位职责',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached:{
                    TRANS_CODE_ALL:CacheService.getCache('TRANS_CODE_ALL'),
                }
            }, function (item) {
                queryDetail();
            },'BasicDataModalInstanceCtrl','myModalNoFooter.html');
        }


        function updateItem() {

            if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                alert("必须勾选一条记录！");
                return;
            }

            var temp = CheckboxService.getCheckedNew(vm.pagination.list)[0];
            temp.createTime=dateFilter(temp.createTime,'yyyyMMdd');
            temp.lastUpdTime=dateFilter(temp.lastUpdTime,'yyyyMMdd');
            OpenService({
                modalTitle: '修改岗位职责',
                modalBody: 'toUpdate',
                url: 'update',
                item: temp,
                cached:{
                    TRANS_CODE_ALL:CacheService.getCache('TRANS_CODE_ALL'),
                }
            },function(){
                queryDetail();
            },'updateJobResponsibility','myModalNoFooter.html');

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
angular.module('myApp').controller('updateJobResponsibility',['$scope', '$uibModalInstance', 'modalItem', '$http',
    'myConstant', '$httpParamSerializerJQLike', '$log', 'dateFilter', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                myConstant, $httpParamSerializerJQLike, $log, dateFilter) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {}
        }

        vm = $scope.vm = modalItem;
        vm.constant = myConstant;
        vm.basicData =new Array();

        var jobRes =new Array();
        vm.url = config.ctx + "/system/jobResponsibility/queryByName";

        //组织参数
        $http.post(vm.url, $httpParamSerializerJQLike(vm.item))
            .then(function (response) {
                var date = response.data;
                jobRes=date;
                for(var i=0;i<jobRes.length;i++){
                    vm.basicData[i]={"step":jobRes[i].step,"stepContent":jobRes[i].stepContent};
                }
            });


        vm.addDataDictInfo= function addDataDictInfo(obj){
            vm.basicData.push({"step":vm.basicData.length+1,"stepContent":""});

        }


        vm.deleteDataDictInfo=function deleteDataDictInfo(index){
            if(index==0){
                alert("第一条信息不能删除!");
            }else{
                vm.basicData.remove(index);
            }
        }
        Array.prototype.remove=function(dx)
        {
            if(isNaN(dx)||dx>this.length){return false;}
            for(var i=0,n=0;i<this.length;i++)
            {
                if(this[i]!=this[dx])
                {
                    this[n++]=this[i]
                }
            }
            this.length-=1
        }

        $scope.save = function () {

            for(var i=0;i<vm.basicData.length;i++){
                vm.basicData[0].jobName=vm.item.jobName;
                vm.basicData[0].priority=vm.item.priority;

            }
            vm.url = config.ctx + "/system/jobResponsibility/update";
            var header = {
                headers: {
                    'Content-Type': 'application/json'
                }
            };

            $http.post(vm.url, vm.basicData, header)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        $scope.cancel();
                    } else {
                        alert(data.msg);
                        $scope.cancel();
                    }
                });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }]);
angular.module('myApp').controller('BasicDataModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    'myConstant', '$httpParamSerializerJQLike', '$log', 'dateFilter', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                      myConstant, $httpParamSerializerJQLike, $log, dateFilter) {
    
    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }


    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
    vm.basicData =new Array();
    vm.basicData[0]={"step":vm.basicData.length+1,"stepContent":""};

   vm.addDataDictInfo= function addDataDictInfo(obj){
       vm.basicData.push({"step":vm.basicData.length+1,"stepContent":""});

    }

    vm.deleteDataDictInfo=function deleteDataDictInfo(index){
        if(index==0){
            alert("第一条信息不能删除!");
        }else{
            vm.basicData.remove(index);
        }
    }
    Array.prototype.remove=function(dx)
    {
        if(isNaN(dx)||dx>this.length){return false;}
        for(var i=0,n=0;i<this.length;i++)
        {
            if(this[i]!=this[dx])
            {
                this[n++]=this[i]
            }
        }
        this.length-=1
    }
    
    $scope.save = function () {
        
        for(var i=0;i<vm.basicData.length;i++){
            vm.basicData[0].jobName=vm.item.jobName;
            vm.basicData[0].priority=vm.item.priority;

        }
        vm.url = config.ctx + "/system/jobResponsibility/add";
        var header = {
            headers: {
                'Content-Type': 'application/json'
            }
        };

        $http.post(vm.url, vm.basicData, header)
            .then(function (response) {
                var data = response.data;
                if (data.success) {
                    $scope.cancel();
                } else {
                    alert(data.msg);
                    $scope.cancel();
                }
            });
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);