package com.qingqiu.xiaozhiyiliao;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

import com.qingqiu.xiaozhiyiliao.service.AssistantService;
import com.qingqiu.xiaozhiyiliao.store.PersistentChatMemoryStore;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.spi.QwenChatModelBuilderFactory;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.ImageContent.DetailLevel;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.http.client.spring.restclient.SpringRestClientBuilder;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory.Builder;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import jakarta.annotation.Resource;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

/**
 * @Author: QingQiu
 * @Date: 2025/5/4
 * @Description:
 */

@SpringBootTest
@Slf4j
class XiaozhiyiliaoApplicationTest {


  static String apiKey = System.getenv("AI_KEY");

//  OpenAiChatModel的构建依赖于langchain4j框架的HTTP客户端。框架会尝试加载一个默认的HTTP客户端，
//  但由于存在多个实现类（JdkHttpClientBuilderFactory和 SpringRestClientBuilderFactory），框架无法自动选择，因此显式指定客户端
  OpenAiChatModel openaiChatModel = OpenAiChatModel.builder()
      .apiKey(apiKey)
      .httpClientBuilder(new SpringRestClientBuilder())
      .modelName("qwen-plus")
      .timeout(Duration.ofSeconds(60))
      .build();

  @Resource
  ChatMemory chatMemory;

  @Resource
  ChatMemoryProvider chatMemoryProvider;

  @Resource
  QwenChatModel qwenChatModel;

  @Resource
  ChatModel chatModel ;

  @Resource
  AssistantService assistantService;

  @Test
  public void test1(){
    String chat = chatModel.chat("你是谁，讲个故事");
    log.info("{}",chat);
  }


  /**
   * 设置系统聊天环境
   */
  @Test
  public void test2() {
    ChatResponse chatResponse = chatModel.chat(new SystemMessage("你是一个码农，只能回答it行业的知识"));
    log.info("{}",chatResponse.toString());
  }

  /**
   * 向 LLM 发送文本和图像
   */
  @Test
  public void test3(){
    UserMessage userMessage = UserMessage.from(
        TextContent.from("描述这张图片内容"),
        ImageContent.from("https://ts1.tc.mm.bing.net/th/id/OIP-C.dkkJHo-dkLIlvkGnGOCk-QHaHY?w=175&h=185&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2",DetailLevel.HIGH)
    );
    ChatResponse response = chatModel.chat(userMessage);
    log.info("{}",response.toString());
  }

  /**
   * 记忆模式，多轮对话
   */
  @Test
  public void test4(){

    interface Assistant {
      String chat(String message);
    }

    //最大存储6条，包括问和答
//    MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(6).build();

    //    MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
//        .id("1").maxMessages(3).chatMemoryStore(new PersistentChatMemoryStore())
//        .build();

//    每次将新的 ChatMessage 添加到 ChatMemory 时，都会调用 updateMessages（） 方法。
//    在与 LLM 的每次交互期间，这通常发生两次：一次是在添加新的 UserMessage 时，另一次是在添加新的 AiMessage 时。
//    updateMessages（） 方法应更新与给定内存 ID 关联的所有消息。
//    ChatMessage 可以单独存储（例如，每条消息一条记录/行/对象），也可以一起存储（例如，整个 ChatMemory 的一条记录/行/对象）
//    从 ChatMemory 中逐出的消息也将从 ChatMemoryStore 中逐出。当消息被逐出时，将调用 updateMessages（） 方法，其中包含不包含被逐出消息的消息列表
//    chatMemory.add(new UserMessage());

//    每当 ChatMemory 的用户请求所有消息时，都会调用 getMessages（） 方法。这通常在每次与 LLM 交互期间发生一次。Object memoryId 参数的值对应于在创建 ChatMemory 期间指定的 ID。
//    它可用于区分多个用户和/或对话。getMessages（） 方法应返回与给定内存 ID 关联的所有消息。
//    List<ChatMessage> chatMessageList = chatMemory.messages();

//    每当调用 ChatMemory.clear（） 时，都会调用 deleteMessages（） 方法。如果不使用此功能，可以将此方法留空。
//    chatMemory.clear();

    Assistant assistant = AiServices
        .builder(Assistant.class)
        .chatModel(qwenChatModel)
        .chatMemory(chatMemory)
        .build();

    String ans1 = assistant.chat("我是青秋");
    log.info("{}",ans1);

    String ans2 = assistant.chat("我是谁");
    log.info("{}",ans2);

    String ans3 = assistant.chat("你是谁");
    log.info("{}",ans3);

    List<ChatMessage> chatMessageList1 = chatMemory.messages();
    log.info("chatMessageList1：{}",chatMessageList1.toString());

    String ans4 = assistant.chat("我是谁");
    log.info("{}",ans4);

    List<ChatMessage> chatMessageList2 = chatMemory.messages();
    log.info("chatMessageList2：{}",chatMessageList2.toString());

    String ans5 = assistant.chat("我是谁");
    log.info("{}",ans5);

    List<ChatMessage> chatMessageList3 = chatMemory.messages();
    log.info("chatMessageList3：{}",chatMessageList3.toString());


  }


  /**
   * 每个用户聊天内容隔离
   */
  @Test
  public void test5(){

    System.out.println(assistantService.separateChat(1, "我是青秋你记住"));
    System.out.println(assistantService.separateChat(2, "我是猪猪侠你记住"));
    System.out.println(assistantService.separateChat(1, "我是谁，我是猪猪侠吗"));
    System.out.println(assistantService.separateChat(2, "我是谁，我是青秋吗"));

  }


  /**
   * 持久化聊天内容
   */
  @Test
  public void test6(){
    interface Assistant {
      String chat(String message);
    }

    ChatMemory chatMemory = MessageWindowChatMemory.builder()
        .maxMessages(10)
        .chatMemoryStore(new PersistentChatMemoryStore())
        .build();


    Assistant assistant = AiServices.builder(Assistant.class)
        .chatModel(openaiChatModel)
        .chatMemory(chatMemory)
        .build();

    String answer = assistant.chat("Hello! My name is Klaus.");
    System.out.println(answer); // Hello Klaus! How can I assist you today?

    // 然后，注释掉上面的两行，仅运行下面的两行
    // String answerWithName = assistant.chat("What is my name?");
    // System.out.println(answerWithName); // Your name is Klaus.
  }


  /**
   * 为每个用户持久化聊天内容
   */
  @Test
  public void test7(){
    interface Assistant {
      String chat(@MemoryId int memoryId,  String userMessage);
    }

    ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
        .id(memoryId)
        .maxMessages(10)
        .chatMemoryStore(new PersistentChatMemoryStore())
        .build();

    Assistant assistant = AiServices.builder(Assistant.class)
        .chatModel(openaiChatModel)
        .chatMemoryProvider(chatMemoryProvider)
        .build();

    System.out.println(assistant.chat(1, "Hello, my name is Klaus"));
    System.out.println(assistant.chat(2, "Hi, my name is Francine"));

    // 然后，注释掉上面的两行，仅运行下面的两行
    // System.out.println(assistant.chat(1, "What is my name?"));
    // System.out.println(assistant.chat(2, "What is my name?"));
  }

  @Test
  public void test8(){
    AssistantService assistantService = AiServices.create(AssistantService.class, qwenChatModel);
    String ans = assistantService.chat("你是谁");
    log.info("{}",ans);
  }


}
