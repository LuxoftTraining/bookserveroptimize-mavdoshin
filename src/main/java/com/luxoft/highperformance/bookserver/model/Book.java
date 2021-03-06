package com.luxoft.highperformance.bookserver.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(indexes = {@Index(columnList = "title")})
public class Book {
    @Id
    //@GeneratedValue
    private Integer id;
    private String title;

}
