package ddd.leave.infrastructure.common.event;

import ddd.leave.domain.leave.event.LeaveEvent;
import org.springframework.stereotype.Service;

/**
 * @author apple
 */
@Service
public class EventPublisher {

    public void publish(LeaveEvent event){
        // 发送到 MQ
        // mq.send(event);
    }
}