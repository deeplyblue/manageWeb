/**
 * Created by shulw on 2016/12/21.
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


            //文件状态
            $scope.reserveReportStatus= [
                { name: '初始化', value: '0' },
                { name: '解析成功', value: '1' },
                { name: '解析失败', value: '2' },
                { name: '请选择', value: '' }

            ];
            //季度
            $scope.reserveReportQuarter= [
                { name: '请选择', value: '' },
                { name: '第一季度', value: '1' },
                { name: '第二季度', value: '2' },
                { name: '第三季度', value: '3' },
                { name: '第四季度', value: '4' }

            ];

                //缓存数据初始化(需要缓存的key请自定义)
            /*数据格式{
             key1 :value1,
             key2:value2
             }*/
            vm.cached = {
                REPORT_TYPE_LIST:{},
                RESERVE_REPORT_TYPE_LIST:{},
                RESERVE_REPORT_TYPE:{}
            };

            (function init() {
                vm.queryBean = {};
                vm.queryBean.reportYear =new Date();//当前年份
                vm.queryBean.reportQuarter = '';//默认请选择
                vm.queryBean.reportStatus = '';//默认请选择

                vm.cached.RESERVE_REPORT_TYPE_LIST = [];
                vm.cached.RESERVE_REPORT_TYPE_LIST.push(
                    {
                        'itemDesc':"请选择",
                        'reportType':''
                    });
                vm.cached.RESERVE_REPORT_TYPE_LIST.push(
                    {
                        'itemDesc':"季报",
                        'reportType':'QUARTER'
                    });
                vm.cached.RESERVE_REPORT_TYPE_LIST.push(
                    {
                        'itemDesc':"年报",
                        'reportType':'YEAR'
                    });
                vm.queryBean.reportType='';
                vm.cached.REPORT_TYPE_LIST = [];
                $http.post(config.ctx + '/reserve/reserveReport/searchReportType',{},config.jsonHeader)
                    .then(function (response) {
                        if (response.data.success){
                           vm.cached.RESERVE_REPORT_TYPE=response.data.object;
                           format();
                        }else {
                            $log.error('获取数据%s失败',response.data.msg);
                        }
                    }, function (response) {
                        $log.error('获取数据%s失败', cacheKey);
                    })
            }());

            function format(){
                vm.cached.REPORT_TYPE_LIST = [];

                for (var index in vm.cached.RESERVE_REPORT_TYPE){
                    vm.cached.REPORT_TYPE_LIST.push(
                        {
                            'itemDesc':vm.cached.RESERVE_REPORT_TYPE[index],
                            'reportType':index
                        });
                }
               // vm.queryBean.reportType='';
            }

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

            vm.upload = upload;

            /*------------------以上方法名可选择性通用---------------------*/


            function queryDetail() {

                var queryForm = document.getElementById('queryForm');
                vm.url = angular.element(queryForm).prop('action');

                //组织参数：查询条件 + 分页数据
                var postData = angular.copy(vm.queryBean);
                if(vm.queryBean.reportYear!=null){
                    postData.reportYear=vm.queryBean.reportYear.getFullYear();
                }
                postData.pageNum = vm.pagination.pageNum;
                postData.pageSize = vm.pagination.pageSize;
                $http.post(vm.url, postData,config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            vm.pagination = data.object;
                            //清空选中记录
                           // CheckboxService.clearChecked();
                        } else {
                            alert(data.msg);
                        }
                    });

            };

            function resetForm() {
                vm.queryBean = {};
                $scope.errMessage = '';
            }



            function upload() {
                OpenService({
                    modalTitle: '上传文件',
                    modalBody: 'toUpload',
                    url: 'upload',
                    item: {},
                    cached: {
                        RESERVE_REPORT_TYPE_LIST:vm.cached.RESERVE_REPORT_TYPE_LIST
                    }
                }, function (iteaddItemm) {

                }, 'uploadReserveReportInstanceCtrl', 'myModalNoSave.html');
            }

    }]);






angular.module('myApp').controller('uploadReserveReportInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'FileUploader', '$log', function ($scope, $uibModalInstance, modalItem, $http,
                                                                    $httpParamSerializerJQLike, FileUploader, $log) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {}
        };

        vm = $scope.vm = modalItem;
        //上传条件
        vm.queryBean = {};
        //季度
        $scope.reserveReportQuarter= [
            { name: '请选择', value: '' },
            { name: '第一季度', value: '1' },
            { name: '第二季度', value: '2' },
            { name: '第三季度', value: '3' },
            { name: '第四季度', value: '4' }

        ];

        vm.queryBean.reportType='';
        vm.queryBean.reportQuarter = '';//默认请选择
        vm.queryBean.reportYear =new Date();//当前年份

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
                        var reg = new RegExp('^.*\.(?:xls|xlsx)$','g');
                        if(reg.test(item.name)){
                            if (vm.fileUploader.queue.length >= 1) {
                                alert("请先取消原先文件,再选择新文件!");
                                return false;
                            } else {
                                return true;
                            }
                        }else{
                            alert("请上传已.xls和.xlsx结尾的excel文档!");
                            return false;
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
                groupName: response.object.dfsGroupname,
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
            if(vm.queryBean.reportYear!=null){
                vm.queryBean.reportYear=vm.queryBean.reportYear.getFullYear();
            }

            if(vm.queryBean.reportType){
                vm.queryBean.groupName =vm.file1.groupName;
                vm.queryBean.dfsFilePath=vm.file1.dfsFilePath;
                vm.queryBean.localFileName=vm.file1.originFileName;
            }else {
                alert("请先选择报表类型");
                return false;
            }
            if(vm.file1.originFileName){
                $http.post(config.ctx + '/reserve/reserveReport/upload', $httpParamSerializerJQLike(vm.queryBean))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert("提交成功");
                            $scope.cancel();
                        } else {
                            vm.queryBean.reportYear =new Date();//当前年份
                            alert(data.msg);
                        }
                    });
            }else{
                vm.queryBean.reportYear =new Date();//当前年份
                alert("请先上传文件");
                return false;
            }
        }






    }]);