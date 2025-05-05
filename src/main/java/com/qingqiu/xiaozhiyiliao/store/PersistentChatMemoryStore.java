package com.qingqiu.xiaozhiyiliao.store;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * @Author: QingQiu
 * @Date: 2025/5/4
 * @Description: 默认情况下，ChatMemory 实现将 ChatMessage 存储在内存中。
 * 如果需要持久性，则可以实现自定义 ChatMemoryStore 以将 ChatMessage 存储在任何持久性存储中
 */

@Component
public class PersistentChatMemoryStore implements ChatMemoryStore {


//  private final DB db = DBMaker.fileDB("chat-memory.db").transactionEnable().make();
//  private final Map<Integer, String> map = db.hashMap("messages", INTEGER, STRING).createOrOpen();
//  private final Map<String, String> map = db.hashMap("messages", STRING, STRING).createOrOpen();

  @Override
  public List<ChatMessage> getMessages(Object memoryId) {
    // Implement getting all messages from the persistent store by memory ID.
    // ChatMessageDeserializer.messageFromJson(String) and
    // ChatMessageDeserializer.messagesFromJson(String) helper methods can be used to
    // easily deserialize chat messages from JSON.

//    String json = map.get((String) memoryId);
//    return messagesFromJson(json);
    return null;
  }

  @Override
  public void updateMessages(Object memoryId, List<ChatMessage> messages) {
    // Implement updating all messages in the persistent store by memory ID.
    // ChatMessageSerializer.messageToJson(ChatMessage) and
    // ChatMessageSerializer.messagesToJson(List<ChatMessage>) helper methods can be used to
    // easily serialize chat messages into JSON.

//    String json = messagesToJson(messages);
//    map.put((String) memoryId, json);
//    db.commit();
  }

  @Override
  public void deleteMessages(Object memoryId) {
    // Implement deleting all messages in the persistent store by memory ID.

//    map.remove((String) memoryId);
//    db.commit();
  }
}
