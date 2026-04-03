package hy.ea.util.layercache;

import java.util.Collections;
import java.util.List;

public class Trie {
    private TrieNode root = new TrieNode();

    public void insert(String key, Record record) {
        TrieNode node = root;
        for (char c : key.toCharArray()) {
            TrieNode child = node.children.get(c);
            if (child == null) {
                child = new TrieNode();
                node.children.put(c, child);
            }
            node = child;
            node.records.add(record);
        }
    }

    public List<Record> search(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return Collections.emptyList();
        }
        return node.records;
    }
}
