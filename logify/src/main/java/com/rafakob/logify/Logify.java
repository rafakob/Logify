package com.rafakob.logify;

import android.content.Context;

import com.rafakob.logify.repository.LogsRepository;
import com.rafakob.logify.repository.entity.AppLog;

public class Logify {

    /**
     * Initialize with defaults
     *
     * @param context
     */
    public static void init(Context context) {
        new InitializerBuilder(context)
                .build()
                .start();
    }

    /**
     * Initialize with builder
     *
     * @param initializer
     */
    public static void init(Initializer initializer) {
        initializer.start();
    }

    /**
     * Create new initialize builder
     *
     * @param context
     * @return
     */
    public static InitializerBuilder newInitializer(Context context) {
        return new InitializerBuilder(context);
    }

    public static void v(String message) {
        insertAppLog(AppLog.TYPE_VERBOSE, message);
    }

    public static void i(String message) {
        insertAppLog(AppLog.TYPE_INFO, message);
    }

    public static void d(String message) {
        insertAppLog(AppLog.TYPE_DEBUG, message);
    }

    public static void w(String message) {
        insertAppLog(AppLog.TYPE_WARNING, message);
    }

    public static void e(String message) {
        insertAppLog(AppLog.TYPE_ERROR, message);
    }

    private static void insertAppLog(String type, String message) {
        LogsRepository.getInstance().insertAppLog(type, message);
    }

    private Logify() {
    }

    public static final class Initializer {
        final Context context;

        public Initializer(InitializerBuilder builder) {
            this.context = builder.context;
        }

        private void start() {
            LogsRepository.getInstance().init(context);
        }
    }

    public static final class InitializerBuilder {
        final Context context;

        public InitializerBuilder(Context context) {
            this.context = context;
        }

        public Initializer build() {
            return new Initializer(this);
        }
    }
}