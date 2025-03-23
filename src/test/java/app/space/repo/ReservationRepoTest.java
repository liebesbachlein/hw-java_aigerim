package app.space.repo;

import app.space.entity.Reservation;
import app.space.entity.Space;
import app.space.util.DuplicateIdException;
import app.space.util.matcher.CriteriaMatcher;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationRepoTest {
    @Mock
    private RepoSource<Reservation> repoSource;
    private ReservationRepo repo;
    private Map<Integer, Reservation> map;

    @BeforeEach
    void setMap() {
        map = new HashMap<Integer, Reservation>();
        Space space1 = new Space(100,"Cozy", Space.Type.OPEN, 1000);
        Space space2 = new Space(101,"Nice", Space.Type.ROOM, 1000);
        Space space3 = new Space(100,"Pretty", Space.Type.OPEN, 1000);
        Space space4 = new Space(103,"Warm", Space.Type.PRIVATE, 1000);
        Reservation reservation1 = new Reservation(100, "Aigerim", space1, 1, 14, 15);
        Reservation reservation2 = new Reservation(101, "Sabina", space2, 2, 14, 15);
        Reservation reservation3 = new Reservation(102, "Mukhtar", space3, 3, 14, 15);
        Reservation reservation4 = new Reservation(103, "Aigerim", space4, 4, 14, 15);
        map.put(100, reservation1);
        map.put(101, reservation2);
        map.put(102, reservation3);
        map.put(103, reservation4);
        when(repoSource.getEntityMap()).thenReturn(map);
        repo = new ReservationRepo(repoSource);
    }

    @Test
    void findById_IdExists_OptionalWithEntity() {
        int id = 100;

        Optional<Reservation> item = repo.findById(id);

        assertTrue(item.isPresent());
        assertEquals(id, item.get().getId());
    }

    @Test
    void findByCriteria_AllListItems() {
        CriteriaMatcher<Reservation> filter = (Reservation res) -> true;
        List<Reservation> res = repo.findByCriteria(filter);

        assertArrayEquals(map.values().toArray(), res.toArray());
    }

    @Test
    void findByCriteria_EmptyList() {
        CriteriaMatcher<Reservation> filter = (Reservation res) -> false;
        List<Reservation> res = repo.findByCriteria(filter);

        assertTrue(res.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 101, 103})
    void findByCriteria_AllListItemsWithSameSpaceId(int spaceId) {
        CriteriaMatcher<Reservation> filter = (Reservation res) -> res.getSpaceId() == spaceId;
        List<Reservation> res = repo.findByCriteria(filter);

        assertArrayEquals(map.values().stream().filter(e -> e.getSpaceId() == spaceId).toArray(), res.toArray());
    }

    @Test
    void findById_IdNotExists_OptionalWithEmpty() {
        int id = 555;

        Optional<Reservation> item = repo.findById(id);

        assertTrue(item.isEmpty());
    }

    @Test
    void getAll_NonEmptyList() {
        List<Reservation> res = repo.getAll();

        assertArrayEquals(map.values().toArray(), res.toArray());
    }

    @Test
    void getAll_EmptyList() {
        RepoSource<Reservation> newRepoSource = mock(RepoSource.class);
        when(newRepoSource.getEntityMap()).thenReturn( new HashMap<>());
        ReservationRepo newRepo = new ReservationRepo(newRepoSource);

        List<Reservation> res = newRepo.getAll();

        assertTrue(res.isEmpty());
        Mockito.verify(newRepoSource, times(1)).getEntityMap();
    }

    @SneakyThrows
    @Test
    void save_ItemWithNewId() {
        Space space = new Space(110,"Cold", Space.Type.PRIVATE, 1000);
        Reservation reservation = new Reservation(110, "Aigerim", space, 1, 14, 15);

        Reservation res = repo.save(reservation);

        assertEquals(reservation, res);
    }

    @Test
    void save_ItemWithDuplicateId_ThrowsException() {
        //Arrange
        Space space = new Space(100,"Cold", Space.Type.PRIVATE, 1000);
        Reservation reservation = new Reservation(100, "Aigerim", space, 1, 14, 15);

        //Act & Assert
        assertThrows(DuplicateIdException.class, () -> repo.save(reservation));
    }

    @Test
    void delete_ExistentItem() {
        //Act
        boolean res = repo.delete(100);

        //Assert
        assertTrue(res);
    }

    @Test
    void delete_NonExistentItem() {
        //Act
        boolean res = repo.delete(555);

        //Assert
        assertFalse(res);
    }

    @AfterEach
    void verify() {
        Mockito.verify(repoSource, times(1)).getEntityMap();
    }
}