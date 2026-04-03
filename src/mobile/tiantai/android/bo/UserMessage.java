package mobile.tiantai.android.bo;

import mobile.tiantai.android.bo.scMobile.Tools;

import java.util.List;

public class UserMessage {
    private String model;
    private ThinkingType thinking;
    private List<Content> input;
    private List<Tools> tools;
    public UserMessage() {
    }

    public UserMessage(String model, ThinkingType thinking, List<Content> input) {
        this.model = model;
        this.thinking = thinking;
        this.input = input;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Content> getInput() {
        return input;
    }

    public void setInput(List<Content> input) {
        this.input = input;
    }

    public ThinkingType getThinking() {
        return thinking;
    }

    public void setThinking(ThinkingType thinking) {
        this.thinking = thinking;
    }

    public List<Tools> getTools() {
        return tools;
    }

    public void setTools(List<Tools> tools) {
        this.tools = tools;
    }
}