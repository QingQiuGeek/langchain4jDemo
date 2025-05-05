package com.qingqiu.xiaozhiyiliao.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.UserName;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * @Author: QingQiu
 * @Date: 2025/5/4
 * @Description:
 */

//因为在配置文件中同时配置了多个大语言模型，所以需要在这里明确指定（EXPLICIT）模型的beanName （qwenChatModel）
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,chatModel = "qwenChatModel" ,chatMemory = "chatMemory" , chatMemoryProvider = "chatMemoryProvider")
public interface AssistantService {

  /**
   * @SystemMessage 设定角色，塑造AI助手的专业身份，明确助手的能力范围,@SystemMessage 的内容将在后台转换为 SystemMessage 对象，并与 UserMessage 一起发送给大语
   * 言模型（LLM）。SystemMessaged的内容只会发给大模型一次，如果修改了SystemMessage的内容，新的SystemMessage会被发送给大模型，之前的聊天记忆会失效。
   *
   * @param userMessage
   * @return
   */
  @SystemMessage("你是一个顶级IT开发专家，仅回答IT领域的知识")
  String chat(String userMessage);

  /**
   * @return
   */
  @SystemMessage("你是一个中二少年")
  String chat4( String userMessage);

  /**
   * @SystemMessage 注解还可以从资源中加载提示模板
   * @param userMessage
   * @return
   */
  @SystemMessage(fromResource = "prompt1.txt")
  String chat2(String userMessage);


  /**
   * @UserMessage 用户提示词
   * @param userMessage
   * @return
   */
  @UserMessage("你是我的好哥们，精通各种医术，可以起死回生")
  String chat3(String userMessage);


  /**
   * @V 明确指定传递的参数名称
   * @param userMessage
   * @return
   */
  @UserMessage("你是我的好朋友，请用{{area}}话回答问题，并且添加一些表情符号。")
  String chat5(@V("area") String area ,@V("message") String userMessage);

  String separateChat(@MemoryId int memoryId ,@UserMessage String userMessage);

}
