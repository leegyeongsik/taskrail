
// 비밀번호 내용 표시 및 숨기기
const removePasswordInput = document.getElementById('removePassword');
const oldPasswordInput = document.getElementById('oldPassword');
const newPasswordInput = document.getElementById('newPassword');
const showRemovePwBtn = document.getElementById('showRemovePwBtn');
const showOldPwBtn = document.getElementById('showOldPwBtn');
const showNewPwBtn = document.getElementById('showNewPwBtn');

showRemovePwBtn.addEventListener('click', (event) => {
  event.preventDefault();

  if (removePasswordInput.type === 'password') {
    removePasswordInput.type = 'text';
    //removePasswordInput.classList.add('visible'); // Apply the 'visible' class
  } else {
    removePasswordInput.type = 'password';
    //removePasswordInput.classList.remove('visible'); // Remove the 'visible' class
  }
});

showOldPwBtn.addEventListener('click', (event) => {
  event.preventDefault();

  if (oldPasswordInput.type === 'password') {
    oldPasswordInput.type = 'text';
    //removePasswordInput.classList.add('visible'); // Apply the 'visible' class
  } else {
    oldPasswordInput.type = 'password';
    //removePasswordInput.classList.remove('visible'); // Remove the 'visible' class
  }
});

showNewPwBtn.addEventListener('click', (event) => {
  event.preventDefault();

  if (newPasswordInput.type === 'password') {
    newPasswordInput.type = 'text';
    //removePasswordInput.classList.add('visible'); // Apply the 'visible' class
  } else {
    newPasswordInput.type = 'password';
    //removePasswordInput.classList.remove('visible'); // Remove the 'visible' class
  }
});



const updateBtn = document.getElementById("update");
const removeBtn = document.getElementById("remove");
const fistForm = document.getElementById("form1");
const secondForm = document.getElementById("form2");
const container = document.querySelector(".container");

updateBtn.addEventListener("click", () => {
  container.classList.remove("right-panel-active");
});

removeBtn.addEventListener("click", () => {
  container.classList.add("right-panel-active");
});

fistForm.addEventListener("submit", (e) => e.preventDefault());
secondForm.addEventListener("submit", (e) => e.preventDefault());




// alert 창 표시를 위한 변수 선언
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

$(document).ready(function () {

  if (token) { //  헤더에 토큰이 있을 경우
    // 1. 토큰 만료 여부 판단
    const expired = isTokenExpired(token);
    if (!expired) { // 만료 전
      console.log('Token is still valid.');
      // 2. 사용자 프로필 정보를 가져옴.
      getUser(token);
      // 3. ajax 통신을 위해 헤더에 토큰 setting
      setToken(token);
    } else { // 만료되었을 때
      console.log('Token has expired.');
      removeToken();
    }
  } else { // 헤더에 토큰이 없을 경우
    Swal.fire({
      icon: 'warning',
      title: '로그인 요망',
      text: '로그인 후에 요청 부탁드립니다.',
    }).then(function () {
      window.location.href = "/login";
    })
  }
});

function setToken(token) {
  $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
    jqXHR.setRequestHeader('Authorization', token);
  });
}

function getUser(token) {
  // JWT 토큰 디코딩하여 페이로드 추출
  const payload = JSON.parse(atob(token.split(".")[1]));
  // 예시 {sub: 'sally', exp: 1689745728, iat: 1689742128}
  const username = payload.sub;

  if (username === "") { // 페이로드 추출이 안될 경우
    removeToken();
  } else {
    document.getElementById("updateUsername").value = username;
    document.getElementById("removeUsername").value = username;
  }
}

function isTokenExpired(token) {
  const tokenParts = token.split('.');

  if (tokenParts.length !== 3) {
    // Invalid token format
    return true;
  }

  const payload = JSON.parse(atob(tokenParts[1]));
  if (!payload.exp) {
    // Token doesn't have an expiration claim
    return false;
  }

  const currentTimestamp = Math.floor(Date.now() / 1000);
  return payload.exp < currentTimestamp;
}

function removeToken() {
  Cookies.remove('Authorization', {path: '/'});
  Swal.fire({
    icon: 'warning',
    title: '로그인 요망',
    text: '인증이 만료되어 재로그인 부탁드립니다.',
  }).then(function () {
    window.location.href = "/main";
  })
}

function updateUser() {
  let email = document.getElementById('email').value;
  let oldPassword = document.getElementById('oldPassword').value;
  let newPassword = document.getElementById('newPassword').value;

  const RegExp2 = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
  const num = newPassword.search(/[0-9]/g);
  const eng = newPassword.search(/[a-z]/ig);
  const spe = newPassword.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

  if (newPassword.length < 8 || newPassword.length > 20) {
    Swal.fire({
      icon: 'warning',
      title: '비밀번호 입력 오류',
      text: '8자리 ~ 20자리 이내로 입력해주세요.',
    });
    $('#newPassword').focus();
    return false;
  }

  if (newPassword.search(/\s/) != -1) {
    Swal.fire({
      icon: 'warning',
      title: '비밀번호 입력 오류',
      text: '비밀번호는 공백 없이 입력해주세요.',
    });
    $('#newPassword').focus();
    return false;
  }

  if (num < 0 || eng < 0 || spe < 0) {
    Swal.fire({
      icon: 'warning',
      title: '비밀번호 입력 오류',
      text: '영문,숫자, 특수문자를 혼합하여 입력해주세요.',
    });
    $('#newPassword').focus();
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
    type: "PUT",
    url: `/api/users`,
    contentType: "application/json",
    data: JSON.stringify({
      password: oldPassword,
      newPassword : newPassword,
      email: email
    }),
  })
  .done(function (res, status, xhr) {
    Toast.fire({
      icon: 'success',
      title: '수정에 성공하였습니다.'
    }).then(function () {
      window.location.href = "/main";
    })
  })
  .fail(function (jqXHR, textStatus, error) {
    Toast.fire({
      icon: 'error',
      title: '수정에 실패하였습니다.'
    }).then(function () {
      window.location.reload();
    })
  });
}

function removeUser() {
  let removePassword = document.getElementById('removePassword').value;

  if (removePassword.search(/\s/) != -1) {
    Swal.fire({
      icon: 'warning',
      title: '비밀번호 입력 오류',
      text: '비밀번호는 공백 없이 입력해주세요.',
    });
    $('#removePassword').focus();
    return false;
  }

  $.ajax({
    type: "DELETE",
    url: `/api/users`,
    contentType: "application/json",
    data: JSON.stringify({
      password: removePassword
    }),
  })
  .done(function (res, status, xhr) {
    Toast.fire({
      icon: 'success',
      title: '회원을 탈퇴하였습니다.'
    }).then(function () {
      Cookies.remove('Authorization', {path: '/'});
      window.location.href="/main";
    })
  })
  .fail(function (jqXHR, textStatus, error) {
    Toast.fire({
      icon: 'error',
      title: '탈퇴를 실패하였습니다.'
    }).then(function () {
      window.location.reload();
    })
  });
}
