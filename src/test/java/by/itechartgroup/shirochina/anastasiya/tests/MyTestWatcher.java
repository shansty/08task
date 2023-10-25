package by.itechartgroup.shirochina.anastasiya.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MyTestWatcher implements TestWatcher {
    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable cause) {
        BrowserContext browserContext = TestHelper.getContext();
        Page page = TestHelper.getPage();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");
        String time = formatter.format(timestamp.getTime());

        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("build/allure-results/screenshot_"
                        + time + "screenshot.png")).setFullPage(true));
        Allure.addAttachment(time, new ByteArrayInputStream(screenshot));

        String traceFileName = String.format("build/trace.zip", time);
        Path tracePath = Paths.get(traceFileName);

        browserContext.tracing()
                .stop(new Tracing.StopOptions()
                        .setPath(tracePath));
        try {
            Allure.addAttachment("trace.zip", new ByteArrayInputStream(Files.readAllBytes(tracePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}