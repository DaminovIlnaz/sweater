package ru.itis.kpfu.sweater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.sweater.domains.Message;
import ru.itis.kpfu.sweater.domains.User;
import ru.itis.kpfu.sweater.repositories.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Iterable<Message> findAll(String tag) {
        if(tag != null && !tag.isEmpty()) {
            return messageRepository.findByTag(tag);
        }
        return messageRepository.findAll();
    }

    @Override
    public Iterable<Message> findAll(){
        return messageRepository.findAll();
    }

    @Override
    public void save(String text, String tag, User user) {
        Message message = Message.builder()
                .tag(tag)
                .text(text)
                .author(user)
                .build();

        messageRepository.save(message);
    }
}
