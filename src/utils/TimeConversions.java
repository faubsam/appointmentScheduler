package utils;

import java.time.*;


public class TimeConversions {
    private static ZoneId utcZone = ZoneId.of("UTC");
    private static ZoneId estZone = ZoneId.of("America/New_York");
    private static ZoneId localZone = ZoneId.systemDefault();
    private static ZonedDateTime businessStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), estZone);
    private static ZonedDateTime businessEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), estZone);

    /**
     * Returns time for the local system
     * @param ldt LocalDateTime object to convert to local time
     * @return LocalDateTime converted to local system time
     */
    public static LocalDateTime toLocalSystemTime(LocalDateTime ldt){
            LocalTime ldtTime = ldt.toLocalTime();
            LocalDate ldtDate = ldt.toLocalDate();
            ZonedDateTime ldtZone = ldt.atZone(utcZone);
            ZonedDateTime conv = ldtZone.withZoneSameInstant(localZone);

            LocalDateTime converted = conv.toLocalDateTime();
            return converted;
        }

    /**
     * Converts time to UTC before storing in the DB
     * @param ldt takes the LocalDateTime object to convert
     * @return Converted LocalDateTime object
     */
    public static LocalDateTime toUTCTime(LocalDateTime ldt){
            LocalTime ldtTime = ldt.toLocalTime();
            LocalDate ldtDate = ldt.toLocalDate();
            ZonedDateTime ldtZone = ldt.atZone(localZone);
            ZonedDateTime conv = ldtZone.withZoneSameInstant(utcZone);

            LocalDateTime converted = conv.toLocalDateTime();


            return converted;
        }

    /**
     * Converts time to EST to verify appointments during business hours
     * @param ldt LocalDateTime object
     * @return LocalDateTime object converted to EST
     */
    public static LocalDateTime toEasternTime(LocalDateTime ldt) {
            LocalTime ldtTime = ldt.toLocalTime();
            LocalDate ldtDate = ldt.toLocalDate();
            ZonedDateTime ldtZone = ldt.atZone(utcZone);
            ZonedDateTime conv = ldtZone.withZoneSameInstant(estZone);

            LocalDateTime converted = conv.toLocalDateTime();


            return converted;

        }

}
