let ws = new WebSocket("ws://localhost:8080/chating");
console.log(ws);
connect();

function connect(){
    ws.onopen = function() {
        console.log("connection open");
    };

    ws.onmessage = function(event) {
        console.log("onmessage" + event);
    };

    ws.onclose = function(event) {
        console.log("connection closed");
        setTimeout(function(){ connect(); }, 1000); //연결이 끊기면 1초 뒤 다시 연결시도한다
    };

    ws.onerror = function(err) {
        console.log("err : " + err);
    };
}


function send(){
    let message = $("input.write_msg").val();
    ws.send(message);   //메시지 서버로 전송
    console.log("send message" : message);
}



$("button.msg_send_btn").on("click", function(event){
    send();
});

$("input.write_msg").on("keyup", function(event){
    if(event.keyCode == 13){
        send();
    }
});