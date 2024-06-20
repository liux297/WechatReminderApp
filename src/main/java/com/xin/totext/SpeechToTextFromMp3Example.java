package com.xin.totext;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.Microphone;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileInputStream;

public class SpeechToTextFromMp3Example {

    public static void main(String[] args) {
        try {
            // 假设你已经将mp3转换为wav，这里是wav文件路径
            File audioFile = new File("/Users/xiangwanting/Desktop/sphinx/wav.wav");

            // 配置Sphinx识别器
            Configuration configuration = new Configuration();

            // 设置资源路径
//            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
//            configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
//            configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
            configuration.setAcousticModelPath("file:/Users/xiangwanting/Desktop/sphinx/cmusphinx-zh-cn/zh_cn");
            configuration.setDictionaryPath("file:/Users/xiangwanting/Desktop/sphinx/cmusphinx-zh-cn/zh_cn.dic");
            configuration.setLanguageModelPath("file:/Users/xiangwanting/Desktop/sphinx/cmusphinx-zh-cn/zh_cn.lm.bin");
            configuration.setSampleRate(44100);
            // 使用文件输入流创建音频输入流
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);

            // 创建基于文件输入的识别器
            StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);

            // 开始识别前，需要打开流并传递给识别器
            recognizer.startRecognition(ais);

            System.out.println("开始转换音频文件...");

            // 循环获取识别结果
            while (true) {
                SpeechResult result = recognizer.getResult();
                if (result != null) {
                    String text = result.getHypothesis();
                    System.out.println("识别到的文字: " + text);
                } else {
                    // 如果没有更多结果，则认为转换完成
                    break;
                }
            }

            // 关闭识别器和音频流
            recognizer.stopRecognition();
            ais.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("转换过程中发生错误: " + e.getMessage());
        }
    }
}
