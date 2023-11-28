package cs309_dorm_backend.service.message;

import cs309_dorm_backend.domain.Message;
import cs309_dorm_backend.dto.MessageDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface MessageService {
    List<Message> findAll();
    Message findById(int messageId) ;
    boolean deleteById(int messageId) ;
    Message save(Message message) ;

    Message addOne(MessageDto messageDto, BindingResult result);

    Message update(MessageDto messageDto, BindingResult result);
}
