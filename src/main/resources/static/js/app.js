'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim(); // 獲取輸入的用戶名

    if(username) {
        usernamePage.classList.add('hidden'); // 隱藏用戶名輸入界面
        chatPage.classList.remove('hidden'); // 顯示聊天界面

        var socket = new SockJS('/websocket'); // 創建 SockJS 物件，連接到 WebSocket 伺服器
        stompClient = Stomp.over(socket); // 使用 Stomp 來進行 WebSocket 通信

        stompClient.connect({}, onConnected, onError); // 進行 WebSocket 連接，並指定連接成功和失敗時的回調函式
    }
    event.preventDefault(); // 防止表單的預設提交行為
}



function onConnected() {
    // 訂閱 Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

     // 告訴伺服器你的用戶名
    stompClient.send("/app/chat.addUser",{},JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault(); //防止事件的預設行為發生。在表單提交事件的情況下，預設行為是瀏覽器提交表單並重新載入頁面。通常，你可能希望使用 JavaScript 來處理表單的提交，而不希望瀏覽器自動處理。
}



usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)