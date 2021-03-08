package com.codessquad.qna.domain;

import com.codessquad.qna.util.DateTimeUtils;

import java.time.LocalDateTime;

public class Question {
    private long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getCreatedDate() {
        return DateTimeUtils.formatByPattern(createdDate);
    }

}
