// userUpdate
document.getElementById('pwdChange').onclick = function(){
    let password = document.getElementById('passwordUpt');
    let pwdUpdate = document.getElementById('pwdUpdate');
    let pwdUpdateCancel = document.getElementById('pwdUpdateCancel');
    let pwdChange = document.getElementById('pwdChange');
    let label = document.getElementById('labelpwd');

    pwdChange.style.display='none';
    label.style.display='block';
    password.style.display='block';
    pwdUpdate.style.display='inline';
    pwdUpdateCancel.style.display='inline';

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
                document.getElementById('labelpwd').style.display='none';
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
    document.getElementById('labelpwd').style.display='none';
    document.getElementById('pwdChange').style.display='block';
};

document.getElementById('phoneUptBtn').onclick = function () {
    document.getElementById('phoneSpan').style.display='none';
    document.getElementById('phoneSpanlabel').style.display='none';
    document.getElementById('phoneUptBtn').style.display='none';
    document.getElementById('phone').style.display='block';
    document.getElementById('phonelabel').style.display='block';
    document.getElementById('phoneUptSave').style.display='inline';
    document.getElementById('phoneUptCancel').style.display='inline';
};

document.getElementById('phoneUptCancel').onclick = function() {
    document.getElementById('phone').value='';
    document.getElementById('phone').style.display='none';
    document.getElementById('phonelabel').style.display='none';
    document.getElementById('phoneUptSave').style.display='none';
    document.getElementById('phoneUptCancel').style.display='none';
    document.getElementById('phoneSpan').style.display='block';
    document.getElementById('phoneSpanlabel').style.display='block';
    document.getElementById('phoneUptBtn').style.display='block';
};

document.getElementById('phoneUptSave').onclick = function() {
    let phoneNum = document.getElementById('phone');

    if(!formCheckPhoneNum(phoneNum)){
        return;
    }

    const payload = {
        phone : phoneNum.value
    }

    axios.post('phoneUpdate', payload)
        .then( res => {
            if(res.data == 1){
                alert("전화번호 변경이 완료 되었습니다.");
                document.getElementById('phone').value='';
                document.getElementById('phone').style.display='none';
                document.getElementById('phonelabel').style.display='none';
                document.getElementById('phoneUptSave').style.display='none';
                document.getElementById('phoneUptCancel').style.display='none';
                document.getElementById('phoneSpan').value = payload.phone;
                document.getElementById('phoneSpan').style.display='block';
                document.getElementById('phoneSpanlabel').style.display='block';
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
    document.getElementById('postSearch').style.display='block';
    document.getElementById('address1').style.display='block';
    document.getElementById('address2').style.display='block';
    document.getElementById('addressUptSave').style.display='inline';
    document.getElementById('addressUptCancel').style.display='inline';
};

document.getElementById('addressUptCancel').onclick = function() {
    document.getElementById('postnum').value='';
    document.getElementById('address1').value='';
    document.getElementById('address2').value='';
    document.getElementById('postnum').style.display='none';
    document.getElementById('postSearch').style.display='none';
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
    let postnum = document.getElementById('postnum');
    let address1 = document.getElementById('address1');
    let address2 = document.getElementById('address2');

    if(postnum.value.length<=0){
        alert("우편번호를 입력하세요.");
        return;
    }
    if(address1.value.length<=0){
        alert("주소를 입력하세요.");
        return;
    }

    if(!formCheckAddress2(address2)){
        return;
    }

    const payload = {
        postnum : postnum.value,
        address1 : address1.value,
        address2 : address2.value
    }

    axios.post('addressUpdate', payload)
        .then( res => {
            if(res.data == 1){
                alert("주소 변경이 완료 되었습니다.");
                document.getElementById('postnum').value='';
                document.getElementById('address1').value='';
                document.getElementById('address2').value='';
                document.getElementById('postnum').style.display='none';
                document.getElementById('postSearch').style.display='none';
                document.getElementById('address1').style.display='none';
                document.getElementById('address2').style.display='none';
                document.getElementById('addressUptSave').style.display='none';
                document.getElementById('addressUptCancel').style.display='none';

                document.getElementById('spanPost').value="("+payload.postnum+")";
                document.getElementById('spanAddr1').value=payload.address1;
                document.getElementById('spanAddr2').value=payload.address2;
                document.getElementById('spanPost').style.display='block';
                document.getElementById('spanAddr1').style.display='block';
                document.getElementById('spanAddr2').style.display='block';
                document.getElementById('addressUptBtn').style.display='block';
            }
        })
        .catch(err => console.log(err))
}

function postSearch() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postnum').value = data.zonecode;
            document.getElementById("address1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("address2").focus();
        }
    }).open();
}