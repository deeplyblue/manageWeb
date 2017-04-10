/**
 * Created by lupf on 2016/4/18.
 */
function myAjaxRequest(par){
    if(par.dataType == undefined){
        par.dataType = "json";
    }
    if(par.mask == undefined){
        par.mask = true;
    }
    if(par.blockUI == undefined){
        par.blockUI = true;
    }

    var url = par.url;
    var postData = par.postData;
    var onSuccessFunction = par.onSuccessFunction;
    var onFailureFunction = par.onFailureFunction;
    var async = par.async ;
    var mask = par.mask;
    var dataType = par.dataType;
    var blockUI = par.blockUI;

    if (async != null){
        async = true;//默认不同步
    }
    /*if(mask){
        loadJs();
    }*/
    $.ajax({
        timeout:300000, //5分钟
        type:"POST",
        async:async,
        url: url,
        data:postData,
        dataType:dataType,
        success:function(data, textStatus, jqXHR){
            if(data){
                if (data.result=='success'){
                    if(onSuccessFunction){
                        onSuccessFunction(data);
                    }
                }else if (data.result=='error'||data.result=='fail'){
                    if(onFailureFunction){
                        onFailureFunction(data);
                    }else{
                        alert(data.msg);
                    }
                }else if (data.result=='notlogin'){
                    alert('请重新登录');
                }else if(data.result == "noprivilege"){
                    alert("您无此权限操作该功能模块!若要开通，请联系管理员。");
                }else if(data.result == "illegalPath"){
                    alert("您访问的路径为非法路径，请检查！");
                }else {
                    if(onSuccessFunction){
                        onSuccessFunction(data);
                    }
                }
            }else{
                alert("未找到数据!");
            }
            setTimeout(function(){
                if(blockUI){
                    $(".blockUI").remove();
                }
            },0000);//用户查看页面时间
        },
        error:function(jqXHR, textStatus, errorThrown){
            if (textStatus=='timeout'){
                alert("连接超时，请检查网络");
            /*}else if(errorThrown.indexOf('会话时间已过期')>0){
                //跳转到sessiontimeout.jsp
                ajaxTimeOutContinue();*/
            }else if (textStatus=='error'){
                alert("请求错误");
            }else if (textStatus=='abort'){
                alert("请求中止");
            }/*else if (textStatus == "parsererror"){
                alert("返回格式错误！");
            }else{
                alert(textStatus+",HTTP error occurs"+errorThrown);
            }*/

        },
        statusCode:{404:function(){alert("404,页面不存在"+this.url);},
            500:function(){alert("500,服务器内部错误"+this.url);}}
    });
}