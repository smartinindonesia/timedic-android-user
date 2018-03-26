package id.smartin.org.homecaretimedic.model;

/**
 * Created by Hafid on 9/23/2017.
 */

public class Caregiver {
    public static String TAG = "[Caregiver]";

    private Integer idPerawat;
    private String namaPerawat;
    private String tempatLahir;
    private String kategoriPerawat;
    private String agama;
    private String alamat;
    private String skill;
    private Klinik clinic;
    private String jenisKelamin;
    private String photoPath;
    private String pendidikan;
    private Double tinggiBadan;
    private Double beratBadan;

    public Integer getIdPerawat() {
        return idPerawat;
    }

    public void setIdPerawat(Integer idPerawat) {
        this.idPerawat = idPerawat;
    }

    public String getNamaPerawat() {
        return namaPerawat;
    }

    public void setNamaPerawat(String namaPerawat) {
        this.namaPerawat = namaPerawat;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getKategoriPerawat() {
        return kategoriPerawat;
    }

    public void setKategoriPerawat(String kategoriPerawat) {
        this.kategoriPerawat = kategoriPerawat;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Klinik getClinic() {
        return clinic;
    }

    public void setClinic(Klinik clinic) {
        this.clinic = clinic;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public Double getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(Double tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public Double getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(Double beratBadan) {
        this.beratBadan = beratBadan;
    }
}
