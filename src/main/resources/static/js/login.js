// 비밀번호 내용 표시 및 숨기기
const passwordInput = document.getElementById('password');
const loginPasswordInput = document.getElementById('loginPassword');
const showSignupPwBtn = document.getElementById('showSignupPwBtn');
const showLoginPwBtn = document.getElementById('showLoginPwBtn');

showSignupPwBtn.addEventListener('click', (event) => {
    event.preventDefault();
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
    } else {
        passwordInput.type = 'password';
    }
});

showLoginPwBtn.addEventListener('click', (event) => {
    event.preventDefault();
    if (loginPasswordInput.type === 'password') {
        loginPasswordInput.type = 'text';
    } else {
        loginPasswordInput.type = 'password';
    }
});


const logInBtn = document.getElementById("logIn");
const signUpBtn = document.getElementById("signUp");
const fistForm = document.getElementById("form1");
const secondForm = document.getElementById("form2");
const container = document.querySelector(".container");

logInBtn.addEventListener("click", () => {
    container.classList.remove("right-panel-active");
});

signUpBtn.addEventListener("click", () => {
    container.classList.add("right-panel-active");
});

fistForm.addEventListener("submit", (e) => e.preventDefault());
secondForm.addEventListener("submit", (e) => e.preventDefault());


const Toast = Swal.mixin({
    toast: true,
    position: 'center-center',
    showConfirmButton: false,
    timer: 2000,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})


// 쿠키값 가져오기
let token = Cookies.get('Authorization');

function signup() {
    let name = $('#username').val();
    let password = $('#password').val();
    let email = $('#email').val();
    const RegExp2 = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
    const RegExp3 = /^[a-zA-Z0-9]{4,10}$/;
    const num = password.search(/[0-9]/g);
    const eng = password.search(/[a-z]/ig);
    const spe = password.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

    if (name == "") {
        Swal.fire({
            icon: 'warning',
            title: '아이디 입력 오류',
            text: '아이디가 공백입니다. 문자 입력해주세요.',
        });
        $('#username').focus();
        return false;
    }

    if (!RegExp3.test(name)) {
        Swal.fire({
            icon: 'warning',
            title: '아이디 입력 오류',
            text: '최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9) 로 구성되어야 합니다.',
        });
        $('#username').focus();
        return false;
    }

    if (password.length < 8 || password.length > 20) {
        Swal.fire({
            icon: 'warning',
            title: '비밀번호 입력 오류',
            text: '8자리 ~ 20자리 이내로 입력해주세요.',
        });
        $('#password').focus();
        return false;
    }

    if (password.search(/\s/) != -1) {
        Swal.fire({
            icon: 'warning',
            title: '비밀번호 입력 오류',
            text: '비밀번호는 공백 없이 입력해주세요.',
        });
        $('#password').focus();
        return false;
    }

    if (num < 0 || eng < 0 || spe < 0) {
        Swal.fire({
            icon: 'warning',
            title: '비밀번호 입력 오류',
            text: '영문,숫자, 특수문자를 혼합하여 입력해주세요.',
        });
        $('#password').focus();
        return false;
    }

    if (email == "") {
        Swal.fire({
            icon: 'warning',
            title: '이메일 입력 오류',
            text: '이메일이 공백입니다. 문자 입력해주세요.',
        });
        $('#email').focus();
        return false;
    }

    if (!RegExp2.test(email)) {
        Swal.fire({
            icon: 'warning',
            title: '이메일 입력 오류',
            text: '이메일 형식에 맞게 입력해주세요.',
        });
        $('#email').focus();
        return false;
    }


    $.ajax({
        type: "POST",
        url: `/api/users/signup`,
        contentType: "application/json",
        data: JSON.stringify({
            name: name, password: password, email: email
        }),
    })
        .done(function (res, status, xhr) {
            Toast.fire({
                icon: 'success',
                title: '회원가입에 성공하셨습니다.'
            }).then(function () {
                window.location.reload();
            })
        })
        .fail(function (jqXHR, textStatus, error) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(error);
            Toast.fire({
                icon: 'error',
                title: '회원가입에 실패하였습니다.'
            })
        });
}

function onLogin() {
    let loginUsername = $('#loginUsername').val();
    let loginPassword = $('#loginPassword').val();

    $.ajax({
        type: "POST",
        url: `/api/users/login`,
        contentType: "application/json",
        data: JSON.stringify({name: loginUsername, password: loginPassword}),
    })
        .done(function (res, status, xhr) {
            const token = xhr.getResponseHeader('Authorization');

            Cookies.set('Authorization', token, {path: '/'})

            $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
                jqXHR.setRequestHeader('Authorization', token);
            });

            Toast.fire({
                icon: 'success',
                title: loginUsername + '님 환영합니다!'
            }).then(function () {
                window.location.href = "/main"
            })
        })
        .fail(function (jqXHR, textStatus) {
            Toast.fire({
                icon: 'warning',
                title: '가입한 내역 여부 혹은 \n 로그인 정보를 확인부탁드립니다.'
            }).then(function () {
                window.location.reload();
            })
        });
}
