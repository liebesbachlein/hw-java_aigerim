package app.space.repo;

import app.space.entity.Space;
import app.space.util.DuplicateIdException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class SpaceRepoTest {
    @Mock
    private RepoSource<Space> repoSource;
    private SpaceRepo repo;
    private Map<Integer, Space> map;

    @BeforeEach
    void setMap() {
        map = new HashMap<Integer, Space>();
        Space space1 = new Space(100,"Cozy", Space.Type.OPEN, 1000);
        Space space2 = new Space(101,"Nice", Space.Type.ROOM, 1000);
        Space space3 = new Space(102,"Pretty", Space.Type.OPEN, 1000);
        Space space4 = new Space(103,"Warm", Space.Type.PRIVATE, 1000);
        map.put(100, space1);
        map.put(101, space2);
        map.put(102, space3);
        map.put(103, space4);
        when(repoSource.getEntityMap()).thenReturn(map);
        repo = new SpaceRepo(repoSource);
    }

    @Test
    void findById_IdExists_OptionalWithEntity() {
        //Arrange
        int id = 100;

        //Act
        Optional<Space> item = repo.findById(id);

        //Assert
        assertTrue(item.isPresent());
        assertEquals(id, item.get().getId());
    }

    @Test
    void findById_IdNotExists_OptionalWithEmpty() {
        //Arrange
        int id = 555;

        //Act
        Optional<Space> item = repo.findById(id);

        //Assert
        assertTrue(item.isEmpty());
    }

    @Test
    void getAll_NonEmptyList() {
        //Act
        List<Space> list = repo.getAll();

        //Assert
        assertArrayEquals(map.values().toArray(), list.toArray());
    }

    @Test
    void getAll_EmptyList() {
        //Arrange
        RepoSource<Space> newRepoSource = mock(RepoSource.class);
        when(newRepoSource.getEntityMap()).thenReturn( new HashMap<>());
        SpaceRepo newRepo = new SpaceRepo(newRepoSource);

        //Act
        List<Space> list = newRepo.getAll();

        //Assert
        assertTrue(list.isEmpty());
        Mockito.verify(newRepoSource, times(1)).getEntityMap();
    }

    @SneakyThrows
    @Test
    void save_ItemWithNewId() {
        //Arrange
        Space space = new Space(110,"Cold", Space.Type.PRIVATE, 1000);

        //Act
        Space res = repo.save(space);

        //Assert
        assertEquals(space, res);
    }

    @Test
    void save_ItemWithDuplicateId() {
        //Arrange
        Space space = new Space(100,"Cold", Space.Type.PRIVATE, 1000);

        //Act & Assert
        assertThrows(DuplicateIdException.class, () -> repo.save(space));
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
