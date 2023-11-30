package cs309_dorm_backend.service.message;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.domain.Message;
import cs309_dorm_backend.domain.User;
import cs309_dorm_backend.dto.MessageDto;
import cs309_dorm_backend.dto.MessageUpdateDto;
import cs309_dorm_backend.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        return save(convertToMessage(messageDto));
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
        String messageContent = messageDto.getMessageContent();
//        boolean isRead = messageDto.isRead();
        String receiverId = messageDto.getMessageReceiverId();
        User receiver = userService.findByCampusId(receiverId);
        if (receiver == null) {
            throw new MyException(4, "User " + receiverId + " does not exist");
        }
        return Message.builder()
                .messageTitle(messageDto.getMessageTitle())
                .messageContent(messageContent)
                .messageTime(new Timestamp(System.currentTimeMillis()))
                .receiver(receiver)
                .isRead(false)
                .build();
    }
}
