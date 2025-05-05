package com.qingqiu.xiaozhiyiliao.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: QingQiu
 * @Date: 2025/5/5
 * @Description:
 */

@Configuration
public class MemoryChatAssistantConfig {

  @Bean
  ChatMemory chatMemory(){
    return MessageWindowChatMemory.withMaxMessages(6);
  }

  @Bean
  ChatMemoryProvider chatMemoryProvider(){
//  表示聊天窗口中最多可以存储10条消息。
    return memoryId -> MessageWindowChatMemory.builder().maxMessages(10).id(memoryId).build();
  }
}
