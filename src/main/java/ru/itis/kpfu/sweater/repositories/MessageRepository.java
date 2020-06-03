package ru.itis.kpfu.sweater.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.kpfu.sweater.domains.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
