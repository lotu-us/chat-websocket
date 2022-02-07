//로그인 기능 대체
const userId = prompt("아이디를 입력해주세요 : ");
const username = prompt("이름을 입력해주세요 : ");
fetch(`http://localhost:8080/login/${userId}/${username}`, {
    method: "post"
})
.then((response) => {
    console.log("ok");
    connect();
});



let ws = new WebSocket(`ws://${location.host}/chating/${userId}`);
console.log(ws);

function connect(){
    ws.onopen = function() {
        console.log("connection open");
    };

    ws.onmessage = function(event) {
        //console.log(event);
        //MessageEvent {isTrusted: true, data: 'c5cae86e-2f66-ab87-53c1-f8cdfb840873 : ㅇㅇ', origin: 'ws://localhost:8080', lastEventId: '', source: null, ....}
        let data = event.data;
        console.log("receive data : " + data);

        let chars = data.split("#,");
        let receiver = chars[0];
        let message = chars[1];

        if(receiver != userId){
            receive(receiver, message);
        }

        //스크롤 맨 아래로
        document.querySelector(".msg_history").scrollTop = document.querySelector(".msg_history").scrollHeight;
    };

    ws.onclose = function(event) {
        console.log("connection closed");   //서버 종료 시
        setTimeout(function(){ connect(); }, 1000); //연결이 끊기면 1초 뒤 다시 연결시도한다
    };

    ws.onerror = function(err) {
        console.log("err : " + err);
    };
}




$("button.msg_send_btn").on("click", function(event){
    send();
});

$("input.write_msg").on("keyup", function(event){
    if(event.keyCode == 13){
        send();
    }
});


function send(){
    let message = $("input.write_msg").val();
    console.log("send message : "+ message);
    ws.send(message);   //메시지 서버로 전송

    $(".msg_history").append(`
        <div class="outgoing_msg">
          <div class="sent_msg">
              <p>${message}</p>
              <span class="time_date"> 11:01 AM    |    Today</span>
          </div>
        </div>
    `);
}

function receive(receiver, message){
    $(".msg_history").append(`
        <div class="incoming_msg">
            <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
            <div class="received_msg">
                <div class="receiver">${receiver}</div>
                <div class="received_withd_msg">
                    <p>${message}</p>
                    <span class="time_date"> 11:01 AM    |    Today</span></div>
            </div>
        </div>
    `);

}