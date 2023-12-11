package cs309_dorm_backend.controller;
import java.util.List;

import cn.keking.anti_reptile.annotation.AntiReptile;
import cs309_dorm_backend.domain.Message;
import cs309_dorm_backend.service.message.MessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/message")
@Api(tags = "Message Controller")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @AntiReptile
    @GetMapping("/findAll")
    public List<Message> findAll() {
        return messageService.findAll();
    }
    @AntiReptile
    @GetMapping("/findById/{messageId}")
    public Message findById(@PathVariable int messageId) {
        return messageService.findById(messageId);
    }

    @DeleteMapping("/deleteById/{messageId}")

    public boolean deleteById(@PathVariable int messageId) {
        return messageService.deleteById(messageId);
    }

    @PostMapping("/save")
    public Message addOne(@RequestBody @Valid Message message) {
        return messageService.save(message);
    }

    @PutMapping("/update")
    public Message update(@RequestBody @Valid Message message) {
        return messageService.save(message);
    }


}
