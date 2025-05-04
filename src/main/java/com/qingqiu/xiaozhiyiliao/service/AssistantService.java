package com.qingqiu.xiaozhiyiliao.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * @Author: QingQiu
 * @Date: 2025/5/4
 * @Description:
 */

@AiService
public interface AssistantService {

  /**
   * SystemMessage相当于制定了这个方法的chat上下文环境，比如指定一个角色
   * @param userMessage
   * @return
   */
  @SystemMessage("你是一个顶级IT开发专家，仅回答IT领域的知识")
  String chat(String userMessage);
}
