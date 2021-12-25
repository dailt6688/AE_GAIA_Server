package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Question extends Model {

    @Id
    public Integer id;
    public String question;
    public String answer;

    public Question(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public static Finder<Integer, Question> find = new Finder<>(Question.class);
}
