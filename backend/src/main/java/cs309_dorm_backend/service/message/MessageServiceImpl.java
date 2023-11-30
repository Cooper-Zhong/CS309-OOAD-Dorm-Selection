package cs309_dorm_backend.service.message;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.domain.Message;
import cs309_dorm_backend.dto.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import cs309_dorm_backend.dao.MessageRepo;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepo messageRepo;
    @Override
    public List<Message> findAll() {
        return messageRepo.findAll();
    }

    @Override
    public Message findById(int id) {
        return messageRepo.findById(id).orElse(null);
    }

    @Override
    public boolean deleteById(int id) {
        Optional<Message> message = messageRepo.findById(id);
        if (message.isPresent()) {
            messageRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Message save(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public Message addOne(@Valid MessageDto messageDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new MyException(400, result.getFieldError().getDefaultMessage());
        }
        int messageId = messageDto.getMessageId();
        Message message1 = messageRepo.findById(messageId).orElse(null);
        if (message1 != null) {
            throw new MyException(400, "message already exists");
        }
        String messageContent = messageDto.getMessageContent();
        String messageTime = messageDto.getMessageTime();
        boolean isRead = false;
        int messageOwnerId = messageDto.getMessageOwner();
        int messageSenderId = messageDto.getMessageSenderId();
        return messageRepo.save(new Message(messageId, messageContent, messageTime,messageOwnerId,isRead,messageSenderId));
    }

    @Override
    public Message update(@Valid MessageDto messageDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new MyException(400, result.getFieldError().getDefaultMessage());
        }
        int messageId = messageDto.getMessageId();
        Message message1 = messageRepo.findById(messageId).orElse(null);
        if (message1 == null) {
            throw new MyException(400, "message does not exist");
        }
        String messageContent = messageDto.getMessageContent();
        String messageTime = messageDto.getMessageTime();
        boolean isRead = messageDto.isRead();
        int messageOwnerId = messageDto.getMessageOwner();
        return messageRepo.save(new Message(messageId, messageContent, messageTime,messageOwnerId,isRead,messageDto.getMessageSenderId()));
    }
}
