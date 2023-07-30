/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.services;

import fa.group.mock.entity.Topic;
import fa.group.mock.repository.TopicRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Duc Huy
 */
@Service
public class TopicService {
    
    @Autowired
    TopicRepository topicRepository;
    public List<Topic> listAllTopic() {
        return topicRepository.findAll();
    }
    
}
