package ru.itis.kpfu.sweater.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.kpfu.sweater.domains.Message;
import ru.itis.kpfu.sweater.domains.User;
import ru.itis.kpfu.sweater.services.MessageService;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public String greeting(Map<String,Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false) String tag, Map<String, Object> model){
        Iterable<Message> messages = messageService.findAll(tag);
        model.put("messages", messages);
        model.put("tag", tag);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user, @RequestParam String text, @RequestParam String tag, Map<String, Object> model){
        messageService.save(text, tag, user);
        Iterable<Message> messages = messageService.findAll();
        model.put("messages", messages);
        return "main";
    }

}
