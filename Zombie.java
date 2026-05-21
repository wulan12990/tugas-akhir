public class Zombie extends Musuh implements BisaLoot {
    public Zombie() { super("Zombie Merangkak", 150); }

    @Override
    public void serangPemain() {
    System.out.println(this.namaMusuh + " mencakar pemain! Player -20 HP");
    }

    @Override
    public void suaraKhas() {
    System.out.println("Suara: *Groaaarrr... *");
    }

    @Override
    public void jatuhkanItem() {
    System.out.println("Loot Drop: " + this.namaMusuh + " menjatuhkan menjatuhkan 1x Daging Busuk!");
    }
}