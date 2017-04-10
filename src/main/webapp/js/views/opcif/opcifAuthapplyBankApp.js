/**
 * Created by zhangxinhai on 2016/8/25.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'dateFilter','myConstant','DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, dateFilter,myConstant,DateCalculateService) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        //变量初始化
        //分页数据
        vm.pagination = {
            pageSize: 10,
            pageNum: 1
        };
        vm.sumObject = {};
        //查询条件
        vm.queryBean = {};


        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            OPCIF_BANK_CARD_TYPE:{},
            OPCIF_AGREEMENT_TYPE:{},
            OPCIF_CERTIFICATE_TYPE:{},
            OPCIF_OPERATOR_ROLE:{},
            OPCIF_AUTH_STATUS:{},
            OPCIF_APPLY_CHANNEL:{},
            COMANY_CODE:{}
        };

        CacheService.initCache(vm.cached);

        /*将前面缓存的数据格式化
         [{key:value},{key:value}]*/
        vm.getCache = function (key) {
            return CacheService.getCache(key);
        };

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;
            vm.checkStartDay=checkStartDay;
            vm.checkEndDay=checkEndDay;

        /*------------------以上配置通用---------------------*/


        /*------------------以上方法名可选择性通用---------------------*/


        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            var postData = {};
            postData.queryBean = vm.queryBean;
            postData.pageSize = vm.pagination.pageSize;
            postData.pageNum = vm.pagination.pageNum;
            $http.post(vm.url,postData,config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination = data.object;
                        vm.sumObject = data.sumObject;
                    } else {
                        alert(data.msg);
                    }
                });

        };

            function checkStartDay(startDate,endDate) {
                var startD = new Date(startDate);
                var endD   = new Date(endDate);
                if(startD > endD){
                    alert("日期选择错误！请重新选择!");
                    vm.queryBean.startDate = null;
                    vm.queryBean.endDate = DateCalculateService.getToday();
                    return false;
                }
            }

            function checkEndDay(startDate,endDate) {
                var startD = new Date(startDate);
                var endD   = new Date(endDate);
                if(endD < startD){
                    alert("日期选择错误！请重新选择!");
                    vm.queryBean.startDate = null;
                    vm.queryBean.endDate = DateCalculateService.getToday();
                    return false;
                }
            }

        function resetForm() {
            vm.queryBean = {};
        }
    }]);


