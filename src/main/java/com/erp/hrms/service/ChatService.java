package com.erp.hrms.service;

import com.erp.hrms.Entity.ChatMessage;
import com.erp.hrms.Repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    @Cacheable(value = "chat", key = "#deptId")
    public List<ChatMessage> getChatHistory(String deptId) {
        return chatRepository.findTop100ByDeptIdOrderByCreatedDtAsc(deptId);
    }

    @CacheEvict(value = "chat", key = "#deptId")
    public void saveMessage(String deptId, ChatMessage message) {
        chatRepository.save(message);
    }

}
