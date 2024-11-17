package edu.nyu.websocket;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;
import org.apache.bcel.Const;

public class WebSocketDetector extends OpcodeStackDetector {
    private final BugReporter bugReporter;

    public WebSocketDetector(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    @Override
    public void sawOpcode(int seen) {
        if (seen == Const.INVOKEVIRTUAL) {
            // client
            if (getClassConstantOperand().equals("org/java_websocket/client/WebSocketClient")
                    && getNameConstantOperand().equals("connect")) {
                reportBug();
            }
        } else if (seen == Const.INVOKEINTERFACE) {
            // server
            if (getClassConstantOperand().equals("org/java_websocket/WebSocket")
                 && getNameConstantOperand().equals("send")) {
                reportBug();
            }
        }
    }

    private void reportBug() {
        // report bug when System.out is used in code
        BugInstance bug = new BugInstance(this, "WEBSOCKET_BUG", NORMAL_PRIORITY)
                .addClassAndMethod(this)
                .addSourceLine(this, getPC());
        bugReporter.reportBug(bug);
    }
}
