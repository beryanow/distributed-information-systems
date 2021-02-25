package ru.nsu.g.beryanov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NodeDTO {
    private long id;
    private int version;
    private Date timestamp;
    private long uid;
    private String username;
    private long changeset;
    private double latitude;
    private double longitude;
    private List<TagDTO> tags;
}
