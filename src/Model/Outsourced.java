package Model;

/**
 * Outsourced class inherits from Part class. This creates objects that are outsourced
 * to different companies.
 * companyName added as an attribute.
 */

public class Outsourced extends Part{
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

}
