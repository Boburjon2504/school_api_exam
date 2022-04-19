package com.example.maktabapi_exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String fileOriginalName;//pdp.jpg

    private long size;//1024000

    private String contentType;//image/png

    public Book(final String fileOriginalName, final long size, final String contentType) {
        this.fileOriginalName = fileOriginalName;
        this.size = size;
        this.contentType = contentType;
    }
    private String language;

    @JsonIgnore
    @ManyToOne
    private Class aClass;
}
