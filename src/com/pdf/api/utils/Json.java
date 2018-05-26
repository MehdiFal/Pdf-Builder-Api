package com.pdf.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Json {
    private static final ObjectMapper mapper;
    
    static {
        mapper = (new ObjectMapper()).setSerializationInclusion(Include.NON_NULL).configure(Feature.ALLOW_COMMENTS, true);
    }

    public Json() {
    }

    public static ObjectMapper getMapperInstance() {
        return mapper;
    }

    public static JsonNode load(InputStream is) throws IOException {
        return mapper.readTree(is);
    }

    public static ArrayNode createArray() {
        return mapper.createArrayNode();
    }

    public static ObjectNode createObject() {
        return mapper.createObjectNode();
    }

    public static JsonNode build(String input) throws IOException {
        return Lang.isNullOrEmpty(input) ? null : mapper.readTree(input);
    }

    public static <T> T buildObjectFromJsonStr(String jsonStr, Class<T> modelClass) {
        try {
            return mapper.readValue(jsonStr, modelClass);
        } catch (IOException var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }

    public static <T> List<T> buildListFromJsonStr(String jsonStr, Class<T[]> modelClass) {
        try {
            return new ArrayList(Arrays.asList((Object[])mapper.readValue(jsonStr, modelClass)));
        } catch (IOException var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }

    public static JsonNode find(String path, JsonNode inputNode) {
        String[] treeDepth = path.split("\\.");
        if (!inputNode.has(path) && treeDepth.length == 0) {
            return null;
        } else {
            JsonNode node = inputNode;
            String[] var4 = treeDepth;
            int var5 = treeDepth.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String depth = var4[var6];
                if (depth.contains("[") && depth.endsWith("]")) {
                    String[] split = depth.substring(0, depth.length() - 1).split("\\[");
                    node = node.get(split[0]);
                    if (node == null || !node.isArray()) {
                        return null;
                    }

                    node = node.get(Integer.valueOf(split[1]));
                } else {
                    if (!node.has(depth)) {
                        return null;
                    }

                    node = node.get(depth);
                }
            }

            return node;
        }
    }

    public static void deepCreate(String parentTree, Object object, ObjectNode inputNode) {
        String[] treeDepth = parentTree.split("\\.");
        if (!inputNode.has(parentTree) && treeDepth.length == 0) {
            if (object instanceof JsonNode) {
                inputNode.set(parentTree, (JsonNode)object);
            } else {
                inputNode.put(parentTree, object.toString());
            }
        } else {
            int length = treeDepth.length;
            ObjectNode node = inputNode;

            for(int i = 0; i < length; ++i) {
                String depth = treeDepth[i];
                JsonNode currentNode = node.get(depth);
                if (i < length - 1) {
                    if (currentNode == null) {
                        node.set(depth, createObject());
                    }

                    try {
                        node = (ObjectNode)node.get(depth);
                    } catch (ClassCastException var10) {
                        throw new ClassCastException("\nTrying to replace the field: '" + currentNode + "'\non: " + inputNode + "\nfrom: " + currentNode.getClass().getSimpleName() + ":" + currentNode + "\nto Json.");
                    }
                } else {
                    if (currentNode != null) {
                        boolean castExc = object instanceof ObjectNode && !(currentNode instanceof ObjectNode) || object instanceof ArrayNode && !(currentNode instanceof ArrayNode) || !(object instanceof ObjectNode) && currentNode instanceof ObjectNode || !(object instanceof ArrayNode) && currentNode instanceof ArrayNode;
                        if (castExc) {
                            throw new ClassCastException("\nTrying to replace the field: '" + depth + "'\non: " + inputNode + "\nfrom: " + currentNode.getClass().getSimpleName() + ":" + currentNode + "\nto: " + object.getClass().getSimpleName() + ":" + object);
                        }
                    }

                    if (object instanceof JsonNode) {
                        node.set(depth, (JsonNode)object);
                    } else {
                        node.put(depth, object.toString());
                    }
                }
            }

        }
    }

    public static String toString(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException var2) {
            throw new RuntimeException(var2.getMessage(), var2);
        }
    }

    public static String buildPath(String... args) {
        if (args != null && args.length != 0) {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < args.length; ++i) {
                sb.append(args[i]);
                if (i != args.length - 1) {
                    sb.append(".");
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }
}