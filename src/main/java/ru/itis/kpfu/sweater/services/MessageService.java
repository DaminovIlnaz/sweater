package ru.itis.kpfu.sweater.services;

import ru.itis.kpfu.sweater.domains.Message;
import ru.itis.kpfu.sweater.domains.User;

public interface MessageService {
    Iterable<Message> findAll(String tag);
    Iterable<Message> findAll();

    void save(Message message, User user);
}
