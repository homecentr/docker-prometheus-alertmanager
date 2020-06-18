import helpers.AlertManagerImageTagResolver;
import io.homecentr.testcontainers.containers.GenericContainerEx;
import io.homecentr.testcontainers.containers.HttpResponse;
import io.homecentr.testcontainers.containers.wait.strategy.WaitEx;
import io.homecentr.testcontainers.images.PullPolicyEx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class AlertManagerContainerShould {
    private static GenericContainerEx _alertManagerContainer;
    private static final Logger logger = LoggerFactory.getLogger(AlertManagerContainerShould.class);

    @BeforeClass
    public static void before() {
        _alertManagerContainer = new GenericContainerEx<>(new AlertManagerImageTagResolver())
                .withRelativeFileSystemBind(Paths.get("..", "example"), "/config")
                .withImagePullPolicy(PullPolicyEx.never())
                .waitingFor(WaitEx.forLogMessage("(.*)Listening address=:9093(.*)", 1));

        _alertManagerContainer.start();
        _alertManagerContainer.followOutput(new Slf4jLogConsumer(logger));
    }

    @AfterClass
    public static void after() {
        _alertManagerContainer.close();
    }

    @Test
    public void returnMetrics() throws IOException {
        HttpResponse response = _alertManagerContainer.makeHttpRequest(9093, "/-/ready");

        assertEquals(200, response.getResponseCode());
    }
}