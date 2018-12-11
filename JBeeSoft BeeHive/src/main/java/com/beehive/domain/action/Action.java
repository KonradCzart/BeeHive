package com.beehive.domain.action;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "action")

public class Action extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Long concreteActionId;

    @NotBlank
    private Long apiaryId;

    @NotBlank
    private Long hiverId;

    @NotBlank
    private Date date;

    @NotBlank
    @Size(max = 40)
    private String weatherCondtion;

    public Action(Long concreteActionId, Long apiaryId, Long hiverId, String weatherCondtion) {
        this.concreteActionId = concreteActionId;
        this.apiaryId = apiaryId;
        this.hiverId = hiverId;
        this.weatherCondtion = weatherCondtion;
    }

    public Long getId() {
        return id;
    }

    public void setConcreteActionId(Long concreteActionId) {
        this.concreteActionId = concreteActionId;
    }

    public Long getConcreteActionId() {
        return concreteActionId;
    }

    public Long getApiaryId() {
        return apiaryId;
    }

    public Long getHiverId() {
        return hiverId;
    }

    public String getWeatherCondtion() {
        return weatherCondtion;
    }

    public void setWeatherCondtion(String weatherCondtion) { this.weatherCondtion = weatherCondtion; }
}
