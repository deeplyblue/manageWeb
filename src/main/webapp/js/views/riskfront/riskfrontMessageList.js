
angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService','myConstant','dateFilter','DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService,myConstant,dateFilter,DateCalculateService) {
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
        vm.queryBean = {
        };

        vm.queryBean.startDate = DateCalculateService.getToday();
        vm.queryBean.endDate = DateCalculateService.getToday();

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;

        /*------------------以上配置通用---------------------*/


        vm.updateChecked = updateChecked;
        vm.queryCustomerDetail=queryCustomerDetail;
        vm.toMessage=toMessage;
        vm.downEnclosure = downEnclosure;
        vm.queryMessageLog=queryMessageLog;


        /*------------------以上方法名可选择性通用---------------------*/
        //缓存数据初始化(需要缓存的key请自定义)
        vm.cached ={
            RISKFRONT_STATUS:{},
            RISKFRONT_DEAL_STATUS:{},
            RISKFRONT_TX_CODE:{},
            RISKFRONT_TYPE:{},
            RISKFRONT_DATA_TYPE:{},
            RISKFRONT_SUBJECT_TYPE:{},
            RISKFRONT_ID_TYPE:{},
            RISKFRONT_CASETYPE:{}
        };
        CacheService.initCache(vm.cached);

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

        function resetForm() {
            vm.queryBean = {};
        }



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
                    RISKFRONT_STATUS:vm.cached.RISKFRONT_STATUS,
                    RISKFRONT_DEAL_STATUS:vm.cached.RISKFRONT_DEAL_STATUS,
                    RISKFRONT_TX_CODE:vm.cached.RISKFRONT_TX_CODE,
                    RISKFRONT_TYPE:vm.cached.RISKFRONT_TYPE,
                    RISKFRONT_DATA_TYPE:vm.cached.RISKFRONT_DATA_TYPE,
                    RISKFRONT_SUBJECT_TYPE:vm.cached.RISKFRONT_SUBJECT_TYPE,
                    RISKFRONT_ID_TYPE:vm.cached.RISKFRONT_ID_TYPE,
                    RISKFRONT_CASETYPE:vm.cached.RISKFRONT_CASETYPE

                }
            }, function (item) {
            }, '', 'myModalNoSave.html');
        }
       
        function queryMessageLog(bean){

            var postData = angular.copy(bean);
            vm.url = config.ctx + "/riskfront/messageList/MessageLog";
            //组织参数
            $http.post(vm.url, $httpParamSerializerJQLike(postData))
                .then(function (response) {
                    var data = response.data;

                    if (data.success) {
                        var messageLog = data.object;
                       
                        $log.debug(messageLog );
                        OpenService({
                            modalTitle: '处理日志查询',
                            modalBody: 'toMessageLog',
                            url: '#',
                            item:messageLog,
                            cached:{

                            }
                        }, function () {

                        },'','myModalNoFooter.html');
                    } else {
                        alert(data.msg);
                    }
                });
        }
        
        function toMessage(bean){
            vm.strs= new Array();
            var postData = angular.copy(bean);
            vm.url = config.ctx + "/riskfront/messageList/toMessage";
            //组织参数
            $http.post(vm.url, $httpParamSerializerJQLike(postData))
                .then(function (response) {
                    var data = response.data;

                    if (data.success) {
                        vm.message = data.object;
                        vm.strs=vm.message.respMessage.split("\n");
                        $log.debug(vm.strs);
                        OpenService({
                            modalTitle: '结果报文',
                            modalBody: 'toResult',
                            url: '#',
                            item: {reMessage:vm.strs },
                            cached:{

                            }
                        }, function () {

                        },'','myModalNoFooter.html');
                    } else {
                        alert(data.msg);
                    }
                });
        }

        function downEnclosure(item) {
            $log.debug(item);
            location.href = config.ctx + '/riskfront/messageList/downEnclosure?id=' + item.msgId;
        }

       
        
    }]);
