/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.services;

import fa.group.mock.entity.Test;
import fa.group.mock.repository.TestRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Duc Huy
 */
@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    public Test save(Test test) {
        return testRepository.save(test);
    }

    public Test joinTest(String joinCode) {
        return testRepository.joinTest(joinCode);
    }

    public List<Test> listAllByUserID(int userID) {
        return testRepository.listAllByUserID(userID);
    }

    public boolean existJoinCode(String joinCode) {
        return testRepository.existJoinCode(joinCode);
    }

}
