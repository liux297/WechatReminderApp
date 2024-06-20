package com.xin.vosk;

import java.io.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import lombok.SneakyThrows;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.wav.WavFileReader;
import org.vosk.LogLevel;
import org.vosk.Recognizer;
import org.vosk.LibVosk;
import org.vosk.Model;

public class SpeechToTextMain {


    public static void main(String[] argv) throws Exception {
        LibVosk.setLogLevel(LogLevel.DEBUG);

        File file = new File("/Users/xiangwanting/Desktop/sphinx/test.wav");
        try (Model model = new Model("/Users/xiangwanting/Desktop/sphinx/vosk-model-small-cn-0.22");
//        try (Model model = new Model("/Users/xiangwanting/Desktop/sphinx/vosk-model-small-cn-0.22");
             InputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(  file)));
             Recognizer recognizer = new Recognizer(model, getSampleRate(file))) {

            int nbytes;
            byte[] b = new byte[4096];
            while ((nbytes = ais.read(b)) >= 0) {
                if (recognizer.acceptWaveForm(b, nbytes)) {
                    System.out.println(recognizer.getResult());
                } else {
                    System.out.println(recognizer.getPartialResult());
                }
            }

            System.out.println(recognizer.getFinalResult());
            System.out.println("文件速率："+getSampleRate(file));
        }
    }

    /**
     * 获取音频文件的采样率
     */
    static Float getSampleRate(File file) throws Exception {
        WavFileReader fileReader = new WavFileReader();
        AudioFile audioFile = fileReader.read(file);
        String sampleRate = audioFile.getAudioHeader().getSampleRate();
        return Float.parseFloat(sampleRate);
    }
}