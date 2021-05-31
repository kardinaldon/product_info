package model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "language")
@Data
public class Language {

    @Id
    @Column(name = "language_id")
    private String languageId;
}
