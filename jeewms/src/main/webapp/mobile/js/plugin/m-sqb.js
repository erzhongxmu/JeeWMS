function sfhy(str){
    var returncode=jsonVal(str,'error_code');
    console.log(str);
    if(returncode=='0'){
        alert("识别成功");
        var resultstr=jsonVal(str,'result');
        var side=jsonVal(resultstr,'side');
        if(side=="front"){
            $('#sfzhy_xm').val(jsonVal(resultstr,'realname'));
            $('#sfzhy_xb').val(jsonVal(resultstr,'sex'));
            $('#sfzhy_csrq').val(jsonVal(resultstr,'born'));
            $('#sfzhy_mz').val(jsonVal(resultstr,'nation'));
            $('#sfzhy_sfzh').val(jsonVal(resultstr,'idcard'));
            //$('sfzhy_#sjh').val();
            $('#sfzhy_hjdz').val(jsonVal(resultstr,'address'));

        }else{
            $('#sfzhy_zjyxq').val(jsonVal(resultstr,'begin')+'-'+jsonVal(resultstr,'end'));
            $('#sfzhy_qfjg').val(jsonVal(resultstr,'department'));
        }

    }else{
        alert('身份证是识别失败');
    }
}