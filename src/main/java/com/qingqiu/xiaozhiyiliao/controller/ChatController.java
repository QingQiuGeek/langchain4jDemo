package com.qingqiu.xiaozhiyiliao.controller;

import com.qingqiu.xiaozhiyiliao.service.AssistantService;
import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: QingQiu
 * @Date: 2025/5/4
 * @Description:
 */

@RestController
public class ChatController {

  ChatModel chatModel;

  public ChatController(ChatModel chatModel) {
    this.chatModel = chatModel;
  }

  @Resource
  AssistantService assistantService;

  @GetMapping("/chatModel")
  public String chatModel(@RequestParam String message) {
    return chatModel.chat(message);
  }


  @GetMapping("/chatAssistant")
  public String chatAssistant(@RequestParam String message) {
    return assistantService.chat(message);
  }

}
