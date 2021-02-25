package ru.nsu.g.beryanov.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Builder
@Setter
@Getter
public class NodeDTO {
    private long id;
    private int version;
    private Date timestamp;
    private long uid;
    private String username;
    private long changeset;
    private Double longitude;
    private Double latitude;
    private List<TagDTO> tags;
}
