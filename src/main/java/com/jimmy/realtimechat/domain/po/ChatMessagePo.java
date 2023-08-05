package com.jimmy.realtimechat.domain.po;

import com.jimmy.realtimechat.config.enums.MessageTypeEnum;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessagePo {

    private MessageTypeEnum type;
    private String content;
    private String sender;

}