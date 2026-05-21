import java.util.Scanner;

public class ArenaPetarungan {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Musuh[] gelombang_monster = new Musuh[3];
        gelombang_monster[0] = new Slime();
        gelombang_monster[1] = new Naga();
        gelombang_monster[2] = new Zombie();

        System.out.println("=======================================");
        System.out.println(" ARENA RPG: GELOMBANG MONSTER ");
        System.out.println("=======================================\n");
        System.out.println(" AWAS! Sekolompok monster menghadang Anda!");

        boolean is_Bermain = true;

        while (is_Bermain) {

            System.out.println("\n--- STATUS MONSTER ---");

            for (int i = 0; i < gelombang_monster.length; i++) {

                if (gelombang_monster[i].hp > 0) {

                    System.out.println((i + 1) + ". "
                            + gelombang_monster[i].namaMusuh
                            + " (HP: "
                            + gelombang_monster[i].hp + ")");
                } else {

                    System.out.println((i + 1) + ". "
                            + gelombang_monster[i].namaMusuh
                            + " [TEWAS]");
                }
            }

            System.out.println("4. Kabur dari petarungan");

            try {

                System.out.print("\nPilih target (1/2/3) atau 4 untuk kabur: ");
                int pilihan_target = input.nextInt();

                if (pilihan_target == 4) {
                    System.out.println("Anda lari dari arena...");
                    is_Bermain = false;
                    continue;
                }

                if (pilihan_target < 1 || pilihan_target > 3) {
                    System.out.println("Pilihan tidak valid!");
                    continue;
                }

                System.out.print("Masukkan kekuatan serangan (10 - 100): ");
                int powerr = input.nextInt();

                if (powerr < 10 || powerr > 100) {
                    throw new SeranganTidakValidException(
                            "Kekuatan harus 10 - 100!");
                }

                System.out.println("\n>>> HASIL SERANGAN <<<");

                int indeksMonster = pilihan_target - 1;

                gelombang_monster[indeksMonster].terimaDamage(powerr);

                if (gelombang_monster[indeksMonster].hp <= 0) {

                    System.out.println(gelombang_monster[indeksMonster].namaMusuh
                            + " berhasil dikalahkan!");

                    if (gelombang_monster[indeksMonster] instanceof BisaLoot) {
                        BisaLoot loot = (BisaLoot) gelombang_monster[indeksMonster];
                        loot.jatuhkanItem();
                    }
                }

            } catch (java.util.InputMismatchException e) {
                System.out.println("ERROR: Harus angka!");
                input.nextLine();

            } catch (SeranganTidakValidException e) {
                System.out.println("KESALAHAN GAME: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Error sistem: " + e.getMessage());
            }

            System.out.println("\n<<< GILIRAN MONSTER >>>");

            for (int i = 0; i < gelombang_monster.length; i++) {

                if (gelombang_monster[i].hp > 0) {

                    Musuh monsterAktif = gelombang_monster[i];

                    monsterAktif.suaraKhas();

                    if (monsterAktif instanceof BisaTerbang) {

                        System.out.println("[SERANGAN UDARA!]");

                        BisaTerbang terbang = (BisaTerbang) monsterAktif;
                        terbang.lepasLandas();
                        terbang.seranganUdara();

                    } else {
                        monsterAktif.serangPemain();
                    }
                }
            }

            boolean semuaMati = true;

            for (int i = 0; i < gelombang_monster.length; i++) {
                if (gelombang_monster[i].hp > 0) {
                    semuaMati = false;
                    break;
                }
            }

            if (semuaMati) {
                System.out.println("\nKAMU MENANG! Semua monster kalah!");
                is_Bermain = false;
            }
        }

        input.close();
        System.out.println("Permainan selesai.");
    }
}