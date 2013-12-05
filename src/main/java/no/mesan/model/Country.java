package no.mesan.model;

/**
 * TODO
 *
 * @author Anders Grotthing Moe
 */
public class Country {
    private final String code;
    private final String name;

    public Country(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Country) {
            final Country that = (Country) object;
            return code.equals(that.code) && name.equals(that.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
