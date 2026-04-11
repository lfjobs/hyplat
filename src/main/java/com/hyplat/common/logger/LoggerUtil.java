package com.hyplat.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统一日志工具类
 * 
 * 使用规范：
 * 1. 禁止使用 System.out.println 和 printStackTrace
 * 2. 所有日志必须通过此类记录
 * 3. 日志级别选择：
 *    - ERROR: 系统错误、异常
 *    - WARN: 警告信息、可恢复错误
 *    - INFO: 关键业务流程、重要状态变更
 *    - DEBUG: 调试信息、详细流程
 *    - TRACE: 最详细的跟踪信息
 * 
 * @author red
 * @since 2026-04-11
 */
public class LoggerUtil {
	private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);
    
    /**
     * 获取 Logger 实例
     * 
     * @param clazz 类对象
     * @return Logger 实例
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
    
    /**
     * 获取 Logger 实例
     * 
     * @param name Logger 名称
     * @return Logger 实例
     */
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }
    
    // ==================== ERROR 级别 ====================
    
    public static void error(Logger logger, String message) {
        if (logger != null) {
            logger.error(message);
        }
    }
    
    public static void error(Logger logger, String format, Object arg) {
        if (logger != null) {
            logger.error(format, arg);
        }
    }
    
    public static void error(Logger logger, String format, Object arg1, Object arg2) {
        if (logger != null) {
            logger.error(format, arg1, arg2);
        }
    }
    
    public static void error(Logger logger, String format, Object... arguments) {
        if (logger != null) {
            logger.error(format, arguments);
        }
    }
    
    public static void error(Logger logger, String message, Throwable t) {
        if (logger != null) {
            logger.error(message, t);
        }
    }
    
    // ==================== WARN 级别 ====================
    
    public static void warn(Logger logger, String message) {
        if (logger != null) {
            logger.warn(message);
        }
    }
    
    public static void warn(Logger logger, String format, Object arg) {
        if (logger != null) {
            logger.warn(format, arg);
        }
    }
    
    public static void warn(Logger logger, String format, Object arg1, Object arg2) {
        if (logger != null) {
            logger.warn(format, arg1, arg2);
        }
    }
    
    public static void warn(Logger logger, String format, Object... arguments) {
        if (logger != null) {
            logger.warn(format, arguments);
        }
    }
    
    public static void warn(Logger logger, String message, Throwable t) {
        if (logger != null) {
            logger.warn(message, t);
        }
    }
    
    // ==================== INFO 级别 ====================
    
    public static void info(Logger logger, String message) {
        if (logger != null) {
            logger.info(message);
        }
    }
    
    public static void info(Logger logger, String format, Object arg) {
        if (logger != null) {
            logger.info(format, arg);
        }
    }
    
    public static void info(Logger logger, String format, Object arg1, Object arg2) {
        if (logger != null) {
            logger.info(format, arg1, arg2);
        }
    }
    
    public static void info(Logger logger, String format, Object... arguments) {
        if (logger != null) {
            logger.info(format, arguments);
        }
    }
    
    public static void info(Logger logger, String message, Throwable t) {
        if (logger != null) {
            logger.info(message, t);
        }
    }
    
    // ==================== DEBUG 级别 ====================
    
    public static void debug(Logger logger, String message) {
        if (logger != null) {
            logger.debug(message);
        }
    }
    
    public static void debug(Logger logger, String format, Object arg) {
        if (logger != null) {
            logger.debug(format, arg);
        }
    }
    
    public static void debug(Logger logger, String format, Object arg1, Object arg2) {
        if (logger != null) {
            logger.debug(format, arg1, arg2);
        }
    }
    
    public static void debug(Logger logger, String format, Object... arguments) {
        if (logger != null) {
            logger.debug(format, arguments);
        }
    }
    
    public static void debug(Logger logger, String message, Throwable t) {
        if (logger != null) {
            logger.debug(message, t);
        }
    }
    
    // ==================== TRACE 级别 ====================
    
    public static void trace(Logger logger, String message) {
        if (logger != null) {
            logger.trace(message);
        }
    }
    
    public static void trace(Logger logger, String format, Object arg) {
        if (logger != null) {
            logger.trace(format, arg);
        }
    }
    
    public static void trace(Logger logger, String format, Object arg1, Object arg2) {
        if (logger != null) {
            logger.trace(format, arg1, arg2);
        }
    }
    
    public static void trace(Logger logger, String format, Object... arguments) {
        if (logger != null) {
            logger.trace(format, arguments);
        }
    }
    
    public static void trace(Logger logger, String message, Throwable t) {
        if (logger != null) {
            logger.trace(message, t);
        }
    }
}
