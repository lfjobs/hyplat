package hy.ea.collage.action.dto;

public enum VODProvider {
    ALIYUN("aliyun", "阿里云"), HUAWEI("huawei", "华为云");

    private final String name;
    private final String description;

    VODProvider(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static VODProvider getByName(String name) {
        for (VODProvider vodProvider : VODProvider.values()) {
            if (vodProvider.getName().equals(name)) {
                return vodProvider;
            }
        }
        return null;
    }
}