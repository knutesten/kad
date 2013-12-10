package no.mesan.model;

/*
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class Category {
    private String name;
    private final int id;

    public Category(final int id, final String name) {
        this.id   = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Category) {
            final Category that = (Category) object;
            return name.equals(that.name) && id == that.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public int getId() {
        return id;
    }
}
