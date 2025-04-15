package space.model;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.ui.Model;
import space.entity.Space;
import space.entity.SpaceType;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpaceModel implements Serializable {
    int id;

    @NotBlank(message = "Required field")
    @Size(min = 3, max=100, message = "Must be of size 3 - 100")
    String name;

    @NotNull(message = "Required field")
    @Min(value = 1, message = "Required field")
    int typeId;

    String typeName;

    @NotNull(message = "Required field")
    @Max(value = 1000000,  message = "Must be less than 1000000")
    @Min(value = 10, message = "Must be more than 10")
    int price;

    public static SpaceModel mapToSpaceModel(Space space) {
        SpaceModel spaceModel = new SpaceModel();
        spaceModel.setId(space.getId());
        spaceModel.setName(space.getName());
        spaceModel.setTypeId(space.getType().getId());
        spaceModel.setTypeName(space.getType().getName());
        spaceModel.setPrice(space.getPrice());
        return spaceModel;
    }

    public static void injectSpaceInput(Model model) {
        model.addAttribute("spaceInput", new SpaceModel());
    }

    public static void injectSpaceList(Model model, List<Space> spaces) {
        List<SpaceModel> spaceModels = spaces
                .stream()
                .map(SpaceModel::mapToSpaceModel)
                .toList();

        model.addAttribute("spaceList", spaceModels);
    }

    public static void injectSpaceDetails(Model model, Space space) {
        model.addAttribute("spaceDetails", mapToSpaceModel(space));
    }
}
