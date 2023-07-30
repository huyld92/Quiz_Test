/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.services;

import fa.group.mock.entity.Quiz;
import fa.group.mock.repository.QuizRepository;
import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Duc Huy
 */
@Service
public class QuizService { 
    
     @Autowired
    QuizRepository quizRepository;

    public Quiz save(Quiz quiz) {
        quiz.setTotalQuestion(0);
        quiz.setTotalTime(0);
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Integer quizID) {
        quizRepository.deleteById(quizID);
    }

    public void update(Quiz quiz) {
        quizRepository.update(quiz.getQuizTitle(), quiz.getTotalTime(),
                quiz.getTopic().getTopicID(), quiz.getQuizID());
    }

    public void updateTotalQuestion(int totalQuestion, int quizID) {
        quizRepository.updateTotalQuestion(totalQuestion, quizID);
    }

    public Quiz getById(Integer quizID) {
        return quizRepository.getById(quizID);
    }

//    public Quiz createClone(Quiz quiz) { 
//        return quizRepository.save(quiz);
//    }
    public List<Quiz> findAllByUserID(Integer userID) {
        return quizRepository.findAllByUserID(userID);
    }

    public List<Quiz> searchByTitle(String searchValue) {
        return quizRepository.findAllByTitle(searchValue);
    }

}
