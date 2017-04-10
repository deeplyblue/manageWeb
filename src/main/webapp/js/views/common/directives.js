/**
 * Created by lupf on 2016/5/3.
 */
angular.module('myApp')
    .directive('commonModalForm', [function () {
        return {
            replace: true,
            transclude: true,
            template: '<form name="commonModalForm" novalidate w5c-form-validate autocomplete="off">' +
            '<div ng-transclude=""></div>' +
            '<div class="modal-footer" style="height: 40px">' +
            '<button class="btn btn-primary" type="button" w5c-form-submit="save()">save</button>' +
            '<button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>' +
            '</div>' +
            '</form>'
        }
    }])
    .directive('myColor', [function () {
        return {
            restrict: 'A',
            scope: {
                myColor: '=',
                myColorTrue: '@',
                myColorCode: '@',
                myColorType: '@'   //bg:background,font:font
            },
            link: function (scope, element, attrs) {
                var ele = $(element);
                scope.$watch('myColor', function (newValue, oldValue) {
                    if (newValue + '' == scope.myColorTrue) {
                        if (!scope.myColorType || scope.myColorType == 'bg') {
                            ele.css('background-color', scope.myColorCode)
                        } else if (scope.myColorType == 'font') {
                            ele.css('color', scope.myColorCode)
                        } else {
                            console.error('myColorType:', scope.myColorType, ' didnt support!')
                        }
                    } else {
                        ele.css('background-color', 'white')
                    }
                })
            }
        }
    }])
    .directive('tableDetail', [function () {
            return {
                replace: true,
                transclude: true,
                template: '<table class="table table-bordered table-striped table-condensed myTable" ' +
                'style = "word-wrap:break-word;word-break:break-all;" ng-transclude></table> '

            };
        }]
    )
    .directive('tableForm', [function () {
        return {
            replace: true,
            transclude: true,
            template: '<table class="table table-bordered table-condensed myTable" ' +
            'ng-transclude></table> '
        }
    }])
    .directive('checkboxAll', [function () {
        return {
            restrict: 'A',
            scope: {
                model: '=ngModel',
                checkboxAll: '='
            },
            link: function (scope, element, attrs) {
                scope.$watch('model', function (newValue, oldValue, sc) {
                    if (angular.isArray(scope.checkboxAll)) {
                        scope.checkboxAll.forEach(function (item) {
                            item._checked = newValue;
                        });
                    }
                });
            }
        }
    }])
    .directive('downFile', ['$http', '$httpParamSerializerJQLike', 'dateFilter', function ($http, $httpParamSerializerJQLike, dateFilter) {
        return {
            restrict: 'A',
            scope: {
                params: '=',
                downFileType: '@',
                downFile: '@',
                /**
                 * downCfg = {contentType : 'json',
                  *           date : {'date1' : 'yyyyMMdd','date2' : 'yyyyMMdd'}
                  *          }
                 */
                downCfg: '='
            },
            link: function (scope, element, attr) {
                var ele = $(element);
                ele.on('click', function (e) {
                    ele.prop('disabled', true);
                    e.preventDefault();
                    //给定json/jqLike两种提交方式
                    var params = angular.copy(scope.params);
                    if (!scope.downCfg) {
                        scope.downCfg = {
                            contentType: 'json'
                        };
                    } else if (scope.downCfg.contentType == 'jqLike') {
                        var dateFields = scope.downCfg.date;
                        for (var dateField in dateFields) {
                            params[dateField] = dateFilter(params[dateField], dateFields[dateField]);
                        }
                    }

                    var headerCfg = (scope.downCfg.contentType == 'json') ? config.downHeaderJson : config.downHeaderJqLike;
                    var params = (scope.downCfg.contentType == 'json') ? params : $httpParamSerializerJQLike(params);
                    $http.post(
                        attr.downFile,
                        params,
                        headerCfg
                    ).success(function (data, status, headers) {
                        ele.prop('disabled', false);
                        var type;
                        switch (attr.downFileType) {
                            case 'xlsx':
                                type = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
                                break;
                            case  'xls':
                                type = 'application/vnd.ms-excel;charset=utf-8';
                                break;
                            default:
                                type = 'application/vnd.ms-excel;charset=utf-8';
                                break;
                        }
                        if (!type) throw '无效类型';
                        saveAs(new Blob([data], {type: type}), decodeURI(headers()["x-filename"]));  // 中文乱码
                    }).error(function (data, status) {
                        alert(data);
                        ele.prop('disabled', false);
                    });
                });
            }
        };
    }])
    //带时分的日期控件
    .directive('dateTime', [function () {
        return {
            restrict: 'A',
            scope: {
                model: '=ngModel'
            },
            link: function (scope, element, attrs, ctr) {
                $(element).datetimepicker({
                    format: "yyyymmdd-hh:ii",
                    autoclose: true,
                    todayBtn: true,
                    minuteStep: 5,
                    language: 'zh-CN'
                })
                    .on('changeDate', function (ev) {
                        scope.model = ev.date;
                        scope.$apply();
                    });
            }
        }
    }])

    /*
     * example
     * <table st-table='list' st-table-options="[{name:'',headName:'',mappingCache:''},{},{}]"></table>
     * */
    .directive('stTable', ['$log', function ($log) {
        return {
            restrict: 'A',
            scope: {
                stTable: '=',
                /*
                 * stTableOptions
                 * {
                 *      name:
                 *      headName:
                 *      mappingCache:
                 *      notMerge:false    //默认合并
                 *      mergeAsCol: index
                 *      highlightNE: [index,index,color]
                 * }
                 * */
                stTableOptions: '='
            },
            link: function (scope, ele, attrs) {


                scope.$watch('stTable', function (newValue, oldValue) {
                    ele.html('');

                    $log.debug('run in again');
                    if (newValue && newValue.length > 0) {
                        $log.info(attrs.stTable);
                    } else {
                        $log.info('aaaaaaaaaa');
                        return;
                    }

                    // 创建表头
                    var thead = document.createElement('thead');
                    var tr = document.createElement('tr');
                    for (var i = 0; i < scope.stTableOptions.length; i++) {
                        var td = document.createElement('td');
                        // $log.debug(scope.stTableOptions[i].headName);
                        td.innerText = scope.stTableOptions[i].headName;
                        tr.appendChild(td);
                    }
                    thead.appendChild(tr);
                    ele.append(thead);

                    // 遍历name,list计算每一行数据的value，mergedBy以及rowspan
                    /*
                     * 只有存在rowSpan的td才会被创建
                     * mergedBy用于记录该单元格被哪一行合并；往下遇见重复时，便于重新计算拥有rowSpan的那一行的rowSpan属性
                     * */
                    var arr = [];
                    for (var i in scope.stTableOptions) {
                        var name = scope.stTableOptions[i]['name'];

                        $log.debug(name);

                        for (var j in scope.stTable) {
                            var detail = scope.stTable[j];
                            var row;
                            if (arr[j]) {
                                row = arr[j];
                            } else {
                                row = [];
                                arr.push(row);
                            }

                            if (j == 0) {
                                var asIndex = scope.stTableOptions[i]['mergeAsCol'];
                                var temp = {
                                    value: detail[name],
                                    rowSpan: row[asIndex] ? row[asIndex]['rowSpan'] : 1,
                                    mergedBy: 0
                                };
                                row[i] = temp;
                            } else {
                                var preRow = arr[j - 1];
                                if (scope.stTableOptions[i]['mergeAsCol'] != undefined) {
                                    var asIndex = scope.stTableOptions[i]['mergeAsCol'];
                                    if (row[asIndex]['rowSpan']) {
                                        var temp = {
                                            value: detail[name],
                                            rowSpan: row[asIndex]['rowSpan'],
                                            mergedBy: row[asIndex]['mergedBy']
                                        };
                                        row[i] = temp;
                                    } else {
                                        var temp = {
                                            value: detail[name],
                                            mergedBy: preRow[i]['mergedBy']
                                        };
                                        row[i] = temp;
                                    }
                                } else if ((!scope.stTableOptions[i]['notMerge']) && (preRow[i]['value'] == detail[name] && (i == 0 || preRow[i - 1]['mergedBy'] == row[i - 1]['mergedBy']))) {
                                    var temp = {
                                        value: detail[name],
                                        mergedBy: preRow[i]['mergedBy']
                                    };
                                    row[i] = temp;
                                    //更新初始行合并数
                                    arr[temp['mergedBy']][i]['rowSpan'] += 1;
                                } else {
                                    var temp = {
                                        value: detail[name],
                                        rowSpan: 1,
                                        mergedBy: j
                                    }
                                    row[i] = temp;
                                }
                            }

                        }
                    }

                    // 遍历list，创建td
                    var tbody = document.createElement('tbody');
                    for (i in arr) {
                        var tr = document.createElement('tr');
                        for (j in arr[i]) {
                            var mergeObj = arr[i][j];
                            if (mergeObj['rowSpan']) {
                                var td = document.createElement('td');
                                td.setAttribute('rowSpan', mergeObj['rowSpan']);
                                $log.debug(scope.stTableOptions[j]['mappingCache']);
                                if (scope.stTableOptions[j]['mappingCache']) {
                                    var tempValue = scope.stTableOptions[j]['mappingCache'][mergeObj['value']];
                                    if (tempValue) {
                                        td.innerText = tempValue;
                                    } else {
                                        td.innerText = '';
                                    }

                                } else {
                                    td.innerText = mergeObj['value'];
                                }

                                if (scope.stTableOptions[j]['highlightNE']) {
                                    var ne = scope.stTableOptions[j]['highlightNE'];
                                    var td1 = arr[i][ne[0]]['value'];
                                    var td2 = arr[i][ne[1]]['value'];
                                    if (td1 != td2) {
                                        td.style.color = ne[2];
                                    }
                                }

                                tr.appendChild(td);
                            }
                        }

                        tbody.appendChild(tr);
                    }
                    ele.append(tbody);
                })


            }
        }
    }])

    .directive('stTableData', ['$log', '$filter', function ($log, $filter) {
        return {
            restrict: 'E',
            scope: {
                stData: '=',
                stCaches: '='
                /*
                 * stTableOptions
                 * {
                 *      name:
                 *      headName:
                 *      mappingCache:
                 *      notMerge:false    //默认合并
                 *      mergeAsCol: index
                 *      highlightNE: [index,index,color]
                 * }
                 * */
                // stTableOptions: '='
            },
            controller: ['$scope', function myController($scope) {
                var stTableOptions = $scope.stTableOptions = [];

                this.addColumn = function (columnDef) {
                    //属性名转换
                    var column = {
                        name: columnDef.stField,
                        headName: columnDef.stHead,
                        mappingCache: columnDef.stMapping,
                        notMerge: columnDef.stNoMerge,
                        mergeAsCol: columnDef.stMergeAs,
                        highlightNE: columnDef.stHighlightNe,
                        fieldType: columnDef.stFieldType,
                        buttons: columnDef.stButtons,
                        functions: columnDef.stFunctions()
                    }
                    stTableOptions.unshift(column);
                };

                this.addHead = function (head) {
                    $scope.stHead = head;
                }
            }],
            link: function (scope, ele, attrs) {

                scope.$watch('stData', function (newValue, oldValue) {

                    ele.html('');
                    var table = document.createElement('table');
                    table.setAttribute('class', 'table table-bordered table-striped table-condensed myTable');
                    ele.append(table);
                    table = angular.element(table);

                    $log.debug('run in again');
                    if (newValue && newValue.length > 0) {
                        $log.info(attrs.stData);
                    } else {
                        $log.info('aaaaaaaaaa');
                        return;
                    }

                    //允许定制表头
                    if (scope.stHead) {
                        table.append(scope.stHead);
                    } else {
                        // 创建表头
                        var thead = document.createElement('thead');
                        var tr = document.createElement('tr');
                        for (var i = 0; i < scope.stTableOptions.length; i++) {
                            var td = document.createElement('td');
                            // $log.debug(scope.stTableOptions[i].headName);
                            td.innerText = scope.stTableOptions[i].headName;
                            tr.appendChild(td);
                        }
                        thead.appendChild(tr);
                        table.append(thead);
                    }

                    // 遍历name,list计算每一行数据的value，mergedBy以及rowspan
                    /*
                     * 只有存在rowSpan的td才会被创建
                     * mergedBy用于记录该单元格被哪一行合并；往下遇见重复时，便于重新计算拥有rowSpan的那一行的rowSpan属性
                     * */
                    var arr = [];
                    $log.debug(scope.stCaches);
                    for (var i in scope.stTableOptions) {
                        //初始化cache
                        if (angular.isString(scope.stTableOptions[i]['mappingCache'])){
                            scope.stTableOptions[i]['mappingCache'] = scope.stCaches[scope.stTableOptions[i]['mappingCache']];
                        }

                        var name = scope.stTableOptions[i]['name'];

                        $log.debug(name);
                        for (var j in scope.stData) {
                            var detail = scope.stData[j];

                            //预处理fieldType
                            var fieldType = scope.stTableOptions[i]['fieldType'];
                            if (fieldType) {
                                detail[name] = $filter(fieldType)(detail[name]/100, '');
                            }
                            
                            var row;
                            if (arr[j]) {
                                row = arr[j];
                            } else {
                                row = [];
                                arr.push(row);
                            }

                            if (j == 0) {
                                var asIndex = scope.stTableOptions[i]['mergeAsCol'];
                                var temp = {
                                    value: detail[name],
                                    rowSpan: row[asIndex] ? row[asIndex]['rowSpan'] : 1,
                                    mergedBy: 0
                                };
                                row[i] = temp;
                            } else {
                                var preRow = arr[j - 1];
                                if (scope.stTableOptions[i]['mergeAsCol'] != undefined) {
                                    var asIndex = scope.stTableOptions[i]['mergeAsCol'];
                                    if (row[asIndex]['rowSpan']) {
                                        var temp = {
                                            value: detail[name],
                                            rowSpan: row[asIndex]['rowSpan'],
                                            mergedBy: row[asIndex]['mergedBy']
                                        };
                                        row[i] = temp;
                                    } else {
                                        var temp = {
                                            value: detail[name],
                                            mergedBy: preRow[i]['mergedBy']
                                        };
                                        row[i] = temp;
                                    }
                                } else if ((!scope.stTableOptions[i]['notMerge']) && (preRow[i]['value'] == detail[name] && (i == 0 || preRow[i - 1]['mergedBy'] == row[i - 1]['mergedBy']))) {
                                    var temp = {
                                        value: detail[name],
                                        mergedBy: preRow[i]['mergedBy']
                                    };
                                    row[i] = temp;
                                    //更新初始行合并数
                                    arr[temp['mergedBy']][i]['rowSpan'] += 1;
                                } else {
                                    var temp = {
                                        value: detail[name],
                                        rowSpan: 1,
                                        mergedBy: j
                                    }
                                    row[i] = temp;
                                }
                            }

                        }
                    }

                    //处理小计、合并问题
                    for (i in arr) {
                        var obj0 = arr[i][0];
                        var obj1 = arr[i][1];
                        if (obj0['value'] == '合计' && obj1['value'] == '小计') {
                            obj0['colSpan'] = 2;
                            obj1['rowSpan'] = undefined;
                        } else if (obj1['value'] == '小计') {
                            var mergedBy = arr[obj0['mergedBy']][0];
                            mergedBy['rowSpan'] = mergedBy['rowSpan'] - 1;
                            obj1['colSpan'] = 2;
                        }
                    }

                    $log.debug(arr);

                    // 遍历list，创建td
                    var tbody = document.createElement('tbody');
                    for (i in arr) {
                        var tr = document.createElement('tr');
                        for (j in arr[i]) {
                            var mergeObj = arr[i][j];
                            if (mergeObj['rowSpan']) {
                                var td = document.createElement('td');
                                td.setAttribute('rowSpan', mergeObj['rowSpan']);

                                //辅助处理合计、小计问题
                                if (mergeObj['colSpan']) {
                                    td.setAttribute('colSpan', mergeObj['colSpan']);
                                }

                                // $log.debug(scope.stTableOptions[j]['mappingCache']);
                                var buttons = scope.stTableOptions[j]['buttons'];
                                var functions = scope.stTableOptions[j]['functions'];
                                if (buttons) {
                                    //表格中存在按钮时
                                    var bean = scope.stData[i];
                                    for (i in buttons) {
                                        var button = document.createElement('button');
                                        button.bind('click', functions[i](bean));
                                    }
                                } else if (scope.stTableOptions[j]['mappingCache']) {
                                    var tempValue = scope.stTableOptions[j]['mappingCache'][mergeObj['value']];
                                    if (tempValue) {
                                        td.innerText = tempValue;
                                    } else {
                                        td.innerText = mergeObj['value'];
                                    }

                                } else {
                                    td.innerText = mergeObj['value'];
                                }

                                if (scope.stTableOptions[j]['highlightNE']) {
                                    var ne = scope.stTableOptions[j]['highlightNE'];
                                    var td1 = arr[i][ne[0]]['value'];
                                    var td2 = arr[i][ne[1]]['value'];
                                    if (td1 != td2) {
                                        td.style.color = ne[2];
                                    }
                                }

                                tr.appendChild(td);
                            }
                        }

                        tbody.appendChild(tr);
                    }
                    table.append(tbody);
                })


            }
        }
    }])

    .directive('stTableHead', [function () {
        return {
            restrict: 'E',
            require: '^^stTableData',
            scope: {},
            link: function (scope, ele, attrs, controller) {
                controller.addHead(ele.children().html());
            }
        }
    }])

    .directive('stTableColumn', [function () {
        return {
            restrict: 'E',
            require: '^^stTableData',
            scope: {
                stHead: '@',
                stField: '@',
                stFieldType: '@',
                stMapping: '@',
                stNoMerge: '@',
                stMergeAs: '@',
                stHighlightNe: '@',
                stButtons: '@',
                stFunctions: '&'
            },
            link: function (scope, ele, attrs, controller) {
                controller.addColumn(scope);
            }
        }
    }]);


/*
 angular.module("custom/template/form/withModal.html", []).run(["$templateCache", function ($templateCache) {
 $templateCache.put("custom/template/form/withModal.html",
 '<form name="commonModalForm" novalidate w5c-form-validate>' +
 '<div ng-transclude=""></div>' +
 '<div class="modal-footer" style="height: 40px">' +
 '<button class="btn btn-primary" type="button" ng-disabled="commonModalForm.$invalid" ng-click="save()">save</button>' +
 '<button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>' +
 '</div>' +
 '</form>');
 }]);*/
