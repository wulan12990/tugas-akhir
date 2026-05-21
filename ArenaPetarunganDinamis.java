import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class ArenaPetarunganDinamis {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ArrayList<Musuh> gelombangMonster = new ArrayList<>();

        gelombangMonster.add(new Slime());
        gelombangMonster.add(new Naga());
        gelombangMonster.add(new Slime());
        gelombangMonster.add(new Zombie());

        System.out.println("=======================================");
        System.out.println(" ARENA RPG: GELOMBANG MONSTER ");
        System.out.println("=======================================");
        System.out.println("AWAS! Sekolompok monster menghadang Anda!");

        boolean isBermain = true;

        while (isBermain && !gelombangMonster.isEmpty()) {

            System.out.println("\n--- STATUS MONSTER ---");

            for (int i = 0; i < gelombangMonster.size(); i++) {
                Musuh m = gelombangMonster.get(i);
                System.out.println((i + 1) + ". " + m.namaMusuh + " (HP: " + m.hp + ")");
            }

            System.out.println("----------------------");
            System.out.println("8. [SAVE GAME] Simpan progres pertarungan");
            System.out.println("9. [LOAD GAME] Muat progres pertarungan");
            System.out.println("0. Kabur dari petarungan");

            System.out.print("\nPilih target monster atau aksi lainnya: ");

            try {

                int pilihanTarget = input.nextInt();

                // Keluar game
                if (pilihanTarget == 0) {
                    System.out.println("Anda lari dari arena...");
                    isBermain = false;
                    continue;
                }

                // SAVE GAME
                else if (pilihanTarget == 8) {

                    try (ObjectOutputStream oos =
                                 new ObjectOutputStream(
                                         new FileOutputStream("savegame_rpg.dat"))) {

                        oos.writeObject(gelombangMonster);

                        System.out.println(
                                ">>> BERHASIL: Game telah disimpan! <<<");

                    } catch (IOException e) {

                        System.out.println(
                                ">>> GAGAL: Terjadi kesalahan saat menyimpan game. "
                                        + e.getMessage());
                    }

                    continue;
                }

                // LOAD GAME
                else if (pilihanTarget == 9) {

                    try (ObjectInputStream ois =
                                 new ObjectInputStream(
                                         new FileInputStream("savegame_rpg.dat"))) {

                        gelombangMonster =
                                (ArrayList<Musuh>) ois.readObject();

                        System.out.println(
                                ">>> BERHASIL: Game berhasil dimuat! <<<");

                    } catch (FileNotFoundException e) {

                        System.out.println(
                                ">>> GAGAL: File save game belum ada! <<<");

                    } catch (IOException | ClassNotFoundException e) {

                        System.out.println(
                                ">>> GAGAL: Terjadi kesalahan saat load game. "
                                        + e.getMessage());
                    }

                    continue;
                }

                // Validasi target monster
                if (pilihanTarget < 1 ||
                        pilihanTarget > gelombangMonster.size()) {

                    System.out.println("Pilihan tidak valid!");
                    continue;
                }

                System.out.print("Masukkan kekuatan serangan (10 - 100): ");
                int power = input.nextInt();

                if (power < 10 || power > 100) {

                    throw new SeranganTidakValidException(
                            "Kekuatan harus 10 - 500!");
                }

                System.out.println("\n>>> HASIL SERANGAN <<<");

                int indeksMonster = pilihanTarget - 1;

                Musuh target =
                        gelombangMonster.get(indeksMonster);

                target.terimaDamage(power);

                if (target.hp <= 0) {

                    System.out.println(
                            target.namaMusuh +
                                    " hancur menjadi debu!");

                    if (target instanceof BisaLoot) {

                        BisaLoot loot = (BisaLoot) target;
                        loot.jatuhkanItem();
                    }

                    gelombangMonster.remove(indeksMonster);
                }

            } catch (Exception e) {

                System.out.println(
                        "Terjadi kesalahan input, silakan coba lagi.");

                input.nextLine();
                continue;
            }

            if (gelombangMonster.isEmpty()) {

                System.out.println(
                        "\nSELAMAT! Semua monster telah dibersihkan!");
                break;
            }

            System.out.println("\n<<< GILIRAN MONSTER >>>");

            for (int i = 0; i < gelombangMonster.size(); i++) {

                Musuh monsterAktif =
                        gelombangMonster.get(i);

                if (monsterAktif.hp > 0) {

                    monsterAktif.suaraKhas();

                    if (monsterAktif instanceof BisaTerbang) {

                        System.out.println("[SERANGAN UDARA!]");

                        BisaTerbang terbang =
                                (BisaTerbang) monsterAktif;

                        terbang.lepasLandas();
                        terbang.seranganUdara();

                    } else {

                        monsterAktif.serangPemain();
                    }
                }
            }
        }

        input.close();
        System.out.println("Permainan selesai.");
    }
}