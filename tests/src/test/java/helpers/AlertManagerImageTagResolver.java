package helpers;

import io.homecentr.testcontainers.images.EnvironmentImageTagResolver;

public class AlertManagerImageTagResolver extends EnvironmentImageTagResolver {
    public AlertManagerImageTagResolver() {
        super("homecentr/prometheus-alertmanager:local");
    }
}
