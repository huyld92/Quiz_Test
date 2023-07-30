/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.services;

import fa.group.mock.entity.Answer;
import fa.group.mock.repository.AnswerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Duc Huy
 */
@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public void update(List<Answer> answers) {
        for (Answer answer : answers) {
            answerRepository.update(answer.getAnswerText(), answer.isCorrect(),
                    answer.getAnswerID());
        }
    }

}
