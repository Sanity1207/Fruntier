package com.fruntier.fruntier.message;

import com.fruntier.fruntier.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<List<Message>> findByReceiver(User user);
    Optional<List<Message>> findBySender(User user);

    List<Message> findByReceiverUsernameAndSenderUsername(String receiverUsername, String senderUsername);

}
