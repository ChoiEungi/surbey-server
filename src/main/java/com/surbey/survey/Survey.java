package com.surbey.survey;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Survey {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String title;

    @Lob
    private String purpose;

    private Instant startDate;

    private Instant endDate;

    private String password;


    public Survey(String title, String purpose, Instant startDate, Instant endDate, String password) {
        this(null, title, purpose, startDate, endDate, password);
    }

    private Survey(UUID id, String title, String purpose, Instant startDate, Instant endDate, String password) {
        this.id = id;
        this.title = title;
        this.purpose = purpose;
        this.startDate = startDate;
        this.endDate = endDate;
        this.password = password;
    }
}
