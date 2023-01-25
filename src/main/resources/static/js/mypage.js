// userUpdate
document.getElementById('pwdChange').onclick = function(){
    let password = document.getElementById('passwordUpt');
    let pwdUpdate = document.getElementById('pwdUpdate');
    let pwdUpdateCancel = document.getElementById('pwdUpdateCancel');
    let pwdChange = document.getElementById('pwdChange');

    pwdChange.style.display='none';
    password.style.display='block';
    pwdUpdate.style.display='block';
    pwdUpdateCancel.style.display='block';

};

document.getElementById('pwdUpdate').onclick = function(){
    const payload = {
        password : document.getElementById('passwordUpt').value
    }

    if(payload.password.trim().length<=0){
        alert("비밀번호를 입력하세요.");
        return;
    }

    console.log(document.getElementById('passwordUpt').value);
    axios.post('pwdUpdate', payload)
        .then( res => {
            if(res.data == 1){
                alert("비밀번호 변경이 완료 되었습니다.");
                document.getElementById('passwordUpt').value='';
                document.getElementById('passwordUpt').style.display='none';
                document.getElementById('pwdUpdate').style.display='none';
                document.getElementById('pwdUpdateCancel').style.display='none';
                document.getElementById('pwdChange').style.display='block';
            }
        })
        .catch(err => console.log(err))
};

document.getElementById('pwdUpdateCancel').onclick = function() {
    document.getElementById('passwordUpt').value='';
    document.getElementById('passwordUpt').style.display='none';
    document.getElementById('pwdUpdate').style.display='none';
    document.getElementById('pwdUpdateCancel').style.display='none';
    document.getElementById('pwdChange').style.display='block';
};

document.getElementById('phoneUptBtn').onclick = function () {
    document.getElementById('phoneSpan').style.display='none';
    document.getElementById('phoneUptBtn').style.display='none';
    document.getElementById('phone').style.display='block';
    document.getElementById('phoneUptSave').style.display='block';
    document.getElementById('phoneUptCancel').style.display='block';
};

document.getElementById('phoneUptCancel').onclick = function() {
    document.getElementById('phone').value='';
    document.getElementById('phone').style.display='none';
    document.getElementById('phoneUptSave').style.display='none';
    document.getElementById('phoneUptCancel').style.display='none';
    document.getElementById('phoneSpan').style.display='block';
    document.getElementById('phoneUptBtn').style.display='block';
};

document.getElementById('phoneUptSave').onclick = function() {
    const payload = {
        phone : document.getElementById('phone').value
    }

    if(payload.phone.trim().length<=0){
        alert("전화번호를 입력하세요.");
        return;
    }

    axios.post('phoneUpdate', payload)
        .then( res => {
            if(res.data == 1){
                alert("전화번호 변경이 완료 되었습니다.");
                document.getElementById('phone').value='';
                document.getElementById('phone').style.display='none';
                document.getElementById('phoneUptSave').style.display='none';
                document.getElementById('phoneUptCancel').style.display='none';
                document.getElementById('phoneSpan').innerHTML = payload.phone;
                document.getElementById('phoneSpan').style.display='block';
                document.getElementById('phoneUptBtn').style.display='block';
            }
        })
        .catch(err => console.log(err))
}

document.getElementById('addressUptBtn').onclick = function () {
    document.getElementById('spanPost').style.display='none';
    document.getElementById('spanAddr1').style.display='none';
    document.getElementById('spanAddr2').style.display='none';
    document.getElementById('addressUptBtn').style.display='none';
    document.getElementById('postnum').style.display='block';
    document.getElementById('address1').style.display='block';
    document.getElementById('address2').style.display='block';
    document.getElementById('addressUptSave').style.display='block';
    document.getElementById('addressUptCancel').style.display='block';
};

document.getElementById('addressUptCancel').onclick = function() {
    document.getElementById('postnum').value='';
    document.getElementById('address1').value='';
    document.getElementById('address2').value='';
    document.getElementById('postnum').style.display='none';
    document.getElementById('address1').style.display='none';
    document.getElementById('address2').style.display='none';
    document.getElementById('addressUptSave').style.display='none';
    document.getElementById('addressUptCancel').style.display='none';

    document.getElementById('spanPost').style.display='block';
    document.getElementById('spanAddr1').style.display='block';
    document.getElementById('spanAddr2').style.display='block';
    document.getElementById('addressUptBtn').style.display='block';
};

document.getElementById('addressUptSave').onclick = function() {
    const payload = {
        postnum : document.getElementById('postnum').value,
        address1 : document.getElementById('address1').value,
        address2 : document.getElementById('address2').value
    }

    if(payload.postnum.trim().length<=0){
        alert("우편번호를 입력하세요.");
        return;
    }
    if(payload.address1.trim().length<=0){
        alert("주소를 입력하세요.");
        return;
    }
    if(payload.address2.trim().length<=0){
        alert("상세주소를 입력하세요.");
        return;
    }

    axios.post('addressUpdate', payload)
        .then( res => {
            if(res.data == 1){
                alert("주소 변경이 완료 되었습니다.");
                document.getElementById('postnum').value='';
                document.getElementById('address1').value='';
                document.getElementById('address2').value='';
                document.getElementById('postnum').style.display='none';
                document.getElementById('address1').style.display='none';
                document.getElementById('address2').style.display='none';
                document.getElementById('addressUptSave').style.display='none';
                document.getElementById('addressUptCancel').style.display='none';

                document.getElementById('spanPost').innerHTML="("+payload.postnum+")";
                document.getElementById('spanAddr1').innerHTML=payload.address1;
                document.getElementById('spanAddr2').innerHTML=payload.address2;
                document.getElementById('spanPost').style.display='block';
                document.getElementById('spanAddr1').style.display='block';
                document.getElementById('spanAddr2').style.display='block';
                document.getElementById('addressUptBtn').style.display='block';
            }
        })
        .catch(err => console.log(err))
}