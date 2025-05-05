package com.qingqiu.xiaozhiyiliao.controller;

import com.qingqiu.xiaozhiyiliao.service.AssistantService;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.output.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: QingQiu
 * @Date: 2025/5/4
 * @Description:
 */
@Slf4j
@RestController
public class ChatController {

  @Resource
  ChatModel chatModel;

  @Resource
  QwenChatModel qwenChatModel;

  @Resource
  AssistantService assistantService;

  @Value("${langchain4j.community.dashscope.chat-model.api-key}")
  String apiKey;

//  @Resource
//  QwenStreamingChatModel qwenStreamingChatModel;

  @GetMapping("/chatModel")
  public String chatModel(@RequestParam(value = "message") String message) {
    return chatModel.chat(message);
  }


  @GetMapping("/chatAssistant")
  public String chatAssistant(@RequestParam(value = "message") String message) {
    return assistantService.chat(message);
  }

  @GetMapping("/chatAssistant2")
  public String chatAssistant2(@RequestParam(value = "message") String message) {
    return assistantService.chat2(message);
  }

  @GetMapping("/chatAssistant3")
  public String chatAssistant3(@RequestParam(value = "message") String message) {
    return assistantService.chat3(message);
  }

  @GetMapping("/chatAssistant4")
  public String chatAssistant4(@RequestParam(value = "message") String message) {
    return assistantService.chat4(message);
  }

  @GetMapping("/chatAssistant5")
  public String chatAssistant5(@RequestParam(value = "area") String area , @RequestParam(value = "message") String message) {
    return assistantService.chat5(area,message);
  }

  @GetMapping("/qWenChatModel")
  public String qWenChatModel(@RequestParam(value = "message") String message) {
    return qwenChatModel.chat(message);
  }

  @GetMapping("/qwenStreamingChatModel")
  public String qwenStreamingChatModel(@RequestParam(value = "message") String message) {
//    return qwenStreamingChatModel.chat(message);
    return null;
  }

  @GetMapping("/generateImg")
  public String generateImg(@RequestParam(value = "prompt") String prompt) {
    WanxImageModel wanxImageModel = WanxImageModel.builder().modelName("wanx2.1-t2i-plus")
        .apiKey(apiKey).build();

    Response<Image> imageResponse = wanxImageModel.generate(prompt);
    log.info("{}",imageResponse.toString());
    return imageResponse.toString();
  }



}
