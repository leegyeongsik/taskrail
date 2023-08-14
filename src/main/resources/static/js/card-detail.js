const Toast = Swal.mixin({
  toast: true,
  position: 'center-center',
  showConfirmButton: false,
  timer: 600,
  timerProgressBar: true,
  didOpen: (toast) => {
    toast.addEventListener('mouseenter', Swal.stopTimer)
    toast.addEventListener('mouseleave', Swal.resumeTimer)
  }
})


$(document).ready(function () {
  // 컬럼 이름 수정
  $(".edit-column-btn").click(function () {
    var columnId = $(this).attr("id");
    console.log(columnId);
    console.log(boardId);
    editColumn(columnId);
  });
});


window.onload = function () {
  getCardData();
}

function getCardData() {
  fetch('/api/cards/' + cardId, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtCookie}`,
      'Content-Type': 'application/json'
    },
  })
  .then(response => {
    if (response.status === 400) {
      alert('요청이 실패했습니다.');
    }
    return response.json(); // JSON 형태의 응답 데이터 파싱
  })
  .then(data => {
    console.log(data);
    let due_date = data.due_Date;
    let title = data.title;
    let content = data.content;
    let cardRoleList = data.cardRoleResponseDtoList;
    let commentList = data.commentList;

    let temp_html = `
                        <div class="row">
                            <div class="col-md-12">
                                <div class="d-flex justify-content-between mb-5">
                                    <h4 class="h4 fw-bold">카드 상세화면</h4>
                                    <div>
                                        <h5 class="h5">만료기한 : </h5>
                                        <h4 class="h4">${due_date}</h4>
                                    </div>
                                </div>
                                <h4 class="h4 my-4">${title}</h4>
                                <div class="border border-2 " style="height: 300px">
                                    <p class="m-3">${content}</p>
                                </div>
                                <div class="btn-group mt-4 d-flex justify-content-around">
                                    <button class="btn btn-dark me-5" onclick="addUser()">작업인원추가</button>
                                    <button class="btn btn-dark me-5" onclick="updateCardPage(cardId)">카드수정</button>
                                    <button class="btn btn-dark me-5" onclick="deleteCard(cardId)">카드삭제</button>
                                    <a class="btn btn-dark" href="javascript:window.history.back();">뒤로가기</a>
                                </div>
                                <div class="mt-5">
                                    <h5 class="h5">작업 인원</h5>
                                    <div class="work-userList">
`;

    let user_role_html = '';
    for (let i = 0; i < cardRoleList.length; i++) {
      let role_user = cardRoleList[i].userName;

      let temp_html2 = `
                                        <span class="me-3">${role_user}</span>
                        `;

      user_role_html += temp_html2;
    }
    let temp_html3 = `
                                    </div>
                                </div>
                                <div class="mt-5">
                                    <h5 class="h5">댓글</h5>
                                    <label for="comment-input"></label>
                                    <textarea class="comment-input form-control col-sm-5 mb-3" id="comment-input" rows="5" placeholder="댓글을 입력하세요"></textarea>
                                    <button class="btn btn-dark mt-3" onclick="createComments(cardId)">작성</button>
                                    <div class="commentList-box mt-5">
`;
    let comment_html = '';
    for (let j = 0; j < commentList.length; j++) {
      let comment_username = commentList[j].userName;
      let comment_content = commentList[j].content;

      let temp_html4 = `
                                        <div class="d-flex justify-content-between">
                                            <span>${comment_username}</span>
                                            <div>
                                                <button class="btn btn-dark" onclick="toggleUpdateComment('comment-content-${commentList[j].id}','comment-input-${commentList[j].id}','comment-update-btn-${commentList[j].id}')" value="${commentList[j].id}">수정</button>
                                                <button class="btn btn-outline-dark" onclick="deleteComments(this)" value="${commentList[j].id}">삭제</button>
                                                <button class="btn btn-dark me-2" style="display: none" id="comment-update-btn-${commentList[j].id}" onclick="updateComments('comment-input-${commentList[j].id}',this)" value="${commentList[j].id}">완료</button>
                                            </div>
                                        </div>
                                        <div class="my-2" style="height: 100px">
                                            <p class="m-3" id="comment-content-${commentList[j].id}">${comment_content}</p>
                                            <textarea class="form-control" rows="3" id="comment-input-${commentList[j].id}" style="display: none">${commentList[j].content}</textarea>
                                        </div>
                    `;
      comment_html += temp_html4;
    }
    let temp_html5 =
        `
                                    </div>
                                </div>
                            </div>
                        </div>
                    `
    let box_html = `${temp_html}${user_role_html}${temp_html3}${comment_html}${temp_html5}`;
    $('.container').append(box_html);
  })
  .catch(error => {
    // 오류 처리 로직을 작성합니다.
    console.error(error);
  });
}

let state = true
function toggleUpdateComment(prevElementId,nextElementId,completeBtn){

  console.log(prevElementId,nextElementId,completeBtn)

  let prev = document.getElementById(prevElementId);
  let next = document.getElementById(nextElementId);
  let btn = document.getElementById(completeBtn);

  if(state){
    prev.style.display = 'none';
    next.style.display = 'inline';
    btn.style.display = 'inline';
    state = false
  }else{
    prev.style.display = 'inline';
    next.style.display = 'none';
    btn.style.display = 'none';
    state = true
  }
}
function createComments(cardId){
  let url = '/api/cards/'+cardId+'/comments';
  let bodyContent = document.getElementById('comment-input').value;
  let formData = {
    content : bodyContent
  }

  fetch(url, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${jwtCookie}`,
      "Content-Type": "application/json"
    },
    body: JSON.stringify(formData)
  })
  .then(response => response.json())
  .then(data => {
    successMsg("댓글 작성 성공");
    window.location.reload();
  })
  .catch(error => {
    errorMsg("댓글 작성 실패");
  });

}

