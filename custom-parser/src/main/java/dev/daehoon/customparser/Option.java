package dev.daehoon.customparser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Option {

    private Integer maxLength;

    private Integer max;

    private Integer min;

    private Integer interval;

    private Boolean hasTime;
}
