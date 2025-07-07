package com.ruoyi.system.domain;

import java.util.List;

public class DeepSeekResponse {
    private String id;
    private List<Choice> choices;
    private long created;
    private String model;
    private String object;
    private Usage usage;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<Choice> getChoices() { return choices; }
    public void setChoices(List<Choice> choices) { this.choices = choices; }

    public long getCreated() { return created; }
    public void setCreated(long created) { this.created = created; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public Usage getUsage() { return usage; }
    public void setUsage(Usage usage) { this.usage = usage; }

    public static class Choice {
        private String finish_reason;
        private int index;
        private Message message;

        public String getFinish_reason() { return finish_reason; }
        public void setFinish_reason(String finish_reason) { this.finish_reason = finish_reason; }

        public int getIndex() { return index; }
        public void setIndex(int index) { this.index = index; }

        public Message getMessage() { return message; }
        public void setMessage(Message message) { this.message = message; }
    }

    public static class Message {
        private String content;
        private String role;
        private List<ToolCall> tool_calls;

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public List<ToolCall> getTool_calls() { return tool_calls; }
        public void setTool_calls(List<ToolCall> tool_calls) { this.tool_calls = tool_calls; }
    }

    public static class ToolCall {
        private String id;
        private String type;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class Usage {
        private int completion_tokens;
        private int prompt_tokens;
        private int total_tokens;

        public int getCompletion_tokens() { return completion_tokens; }
        public void setCompletion_tokens(int completion_tokens) { this.completion_tokens = completion_tokens; }

        public int getPrompt_tokens() { return prompt_tokens; }
        public void setPrompt_tokens(int prompt_tokens) { this.prompt_tokens = prompt_tokens; }

        public int getTotal_tokens() { return total_tokens; }
        public void setTotal_tokens(int total_tokens) { this.total_tokens = total_tokens; }
    }
}
