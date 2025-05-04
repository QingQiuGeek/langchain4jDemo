package com.qingqiu.xiaozhiyiliao.tools;

import com.qingqiu.xiaozhiyiliao.service.AssistantService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

/**
 * @Author: QingQiu
 * @Date: 2025/5/4
 * @Description:
 */

@Component
public class BookingTools {

  private final AssistantService assistantService;

  public BookingTools(AssistantService assistantService) {
    this.assistantService = assistantService;
  }

  @Tool
  public Booking getBookingDetails(String bookingNumber, String customerName, String customerSurname) {
    return assistantService.getBookingDetails(bookingNumber, customerName, customerSurname);
  }

  @Tool
  public void cancelBooking(String bookingNumber, String customerName, String customerSurname) {
    assistantService.cancelBooking(bookingNumber, customerName, customerSurname);
  }
}
