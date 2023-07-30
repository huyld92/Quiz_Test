/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@Table(name = "tblResult")
@AllArgsConstructor
public class Result implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultID;
    private Integer userID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testID")
    private Test test;
    private Timestamp joinTime;
    private Timestamp submitTime;
    private int countCorrect;
    private double totaMark;
    private boolean statusResult;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tblResult_Answer",
            joinColumns = @JoinColumn(name = "resultID"),
            inverseJoinColumns = @JoinColumn(name = "answerID")
    )
    private List<Answer> answer = new ArrayList<>();

    public Result() {
    }

    public Result(Integer userID, Test test, Timestamp joinTime) {
        this.userID = userID;
        this.test = test;
        this.joinTime = joinTime;
        this.submitTime = null;
        this.countCorrect = 0;
        this.totaMark = 0;
        this.statusResult = false;
    }
}
