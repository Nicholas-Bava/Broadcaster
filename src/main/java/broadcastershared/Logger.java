package broadcastershared;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private FileWriter writer;
    private final String componentId;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private volatile boolean isClosed = false;

    public Logger(String componentId, String logFileName) {
        this.componentId = componentId;
        try {
            this.writer = new FileWriter(logFileName, true);
        } catch (IOException e) {
            System.err.println("Failed to create logger for " + componentId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void logSent(Message message) {
        if (!isClosed && writer != null) {
            String logEntry = String.format("[%s] \n %s SENT: %s \n TO: %s \n TYPE: %s \n PAYLOAD: %s%n\n",
                    LocalTime.now().format(timeFormatter), componentId,
                    message.getMessageType(), message.getRecipientID(), message.getMessageType(), message.getPayload());
            writeLog(logEntry);
        }
    }

    public synchronized void logText(Message message) {
        if (!isClosed && writer != null) {
            String logEntry = String.format("[%s] \n %s RECEIVED \n FROM: %s \n TYPE: %s \n PAYLOAD: %s%n\n",
                    LocalTime.now().format(timeFormatter), componentId,
                    message.getSenderID(), message.getMessageType().name(), message.getPayload());
            writeLog(logEntry);
        }
    }

    public synchronized void logCommand(Message message, String executionTime) {
        if (!isClosed && writer != null) {
            String logEntry = String.format("[%s] \n %s RECEIVED: \n FROM: %s \n TYPE: %s \n Execution took %s ms%n\n",
                    LocalTime.now().format(timeFormatter), componentId,
                    message.getSenderID(), message.getMessageType().name(), executionTime);
            writeLog(logEntry);
        }
    }

    private void writeLog(String entry) {
        if (!isClosed && writer != null) {
            try {
                writer.write(entry);
                writer.flush();
            } catch (IOException e) {
                if (!isClosed) {
                    System.err.println("Failed to write log for " + componentId + ": " + e.getMessage());
                }
            }
        }
    }

    public synchronized void close() {
        if (!isClosed && writer != null) {
            isClosed = true;
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Failed to close logger for " + componentId + ": " + e.getMessage());
            }
        }
    }
}
