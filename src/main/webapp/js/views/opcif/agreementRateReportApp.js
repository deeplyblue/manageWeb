
angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService','myConstant','dateFilter','DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService,myConstant,dateFilter,DateCalculateService) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        /*vm.downCfg = {
            contentType : 'jqLike',
            date : {
                'startDate' : 'yyyyMMdd',
                'endDate' : 'yyyyMMdd'
            }
        }*/
        //变量初始化
        //分页数据
        vm.pagination = {
            pageSize: 10,
            pageNum: 1
        };
        //查询条件
        vm.queryBean = {
        };
        vm.queryBean.endDate = DateCalculateService.getToday();
        vm.queryBean.startDate = DateCalculateService.getYesterday();

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;

        /*------------------以上配置通用---------------------*/


        vm.updateChecked = updateChecked;
        vm.queryCustomerDetail=queryCustomerDetail;
        vm.freezeOperator = freezeOperator;
        vm.unFreezeOperator =unFreezeOperator;
        vm.defrosting=defrosting;
        vm.checkEndDay=checkEndDay;
        vm.checkStartDay=checkStartDay;

        /*------------------以上方法名可选择性通用---------------------*/

        vm.getCache = function (key) {
            return CacheService.getCache(key);
        };

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = queryForm.getAttribute('action');
            
            //组织参数：查询条件 + 分页数据
            vm.pagination.queryBean = vm.queryBean;
            vm.pagination.list=null;
            var postData = vm.pagination;
            $http.post(vm.url, postData, config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination = data.object;

                    } else {
                        alert(data.msg);
                    }
                });

        };

        
        function checkEndDay(startDate,endDate) {
            var startD = new Date(startDate);
            var endD   = new Date(endDate);
            var days = parseInt((endD.getTime()-startD.getTime()) / (1000 * 60 * 60 * 24));
            if(days > 30){
                alert("日期范围应在一个月之内");
                vm.queryBean.endDate = DateCalculateService.getToday();
                return false;
            }
            if(days < 0){
                alert("日期范围应在一个月之内");
                vm.queryBean.endDate = DateCalculateService.getToday();
                return false;
            }
        }

        function checkStartDay(startDate,endDate) {
            var startD = new Date(startDate);
            var endD   = new Date(endDate);

            var days = parseInt((endD.getTime()-startD.getTime()) / (1000 * 60 * 60 * 24));
            if(days > 30){
                alert("日期范围应在一个月之内");
                vm.queryBean.startDate = DateCalculateService.getYesterday();
                return false;
            }
            if(days < 0){
                alert("日期范围应在一个月之内");
                vm.queryBean.startDate = DateCalculateService.getYesterday();
                return false;
            }
        }

        function resetForm() {
            vm.queryBean = {
                endDate : DateCalculateService.getToday(),
                startDate :DateCalculateService.getYesterday(),
            };
        }

        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached ={
        };
        CacheService.initCache(vm.cached);


        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }

        function queryCustomerDetail(bean) {
            var item=bean;
            OpenService({
                modalTitle: '查看明细',
                modalBody: 'toDetail',
                url: '#',
                item: item,
                cached: {
                    ACCOUNT_TYPE:vm.cached.ACCOUNT_TYPE,
                    TGB_CUSTOMER_STATUS:vm.cached.TGB_CUSTOMER_STATUS,
                    OPCIF_CERTIFICATE_TYPE:vm.cached.OPCIF_CERTIFICATE_TYPE,
                    REALNAME_LV:vm.cached.REALNAME_LV,
                    CUSTOMER_CRADE:vm.cached.CUSTOMER_CRADE
                }
            }, function (item) {
            }, '', 'myModalNoSave.html');
        }

        function freezeOperator(item){
            if (!confirm("是否确认此操作?")) {
                return;
            }
            var postData = angular.copy(item);
            postData.authTime=null;
            postData.birthDatetime=null;
            postData.certStartDate=null;
            postData.certExpiryDate=null;
            $http.post(config.ctx+'/cif/customerInfo/freeze',$httpParamSerializerJQLike(postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert("操作成功");
                       queryDetail();
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }
        function defrosting(item){
            if (!confirm("是否确认此操作?")) {
                return;
            }
            var postData = angular.copy(item);
            postData.authTime=null;
            postData.birthDatetime=null;
            postData.certStartDate=null;
            postData.certExpiryDate=null;
            $http.post(config.ctx+'/cif/customerInfo/defrosting',$httpParamSerializerJQLike(postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert("操作成功");
                        queryDetail();
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }

        function unFreezeOperator(item){
            if (!confirm("是否确认此操作?")) {
                return;
            }
            var postData = angular.copy(item);
            postData.authTime=null;
            postData.birthDatetime=null;
            postData.certStartDate=null;
            postData.certExpiryDate=null;
            $http.post(config.ctx+'/cif/customerInfo/unFreeze',$httpParamSerializerJQLike(postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert("操作成功");
                        vm.queryDetail();
                    } else {
                        alert(data.msg);
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }
        
    }]);
