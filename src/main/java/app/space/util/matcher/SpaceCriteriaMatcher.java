package app.space.util.matcher;
import app.space.entity.Space;

public class SpaceCriteriaMatcher implements CriteriaMatcher<Space> {
    private String name;
    private Space.Type type;
    private int startPrice;
    private int endPrice;

    private SpaceCriteriaMatcher(SpaceCriteriaMatcherBuilder builder) {
        name = builder.name;
        type = builder.type;
        startPrice = builder.startPrice;
        endPrice = builder.endPrice;
    }

    public boolean matchName(String name) {
        return this.name == null || this.name.equals(name);
    }

    public boolean matchStartPriceLoose(int startPrice) {
        return this.startPrice == -1 || this.startPrice >= startPrice;
    }

    public boolean matchEndPriceLoose(int endPrice) {
        return this.endPrice == -1 || this.endPrice <= endPrice;
    }

    public boolean matchType(Space.Type type) {
        return this.type == null || this.type == type;
    }

    public boolean match(Space space) {
        return matchName(space.getName())
                && matchStartPriceLoose(space.getPrice())
                && matchEndPriceLoose(space.getPrice())
                && matchType(space.getType());
    }

    public static class SpaceCriteriaMatcherBuilder {
        private String name = null;
        private Space.Type type = null;
        private int startPrice = -1;
        private int endPrice = -1;

        public SpaceCriteriaMatcherBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SpaceCriteriaMatcherBuilder type(Space.Type type) {
            this.type = type;
            return this;
        }

        public SpaceCriteriaMatcherBuilder startPrice(int startPrice) {
            this.startPrice = startPrice;
            return this;
        }

        public SpaceCriteriaMatcherBuilder endPrice(int endPrice) {
            this.endPrice = endPrice;
            return this;
        }

        public SpaceCriteriaMatcher build(){
            return new SpaceCriteriaMatcher(this);
        }
    }
}
