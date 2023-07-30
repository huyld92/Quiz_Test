/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.entity;

import java.io.Serializable; 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@Table(name = "tblTest")
@AllArgsConstructor
public class Test implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testID;
    private String testTitle;
    private int userID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizID")
    private Quiz quiz;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private String joinCode;
    private int numOfParticipants; 
    @Column(name = "statusTest",insertable = false, updatable = false) 
    private String statusTest;

    public Test() {
    }
}
