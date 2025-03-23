package app.space.util.matcher;

import app.space.entity.Reservation;
import app.space.entity.Space;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ReservationCriteriaMatcherTest {
    @ParameterizedTest
    @ValueSource(strings = {"", "Richard", "Richard The Great Fantastic Very Good Person Actually Very Nice"})
    void match_MatchingSameOwnerName_True(String ownerName) {
        ReservationCriteriaMatcher matcher = new ReservationCriteriaMatcher
                .ReservationCriteriaMatcherBuilder()
                .ownerName(ownerName)
                .build();

        boolean res = matcher.match(new Reservation(ownerName, new Space("Cozy", Space.Type.PRIVATE, 1000), 1, 12, 14));

        assertTrue(res);
    }

    @Test
    void match_NullOwnerName_True() {
        ReservationCriteriaMatcher matcher = new ReservationCriteriaMatcher
                .ReservationCriteriaMatcherBuilder()
                .ownerName(null)
                .build();

        boolean res = matcher.match(new Reservation("Annabelle", new Space("Cozy", Space.Type.PRIVATE, 1000), 1, 12, 14));

        assertTrue(res);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "Richy", "Richard"})
    void match_MatchingDifferentOwnerName_False(String ownerName) {
        ReservationCriteriaMatcher matcher = new ReservationCriteriaMatcher
                .ReservationCriteriaMatcherBuilder()
                .ownerName(ownerName)
                .build();

        boolean res = matcher.match(new Reservation("Annabelle",
                new Space("Cozy", Space.Type.PRIVATE, 1000), 1, 12, 14));

        assertFalse(res);
    }

    @ParameterizedTest
    @MethodSource("provideNonClashingTimeSlots")
    void match_NonIntersectingTimeSlot_False(int matchDate, int matchStartHour, int matchEndHour, int resDate, int resStartHour, int resEndHour) {
        ReservationCriteriaMatcher matcher = new ReservationCriteriaMatcher
                .ReservationCriteriaMatcherBuilder()
                .date(matchDate)
                .hours(matchStartHour, matchEndHour)
                .build();

        boolean res = matcher.match(new Reservation("Annabelle",
                new Space("Cozy", Space.Type.PRIVATE, 1000),
                resDate,
                resStartHour,
                resEndHour));

        assertFalse(res);
    }

    @ParameterizedTest
    @MethodSource("provideClashingTimeSlots")
    void match_IntersectingTimeSlot_True(int matchDate, int matchStartHour, int matchEndHour, int resDate, int resStartHour, int resEndHour) {
        ReservationCriteriaMatcher matcher = new ReservationCriteriaMatcher
                .ReservationCriteriaMatcherBuilder()
                .date(matchDate)
                .hours(matchStartHour, matchEndHour)
                .build();

        boolean res = matcher.match(new Reservation("Annabelle",
                new Space("Cozy", Space.Type.PRIVATE, 1000),
                resDate,
                resStartHour,
                resEndHour));

        assertTrue(res);
    }

    private static Stream<Arguments> provideNonClashingTimeSlots() {
        return Stream.of(
                Arguments.of(10, 13, 18, 11, 13, 18),
                Arguments.of(20, 13, 18, 20, 18, 21),
                Arguments.of(20, 13, 18, 20, 10, 13),
                Arguments.of(20, 13, 18, 20, 20, 22)
        );
    }

    private static Stream<Arguments> provideClashingTimeSlots() {
        return Stream.of(
                Arguments.of(10, 13, 18, 10, 13, 18),
                Arguments.of(20, 13, 18, 20, 7, 21),
                Arguments.of(20, 13, 18, 20, 7, 14),
                Arguments.of(20, 13, 18, 20, 15, 20)
        );
    }
}