package com.rafakob.logify;

import android.content.Context;

import com.rafakob.logify.repository.LogRepository;

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

    private Logify() {
    }

    public static final class Initializer {
        final Context context;

        public Initializer(InitializerBuilder builder) {
            this.context = builder.context;
        }

        private void start() {
            LogRepository.getInstance().init(context);
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