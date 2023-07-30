/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.repository;

import fa.group.mock.entity.Test;
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
public interface TestRepository extends JpaRepository<Test, Integer> {

    @Transactional
    @Query(value = "SELECT * FROM [dbo].[tblTest] "
            + "WHERE [joinCode] = ?  AND [statusTest] = 'True' ", nativeQuery = true)
    public Test joinTest(String joinCode);

    @Transactional
    @Query(value = "SELECT * FROM [dbo].[tblTest] "
            + "WHERE [userID] = ? ORDER BY statusTest DESC, endTime ASC", nativeQuery = true)
    public List<Test> listAllByUserID(int userID);

    @Transactional
    @Query(value = "SELECT CASE WHEN COUNT(testID) > 0 THEN 'true' ELSE 'false' END FROM [dbo].[tblTest]\n"
            + "  WHERE joinCode = ? AND statusTest = 'True'", nativeQuery = true)
    public boolean existJoinCode(String joinCode);
}
