package hy.ea.util.layercache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 前缀树
public class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    List<Record> records = new ArrayList<>();
}