package com.jimmy.realtimechat.controller;

import com.jimmy.realtimechat.domain.po.ChatMessagePo;
import com.jimmy.realtimechat.domain.vo.ChatMessageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessagePo sendMessage(@Payload ChatMessageVo ChatMessageVo) {
        ChatMessagePo ChatMessagePo = new ChatMessagePo();
        BeanUtils.copyProperties(ChatMessageVo, ChatMessagePo);
        return ChatMessagePo;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessagePo addUser(@Payload ChatMessageVo ChatMessageVo,SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", ChatMessageVo.getSender());
        ChatMessagePo ChatMessagePo = new ChatMessagePo();
        BeanUtils.copyProperties(ChatMessageVo, ChatMessagePo);
        return ChatMessagePo;
    }
}