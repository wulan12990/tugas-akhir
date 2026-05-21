import java.io.Serializable;
public abstract class Musuh implements Serializable {

    protected String namaMusuh;
    protected int hp;

    // Constructor
    public Musuh(String namaMusuh, int hp) {
        this.namaMusuh = namaMusuh;
        this.hp = hp;
    }

    // Abstract method
    public abstract void serangPemain();

    public abstract void suaraKhas();

    // Method biasa
    public void tampilkanInfo() {
        System.out.println("Nama Musuh : " + namaMusuh);
        System.out.println("HP Musuh   : " + hp);
    }

    // Method menerima damage
    public void terimaDamage(int damage) {

        hp -= damage;

        if (hp < 0) {
            hp = 0;
        }

        System.out.println(namaMusuh
                + " menerima damage sebesar " + damage);

        System.out.println("Sisa HP: " + hp);
    }
}