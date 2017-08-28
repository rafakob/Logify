package com.rafakob.logify;

import android.content.Context;

import com.rafakob.logify.repository.LogsRepository;
import com.rafakob.logify.repository.entity.AppLog;
import com.rafakob.logify.repository.entity.NetworkLog;
import com.rafakob.logify.view.LogifyActivity;

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

    public static void v(String tag, String message) {
        insertAppLog(tag, AppLog.LEVEL_VERBOSE, message);
    }

    public static void i(String tag, String message) {
        insertAppLog(tag, AppLog.LEVEL_INFO, message);
    }

    public static void d(String tag, String message) {
        insertAppLog(tag, AppLog.LEVEL_DEBUG, message);
    }

    public static void w(String tag, String message) {
        insertAppLog(tag, AppLog.LEVEL_WARNING, message);
    }

    public static void e(String tag, String message) {
        insertAppLog(tag, AppLog.LEVEL_ERROR, message);
    }

    private static void insertAppLog(String tag, String level, String message) {
        LogsRepository.getInstance().insertAppLog(tag, level, message);
    }

    public static void insertNetworkLog(NetworkLog networkLog) {
        LogsRepository.getInstance().insertNetworkLog(networkLog);
    }

    public static void startActivity(Context context) {
        LogifyActivity.start(context);
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