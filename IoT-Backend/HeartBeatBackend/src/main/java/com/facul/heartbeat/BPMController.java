package com.facul.heartbeat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BPMController {

    Beat bpm = new Beat(0);

    @PostMapping("bpm")
    public void postBeat(@RequestBody Beat bpm) {
        System.out.println("Posting " + bpm.getBeat());
        this.bpm = bpm;
        System.out.println("BPM Posted: " + bpm.toString());
    }

/*    @PostMapping("bpms")
    public void postBeats(@RequestBody List<Beat> bpms) {
        this.bpms.addAll(bpms);
        for (Beat beat : bpms) {
            System.out.println(beat.getBeat());
            if (beat.getBeat() > 730) {
                System.out.println("*** BEAT ***");
            }
        }
    }*/

    @GetMapping("bpm")
    public Beat getBeats() {
        System.out.println("Getting Beat");
        return bpm;
    }

    @GetMapping("testando-api")
    public Integer getTeste() {
        return 83;
    }
}
