package com.beehive.domain.action.hiver;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "hiver")

public class Hiver extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Long apriaryId;

    @NotBlank
    private Long hiverTypeId;

    @NotBlank
    private Long beeQueenId;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    private Integer framesNumber;

    public Hiver(Long apriaryId, Long hiverTypeId, Long beeQueenId, String name, Integer framesNumber) {
        this.name = name;
        this.apriaryId = apriaryId;
        this.hiverTypeId = hiverTypeId;
        this.beeQueenId = beeQueenId;
        this.framesNumber = framesNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getApriaryId() {
        return apriaryId;
    }

    public void setApriaryId(Long apriaryId) { this.apriaryId = apriaryId; }

    public Long getHiverTypeId() {
        return hiverTypeId;
    }

    public void setHiverTypeId(Long typeId) { this.hiverTypeId = typeId; }

    public Long getBeeQueenId() {
        return beeQueenId;
    }

    public void setBeeQueenId(Long beeQueenId) { this.beeQueenId = beeQueenId; }

    public Integer getFramesNumber() {
        return framesNumber;
    }

    public void setFramesNumber(Integer framesNumber) { this.framesNumber = framesNumber; }

}
