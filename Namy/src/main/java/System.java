package main.java;

import java.util.Map;

public interface System {
    System parent = null;
    Map<String, String> identity = null;
    String lookUp(String hostname);
    int timeToLive = 0;
}
