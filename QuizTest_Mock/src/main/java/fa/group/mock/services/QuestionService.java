/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.services;

import fa.group.mock.entity.Question;
import fa.group.mock.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Duc Huy
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestionById(Integer questionID) {
        questionRepository.deleteById(questionID);
    }

    public Question getByID(Integer questionID) {
        return questionRepository.getById(questionID);
    }

    public void update(Question question) {
         questionRepository.update(question.getQuestionText(), question.getQuestionID());
    }
}
