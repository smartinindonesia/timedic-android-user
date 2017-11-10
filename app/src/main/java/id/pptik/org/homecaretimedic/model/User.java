package id.pptik.org.homecaretimedic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hafid on 9/8/2017.
 */

public class User {
    public static String TAG = "[User]";

    @SerializedName("id")
    private int id;
    @SerializedName("kode")
    private String kode;
    @SerializedName("name")
    private String nama;
    @SerializedName("username")
    private String usernama;
    @SerializedName("tgl_lahir")
    private String tgl_lahir;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("email")
    private String email;
    @SerializedName("id_pegawai")
    private int id_pegawai;
    @SerializedName("foto_addrs")
    private String foto_addrs;
    @SerializedName("tgl_daftar")
    private String tgl_daftar;
    @SerializedName("jam_daftar")
    private String jam_daftar;
    @SerializedName("kodeuser")
    private String kodeuser;
    @SerializedName("session")
    private String session;//unused field untuk sekarang

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsernama() {
        return usernama;
    }

    public void setUsernama(String usernama) {
        this.usernama = usernama;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(int id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getFoto_addrs() {
        return foto_addrs;
    }

    public void setFoto_addrs(String foto_addrs) {
        this.foto_addrs = foto_addrs;
    }

    public String getTgl_daftar() {
        return tgl_daftar;
    }

    public void setTgl_daftar(String tgl_daftar) {
        this.tgl_daftar = tgl_daftar;
    }

    public String getJam_daftar() {
        return jam_daftar;
    }

    public void setJam_daftar(String jam_daftar) {
        this.jam_daftar = jam_daftar;
    }

    public String getKodeuser() {
        return kodeuser;
    }

    public void setKodeuser(String kodeuser) {
        this.kodeuser = kodeuser;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
