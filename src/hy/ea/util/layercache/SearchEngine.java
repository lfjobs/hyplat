package hy.ea.util.layercache;
import java.util.*;

// 搜索引擎
public class SearchEngine {
    private final Trie trie = new Trie();
    private final UnionFind uf = new UnionFind();
    private final Map<String, List<Record>> snGroups = new HashMap<>();

    // 插入一条记录
    public void insert(Record record) {
        trie.insert(record.name, record);

        // 先注册节点
        uf.addNode(record.codeId, record.level);

        // 如果不是顶层，就 union 到父节点
        if (record.level > 2) {
            if (record.codePid != null) {
                uf.addNode(record.codePid, record.level - 1); // 确保父节点存在
                uf.union(record.codePid, record.codeId);
            }
        }

        // SN 分组存储（按顶层根分组）
        String root = uf.getTopRoot(record.codeId);
        List<Record> list = snGroups.get(root);
        if (list == null) {
            list = new ArrayList<>();
            snGroups.put(root, list);
        }
        list.add(record);
    }
    public List<Record> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Record> result = new LinkedHashSet<>();

        // 1️⃣ 前缀 Trie 结果
        List<Record> prefixResult = searchByName(keyword);
        if (prefixResult != null) {
            result.addAll(prefixResult);
        }

        // 2️⃣ 模糊 contains 结果
        List<Record> fuzzyResult = fuzzySearch(keyword);
        if (fuzzyResult != null) {
            result.addAll(fuzzyResult);
        }

        return new ArrayList<>(result);
    }
    public List<Record> fuzzySearch(String keyword) {
        List<Record> result = new ArrayList<>();
        for (List<Record> list : snGroups.values()) {
            for (Record r : list) {
                if (r.name.contains(keyword)) {
                    result.add(r);
                }
            }
        }
        return result;
    }

    // 按名字前缀搜索
    public List<Record> searchByName(String prefix) {
        return trie.search(prefix);
    }

    // 按 SN 搜索所属顶层集合
    public List<Record> searchByCodeId(String id) {
        String topRoot = uf.getTopRoot(id);
        return snGroups.getOrDefault(topRoot, Collections.emptyList());

    }

    // 获取上一级 SN（例如 "09010101020101" → "090101010201"）

    public Map<String, List<Record>> getSnGroups() {
        return snGroups;
    }
}

