package hy.plat.dao.impl;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    int  count;
}

class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // 插入单词
    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }

    }

    // 检查是否存在某个单词
//    public boolean search(String word) {
//        TrieNode node = root;
//        for (char ch : word.toCharArray()) {
//            node = node.children.get(ch);
//            if (node == null) return false;
//        }
//        return node.isEndOfWord;
//    }

    // 检查是否有以 prefix 开头的单词
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) return false;
        }
        return true;
    }
}


