package com.erp.hrms.controller.chat;

import com.erp.hrms.Entity.ChatMessage;
import com.erp.hrms.service.ChatService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    /**
     * @Method Name : sendMessage
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서 채팅 발송
     * @param : deptId, message
     * @return
     */
    @MessageMapping("/chat/{deptId}")
    public void sendMessage(@DestinationVariable String deptId,
                            ChatMessage message) {

        message.setDeptId(deptId);

        // 1. DB 저장
        chatService.saveMessage(deptId, message);
        // 2. 실시간 전송
        messagingTemplate.convertAndSend("/sub/chat/" + deptId, message);
    }

    /**
     * @Method Name : chatPage
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서별채팅 view 페이지 이동
     * @param : 
     * @return
     */
    @GetMapping("/chat")
    public String chatPage(Model model, HttpSession session) {

        String deptId = session.getAttribute("deptId").toString();
        String name = session.getAttribute("name").toString();

        System.out.println("deptId = " + deptId);
        System.out.println(name);

        List<ChatMessage> chatHistory = chatService.getChatHistory(deptId);

        model.addAttribute("chatHistory", chatHistory);
        model.addAttribute("deptId", deptId);
        model.addAttribute("name", name);

        return "chat/chat-room";
    }

}