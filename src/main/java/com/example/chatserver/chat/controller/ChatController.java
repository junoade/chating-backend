package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.dto.ChatMessageDto;
import com.example.chatserver.chat.dto.ChatRoomListResDto;
import com.example.chatserver.chat.dto.MyChatListResDto;
import com.example.chatserver.chat.dto.PrivateChatRoomResDto;
import com.example.chatserver.chat.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/group")
    public ResponseEntity<?> createGroupRoom(@RequestParam("roomName") String roomName) {
        chatService.createGroupRoom(roomName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/groups")
    public ResponseEntity<?> getGroupChatRooms() {
        List<ChatRoomListResDto> list = chatService.getGroupChatRooms();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @PostMapping("/group/{roomId}/join")
    public ResponseEntity<?> joinGroupChatRoom(@PathVariable("roomId") Long roomId) {
        chatService.joinGroupChat(roomId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/group/{roomId}/history")
    public ResponseEntity<?> getChatHistory(@PathVariable("roomId") Long roomId) {
        List<ChatMessageDto> histories = chatService.getChatHistory(roomId);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    @PostMapping("/{roomId}/read")
    public ResponseEntity<?> readChatRoom(@PathVariable("roomId") Long roomId) {
        chatService.messageRead(roomId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/personal-rooms")
    public ResponseEntity<?> getMyChat() {
        List<MyChatListResDto> dtos = chatService.getMyChatRooms();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @DeleteMapping("/group/{roomId}")
    public ResponseEntity<?> leaveGroupChatRoom(@PathVariable("roomId") Long roomId) {
        chatService.leaveGroupChatRoom(roomId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/private")
    public ResponseEntity<?> getOrCreatePrivateChatRoom(@RequestParam("otherMemberId") Long otherMemberId) {
        Long roomId = chatService.getOrCreatePrivateRoom(otherMemberId);
        PrivateChatRoomResDto dto = PrivateChatRoomResDto.builder()
                .roomId(roomId)
                .build();

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
