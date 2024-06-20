package com.xin.totext;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.Microphone;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SpeechRecognitionApp {

    public static void main(String[] args) {
        // 示例一：从WAV文件转换语音为文字
        convertWavToText();

        // 示例二：实时语音识别（注释或取消注释以下代码块以启用）
        // performLiveSpeechRecognition();
    }

    private static void convertWavToText() {
        try {
            File audioFile = new File("/Users/xiangwanting/Desktop/sphinx/wav.wav"); // WAV文件路径
            Configuration configuration = new Configuration();

            // 设置模型路径，由于使用了sphinx4-data，这些路径会自动解析
            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
//            configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
//            configuration.setAcousticModelPath("file:/Users/xiangwanting/Desktop/sphinx/cmusphinx-zh-cn/zh_cn");
            configuration.setDictionaryPath("file:/Users/xiangwanting/Desktop/sphinx/cmusphinx-zh-cn/zh_cn.dic");
//            configuration.setLanguageModelPath("file:/Users/xiangwanting/Desktop/sphinx/cmusphinx-zh-cn/zh_cn.lm.bin");
            // 使用文件输入流创建识别器
            try (AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile)) {
                StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);

                recognizer.startRecognition(ais);
                System.out.println("开始转换音频文件...");

                while (true) {
                    SpeechResult result = recognizer.getResult();
                    if (result != null) {
                        String text = result.getHypothesis();
                        System.out.println("识别到的文字: " + text);
                    } else {
                        break; // 没有更多的结果，结束循环
                    }
                }

                recognizer.stopRecognition();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("转换过程中发生错误: " + e.getMessage());
        }
    }

    // 实时语音识别示例（可选）
    private static void performLiveSpeechRecognition() {
        try {
            Configuration configuration = new Configuration();

            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

            Microphone microphone = new Microphone(16000, 16, true, false);
            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
            recognizer.startRecognition(true);
            System.out.println("请开始说话...");

            while (true) {
                SpeechResult result = recognizer.getResult();
                if (result != null) {
                    String text = result.getHypothesis();
                    System.out.println("你说了: " + text);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
