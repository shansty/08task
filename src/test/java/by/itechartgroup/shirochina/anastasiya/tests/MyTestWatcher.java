package by.itechartgroup.shirochina.anastasiya.tests;

import by.itechartgroup.shirochina.anastasiya.utils.LoggerHelper;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class MyTestWatcher implements TestWatcher {
    private BrowserContext browserContext;
    private Page page;
    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable cause) {
        initializeVariable();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");
        String time = formatter.format(timestamp.getTime());
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("build/allure-results/screenshot_"
                        + time + "screenshot.png")).setFullPage(true));
        Allure.addAttachment(time, new ByteArrayInputStream(screenshot));

        addTraceToAllure();

        File file = new File("log/" + LoggerHelper.getLogFileName());
        try {
            Allure.addAttachment("Log File", new ByteArrayInputStream(Files.readAllBytes(file.toPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        closeBrowserAndPlaywright();
    }
    @Override
    public void testSuccessful(ExtensionContext context) {
        initializeVariable();
        addTraceToAllure();
        closeBrowserAndPlaywright();
    }
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        initializeVariable();
        addTraceToAllure();
        closeBrowserAndPlaywright();
    }
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        initializeVariable();
        addTraceToAllure();
        closeBrowserAndPlaywright();
    }
    private void initializeVariable() {
        this.browserContext = TestHelper.getContext();
        this.page = TestHelper.getPage();
    }
    private void closeBrowserAndPlaywright() {
        browserContext.close();
    }
    private void addTraceToAllure() {
        String traceFileName = "build/trace.zip";
        Path tracePath = Paths.get(traceFileName);
        browserContext.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
        try {
            Allure.addAttachment("trace.zip", new ByteArrayInputStream(Files.readAllBytes(tracePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}