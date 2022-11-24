package ci.astdev.onep_users;

public class FuitesModel {

    String nomPrenom, phone, atHome, description, quartier, position;

    public FuitesModel() {
    }

    public FuitesModel(String position, String atHome, String description,
                      String nomPrenom, String phone, String quartier){
        this.position = position;
        this.atHome = atHome;
        this.description = description;
        this.nomPrenom = nomPrenom;
        this.phone = phone;
        this.quartier = quartier;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAtHome() {
        return atHome;
    }

    public String getDescription() {
        return description;
    }

    public String getQuartier() {
        return quartier;
    }

    public String getPosition() {
        return position;
    }

}
