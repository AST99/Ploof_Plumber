package ci.astdev.onep_users

class PlombierModel {
    lateinit var nomPrenom: String
    lateinit var mail: String
    lateinit var passWrd: String
    lateinit var phone: String

    constructor() {}
    constructor(nomPrenom: String, mail: String, passWrd: String, phone: String) {
        this.nomPrenom = nomPrenom
        this.mail = mail
        this.passWrd = passWrd
        this.phone = phone
    }
}