package space.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.ui.Model;
import space.entity.SpaceType;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpaceTypeModel implements Serializable {
    int id;
    String name;

    public static SpaceTypeModel mapToSpaceTypeModel(SpaceType spaceType) {
        SpaceTypeModel spaceTypeModel = new SpaceTypeModel();
        spaceTypeModel.setId(spaceType.getId());
        spaceTypeModel.setName(spaceType.getName());
        return spaceTypeModel;
    }

    public static SpaceType mapToSpaceType(SpaceTypeModel spaceTypeModel) {
        return new SpaceType(spaceTypeModel.getId(), spaceTypeModel.getName());
    }

    public static void injectSpaceTypeList(Model model, List<SpaceType> spaceTypes) {
        List<SpaceTypeModel> list = spaceTypes
                .stream()
                .map(SpaceTypeModel::mapToSpaceTypeModel)
                .toList();

        model.addAttribute("spaceTypeList", list);
    }
}
