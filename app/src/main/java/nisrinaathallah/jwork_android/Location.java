/**
 * @author: Nisrina Athallah - 1806148813
 * @version: Modul 9 - Case Study - 27 Mei 2021
 */

package nisrinaathallah.jwork_android;

public class Location{
    private String province;
    private String description;
    private String city;

    public Location(String province, String description, String city){
        this.province = province;
        this.description = description ;
        this.city = city;
    }


    public String getProvince() {
        return province;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
