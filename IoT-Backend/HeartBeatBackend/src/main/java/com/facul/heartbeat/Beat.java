package com.facul.heartbeat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;

public class Beat {

    private Integer beat;

    public Beat() {
    }

    public Beat(Integer beat) {
        this.beat = beat;
    }

    public Integer getBeat() {
        return beat;
    }

    public void setBeat(Integer beat) {
        this.beat = beat;
    }

    @Override
    public String toString() {
        return "Beat{" +
                "beat=" + beat +
                '}';
    }
}
