package com.codesquad.qna.domain;

import com.codesquad.qna.util.DateTimeUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    @NotEmpty(message = "Title may not be empty")
    private String title;

    @Column(length = 3000)
    private String contents;
    private LocalDateTime createdDateTime;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
    }

    protected Question() {
    }

    public long getId() {
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
