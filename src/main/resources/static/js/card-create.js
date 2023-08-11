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

// url 에서 칼럼 id 추출
const columnId = window.location.href.split("/")[4];
console.log("columnId: " + columnId);

$(document).ready(function () {
  pageSetting();
});

function pageSetting() {
  let token = Cookies.get('Authorization');
  if (token) { //  토큰이 있을 경우
    // 1. 토큰 만료 여부 판단
    const expired = isTokenExpired(token);
    if (!expired) { // 만료 전
      console.log('Token is still valid.');
      setAuthor(token);
    } else { // 만료되었을 때
      console.log('Token has expired.');
      Cookies.remove('Authorization', {path: '/'});
      requiredLogin();
    }
  } else { // 헤더에 토큰이 없을 경우
    requiredLogin();
  }
}

// 토큰 만료 여부 체크
function isTokenExpired(token) {
  const tokenParts = token.split('.');
  if (tokenParts.length !== 3) {
    return true;
  }
  const payload = JSON.parse(atob(tokenParts[1]));
  if (!payload.exp) {
    return false;
  }
  const currentTimestamp = Math.floor(Date.now() / 1000);
  return payload.exp < currentTimestamp;
}

// 카드 작성자 세팅
function setAuthor(token) {
  const authorSpan = document.getElementById("authorName");
  const payload = JSON.parse(atob(token.split(".")[1]));
  const username = payload.sub;
  if (username === "") {
    Cookies.remove('Authorization', {path: '/'});
    requiredLogin();
  }
  console.log(username);
  authorSpan.innerText = username;
}

function requiredLogin() {
  Swal.fire({
    icon: 'warning',
    title: '로그인 요망',
    text: '로그인 부탁드립니다.',
  }).then(function () {
    window.location.href = "/view/login";
  });
}

// 카드 작성 요청 보내기
function createCard() {
  const title = document.getElementById("card_title").value;
  const content = document.getElementById("card_content").value;
  const color = document.getElementById("color-picker").value;
  let selectedDate = document.getElementById("selectedDate").value;
  const selectedTime = document.getElementById("selectedTime").value;
  selectedDate = selectedDate + "T" + selectedTime + ":00";
  console.log("title : " + title + ", content: " + content
      + ", color: " + color + ", selectedDate: " + selectedDate);

  const requestData = JSON.stringify({
    title: title,
    content: content,
    color: color,
    due_date: selectedDate
  });

  $.ajax({
    type: 'POST',
    url: `/api/columns/${columnId}/cards`,
    headers: {
      "Content-Type": "application/json",
      "Authorization": Cookies.get('Authorization')
    },
    data: requestData,
    success: function () {
      successMsg("카드 작성 성공");
    },
    error: function () {
      errorMsg("카드 작성 실패");
    }
  });
}

function successMsg(msg) {
  Toast.fire({
    icon: 'success',
    title: msg
  }).then(function () {
    window.history.back();
  })
}

function errorMsg(msg) {
  Toast.fire({
    icon: 'error',
    title: msg
  }).then(function () {
    window.location.reload();
  })
}

