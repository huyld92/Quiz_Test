/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.repository;

import fa.group.mock.entity.Quiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE [dbo].[tblQuiz] SET [quizTitle] = ?, [totalTime] = ?, "
            + "topicID = ? WHERE [quizID] = ?", nativeQuery = true)
    void update(String quizName, Integer totalTime, Integer topicID, Integer quizID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE [dbo].[tblQuiz] SET [totalQuestion] = ?"
            + " WHERE [quizID] = ?", nativeQuery = true)
    void updateTotalQuestion(int totalQuestion, Integer quizID);

    @Transactional
    @Query(value = "SELECT * FROM [dbo].[tblQuiz] "
            + " WHERE [userID] = ?", nativeQuery = true)
    List<Quiz> findAllByUserID(Integer userID);

    @Transactional
    @Query(value = "SELECT * FROM [dbo].[tblQuiz]\n"
            + "  WHERE [quizTitle] LIKE %?1%", nativeQuery = true)
    public List<Quiz> findAllByTitle(String searchValue);
}
