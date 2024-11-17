package edu.nyu.websocket;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.test.SpotBugsRule;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcher;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcherBuilder;
import org.junit.Rule;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static edu.umd.cs.findbugs.test.CountMatcher.containsExactly;
import static org.junit.Assert.assertThat;

public class WebSocketDetectorTest {
    @Rule
    public SpotBugsRule spotbugs = new SpotBugsRule();

    @Test
    public void testWebSocketClient() {
        Path path = Paths.get("target/test-classes", "edu.nyu.websocket".replace('.', '/'), "ChatClient.class");
        BugCollection bugCollection = spotbugs.performAnalysis(path);

        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("WEBSOCKET_BUG").build();
        assertThat(bugCollection, containsExactly(1, bugTypeMatcher));
        System.err.println("testWebSocketClient: " + bugCollection.getCollection().toArray()[6]);
    }

    @Test
    public void testWebSocketServer() {
        Path path = Paths.get("target/test-classes", "edu.nyu.websocket".replace('.', '/'), "ChatServer.class");
        BugCollection bugCollection = spotbugs.performAnalysis(path);

        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("WEBSOCKET_BUG").build();
        assertThat(bugCollection, containsExactly(1, bugTypeMatcher));
        System.err.println("testWebSocketServer: " + bugCollection.findBug("50238935389d2db1b42c5fb4fa8b2c2d", "WEBSOCKET_BUG", 59));
    }

    @Test
    public void testWebSocketSSLClient() {
        Path path = Paths.get("target/test-classes", "edu.nyu.websocket".replace('.', '/'), "SSLClientExample.class");
        BugCollection bugCollection = spotbugs.performAnalysis(path);

        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("WEBSOCKET_BUG").build();
        assertThat(bugCollection, containsExactly(0, bugTypeMatcher));
        System.out.println("testWebSocketSSLClient: " + bugCollection);
    }

    @Test
    public void testWebSocketSSLServer() {
        Path path = Paths.get("target/test-classes", "edu.nyu.websocket".replace('.', '/'), "SSLServerExample.class");
        BugCollection bugCollection = spotbugs.performAnalysis(path);

        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("WEBSOCKET_BUG").build();
        assertThat(bugCollection, containsExactly(0, bugTypeMatcher));
        System.out.println("testWebSocketSSLServer: " + bugCollection);
    }
}
