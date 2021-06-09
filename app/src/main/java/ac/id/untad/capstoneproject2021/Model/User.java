package ac.id.untad.capstoneproject2021.Model;

public class User {
    private String id;
    private String nik;
    private String nama;
    private String alamat;
    private String pekerjaan;
    private String email;
    private String jenis_kelamin;
    private String label;
    private String role;
    private String tanggal_lahir;
    private String no_hp;


    public User() {
    }


    public User(String id, String nik, String nama, String alamat, String pekerjaan, String email, String jenis_kelamin, String label, String role, String tanggal_lahir, String no_hp) {
        this.id = id;
        this.nik = nik;
        this.nama = nama;
        this.alamat = alamat;
        this.pekerjaan = pekerjaan;
        this.email = email;
        this.jenis_kelamin = jenis_kelamin;
        this.label = label;
        this.role = role;
        this.tanggal_lahir = tanggal_lahir;
        this.no_hp = no_hp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }
}
