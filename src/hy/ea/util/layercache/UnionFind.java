package hy.ea.util.layercache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 并查集
class UnionFind {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> level = new HashMap<>();

    public void addNode(String x, int lvl) {
        parent.putIfAbsent(x, x);
        level.putIfAbsent(x, lvl);
    }

    public String find(String x) {
        parent.putIfAbsent(x, x);
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    // 按层级合并
    public void union(String parentNode, String childNode) {
        String rootP = find(parentNode);
        String rootC = find(childNode);

        if (rootP.equals(rootC)) return;

        int lvlP = level.getOrDefault(rootP, Integer.MAX_VALUE);
        int lvlC = level.getOrDefault(rootC, Integer.MAX_VALUE);

        // 层级小的当根（保证顶层2始终是根）
        if (lvlP <= lvlC) {
            parent.put(rootC, rootP);
            level.put(rootP, Math.min(lvlP, lvlC));
        } else {
            parent.put(rootP, rootC);
            level.put(rootC, Math.min(lvlP, lvlC));
        }
    }

    // 获取所属顶层节点
    public String getTopRoot(String x) {
        String root = find(x);
//        while (root != null && level.get(root) != null && level.get(root) > 2) {
//            root = find(parent.get(root));
//        }
        return root;
    }
}
