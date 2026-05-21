public class Naga extends Musuh implements BisaTerbang, BisaLoot {

    // Constructor
    public Naga() {
        super("Naga Gemoy", 500);
    }

    // Override method serangan
    @Override
    public void serangPemain() {
        System.out.println(namaMusuh +
                " menyemburkan nafas gemoy! Player -50 HP");
    }

    // Override suara khas
    @Override
    public void suaraKhas() {
        System.out.println(namaMusuh +
                ": ROAARRR... SEMBURRR!");
    }

    // ===== Interface BisaTerbang =====
    @Override
    public void lepasLandas() {
        System.out.println(namaMusuh +
                " terbang tinggi! Sulit diserang.");
    }

    @Override
    public void seranganUdara() {
        System.out.println(namaMusuh +
                " menyemburkan badai api! Player -80 HP.");
    }

    // ===== Interface BisaLoot =====
    @Override
    public void jatuhkanItem() {
        System.out.println("Loot Drop: " +
                namaMusuh +
                " menjatuhkan Sisik Naga Api!");
    }
}
