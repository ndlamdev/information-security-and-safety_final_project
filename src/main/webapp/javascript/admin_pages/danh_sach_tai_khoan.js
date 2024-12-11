var pageActive = 1;
var totalPage = 0;

$(document).ready(function () {
   $("#search-account-id").next().css({"cursor": "pointer"}).click(function () {
       pageActive = 1
       search_account();
   });

    $("#search-account-name").next().css({"cursor": "pointer"}).click(function () {
        pageActive = 1
        search_account();

    });

    $("#input-lock-account").change(function () {
        pageActive = 1
        search_account();
    });

    $("#input-account-role").change(function () {
        pageActive = 1
        search_account();
    });
});




function formatCurrencyVND(amount) {
    return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}

const handlePage = (page)=>{
    if(page == -2){
        pageActive = pageActive -1
        if(pageActive <= 0)
            pageActive = pageActive +1

    }else
    if(page == -1){
        pageActive = pageActive +1
        if(pageActive > totalPage)
            pageActive = pageActive -1
    }else{
        pageActive = page
    }

    search_account()

}
const handleLockAccount = (e, id) =>{
    $.ajax({
        url: '/mat_kinh_kimi/admin/account',
        method: 'GET',
        data: {
            action: 'lock',
            id: id
        },
        dataType: 'json',
        success: function(data) {
            if(data == 1){
                let a = e.textContent
                if(a == "lock"){
                    e.textContent = "lock_open"
                }else{
                    e.textContent = "lock"
                }

            }
        }
    })
}
const handleRoleAccount = (e, id) =>{
    $.ajax({
        url: '/mat_kinh_kimi/admin/account',
        method: 'GET',
        data: {
            action: 'role',
            id: id,
            role: e.value
        },
        dataType: 'json',
        success: function(data) {
        }
    })

}
const search_account = ()=>{
    const id = $("#search-account-id").val();
    const name = $("#search-account-name").val();
    const lock = $("#input-lock-account").val();
    const role = $("#input-account-role").val();
    $.ajax({
        url: '/mat_kinh_kimi/admin/account',
        method: 'GET',
        data: {
            page: pageActive,
            action: 'get',
            name:name,
            role:role,
            lock:lock,
            id:id

        },
        dataType: 'json',
        success: function(data) {
            totalPage  = data.totalPage
            document.querySelector("#count_user").textContent = data.total
            document.querySelector(".amount").value = data.total;
            let pagi = `<button onclick="handlePage(-2)" id="prev" class="d-flex align-items-center justify-content-center"><span
                            class="material-symbols-outlined">chevron_left</span></button>`
            for (let i = 1; i <= data.totalPage; i++) {
                pagi += `
                <button onclick="handlePage(${i})" class="d-flex align-items-center justify-content-center button-number ${i == pageActive ? 'active' : ''}"
                            >${i}
                </button>
                `
            }
            pagi += `
            <button onclick="handlePage(-1)" id="next" class="d-flex align-items-center justify-content-center"><span
                            class="material-symbols-outlined">chevron_right</span></button>
            `
            document.querySelector(".pagination_account").innerHTML = pagi
            let elm = '';
            data.data.map(tmp=>{
                elm += `
                            <div class="row account align-items-center ps-4" >
                    <div class="col-2 id-account">#${tmp.id}</div>
                    <div class="col-4 d-flex">
                        <div class="avatar-account ">
                            <img class="rounded-circle" width="50" height="50" src="../${tmp.avatar ?? "images/logo/logo.png"}" alt="img">
                        </div>
                        <div class="info-account ms-2 w-100">
                            <p class="name-account">${tmp.fullName}</p>
                            <p class="email-account">${tmp.email}</p>
                        </div>
                    </div>
                    <div class="col-1 text-center">${tmp.sex}</div>
                    <div class="col-1 text-center">${tmp.countOrder}</div>
                    <div class="col-2 text-center">
                        <span class="money-spend-account">${formatCurrencyVND(tmp.sumPrice)}</span>
                    </div> 
                    <div class="col-1 px-0 text-center">
                        <select name="account-role" class="rounded py-1" onchange="handleRoleAccount(this, ${tmp.id})">
                            <option value="0" ${tmp.role == 0 ? "selected" : ""}>Admin</option>
                            <option value="1" ${tmp.role == 1 ? "selected" : ""}>Tài khoản</option>
                            <option value="2" ${tmp.role == 2 ? "selected" : ""}>Shipper</option>
                        </select>
                    </div>
                    <div class="col-1 text-center" style="cursor: pointer">
                        <span onclick="handleLockAccount(this, ${tmp.id})" class="material-symbols-outlined d-inline-block lock">${tmp.lock ? "lock" : "lock_open"}</span>
                    </div>
                </div>
            `
            })
            document.querySelector("#table-body").innerHTML = elm;
        }})
}

search_account();