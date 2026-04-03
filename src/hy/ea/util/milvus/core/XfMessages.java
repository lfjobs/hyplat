package hy.ea.util.milvus.core;

public class XfMessages {
    private String content;
    private String role;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // "messages": [
    //        {
    //            "content": null,
    //            "role": null
    //        }
    //    ]
    @Override
    public String toString() {

        return "messages:[" +
                "{" +
                "content='" + content + '\'' +
                ", role='" + role + '\'' +
                "}" +
                "]";
    }
}
