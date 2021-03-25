package com.codesquad.qna.domain;

import com.codesquad.qna.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "deleted = false")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

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

    private LocalDateTime createdDateTime;

    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    @Where(clause = "deleted = false")
    @JsonIgnore
    private List<Answer> answers;

    private boolean deleted;

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
    }

    protected Question() {
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getCreatedTime() {
        return DateTimeUtils.formatByPattern(createdDateTime);
    }

    public List<Answer> getAnswers() {
        return this.answers;
    }

    public int getAnswerNum() {
        return answers.size();
    }

    public void update(Question updatedQuestion) {
        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
    }

    public void delete() {
        deleted = true;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDateTime=" + createdDateTime +
                '}';
    }

}
