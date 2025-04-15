package space.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.ui.Model;
import space.entity.Reservation;
import space.entity.Space;
import space.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationModel implements Comparable<ReservationModel> {
    int id;

    LocalDate date;

    LocalTime hour;

    Space space;

    ReservationStatus status;

    @Override
    public int compareTo(ReservationModel o) {
        return Comparator
                .comparing(ReservationModel::getDate)
                .thenComparing((value) -> value.getSpace().getName())
                .thenComparing(ReservationModel::getHour)
                .compare(this, o);
    }

    public enum ReservationStatus {
        SELECTED, UNAVAILABLE, AVAILABLE
    }

    public static ReservationModel mapToReservationModel(Reservation reservation) {
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setId(reservation.getId());
        reservationModel.setSpace(reservation.getSpace());
        reservationModel.setDate(reservation.getDate());
        reservationModel.setHour(reservation.getHour());
        return reservationModel;
    }

    public static void injectUserReservationList(Model model, List<Reservation> userReservations) {
        List<ReservationModel> reservationModels = userReservations
                .stream()
                .map(ReservationModel::mapToReservationModel).toList();

        model.addAttribute("userReservationList", reservationModels.stream().sorted().toList());
    }

    public static void injectDateError(Model model, LocalDate date) {
        if (date != null && date.isBefore(LocalDate.now())) {
            model.addAttribute("dateError", "You can't reserve a space for past days!");
        }
    }

    public static void injectSpaceReservationList(
            Model model, User user,  List<Reservation> reservations) {
        List<ReservationModel> reservationModels = new ArrayList<>();
        int openingHour = 8;
        int closingHour = 20;

        for (int hour = openingHour; hour <= closingHour; hour++) {
            ReservationModel reservationModel = new ReservationModel();
            reservationModel.setHour(LocalTime.of(hour, 0));
            reservationModel.setStatus(ReservationModel.ReservationStatus.AVAILABLE);
            reservationModels.add(reservationModel);
        }

        for (Reservation reservation : reservations) {
            if (reservation.getOwner().getId() == user.getId()) {
                reservationModels.get(reservation.getHour().getHour() - openingHour).setStatus(ReservationModel.ReservationStatus.SELECTED);
            } else {
                reservationModels.get(reservation.getHour().getHour() - openingHour).setStatus(ReservationModel.ReservationStatus.UNAVAILABLE);
            }
        }

        model.addAttribute("spaceReservationList", reservationModels);
    }
}
