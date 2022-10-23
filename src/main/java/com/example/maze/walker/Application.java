package com.example.maze.walker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application {
    public static void main(String[] args) throws Exception {
        JsonNode nodeA = new ObjectMapper()
                .readTree("{ \"a\": 1 }")
                .get("a");
        System.out.println(nodeA.toString());
    }
}
