package cs309_dorm_backend.controller;

import java.util.List;

import cn.keking.anti_reptile.annotation.AntiReptile;
import cs309_dorm_backend.domain.Message;
import cs309_dorm_backend.dto.MessageDto;
import cs309_dorm_backend.service.message.MessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/message")
@Api(tags = "Message Controller")
public class MessageController {
    @Autowired
    private MessageService messageService;


    // Handling OPTIONS request explicitly

    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @GetMapping("/findAll")
    public List<Message> findAll() {
        return messageService.findAll();
    }

    @GetMapping("/findById/{messageId}")
    public Message findById(@PathVariable int messageId) {
        return messageService.findById(messageId);
    }

    @GetMapping("/findByReceiverId/{receiverId}")
    public List<Message> findByReceiverId(@PathVariable String receiverId) {
        return messageService.findByReceiverId(receiverId);
    }

    @DeleteMapping("/deleteById/{messageId}")
    public boolean deleteById(@PathVariable int messageId) {
        return messageService.deleteById(messageId);
    }

    @DeleteMapping("/deleteByReceiverId/{receiverId}")
    public boolean deleteByReceiverId(@PathVariable String receiverId) {
        return messageService.deleteByReceiverId(receiverId);
    }

    @PostMapping("/addOne")
    public Message addOne(@RequestBody @Valid MessageDto messageDto, BindingResult result) {
        return messageService.addOne(messageDto, result);
    }

    @GetMapping("/read/{messageId}")
    public boolean read(@PathVariable int messageId) {
        return messageService.read(messageId);
    }

//    @PutMapping("/update")
//    public Message update(@RequestBody @Valid Message message) {
//        return messageService.save(message);
//    }


}
