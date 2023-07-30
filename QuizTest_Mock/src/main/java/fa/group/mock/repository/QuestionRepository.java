/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fa.group.mock.repository;

import fa.group.mock.entity.Question;
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
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE [dbo].[tblQuestion] SET [questionText] = ?\n"
            + " WHERE [questionID] = ? ", nativeQuery = true)
    void update(String questionText,Integer questionID);

}
