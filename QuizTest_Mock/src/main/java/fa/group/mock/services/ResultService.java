/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.services;

import fa.group.mock.entity.Result;
import fa.group.mock.repository.ResultRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Duc Huy
 */
@Service
public class ResultService {

   @Autowired
    private ResultRepository repository;

    public List<Result> listAllByUserID(int userID) {
        return repository.listAllByUserID(userID);
    }

    public Result getById(Integer resultID) {
        return repository.getById(resultID);
    }
    
    public Result exists(Integer testID, Integer userID) {
        return repository.existsById(testID,userID);
    }

    public Result save(Result result) {
        return repository.save(result);
    }

}
