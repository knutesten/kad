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
