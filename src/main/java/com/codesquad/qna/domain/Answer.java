package com.codesquad.qna.domain;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted = false")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String contents;

    private boolean deleted;

    protected Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return writer.getUserId();
    }

    public String getContents() {
        return contents;
    }

    public void delete() {
        deleted = true;
    }
}

