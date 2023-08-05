package com.jimmy.realtimechat.config;


import com.jimmy.realtimechat.config.enums.MessageTypeEnum;
import com.jimmy.realtimechat.domain.vo.ChatMessageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        // 當有新的 WebSocket 連結建立时調用该方法
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("WebSocket連結建立：" + accessor.getSessionId());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("WebSocket連結斷開：" + accessor.getSessionId());
        String username = (String) accessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconnected: {}", username);
            Object chatMessage = ChatMessageVo.builder()
                    .type(MessageTypeEnum.LEAVE)
                    .sender(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
        System.out.println("WebSocket連結斷開：" + accessor.getSessionId());
    }
}
