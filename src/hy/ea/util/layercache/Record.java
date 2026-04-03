package hy.ea.util.layercache;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Record {
    String codePid;
    String codeId;
    String name;


    Integer level;

    public Record(String codePid, String codeId, String name, Integer level) {
        this.codePid = codePid;
        this.codeId = codeId;
        this.name = name;

        this.level=level;
    }

    @Override
    public String toString() {
        return "Record{" +
                "codePid='" + codePid + '\'' +
                ", codeId='" + codeId + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    public String getCodePid() {
        return codePid;
    }

    public void setCodePid(String codePid) {
        this.codePid = codePid;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;
        Record record = (Record) o;
        return Objects.equals(codeId, record.codeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeId);
    }

}
