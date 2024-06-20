//package com.tongyiqianwen.main;
//
//import java.util.ArrayList;
//import java.util.List;
//import com.alibaba.dashscope.aigc.generation.Generation;
//import com.alibaba.dashscope.aigc.generation.GenerationParam;
//import com.alibaba.dashscope.aigc.generation.GenerationResult;
//import com.alibaba.dashscope.common.Message;
//import com.alibaba.dashscope.common.Role;
//import com.alibaba.dashscope.exception.ApiException;
//import com.alibaba.dashscope.exception.InputRequiredException;
//import com.alibaba.dashscope.exception.NoApiKeyException;
//import com.alibaba.dashscope.utils.Constants;
//
//import java.util.Scanner;
//
//public class Main2 {
//
//    public static GenerationParam createGenerationParam(List<Message> messages) {
//        return GenerationParam.builder()
//                .model("qwen-turbo")
//                .messages(messages)
//                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
//                .topP(0.8)
//                .build();
//    }
//
//    public static GenerationResult callGenerationWithMessages(GenerationParam param) throws ApiException, NoApiKeyException, InputRequiredException {
//        Generation gen = new Generation();
//        return gen.call(param);
//    }
//
//    public static void main(String[] args) {
//        Constants.apiKey = "sk-7e13f432628d476e9f8e693f6c90ac6e";
//        try {
//            List<Message> messages = new ArrayList<>();
//
//            messages.add(createMessage(Role.SYSTEM, "You are a helpful assistant."));
//            for (int i = 0; i < 30;i++) {
//                Scanner scanner = new Scanner(System.in);
//                System.out.print("请输入：");
//                String userInput = scanner.nextLine();
//                if ("exit".equalsIgnoreCase(userInput)) {
//                    break;
//                }
//                messages.add(createMessage(Role.USER, userInput));
//                GenerationParam param = createGenerationParam(messages);
//                GenerationResult result = callGenerationWithMessages(param);
//                System.out.println("模型输出："+result.getOutput().getChoices().get(0).getMessage().getContent());
//                messages.add(result.getOutput().getChoices().get(0).getMessage());
//            }
//        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
//            e.printStackTrace();
//        }
//        System.exit(0);
//    }
//
//    private static Message createMessage(Role role, String content) {
//        return Message.builder().role(role.getValue()).content(content).build();
//    }
//}