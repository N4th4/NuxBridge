package com.bukkit.N4th4.NuxBridge;

import java.util.logging.Logger;
import java.util.logging.Level;

public class NBLogger {
    private static Logger log;

    public static void initialize() {
        log = Logger.getLogger("Minecraft");
    }

    public static void info(String message) {
        log.log(Level.INFO, "[NuxBridge] " + message);
    }

    public static void severe(String message) {
        log.log(Level.SEVERE, "[NuxBridge] " + message);
    }
}
