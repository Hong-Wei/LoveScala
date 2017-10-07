import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG

appender("STDOUT", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd HH:mm:ss} [%thread] %c [%level] - %m%n"
  }
}
root(DEBUG, ["STDOUT"])