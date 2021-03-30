package com.codesquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Where(clause = "deleted = false")
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @Column(nullable = false)
    @NotEmpty(message = "Title may not be empty")
    @JsonProperty
    private String title;

    @Column(length = 3000)
    @JsonProperty
    private String contents;

    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    @Where(clause = "deleted = false")
    @JsonBackReference
    private List<Answer> answers;

    @JsonProperty
    private Integer answerNum;

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    protected Question() {
    }

    public User getWriter() {
        return writer;
    }

    public String getUserId() {
        return this.writer.getUserId();
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return this.answers;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void increaseAnswerNum() {
        answerNum++;
    }

    public void decreaseAnswerNum() {
        answerNum--;
    }

    public void update(Question updatedQuestion) {
        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
    }
}
