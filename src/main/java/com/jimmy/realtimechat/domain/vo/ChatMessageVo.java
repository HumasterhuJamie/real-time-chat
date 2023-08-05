package com.jimmy.realtimechat.domain.vo;

import com.jimmy.realtimechat.config.enums.MessageTypeEnum;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageVo {

    private MessageTypeEnum type;
    private String content;
    private String sender;

}