function deleteComments(element) {
  let commentId = element.getAttribute('value');
  let url = '/api/comments/' + commentId

  const confirmDelete = confirm("정말 댓글을 삭제하시겠습니까?");
  if (confirmDelete) {
    fetch(url, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${jwtCookie}`
      },
    })
    .then(response => {
      return response.json(); // JSON 형태의 응답 데이터 파싱
    })
    .then(data => {
      successMsg("댓글 삭제 성공");
      window.location.reload();
    })
    .catch(error => {
      // 오류 처리 로직을 작성합니다.
      errorMsg("댓글 삭제 실패");
    });
  }
}

function updateComments(nextElementId, element) {
  let nextValue = document.getElementById(nextElementId).value
  let commentId = element.getAttribute('value');

  let formData = {
    content: nextValue
  }
  fetch('/api/comments/' + commentId, {
    method: 'PUT',
    headers: {
      'Authorization': `Bearer ${jwtCookie}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formData)
  })
  .then(response => {
    return response.json(); // JSON 형태의 응답 데이터 파싱
  })
  .then(data => {
    successMsg("댓글 수정 성공");
    window.location.reload();
  })
  .catch(error => {
    // 오류 처리 로직을 작성합니다.
    errorMsg("댓글 수정 실패");
  });
}

function updateCardPage(cardId) {
  window.location.href = '/view/cards/' + cardId + '/update';
}

function deleteCard(cardId) {
  const confirmDelete = confirm("정말 카드를 삭제하시겠습니까?");
  if (confirmDelete) {
    let url = '/api/cards/' + cardId;
    fetch(url, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${jwtCookie}`,
      },
    })
    .then(function (response) {
      if (response.ok) {
        successMsg('카드가 성공적으로 삭제되었습니다.');
        history.back();
      } else {
        errorMsg('카드 삭제에 실패했습니다.');
      }
    })
    .catch(function (error) {
      console.log('게시글 삭제 중 오류가 발생했습니다.', error);
    });
  }
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
