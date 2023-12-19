package cs309_dorm_backend.service.message;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.domain.Message;
import cs309_dorm_backend.domain.User;
import cs309_dorm_backend.dto.MessageDto;
import cs309_dorm_backend.service.user.UserService;
import cs309_dorm_backend.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import cs309_dorm_backend.dao.MessageRepo;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserService userService;

    @Override
    public List<Message> findAll() {
        return messageRepo.findAll();
    }

    @Override
    public Message findById(int id) {
        return messageRepo.findById(id).orElse(null);
    }

    @Override
    public List<Message> findByReceiverId(String receiverId) {
        return messageRepo.findByReceiverId(receiverId);
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        try {
            messageRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new MyException(4, "message " + id + " does not exist");
        }
    }

    @Override
    @Transactional
    public boolean deleteByReceiverId(String receiverId) {
        try {
            messageRepo.deleteByReceiverId(receiverId);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new MyException(4, "User " + receiverId + " does not exist");
        }
    }

    @Override
    public Message save(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public Message addOne(@Valid MessageDto messageDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new MyException(4, result.getFieldError().getDefaultMessage());
        }
        String receiverId = messageDto.getReceiverId();
        // send message via websocket, if receiver is online
        WebSocketServer.sendData(messageDto.getContent(), receiverId);

        return save(convertToMessage(messageDto));
    }

    @Override
    public boolean read(int messageId) {
        try {
            Message message = findById(messageId);
            message.setRead(true);
            save(message);
            return true;
        } catch (Exception e) {
            throw new MyException(4, "message " + messageId + " does not exist");
        }
    }

//    @Override
//    public Message update(MessageDto messageDto, BindingResult result) {
//        return null;
//    }

//    @Override
//    public Message update(@Valid MessageUpdateDto messageUpdateDto, BindingResult result) {
//        if (result.hasErrors()) {
//            throw new MyException(400, result.getFieldError().getDefaultMessage());
//        }
//        Message message = findById(messageUpdateDto.getMessageId());
//        if (message == null) {
//            throw new MyException(4, "message " + messageUpdateDto.getMessageId() + " does not exist");
//        }
//        message.setMessageContent(messageUpdateDto.getMessageContent());
//        message.setMessageTitle(messageUpdateDto.getMessageTitle());
//        message.setRead(messageUpdateDto.isRead());
//        return save(message);
//    }


    private Message convertToMessage(MessageDto messageDto) {
        String messageContent = messageDto.getContent();
        if (messageContent == null || messageContent.length() == 0) {
            throw new MyException(4, "message content cannot be empty");
        }
        String receiverId = messageDto.getReceiverId();
        String senderId = messageDto.getSenderId();
        String senderName = messageDto.getSenderName();
        User receiver = userService.findByCampusId(receiverId);
        if (receiver == null) {
            throw new MyException(4, "User " + receiverId + " does not exist");
        }
        User sender = userService.findByCampusId(senderId);
        if (sender == null) {
            throw new MyException(4, "User " + senderId + " does not exist");
        }
        return Message.builder()
                .content(messageContent)
                .time(new Timestamp(System.currentTimeMillis()))
                .receiver(receiver)
                .read(false)
                .sender(sender)
                .build();
    }
}
