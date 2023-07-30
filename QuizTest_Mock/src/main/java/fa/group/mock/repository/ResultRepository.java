/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fa.group.mock.repository;

import fa.group.mock.entity.Result;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

    
    @Transactional
    @Query(value = "SELECT  * FROM [dbo].[tblResult] "
            + "WHERE [userID] = ? ORDER BY submitTime ASC", nativeQuery = true)
    public List<Result> listAllByUserID(int userID);

    @Query(value = "SELECT * FROM [dbo].[tblResult]"
            + "WHERE testID = ? AND userID = ?", nativeQuery = true)
    public Result existsById(Integer testID, Integer userID);

}
