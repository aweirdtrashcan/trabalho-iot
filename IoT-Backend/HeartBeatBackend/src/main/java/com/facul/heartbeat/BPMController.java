package com.facul.heartbeat;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@RestController
public class BPMController {

    ArrayList<Beat> recentBeats = new ArrayList<>();
    Beat lastBeat = new Beat(0);
    Thread saveBeatsThread = null;

    @PostConstruct
    void startSaveBeatsThread() {
        if (saveBeatsThread == null) {
            saveBeatsThread = new Thread(() -> {
                try {
                    while (true) {
                        StringBuilder beats = new StringBuilder();
                        String filePath = System.getProperty("user.dir") + "\\batimento.txt";

                        try {
                            InputStream inputStream = new FileInputStream(filePath);
                            byte[] fileBytes = inputStream.readAllBytes();
                            beats.append(new String(fileBytes));
                            inputStream.close();
                        } catch (FileNotFoundException ignored) {}

                        for (Beat beat : recentBeats) {
                            beats.append(beat).append("\n");
                        }

                        OutputStream outputStream = new FileOutputStream(filePath);
                        outputStream.write(beats.toString().getBytes(StandardCharsets.UTF_8));
                        outputStream.close();

                        // 2 minutos
                        Thread.sleep(120000);
                    }
                } catch (InterruptedException | IOException ignored) {}
            });
            saveBeatsThread.start();
        }
    }

    @PreDestroy
    void destroySaveBeatsThread() {
        if (saveBeatsThread != null && saveBeatsThread.isAlive()) {
            saveBeatsThread.stop();
        }
    }

    @PostMapping("bpm")
    public void postBeat(@RequestBody Beat bpm) {
        System.out.println("Posting " + bpm.getBeat());
        lastBeat = bpm;
        recentBeats.add(lastBeat);
        System.out.println("BPM Posted: " + bpm.toString());
    }

    @GetMapping("bpm")
    public Beat getBeats() {
        System.out.println("Getting Beat");
        return lastBeat;
    }

    @GetMapping("testando-api")
    public Integer getTeste() {
        return 83;
    }
}
